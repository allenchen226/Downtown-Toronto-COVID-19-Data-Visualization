package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * MiddleAgeCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are considered middle aged.
 */
public class MiddleAgeCriteria implements  Criteria{

    /**
     * Method for filtering out cases with younger persons.
     * @return ArrayList of IndividualPoints where it's age is  30 < age <= 60.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            String ages = i.getAge();
            //String[] ages = i.getAge().split(" ");
            if (ages.contains("19 and younger") || ages.contains("20 to 29")){
                L.add(i);
            }
        }
        return L;
    }
}