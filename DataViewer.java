import Memento.CareTaker;
import Memento.FilterStatus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import DataView.*; //import visualization helpers

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
public class DataViewer extends Application {
    // private Stack<Object> undoStack; //undo stack, to facilitate drawing/erasing of graphics objects
    private final ArrayList<IndividualPoint> cases; //list of cases in the FinalCaseData.csv.
    private AnchorPane anchorRoot; //root of the scene graph

    //UI elements on COVID-19 cases view we will want other methods to access
    private ChoiceBox<Object> SelectPostalCodes; //the list of postal codes options

    private ChoiceBox<Object> SelectCriteria; //the list of criteria options
    private TextField txtSummary; //a summary of the cases on the map
    private HBox controls; // all of the filter controls and text summary
    private HBox contrastControls; // all of the contrast controls
    private String filepath; // path to the map background image
    private Paint circleStroke; // default colour of circle border on GUI
    private double circleBorder; // default border weight of circles on the GUi

    private final int h = 613; //dimensions of the map for display
    private final int w = 855;

    public final int original_h = 2452;

    public final int original_w = 3417;

    private  ArrayList<Neighbourhood> areas; // list of postal codes

    private ImageView iv;

    private FilterStatus CurrentStatus; // current status of the filters applied

    private CareTaker careTaker; // care taker object for the memento

