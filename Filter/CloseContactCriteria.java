package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;
/**
 * CloseContactCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * where the source is close contact.
 */
public class CloseContactCriteria implements Criteria{

    /**
     * Method for filtering out cases that are close contact.
     * @return ArrayList of IndividualPoints where the cases are close contact.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.getSourceInfection().contains("Close Contact")){
                L.add(i);
            }
        }
        return L;
    }
}