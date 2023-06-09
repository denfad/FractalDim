package ru.denfad.fractaldim;

public interface Observable {

    void registerObserver(Observer observer);

    void notifyObservers();
}
