package DataHandling;

import IbAccountDataHandling.TwsThread;
import UserInterface.Screen.Observer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class LiveData implements Subject{

    private ArrayList<Observer> observers = new ArrayList<>();

    private double dailyPNL;
    private double unrealizedPNL;
    private double realizedPNL;
    private int sumOrder;
    private int sumAnalysis;
    private double analysisTime;
    private ArrayList<Double> averageAnalysisTime = new ArrayList<>();
    private double minAnalysisTime;
    private double maxAnalysisTime;
    private LocalTime timeSinceStart = new Time(-7200000).toLocalTime();
    private LocalTime startTime = new Time(-7200000).toLocalTime();
    private double startPrice;
    private double currentPrice;

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

    public double getCompellingAnalysisPercentage() {
        if (sumOrder == 0.0 || sumAnalysis == 0.0){
            return 0.0;
        }

        double d = (1.0*sumOrder)/(1.0*sumAnalysis);
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public double getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(double analysisTime) {

        if (analysisTime == 0){
            analysisTime = getAverageAnalysisTime();
        }

        if (minAnalysisTime == 0 && maxAnalysisTime == 0){
            minAnalysisTime = analysisTime;
            maxAnalysisTime = analysisTime;
        }

        if (analysisTime < minAnalysisTime){
            minAnalysisTime = analysisTime;
        }

        if (analysisTime > maxAnalysisTime){
            maxAnalysisTime = analysisTime;
        }

        averageAnalysisTime.add(analysisTime);
        this.analysisTime = analysisTime;
    }

    public double getAverageAnalysisTime() {

        if (averageAnalysisTime.size() == 0){
            return 0.0;
        }

        double avg = 0.0;

        for (double d : averageAnalysisTime){
            avg+=d;
        }

        BigDecimal avgTime = new BigDecimal(Double.toString(avg/averageAnalysisTime.size()));
        avgTime = avgTime.setScale(2, RoundingMode.HALF_UP);
        return avgTime.doubleValue();
    }

    public double getMinAnalysisTime() {
        return minAnalysisTime;
    }

    public double getMaxAnalysisTime() {
        return maxAnalysisTime;
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

    public double getStartPrice() {
        return startPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {

        if (startPrice == 0){
            startPrice = currentPrice;
        }

        this.currentPrice = currentPrice;
    }

    public double getVarOpening() {

        if (startPrice == 0 || currentPrice == 0){
            return 0.0;
        }

        BigDecimal avgTime = new BigDecimal(Double.toString((100.0*currentPrice/startPrice)-100));
        avgTime = avgTime.setScale(4, RoundingMode.HALF_UP);
        return avgTime.doubleValue();
    }

    public void update() {
        notifyObservers();
    }

    public void reset () {
        dailyPNL = 0.0;
        unrealizedPNL = 0.0;
        realizedPNL = 0.0;
        sumOrder = 0;
        sumAnalysis = 0;
        analysisTime = 0.0;
        averageAnalysisTime.clear();
        minAnalysisTime = 0.0;
        maxAnalysisTime = 0.0;
        timeSinceStart = new Time(-7200000).toLocalTime();
        startTime = new Time(-7200000).toLocalTime();
        startPrice = 0.0;
        currentPrice = 0.0;

        update();
    }
}
