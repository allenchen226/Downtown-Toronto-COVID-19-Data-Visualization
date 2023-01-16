package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * YoungCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are considered young.
 */
public class YoungCriteria implements Criteria{

    /**
     * Method for filtering out cases with younger persons.
     * @return ArrayList of IndividualPoints where it's age is 0 <= age < 30.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            String ages = i.getAge();
            if (ages.contains("30 to 39") || ages.contains("40 to 49") || ages.contains("50 to 59")){
                L.add(i);
            }
        }
        return L;
    }
}