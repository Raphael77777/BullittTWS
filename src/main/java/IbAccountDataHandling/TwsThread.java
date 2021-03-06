package IbAccountDataHandling;

import DataHandling.*;
import MarketDataHandling.MarketAdapter;
import TechnicalAnalysisHandling.AdapterManager;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

public class TwsThread implements Runnable {

    public static StrategyData strategyData;
    public static HistoryData historyData;
    public static LiveData liveData;
    public static AlphaVantageData alphaVantageData;
    public static AccountData accountData;
    public static PositionData positionData;

    public static double SMA_200 = 0.0;
    public static double RSI_2 = 0.0;
    public static double SMA_5 = 0.0;

    public static MarketAdapter marketAdapter;
    public static TwsOutputAdapter twsOutputAdapter = new TwsOutputAdapter();

    private ChronoLiveData chronoLiveData;
    private AdapterManager adapterManager;
    private static int nextValidID = 0;

    public TwsThread(StrategyData strategyData, HistoryData historyData, LiveData liveData, AlphaVantageData alphaVantageData, AccountData accountData, PositionData positionData) {
        TwsThread.strategyData = strategyData;
        TwsThread.historyData = historyData;
        TwsThread.liveData = liveData;
        TwsThread.alphaVantageData = alphaVantageData;
        TwsThread.accountData = accountData;
        TwsThread.positionData = positionData;
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

        /* RESET DATA BEFORE STARTING */
        TwsThread.liveData.reset();
        TwsThread.historyData.reset();

        TwsThread.marketAdapter = new MarketAdapter();
        chronoLiveData = new ChronoLiveData(TwsThread.liveData);
        Thread t = new Thread(chronoLiveData);
        t.start();

        adapterManager = new AdapterManager(TwsThread.alphaVantageData, TwsThread.strategyData);
        Thread t1 = new Thread(adapterManager);
        t1.start();

        return true;
    }
    public static void initMktAdapter () {
        twsOutputAdapter.onReqMktData();
    }

    public boolean stopMarketAdapter () {

        if (twsOutputAdapter == null){
            return false;
        }

        /* Stop thread */
        chronoLiveData.stop();
        adapterManager.stop();

        /* Stop market data */
        twsOutputAdapter.onCancelMktData();

        /* Clear open orders */
        twsOutputAdapter.onGlobalCancel();

        /* Clear open positions */
        if (positionData.getContract() != null && strategyData.getAsset().equals(positionData.getContract().localSymbol())){
            double position = positionData.getPos();
            Order order = new Order();
            order.orderId(getNextValidID());
            if (position > 0){
                order.action(Types.Action.SELL);
            } else if (position < 0) {
                order.action(Types.Action.BUY);
            }
            order.orderType(OrderType.MKT);
            order.totalQuantity(Math.abs(position));
            order.transmit(true);
            twsOutputAdapter.onPlaceOrder(order);
        }

        return true;
    }

    public static void setNextValidID(int nextValidID) {
        TwsThread.nextValidID = nextValidID;
    }
    public static int getNextValidID() {
        nextValidID++;
        return nextValidID;
    }
    public static int getNextValidIDOrder() {
        nextValidID+=3;
        return nextValidID;
    }
}
