import CovidCarriers.observer.ObservablePostalCodes;
import CovidCarriers.observer.PostalCodesObserver;
import DataView.DataPoint;
import DataView.DataReader;
import CovidCarriers.*;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class DataViewerHandler implements EventHandler<MouseEvent> {
    private ChoiceBox postalCodeSelect; //select box ref
    private TextField txtSummary; //summary of tree counts
    private DataViewer dataView; //the tree view

    /**
     * Constructor
     *
     * @param view reference to TreeView
     */
    public DataViewerHandler(DataViewer view) {
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
        if (Objects.equals(this.postalCodeSelect.getValue(), "CRITERIA FILTERING") ||
                !(this.postalCodeSelect.getValue() instanceof String)) {
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
        Image image = new Image(input, 3417, 2452, false, false);
        ImageView iv = new ImageView(image); //place the map on the UI
        iv.setFitHeight(dataView.getHeight());
        iv.setFitWidth(dataView.getWidth());
        dataView.getAnchorRoot().getChildren().add(iv); //attach the map to the scene graph
        ImageView setCenter = (ImageView) this.dataView.getAnchorRoot().getChildren().get(0);
        setCenter.setX(23);
        setCenter.setY(0);

        //create undo stack for visualized objects

        double[] postal_code_height= {0.625, 0.6625, 0.7125, 0.6875, 0.4375, 0.59375, 0.125, 0.375, 0.1875, 0.65, 0.375,
                0.625, 0.5, 0.7125, 0.5625, 0.4375};
        double[] postal_code_width = {0.6333, 0.6667, 0.6833, 0.6667, 0.57, 0.5667, 0.6, 0.767, 0.7, 0.62, 0.7, 0.7167,
                0.7, 0.7467, 0.6333, 0.4333};

        //test for observer pattern
        PostalCodes point = new PostalCodes();
        Residents observer = new Residents();

        for (int i = 0; i < this.dataView.getAreas().size(); i++) {
            if (this.postalCodeSelect.getValue() == "ALL CASES") {
                this.dataView.getCriteriaSelect().setValue("ALL CASES");
                Circle circle = new Circle();
                circle.setCenterX(this.dataView.getWidth() * postal_code_width[i] + 23);
                circle.setCenterY(this.dataView.getHeight() * postal_code_height[i] + 23);
                circle.setRadius(this.dataView.CaseCountToRadius().get(i));
                circle.setFill(this.dataView.getAreas().get(i).getMapColour());
                circle.setStrokeWidth(this.dataView.getCircleBorder());
                circle.setStroke(this.dataView.getCircleStroke());
                dataView.getAnchorRoot().getChildren().add(circle);
                point.addPostalCode(this.dataView.getAreas().get(i));
                observer.update(point.getPostalCodesList());
                this.txtSummary.setText("Cases selected: " + 22000);
            }
            if (this.postalCodeSelect.getValue() == dataView.getAreas().get(i).idPostalCode) {
                this.dataView.getCriteriaSelect().setValue("POSTAL CODES FILTERING");
                Circle circle = new Circle();
                circle.setCenterX(this.dataView.getWidth() * postal_code_width[i] + 23);
                circle.setCenterY(this.dataView.getHeight() * postal_code_height[i] + 23);
                circle.setRadius(this.dataView.CaseCountToRadius().get(i));
                circle.setFill(this.dataView.getAreas().get(i).getMapColour());
                circle.setStrokeWidth(this.dataView.getCircleBorder());
                circle.setStroke(this.dataView.getCircleStroke());
                dataView.getAnchorRoot().getChildren().add(circle);
                point.addPostalCode(this.dataView.getAreas().get(i));
                observer.update(point.getPostalCodesList());
                this.txtSummary.setText("Cases selected: " + this.dataView.getAreas().get(i).getCaseCount());
            }
        }
        this.dataView.getCurrentStatus().getFilterApplied().add((String) this.postalCodeSelect.getValue());
        this.dataView.getCareTaker().add(this.dataView.getCurrentStatus().createMemento());
    }
}
