package org.example;

import java.util.ArrayList;
import java.util.List;

public class Subject implements Observer
{
    private List<Observer> observers;

    public Subject()
    {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    public void notifyObservers(String notification, List<Observer> observers)
    {
        for (Observer observer : observers)
        {
            observer.update(notification);
        }
    }

    @Override
    public void update(String notification)
    {

    }
}
