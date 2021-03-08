package StorageHandling;

import UserInterface.JFrameBTWS;
import UserInterface.Screen.Observer;

import java.io.*;
import java.util.ArrayList;

public class StrategyData implements Subject, Serializable {

    private static final long serialVersionUID = -7985850416102010819L;
    private transient ArrayList<Observer> observers;

    private String asset ="--";
    private String order ="--";
    private String timescale ="--";
    private double accuracy;
    private int multiplier;
    private double take_profit;
    private double stop_loss;

    public StrategyData() {
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
            File file = new File(home+"/strategy.bullitt");

            FileOutputStream out = new FileOutputStream(file);
            oos = new ObjectOutputStream(out);
            oos.writeObject(this);
        }
        catch(
                IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* STRATEGY METHOD */

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTimescale() {
        return timescale;
    }

    public void setTimescale(String timescale) {
        this.timescale = timescale;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public double getTake_profit() {
        return take_profit;
    }

    public void setTake_profit(double take_profit) {
        this.take_profit = take_profit;
    }

    public double getStop_loss() {
        return stop_loss;
    }

    public void setStop_loss(double stop_loss) {
        this.stop_loss = stop_loss;
    }

    public void compil (){
        JFrameBTWS.getInstance().positionData.reset();
        notifyObservers();
        serialize();
    }
}
