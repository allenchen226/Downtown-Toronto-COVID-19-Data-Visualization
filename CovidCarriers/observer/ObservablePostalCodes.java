package CovidCarriers.observer;
import DataView.DataPoint;
import DataView.IndividualPoint;
import DataView.Neighbourhood;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Observable class, an abstract observable object
 */
public abstract class ObservablePostalCodes {
    private HashMap<String, Integer> postalCodes = new HashMap<>();

    /**
     * register, ie add to list of observers
     *
     * @param o who is observing
     */
    public abstract void register(PostalCodesObserver o);

    /**
     * unregister, ie remove from list of observers
     *
     * @param o who is observing
     */
    public abstract void unregister(PostalCodesObserver o);

    /**
     * Notify observers of change
     */
    public abstract void notifyObservers();

    public void setObservableState(HashMap<String, Integer> cases) {
        // We want to observe the state, then notify all the observers
        this.postalCodes = cases;
        this.notifyObservers();
    }

    /**
     * Get observable state (i.e guidelines)
     */
    public HashMap<String, Integer> getPostalCodesList() {
        // return the Observable State
        return this.postalCodes;
    }

}
