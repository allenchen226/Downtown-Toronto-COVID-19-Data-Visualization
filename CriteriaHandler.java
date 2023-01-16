import DataView.DataPoint;
import DataView.DataReader;
import DataView.Neighbourhood;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import DataView.IndividualPoint;
import Filter.*;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CriteriaHandler implements EventHandler<MouseEvent> {
    private ChoiceBox criteriaSelect; //select box ref
    private TextField txtSummary; //summary of tree counts
    private DataViewer dataView; //the tree view

    /**
     * Constructor
     *
     * @param view reference to TreeView
     */
    public CriteriaHandler(DataViewer view) {
        this.dataView = view;
        this.criteriaSelect = view.getCriteriaSelect();
        this.txtSummary = view.getTxtSummary();
        this.txtSummary.setEditable(false);
    }

    /**
     * According to the case count in each postal code, adjust the circle size regarding case counts.
     */
    private Pair<ArrayList<Integer>, Integer> FilteredCaseCountToRadius(String filterType) {
        ArrayList<Integer> lst=  new ArrayList<>();
        int caseCount = 0;
        for (Neighbourhood n: this.dataView.getAreas()) {

            ArrayList<IndividualPoint> ipl = new ArrayList<>();
            for (DataPoint dp: n.getChildren()) {
                if (dp instanceof IndividualPoint) {
                    ipl.add((IndividualPoint) dp);
                }
            }
            switch (filterType) {
                case "Male" -> ipl = FilterIndividualPoint.filterMale(ipl);
                case "Female" -> ipl = FilterIndividualPoint.filterFemale(ipl);
                case "Infected by close contact" -> ipl = FilterIndividualPoint.filterCloseContact(ipl);
                case "Infected by community" -> ipl = FilterIndividualPoint.filterCommunity(ipl);
                case "Infected by household contact" -> ipl = FilterIndividualPoint.filterHouseHold(ipl);
                case "Infected during travel" -> ipl = FilterIndividualPoint.filterTravel(ipl);
                case "Hospitalized" -> ipl = FilterIndividualPoint.filterHospitalized(ipl);
                case "Non-hospitalized" -> ipl = FilterIndividualPoint.filterNotHospitalized(ipl);
                case "Sporadic" -> ipl = FilterIndividualPoint.filterSporadic(ipl);
                case "Outbreak-associated" -> ipl = FilterIndividualPoint.filterOutbreak(ipl);
                case "Age: young" -> ipl = FilterIndividualPoint.filterYoung(ipl);
                case "Age: middle" -> ipl = FilterIndividualPoint.filterMiddleAge(ipl);
                case "Age: old" -> ipl = FilterIndividualPoint.filterOld(ipl);
            }

            caseCount += ipl.size();
            lst.add((int) Math.round(1.5 * Math.pow(ipl.size(), (double) 5/12)));
        }
        return new Pair<>(lst, caseCount);

    }

    /**
     * Handle a mouse event (i.e. a button click)!  This routine will need to:
     * 1. Clear (or UNDO) all the circles from the existing view
     * 2. Cycle thru the list of trees and redraw circles that correspond to the tree type selected
     * 3. Remember to register any circles you draw on the "undo" stack, so they can be undone later!
     * 4. Remember also that if the user selects "ALL TREES", all the trees should be drawn
     *
     * @param event The mouse event
     */
    @Override
    public void handle(MouseEvent event) {
        if (Objects.equals(this.dataView.getCriteriaSelect().getValue(), "POSTAL CODES FILTERING") ||
                !(this.dataView.getCriteriaSelect().getValue() instanceof String)) {
            return;
        }
        // step 1
        this.dataView.getAnchorRoot().getChildren().clear();
        FileInputStream input = null;
        try {
            input = new FileInputStream(dataView.getFilepath()); //change this accordingly
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert input != null;
        Image image = new Image(input, dataView.original_w, dataView.original_h, false, false);
        ImageView iv = new ImageView(image); //place the map on the UI
        iv.setFitHeight(dataView.getHeight());
        iv.setFitWidth(dataView.getWidth());
        dataView.getAnchorRoot().getChildren().add(iv);

        ImageView setCenter = (ImageView) this.dataView.getAnchorRoot().getChildren().get(0);
        setCenter.setX(23);
        setCenter.setY(0);


        double[] postal_code_height= {0.625, 0.6625, 0.7125, 0.6875, 0.4375, 0.59375, 0.125, 0.375, 0.1875, 0.65, 0.375,
                0.625, 0.5, 0.7125, 0.5625, 0.4375};
        double[] postal_code_width = {0.6333, 0.6667, 0.6833, 0.6667, 0.57, 0.5667, 0.6, 0.767, 0.7, 0.62, 0.7, 0.7167,
                0.7, 0.7467, 0.6333, 0.4333};

        for (int i = 0; i < this.dataView.getAreas().size(); i++) {
            Circle circle = new Circle();
            circle.setCenterX(this.dataView.getWidth() * postal_code_width[i] + 23);
            circle.setCenterY(this.dataView.getHeight() * postal_code_height[i] + 23);
            if (this.criteriaSelect.getValue() == "ALL CASES") {
                this.dataView.getPostalCodesSelect().setValue("ALL CASES");
                circle.setRadius(this.dataView.CaseCountToRadius().get(i));
                circle.setFill(this.dataView.getAreas().get(i).getMapColour());
                circle.setStrokeWidth(this.dataView.getCircleBorder());
                circle.setStroke(this.dataView.getCircleStroke());
                dataView.getAnchorRoot().getChildren().add(circle);
                this.txtSummary.setText("Cases selected: " + 22000);
            }
            else {
                this.dataView.getPostalCodesSelect().setValue("CRITERIA FILTERING");
                Pair<ArrayList<Integer>, Integer> radius = FilteredCaseCountToRadius((String) this.criteriaSelect.getValue());
                circle.setRadius(radius.getKey().get(i));
                circle.setFill(this.dataView.getAreas().get(i).getMapColour());
                circle.setStrokeWidth(this.dataView.getCircleBorder());
                circle.setStroke(this.dataView.getCircleStroke());
                dataView.getAnchorRoot().getChildren().add(circle);
                this.txtSummary.setText("Cases selected: " + radius.getValue());
            }

        }
        this.dataView.getCurrentStatus().getFilterApplied().add((String) this.dataView.getCriteriaSelect().getValue());
        this.dataView.getCareTaker().add(this.dataView.getCurrentStatus().createMemento());
    }
}
