package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;

/**
 * HospitalizedCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * that are hospitalized.
 */
public class HospitalizedCriteria implements  Criteria{

    /**
     * Method for filtering out cases with hospitalized persons.
     * @return ArrayList of IndividualPoints where it's hospitalized status is true.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.isHospitalized()){
                L.add(i);
            }
        }
        return L;
    }
}