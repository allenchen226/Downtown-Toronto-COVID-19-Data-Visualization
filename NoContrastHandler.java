import DataView.DataPoint;
import DataView.DataReader;
import DataView.Neighbourhood;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class NoContrastHandler implements EventHandler<MouseEvent> {
    private ChoiceBox postalCodeSelect; //select box ref
    private TextField txtSummary; //summary of tree counts
    private DataViewer dataView; //the tree view

    /**
     * Constructor
     *
     * @param view reference to TreeView
     */
    public NoContrastHandler(DataViewer view) {
        this.dataView = view;
        this.postalCodeSelect = view.getPostalCodesSelect();
        this.txtSummary = view.getTxtSummary();
        this.txtSummary.setEditable(false);
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
        // clear the map
        this.dataView.getAnchorRoot().getChildren().clear();

        // change the background map
        dataView.setFilepath("maps.png");

        // undo the high contrast border colour on each circle
        dataView.setCircleStroke(null);
        dataView.setCircleBorder(1);

        // add the new map as the background
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
        dataView.getAnchorRoot().getChildren().add(iv); //attach the map to the scene graph
        ImageView setCenter = (ImageView) this.dataView.getAnchorRoot().getChildren().get(0);
        setCenter.setX(23);
        setCenter.setY(0);

        HBox controls = dataView.getControls();
        HBox contrastControls = dataView.getContrastControls();

        controls.setStyle("");
        contrastControls.setStyle("");

        for (Node n: controls.getChildren()) {
            n.setStyle("");
        }
        for (Node n: contrastControls.getChildren()) {
            n.setStyle("");
        }
    }
}
