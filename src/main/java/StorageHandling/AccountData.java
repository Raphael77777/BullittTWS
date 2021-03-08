package StorageHandling;

import UserInterface.Screen.Observer;

import java.util.ArrayList;

public class AccountData implements Subject {

    private ArrayList<Observer> observers;

    private String accountId = "--";
    private String currency = "--";

    private double availableFunds;
    private double netLiquidationValue;
    private double buyingPower;
    private double marginReq;
    private double dailyPNL;
    private double unrealizedPNL;
    private double realizedPNL;
    private double cushion;

    public AccountData() {
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

    /* AccountDATA METHOD */

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(double availableFunds) {
        this.availableFunds = availableFunds;
    }

    public double getNetLiquidationValue() {
        return netLiquidationValue;
    }

    public void setNetLiquidationValue(double netLiquidationValue) {
        this.netLiquidationValue = netLiquidationValue;
    }

    public double getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(double buyingPower) {
        this.buyingPower = buyingPower;
    }

    public double getMarginReq() {
        return marginReq;
    }

    public void setMarginReq(double marginReq) {
        this.marginReq = marginReq;
    }

    public double getDailyPNL() {
        return dailyPNL;
    }

    public void setDailyPNL(double dailyPNL) {
        this.dailyPNL = dailyPNL;
    }

    public double getUnrealizedPNL() {
        return unrealizedPNL;
    }

    public void setUnrealizedPNL(double unrealizedPNL) {
        this.unrealizedPNL = unrealizedPNL;
    }

    public double getRealizedPNL() {
        return realizedPNL;
    }

    public void setRealizedPNL(double realizedPNL) {
        this.realizedPNL = realizedPNL;
    }

    public double getCushion() {
        return cushion;
    }

    public void setCushion(double cushion) {
        this.cushion = cushion;
    }

    public void update() {
        notifyObservers();
    }
}
