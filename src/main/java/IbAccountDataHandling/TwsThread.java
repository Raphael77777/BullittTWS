package IbAccountDataHandling;

import DataHandling.*;
import MarketDataHandling.MarketAdapter;
import TechnicalAnalysisHandling.AdapterRSI;
import TechnicalAnalysisHandling.AdapterSMA;

public class TwsThread implements Runnable {

    public static StrategyData strategyData;
    public static TransactionData transactionData;
    public static LiveData liveData;
    public static AlphaVantageData alphaVantageData;
    public static AccountData accountData;
    public static PositionData positionData;

    public static AdapterSMA adapterSMA;
    public static AdapterRSI adapterRSI;

    public static MarketAdapter marketAdapter;
    public static TwsOutputAdapter twsOutputAdapter = new TwsOutputAdapter();

    private ChronoLiveData chronoLiveData;
    private static int nextValidID = 0;

    public TwsThread(StrategyData strategyData, TransactionData transactionData, LiveData liveData, AlphaVantageData alphaVantageData, AccountData accountData, PositionData positionData) {
        TwsThread.strategyData = strategyData;
        TwsThread.transactionData = transactionData;
        TwsThread.liveData = liveData;
        TwsThread.alphaVantageData = alphaVantageData;
        TwsThread.accountData = accountData;
        TwsThread.positionData = positionData;

        TwsThread.adapterSMA = new AdapterSMA(alphaVantageData);
        TwsThread.adapterRSI = new AdapterRSI(alphaVantageData);
    }

    @Override
    public void run() {
        try {
            twsOutputAdapter.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean startMarketAdapter () {

        if (twsOutputAdapter == null){
            return false;
        }

        TwsThread.liveData.reset();

        TwsThread.marketAdapter = new MarketAdapter();
        chronoLiveData = new ChronoLiveData(TwsThread.liveData);
        Thread t = new Thread(chronoLiveData);
        t.start();

        twsOutputAdapter.onReqMktData();
        return true;
    }
    public boolean stopMarketAdapter () {

        if (twsOutputAdapter == null){
            return false;
        }

        chronoLiveData.stop();

        twsOutputAdapter.onCancelMktData();
        twsOutputAdapter.onGlobalCancel();

        //TODO : KILL POSITIONS

        return true;
    }

    public static void setNextValidID(int nextValidID) {
        TwsThread.nextValidID = nextValidID;
    }
    public static int getNextValidID() {
        nextValidID++;
        return nextValidID;
    }
}
