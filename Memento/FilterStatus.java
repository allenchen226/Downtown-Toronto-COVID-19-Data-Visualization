package Memento;

import java.util.ArrayList;

public class FilterStatus {

    private ArrayList<String> filterApplied;

    public FilterStatus() {
        this.filterApplied = new ArrayList<>();
    }

    public FilterStateMemento createMemento() {
        FilterStatus copyStatus = new FilterStatus();
        for (String s: this.filterApplied) {
            copyStatus.getFilterApplied().add(s);
        }
        return new FilterStateMemento(copyStatus);
    }

    public static String restoreFromMemento(FilterStateMemento resMemento) {
        ArrayList<String> resFilterList = resMemento.getState().getFilterApplied();
        if (resFilterList.size() == 0) {
            return "ALL CASES";
        }
        return resFilterList.get(resFilterList.size()-1);
    }

    /** Get current filters applied, useful for memento
     *
     * @return filter applied
     */
    public ArrayList<String> getFilterApplied() {return this.filterApplied;}
}
