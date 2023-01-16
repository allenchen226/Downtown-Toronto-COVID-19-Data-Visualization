package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;
import java.util.Objects;

/**
 * FemaleCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are female.
 */
public class FemaleCriteria implements  Criteria{

    /**
     * Method for filtering out cases with females
     * @return ArrayList of IndividualPoints where it's gender is female.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (Objects.equals(i.getGender(), "FEMALE")){
                L.add(i);
            }
        }
        return L;
    }
}