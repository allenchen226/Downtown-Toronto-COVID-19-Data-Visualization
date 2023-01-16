package CovidCarriers.observer;

import DataView.DataPoint;
import DataView.IndividualPoint;
import DataView.Neighbourhood;

import java.util.ArrayList;
import java.util.HashMap;


public interface PostalCodesObserver {
    /**
     * Receive notification from observable and update
     */
    void update(HashMap<String, Integer> observerState);

}
