package Filter;

import DataView.IndividualPoint;

import java.util.ArrayList;
/**
 * CommunityCriteria class implements the Criteria interface.
 * This class filters out covid cases (individual points)
 * where the source is the community.
 */

public class CommunityCriteria implements Criteria{
    /**
     * Method for filtering out cases that are from community.
     * @return ArrayList of IndividualPoints where the cases' source is community.
     */

    @Override
    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints){
        ArrayList<IndividualPoint> L = new ArrayList<>();
        for (IndividualPoint i: dataPoints){
            if (i.getSourceInfection().contains("Community")){
                L.add(i);
            }
        }
        return L;
    }
}