package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;/**
 * TravelCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * where there the source is travel.
 */
public class TravelCriteria implements Criteria{

    /**
     * Method for filtering out cases where the source is travel.
     * @return ArrayList of IndividualPoints where the cases' source is travel.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.getSourceInfection().contains("Travel")){
                L.add(i);
            }
        }
        return L;
    }
}