package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * OutbreakCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are outbreaks.
 */
public class OutbreakCriteria implements Criteria{

    /**
     * Method for filtering out cases with outbreaks.
     * @return ArrayList of IndividualPoints where the cases are an outbreak.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.isOutbreakAssociated()){
                L.add(i);
            }
        }
        return L;
    }

}