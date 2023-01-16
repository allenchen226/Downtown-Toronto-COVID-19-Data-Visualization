package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * OldCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are considered old.
 */
public class OldCriteria implements  Criteria{

    /**
     * Method for filtering out cases with older persons.
     * @return ArrayList of IndividualPoints where it's age is  60 < age.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            String ages = i.getAge();
            if (ages.contains("60 to 69") || ages.contains("70 to 79") || ages.contains("90 and older")){
                L.add(i);
            }
        }
        return L;
    }
}