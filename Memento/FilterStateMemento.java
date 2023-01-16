package Memento;

public class FilterStateMemento {

    private FilterStatus filterState;

    public FilterStateMemento(FilterStatus newStatus) {
        this.filterState = newStatus;
    }

    public FilterStatus getState() {
        return this.filterState;
    }

    public void setState(FilterStatus newStatus) {
        this.filterState = newStatus;
    }
}
