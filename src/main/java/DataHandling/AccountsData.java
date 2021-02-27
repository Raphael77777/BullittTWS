package DataHandling;

import UserInterface.Screen.Observer;

import java.io.*;
import java.util.ArrayList;

public class AccountsData implements Subject {

    private ArrayList<Observer> observers;

    private String VALUE = "";

    public AccountsData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        if (observers == null){
            observers = new ArrayList<>();
        }
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (observers == null){
            return;
        }
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if (observers == null){
            return;
        }
        for (Observer o : observers){
            o.update();
        }
    }

    /* IB Accounts METHOD */

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String value) {
        this.VALUE = value;

        notifyObservers();
    }
}
