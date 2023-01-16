package Filter;

import DataView.DataPoint;
import DataView.DataReader;
import DataView.Neighbourhood;
import DataView.IndividualPoint;

import java.util.*;

/**
 * Interface with methods for each type of filter.
 */
public interface Criteria {
    /**
     * Returns an ArrayList that is filtered by a criteria.
     *
     * @return ArrayList of IndividualPoints
     */

    public ArrayList<IndividualPoint> meetCriteria(ArrayList<IndividualPoint> dataPoints);
}