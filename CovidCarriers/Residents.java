package CovidCarriers;

import CovidCarriers.observer.PostalCodesObserver;
import CovidCarriers.observer.PostalCodesObserver;
import DataView.DataPoint;
import DataView.DataReader;
import DataView.IndividualPoint;
import DataView.Neighbourhood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class Residents implements PostalCodesObserver {
    private HashMap<String, Integer> cases = new HashMap<>();

    /**
     * Receive notification from observable and update
     *
     * @param observerState Updated selected postal code and cases
     */
    @Override
    public void update(HashMap<String, Integer> observerState) {
        this.cases = observerState;
        System.out.println("Check for observer pattern update!");
    }

    /**
     * Get cases from a postal code
     *
     * @param name Name of postal code to be considered
     * @return total cases under a postal code
     */
    public Integer getCases(String name) {
        return this.cases.get(name);
    }
}