package DataHandling;

import UserInterface.Component.Enum.TransactionTYPE;
import UserInterface.Screen.Observer;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TransactionData implements Subject, Serializable {

    private static final long serialVersionUID = -7968850496612010419L;
    private transient ArrayList<Observer> observers;

    private ArrayList<String> assets;
    private ArrayList<String> currencies;
    private ArrayList<TransactionTYPE> types;
    private ArrayList<Date> dates;
    private ArrayList<Time> times;
    private ArrayList<Double> quantities;
    private ArrayList<Double> fees;
    private ArrayList<Double> prices;

    public TransactionData() {
        assets = new ArrayList<>();
        currencies = new ArrayList<>();
        types = new ArrayList<>();
        dates = new ArrayList<>();
        times = new ArrayList<>();
        quantities = new ArrayList<>();
        fees = new ArrayList<>();
        prices = new ArrayList<>();

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
            File file = new File(home+"/transactions.bullitt");

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

    /* TRANSACTION METHOD */

    public ArrayList<String> getAssets() {
        return assets;
    }

    public ArrayList<String> getCurrencies() {
        return currencies;
    }

    public ArrayList<TransactionTYPE> getTypes() {
        return types;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public ArrayList<Double> getQuantities() {
        return quantities;
    }

    public ArrayList<Double> getFees() {
        return fees;
    }

    public ArrayList<Double> getPrices() {
        return prices;
    }

    public int getNumberBuy() {
        int i = 0;
        for (TransactionTYPE type : types){
            if (type == TransactionTYPE.BUY){
                i++;
            }
        }
        return i;
    }

    public int getNumberSell() {
        int i = 0;
        for (TransactionTYPE type : types){
            if (type == TransactionTYPE.SELL){
                i++;
            }
        }
        return i;
    }

    public double getExposition() {
        double sum = 0;
        for (int i = 0; i<types.size(); i++){
            sum += Math.abs((1.*quantities.get(i)*prices.get(i)+fees.get(i)));
        }
        return sum;
    }

    public void addTransactions (String asset, String currency, TransactionTYPE type, Date date, Time time, double quantity, double fee, double price){
        assets.add(0, asset);
        currencies.add(0, currency);
        types.add(0, type);
        dates.add(0, date);
        times.add(0, time);
        quantities.add(0, quantity);
        fees.add(0, fee);
        prices.add(price);

        notifyObservers();
        serialize();
    }
}
