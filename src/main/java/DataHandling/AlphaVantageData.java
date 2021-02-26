package DataHandling;

import UserInterface.Screen.Observer;

import java.io.*;
import java.util.ArrayList;

public class AlphaVantageData implements Subject, Serializable {

    private static final long serialVersionUID = -7238850416677010319L;
    private transient ArrayList<Observer> observers;

    private String API_KEY_1 = "";

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

    public String getAPI_KEY_1() {
        return API_KEY_1;
    }

    public void setAPI_KEY_1(String API_KEY_1) {
        this.API_KEY_1 = API_KEY_1;

        notifyObservers();
        serialize();
    }
}
