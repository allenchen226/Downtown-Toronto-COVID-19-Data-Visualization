package CovidCarriers;

import CovidCarriers.observer.ObservablePostalCodes;
import CovidCarriers.observer.PostalCodesObserver;
import DataView.DataPoint;
import DataView.IndividualPoint;
import DataView.Neighbourhood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostalCodes extends ObservablePostalCodes {
    private List<PostalCodesObserver> residentsList = new ArrayList<>();

    /**
     * register, ie add to list of observers
     *
     * @param o who is observing
     */
    @Override
    public void register(PostalCodesObserver o) {
        if (!this.residentsList.contains(o)) {
            this.residentsList.add(o);
        }
    }

    /**
     * unregister, ie remove from list of observers
     *
     * @param o who is observing
     */
    @Override
    public void unregister(PostalCodesObserver o) {
        int position = this.residentsList.indexOf(o);
        if (position >= 0) {
            this.residentsList.remove(position);
        }
    }

    /**
     * Notify observers of change
     */
    @Override
    public void notifyObservers() {
        HashMap<String, Integer> lst = this.getPostalCodesList();
        for (PostalCodesObserver observer : this.residentsList ) {
            observer.update(lst);

        }
    }


    /**
     * add postal code information
     *
     * @param point selected neighbourhood on UI
     *
     */
    public void addPostalCode(Neighbourhood point) {
        HashMap<String, Integer> lst = this.getPostalCodesList();
        lst.put(point.idPostalCode, point.getCaseCount());
        this.setObservableState(lst);
    }

    /**
     * remove postal code information
     *
     * @param point selected neighbourhood on UI
     */
    public void removePostalCode(Neighbourhood point) {
        HashMap<String, Integer> lst = this.getPostalCodesList();
        lst.remove(point.idPostalCode);
        this.setObservableState(lst);
    }

}
