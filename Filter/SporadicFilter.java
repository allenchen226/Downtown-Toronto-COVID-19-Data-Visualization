package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * SporadicCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are sporadic.
 */
public class SporadicFilter implements Criteria{

    /**
     * Method for filtering out cases that are sporadic.
     * @return ArrayList of IndividualPoints where the cases are sporadic.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (!i.isOutbreakAssociated()){
                L.add(i);
            }
        }
        return L;
    }
}