package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;
import java.util.Objects;

/**
 * MaleCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are male.
 */
public class MaleCriteria implements  Criteria{

    /**
     * Method for filtering out cases with males
     * @return ArrayList of IndividualPoints where it's gender is male.
     */
    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (Objects.equals(i.getGender(), "MALE")){
                L.add(i);
            }
        }
        return L;
    }
}