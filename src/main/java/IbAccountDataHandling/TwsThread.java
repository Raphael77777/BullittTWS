package IbAccountDataHandling;

import DataHandling.*;
import TechnicalAnalysisHandling.AdapterRSI;
import TechnicalAnalysisHandling.AdapterSMA;

public class TwsThread implements Runnable{

    public static StrategyData strategyData;
    public static TransactionData transactionData;
    public static LiveData liveData;
    public static AlphaVantageData alphaVantageData;
    public static AccountData accountData;
    public static PositionData positionData;

    public static AdapterSMA adapterSMA;
    public static AdapterRSI adapterRSI;

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
        new TwsOutputAdapter();
    }
}
