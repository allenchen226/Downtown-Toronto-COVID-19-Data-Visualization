package Memento;

import java.util.ArrayList;

public class CareTaker {

    private ArrayList<FilterStateMemento> history;

    public CareTaker() {
        this.history = new ArrayList<>();
    }

    public ArrayList<FilterStateMemento> get() {
        return this.history;
    }

    public void add(FilterStateMemento newMemento) {
        this.history.add(newMemento);
    }

    public FilterStateMemento undo() {
        if (this.history.size() <= 1) {
            this.history.clear();
            return new FilterStateMemento(new FilterStatus());
        }
        this.history.remove(this.history.size()-1);
        return this.history.get(this.history.size()-1);
    }
}
