package DataHandling;

import UserInterface.Screen.Observer;

import java.io.*;
import java.util.ArrayList;

public class AlphaVantageData implements Subject, Serializable {

    private static final long serialVersionUID = -7238850416677010319L;
    private transient ArrayList<Observer> observers;

    private String API_KEY = "";

    public AlphaVantageData() {
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

    public void serialize() {

        ObjectOutputStream oos = null;
        try {
            String home = System.getProperty("user.home");
            File file = new File(home+"/alpha.bullitt");

            FileOutputStream out = new FileOutputStream(file);
            oos = new ObjectOutputStream(out);
            oos.writeObject(this);
        }
        catch(
                IOException e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* ALPHA VANTAGE METHOD */

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;

        notifyObservers();
        serialize();
    }
}
