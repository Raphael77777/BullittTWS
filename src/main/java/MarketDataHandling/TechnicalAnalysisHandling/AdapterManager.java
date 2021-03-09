package MarketDataHandling.TechnicalAnalysisHandling;

import StorageHandling.AlphaVantageData;
import StorageHandling.StrategyData;
import ConnectionHandling.TwsIB;

import java.sql.Time;

public class AdapterManager implements Runnable {

    /* ADAPTER MANAGER STATUS */
    private volatile boolean exit = false;
    private volatile boolean init = true;

    /* DATA SUBJECT */
    private final StrategyData strategyData;

    /* TECHNICAL ADAPTERS */
    public static AdapterSMA adapterSMA;
    public static AdapterRSI adapterRSI;

    public AdapterManager(AlphaVantageData alphaVantageData, StrategyData strategyData) {
        this.strategyData = strategyData;
        adapterSMA = new AdapterSMA(alphaVantageData);
        adapterRSI = new AdapterRSI(alphaVantageData);
    }

    @Override
    public void run() {
        /* RUN UNTIL EXIT == TRUE */
        while (!exit){
            /* GET SYMBOL FROM strategyData */
            String asset = strategyData.getAsset().replace(".", "");

            double SMA_200 = 0.0;
            double RSI_2 = 0.0;
            double SMA_5 = 0.0;

            try {
                /* REQUEST SMA 200 */
                SMA_200 = adapterSMA.get(asset, "1min", "200", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                /* REQUEST RSI 2 */
                RSI_2 = adapterRSI.get(asset, "1min", "2", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                /* REQUEST SMA 5 */
                SMA_5 = adapterSMA.get(asset, "1min", "5", "open");
            } catch (Exception e) {
                e.printStackTrace();
            }

            /* CHANGE VALUE IN OFFICIAL THREAD */
            TwsIB.SMA_200 = SMA_200;
            TwsIB.RSI_2 = RSI_2;
            TwsIB.SMA_5 = SMA_5;

            /* INIT MKT ADAPTER IF NEEDED */
            if (init && !TwsIB.forceStop){
                TwsIB.initMktAdapter();
                init = false;
            }

            System.out.println("> TECHNICAL ANALYSIS GET AT "+new Time(System.currentTimeMillis()).toString());

            /* SLEEP FOR 1 MINUTES BEFORE STARTING AGAIN */
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