    /**
     * Make a DataViewer, to view cases in DT Toronto.
     */
    public DataViewer() {

        //get the COVID-19 cases list from the file
        try {
            String filename = "DataView/FinalCaseData.csv"; //change this accordingly
            cases = DataReader.initializeData(filename);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        //get the postal codes
        this.areas = DataReader.sortByNeighbourhood(cases);

        List<String> sortedList = SortByPostalCodesName(areas);

        List<String> criteriaList = Arrays.asList("ALL CASES", "Infected by close contact", "Infected by community",
                "Infected by household contact", "Infected during travel", "Male", "Female", "Hospitalized",
                "Non-hospitalized", "Sporadic", "Outbreak-associated", "Age: young", "Age: middle", "Age: old");

        sortedList.add(0, "ALL CASES");

        //create the UI element to hold the tree types
        this.SelectPostalCodes = new ChoiceBox<>(FXCollections.observableArrayList(sortedList));

        this.SelectCriteria = new ChoiceBox<>(FXCollections.observableArrayList(criteriaList));

        this.CurrentStatus = new FilterStatus();

        this.careTaker = new CareTaker();

        this.filepath = "maps.png";
        this.circleBorder = 1.0;
        this.circleStroke = null;

    }
    /**
     * Sort postal codes alphabetically, used for choice box.
     */
    private ArrayList<String> SortByPostalCodesName(ArrayList<Neighbourhood> allData) {
        ArrayList<String> lst = new ArrayList<>();
        for (Neighbourhood n: allData) {
            lst.add(n.idPostalCode);
        }
        Collections.sort(lst);
        return lst;
    }

    /**
     * According to the case count in each postal code, adjust the circle size regarding case counts.
     */
    public ArrayList<Integer> CaseCountToRadius() {
        ArrayList<Integer> lst=  new ArrayList<>();
        for (Neighbourhood n: areas) {
            lst.add((int) Math.round(1.5 * Math.pow(n.getCaseCount(), (double) 5/12)));
        }
        return lst;

    }
    /** Main method
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** Start the visualization
     */
    public void start(Stage primaryStage) {

        double[] postal_code_height= {0.625, 0.6625, 0.7125, 0.6875, 0.4375, 0.59375, 0.125, 0.375, 0.1875, 0.65, 0.375,
                0.625, 0.5, 0.7125, 0.5625, 0.4375};
        double[] postal_code_width = {0.6333, 0.6667, 0.6833, 0.6667, 0.57, 0.5667, 0.6, 0.767, 0.7, 0.62, 0.7, 0.7167,
                0.7, 0.7467, 0.6333, 0.4333};

        //load the map to visualize
        FileInputStream input = null;
        try {
            input = new FileInputStream(this.filepath); //change this accordingly
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert input != null;
        Image image = new Image(input, original_w, original_h, false, false);
        this.iv = new ImageView(image); //place the map on the UI
        this.iv.setFitHeight(h);
        this.iv.setFitWidth(w);
        anchorRoot = new AnchorPane();
        anchorRoot.getChildren().add(this.iv); //attach the map to the scene graph

        ImageView setCenter = (ImageView) anchorRoot.getChildren().get(0);
        setCenter.setX(23);
        setCenter.setY(0);

        ArrayList<Integer> lst = CaseCountToRadius();

        // for loop each postal code to draw circles on the map. The circle's size relates to the total cases
        // in this postal code. The more cases in a postal code, the larger the circle would be.
        for (int i = 0; i < areas.size(); i++) {
            Circle circle = new Circle();
            circle.setCenterX(w * postal_code_width[i] + 23);
            circle.setCenterY(h * postal_code_height[i] + 23);
            circle.setRadius(lst.get(i));
            circle.setFill(areas.get(i).getMapColour());
            anchorRoot.getChildren().add(circle);

        }
            //add button to UI
        Button filterByPostalCode = new Button("Filter by Postal Code");
        filterByPostalCode.setId("filterPostalCode");

        Button filterByCriteria = new Button("Filter by Criteria");
        filterByCriteria.setId("filterCriteria");

        Button revert = new Button("Undo");
        revert.setId("revert");

        Button highContrast = new Button("High Contrast");
        highContrast.setId("High Contrast");

        Button noContrast = new Button("No Contrast");
        noContrast.setId("No Contrast");

            //add text field to UI
        txtSummary = new TextField();
        txtSummary.setId("txtSummary");
//        txtSummary.setText("Cases selected: " );

//            add event handler to UI
        DataViewerHandler dataHandler = new DataViewerHandler(this);
        filterByPostalCode.addEventHandler(MouseEvent.MOUSE_CLICKED, dataHandler);

        CriteriaHandler criteriaHandler = new CriteriaHandler(this);
        filterByCriteria.addEventHandler(MouseEvent.MOUSE_CLICKED, criteriaHandler);

        UndoHandler undoHandler = new UndoHandler(this);
        revert.addEventHandler(MouseEvent.MOUSE_CLICKED, undoHandler);

        HighContrastHandler highContrastHandler = new HighContrastHandler(this);
        highContrast.addEventHandler(MouseEvent.MOUSE_CLICKED, highContrastHandler);

        NoContrastHandler noContrastHandler = new NoContrastHandler(this);
        noContrast.addEventHandler(MouseEvent.MOUSE_CLICKED, noContrastHandler);

            //set UI elements within a horizontal box
        controls = new HBox(this.SelectPostalCodes, filterByPostalCode, txtSummary, this.SelectCriteria, filterByCriteria, revert);
        HBox.setHgrow(txtSummary, Priority.ALWAYS);
        controls.setPadding(new Insets(10));
        controls.setSpacing(10);
        controls.setAlignment(Pos.TOP_CENTER);

        contrastControls = new HBox(noContrast, highContrast);
        contrastControls.setPadding(new Insets(10));
        contrastControls.setSpacing(10);
        contrastControls.setAlignment(Pos.BOTTOM_CENTER);

            //set horizontal box within a vertical box and attach to the scene graph
        VBox vbox = new VBox(controls, anchorRoot, contrastControls);
        vbox.setAlignment(Pos.CENTER);

            //attach all scene graph elements to the scene
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene); //set the scene ...
        primaryStage.setMinHeight(740);
        primaryStage.setMaxHeight(1200);
        primaryStage.setMinWidth(900);
        primaryStage.setMaxHeight(1000);
        primaryStage.centerOnScreen();
        primaryStage.show(); //... and ... go.

        }

    /** Get height of display
     *
     * @return height of display in px
     */
    public int getHeight() {
        return h;
    }

    /** Get width of display
     *
     * @return width of display in px
     */
    public int getWidth() {
        return w;
    }

    /** Get Anchor Root, used for event handling
     *
     * @return anchorRoot
     */
    public AnchorPane getAnchorRoot() {
        return anchorRoot;
    }

    /**
     * Get PostalCodesSelect, useful for event handling
     *
     * @return ChoiceBox
     */
    public ChoiceBox getPostalCodesSelect() {
        return this.SelectPostalCodes;
    }

    /**
     * Get CriteriaSelect, useful for event handling
     *
     * @return ChoiceBox
     */
    public ChoiceBox getCriteriaSelect() {
        return this.SelectCriteria;
    }

    /** Get getTxtSummary, useful for event handling
     *
     * @return TextField
     */
    public TextField getTxtSummary() {
        return this.txtSummary;
    }

    /** Get Individual Point List, useful for event handling
     *
     * @return cases
     */
    public ArrayList<IndividualPoint> getCases() { return this.cases; }

    /** Get current status, useful for memento
     *
     * @return current status
     */
    public FilterStatus getCurrentStatus() { return this.CurrentStatus; }

    /** Get care taker, useful for memento
     *
     * @return care taker
     */
    public CareTaker getCareTaker() { return this.careTaker; }

    /** Get postal codes List, useful for event handling
     *
     * @return postal codes
     */
    public ArrayList<Neighbourhood> getAreas() { return this.areas; }

    /** Get image view (the map background)
     *
     * @return image view
     */
    public ImageView getImageView() { return this.iv; }

    public HBox getControls() {
        return controls;
    }

    public HBox getContrastControls() {
        return contrastControls;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Paint getCircleStroke() {
        return circleStroke;
    }

    public void setCircleStroke(Paint circleStroke) {
        this.circleStroke = circleStroke;
    }

    public double getCircleBorder() {
        return circleBorder;
    }

    public void setCircleBorder(double circleBorder) {
        this.circleBorder = circleBorder;
    }
}
