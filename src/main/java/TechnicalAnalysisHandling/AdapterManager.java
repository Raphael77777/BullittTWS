package TechnicalAnalysisHandling;

import DataHandling.AlphaVantageData;
import DataHandling.StrategyData;
import IbAccountDataHandling.TwsThread;

import java.sql.Time;

public class AdapterManager implements Runnable {

    private volatile boolean exit = false;
    private volatile boolean init = true;

    private final StrategyData strategyData;

    public static AdapterSMA adapterSMA;
    public static AdapterRSI adapterRSI;

    public AdapterManager(AlphaVantageData alphaVantageData, StrategyData strategyData) {
        this.strategyData = strategyData;
        adapterSMA = new AdapterSMA(alphaVantageData);
        adapterRSI = new AdapterRSI(alphaVantageData);
    }

    @Override
    public void run() {
        while (!exit){

            String asset = strategyData.getAsset().replace(".", "");

            double SMA_200 = 0.0;
            double RSI_2 = 0.0;
            double SMA_5 = 0.0;

            try {
                SMA_200 = adapterSMA.get(asset, "1min", "200", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                RSI_2 = adapterRSI.get(asset, "1min", "2", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                SMA_5 = adapterSMA.get(asset, "1min", "5", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            TwsThread.SMA_200 = SMA_200;
            TwsThread.RSI_2 = RSI_2;
            TwsThread.SMA_5 = SMA_5;

            if (init && !TwsThread.forceStop){
                TwsThread.initMktAdapter();
                init = false;
            }

            // TODO : TO REMOVE
            System.out.println("TECHNICAL ANALYSIS GET AT "+new Time(System.currentTimeMillis()).toString());

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        exit = true;
    }
}
