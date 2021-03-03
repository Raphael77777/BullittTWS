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
        TwsThread.marketAdapter = new MarketAdapter();

        if (twsOutputAdapter == null){
            return false;
        }

        twsOutputAdapter.onReqMktData();
        return true;
    }
    public boolean stopMarketAdapter () {

        if (twsOutputAdapter == null){
            return false;
        }

        twsOutputAdapter.onCancelMktData();
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
