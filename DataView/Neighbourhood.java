package DataView;

import javafx.scene.paint.Color;
import java.util.*;

/**
 * Neighbourhood class implements the DataPoint interface. An instance of the
 * <Neighbourhood> class represents the cases found within a particular postal code.
 */
public class Neighbourhood implements DataPoint {
    /**
     * Postal code indicating the geographical area represented by this object.
     */
    public String idPostalCode;
    /**
     * Number of cases represented by this object.
     */
    private int caseCount;
    /**
     * Whether this DataPoint is an instance of <Neighbourhood>
     */
    private final boolean isNeighbourhood;
    /**
     * All of the singular cases (<IndividualPoint> objects) AND / OR neighbourhoods
     * (<Neighbourhood> objects) represented by this neighbourhood.
     * Aggregation in the Composite Pattern.
     */
    private ArrayList<DataPoint> children;

    /**
     * Class constructor. Given a postal code and list of cases, initializes
     * an instance of this class.
     * @param idPostalCode the postal code of the geographical area represented.
     * @param children the cases (<IndividualPoint> objects) and / or neighbourhoods
     *                 (<Neighbourhood> objects) represented by this instance
     *                 of the <Neighbourhood> class.
     */
    public Neighbourhood(String idPostalCode, ArrayList<DataPoint> children) {
        this.idPostalCode = idPostalCode;
        this.isNeighbourhood = true;
        this.children = children;
        this.caseCount = computeCaseCount(children);
    }

    /**
     * Sums the total number of cases represented by ALL of the children of
     * this <Neighbourhood>. Implements the Composite Pattern.
     * @param children contains all of the children of this <Neighbourhood>.
     * @return the total number of cases represented by this <Neighbourhood>.
     */
    private int computeCaseCount(ArrayList<DataPoint> children) {
        int sum = 0;
        for (DataPoint d: children) {
            sum += d.getCaseCount();
        }
        return sum;
    }

    /**
     * Getter method.
     * @return whether this DataPoint is an instance of <Neighbourhood>
     */
    @Override
    public boolean getIsNeighbourhood() {
        return this.isNeighbourhood;
    }

    /**
     * Getter method
     * @return the number of cases represented by this <Neighbourhood> object.
     */
    @Override
    public int getCaseCount() {
        return this.caseCount;
    }

    /**
     * Getter method.
     * @return a list of all of the children (<DataPoint> objects) represented
     * by this <Neighbourhood> object.
     */
    public ArrayList<DataPoint> getChildren() {
        return children;
    }

    /**
     * Add a child (<DataPoint>) to the list of cases (<DataPoint> objects) represented
     * by this <Neighbourhood> object. Update the case count.
     * @param child the <DataPoint> object to be added.
     */
    public void addChild(DataPoint child) {
        this.children.add(child);
        this.caseCount += child.getCaseCount();
    }

    /**
     * Remove a child (<DataPoint>) the list of cases represented by this <Neighbourhood> object.
     * @param child the <DataPoint> object to be removed.
     */
    public void removeChild(DataPoint child) {
        this.children.remove(child);
        this.caseCount -= child.getCaseCount();
    }

    /**
     * Return an integer array corresponding to the RGB colour of the region
     * represented by this neighbourhood on the GUI. The colour varies based
     * on the number of cases in this neighbourhood.
     *
     * PRELIMINARY METHOD -- SUBJECT TO CHANGE based on GUI implementation.
     * @return integer array of the RGB colour of the postal code represented
     * by this neighbourhood on the GUI.
     *
     * @throws Exception since it has not yet been implemented.
     */
    public Color getMapColour() {
        if (caseCount <= 100) {
            return Color.rgb(27, 27, 180, 0.70);
        } else if (caseCount <= 500) {
            return Color.rgb(65,27,160, 0.70);
        } else if (caseCount <= 1000) {
            return Color.rgb(133, 35, 213, 0.70);
        } else if (caseCount <= 1700) {
            return Color.rgb(210, 36, 169, 0.72);
        } else if (caseCount <= 2400) {
            return Color.rgb(210, 36, 135, 0.75);
        } else if (caseCount <= 3000) {
            return Color.rgb(211, 30, 52, 0.78);
        } else {
            return Color.rgb(181, 14, 14, 0.82);
        }
    }
}
