package StorageHandling;

import UserInterface.Screen.Observer;
import com.ib.client.Contract;

import java.util.ArrayList;

public class PositionData implements Subject {

    /* LIST OF OBSERVERS */
    private ArrayList<Observer> observers;

    /* VALUES */
    private String account = "";
    private Contract contract;
    private double pos;
    private double avgCost;
    private double dailyPnL;
    private double unrealizedPnL;
    private double realizedPnL;
    private double value;

    public PositionData() {
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

    /* PositionData METHOD(GETTER AND SETTER) */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(double avgCost) {
        this.avgCost = avgCost;
    }

    public double getDailyPnL() {
        return dailyPnL;
    }

    public void setDailyPnL(double dailyPnL) {
        this.dailyPnL = dailyPnL;
    }

    public double getUnrealizedPnL() {
        return unrealizedPnL;
    }

    public void setUnrealizedPnL(double unrealizedPnL) {
        this.unrealizedPnL = unrealizedPnL;
    }

    public double getRealizedPnL() {
        return realizedPnL;
    }

    public void setRealizedPnL(double realizedPnL) {
        this.realizedPnL = realizedPnL;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void update() {
        notifyObservers();
    }

    public void reset(){
        account = "";
        contract = null;
        pos = 0.0;
        avgCost = 0.0;
        dailyPnL = 0.0;
        unrealizedPnL = 0.0;
        realizedPnL = 0.0;
        value = 0.0;

        update();
    }
}
