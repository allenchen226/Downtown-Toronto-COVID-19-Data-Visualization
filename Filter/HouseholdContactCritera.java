package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;
/**
 * HouseHoldContactCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * where the source is household contact.
 */

public class HouseholdContactCritera implements Criteria{
    /**
     * Method for filtering out cases that are from household contact.
     * @return ArrayList of IndividualPoints where the cases' source is household contact.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.getSourceInfection().contains("Household Contact")){
                L.add(i);
            }
        }
        return L;
    }
}