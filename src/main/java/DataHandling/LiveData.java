package DataHandling;

import UserInterface.Screen.Observer;

import java.time.LocalTime;
import java.util.ArrayList;

public class LiveData implements Subject{

    private ArrayList<Observer> observers = new ArrayList<>();

    private double dailyPNL;
    private double unrealizedPNL;
    private double realizedPNL;
    private int sumOrder;
    private int sumAnalysis;
    private int compellingAnalysisPercentage;
    private double analysisTime;
    private double averageAnalysisTime;
    private double nextAnalysis;
    private LocalTime timeToStart;
    private LocalTime timeSinceStart;
    private LocalTime startTime;
    private double startPrice;
    private double currentPrice;
    private double AveragePrice;

    private boolean initCompleted = false;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers){
            o.update();
        }
    }

    /* LIVE DATA METHOD */

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

    public int getSumOrder() {
        return sumOrder;
    }

    public void setSumOrder(int sumOrder) {
        this.sumOrder = sumOrder;
    }

    public int getSumAnalysis() {
        return sumAnalysis;
    }

    public void setSumAnalysis(int sumAnalysis) {
        this.sumAnalysis = sumAnalysis;
    }

    public int getCompellingAnalysisPercentage() {
        return compellingAnalysisPercentage;
    }

    public void setCompellingAnalysisPercentage(int compellingAnalysisPercentage) {
        this.compellingAnalysisPercentage = compellingAnalysisPercentage;
    }

    public double getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(double analysisTime) {
        this.analysisTime = analysisTime;
    }

    public double getAverageAnalysisTime() {
        return averageAnalysisTime;
    }

    public void setAverageAnalysisTime(double averageAnalysisTime) {
        this.averageAnalysisTime = averageAnalysisTime;
    }

    public double getNextAnalysis() {
        return nextAnalysis;
    }

    public void setNextAnalysis(double nextAnalysis) {
        this.nextAnalysis = nextAnalysis;
    }

    public LocalTime getTimeSinceStart() {
        return timeSinceStart;
    }

    public void setTimeSinceStart(LocalTime timeSinceStart) {
        this.timeSinceStart = timeSinceStart;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(LocalTime timeToStart) {

        if (initCompleted){
            return;
        }

        this.timeToStart = timeToStart;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(double averagePrice) {
        AveragePrice = averagePrice;
    }

    public boolean isInitCompleted() {
        return initCompleted;
    }

    public void setInitCompleted(boolean initCompleted) {
        this.initCompleted = initCompleted;
    }
}
