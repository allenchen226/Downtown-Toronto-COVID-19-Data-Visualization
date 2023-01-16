package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * HospitalizedCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are not hospitalized.
 */
public class NotHospitalizedCriteria implements Criteria{

    /**
     * Method for filtering out cases with not hospitalized persons.
     * @return ArrayList of IndividualPoints where it's hospitalized status is false.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (!(i.isHospitalized())){
                L.add(i);
            }
        }
        return L;
    }
}