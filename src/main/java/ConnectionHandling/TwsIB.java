package ConnectionHandling;

import StorageHandling.*;
import MarketDataHandling.MarketAdapter;
import MarketDataHandling.TechnicalAnalysisHandling.AdapterManager;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

public class TwsIB implements Runnable {

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
    public static OutputAdapterIB outputAdapterIB = new OutputAdapterIB();

    private ChronoLiveData chronoLiveData;
    private AdapterManager adapterManager;
    private static int nextValidID = 0;
    public static boolean forceStop = false;

    public TwsIB(StrategyData strategyData, HistoryData historyData, LiveData liveData, AlphaVantageData alphaVantageData, AccountData accountData, PositionData positionData) {
        TwsIB.strategyData = strategyData;
        TwsIB.historyData = historyData;
        TwsIB.liveData = liveData;
        TwsIB.alphaVantageData = alphaVantageData;
        TwsIB.accountData = accountData;
        TwsIB.positionData = positionData;
    }

    @Override
    public void run() {
        try {
            outputAdapterIB.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean startMarketAdapter () {

        forceStop = false;
        if (outputAdapterIB == null){
            return false;
        }

        /* RESET DATA BEFORE STARTING */
        TwsIB.liveData.reset();
        TwsIB.historyData.reset();
        OutputAdapterIB.statusReqPnLSingle = false;

        TwsIB.marketAdapter = new MarketAdapter();
        chronoLiveData = new ChronoLiveData(TwsIB.liveData);
        Thread t = new Thread(chronoLiveData);
        t.start();

        adapterManager = new AdapterManager(TwsIB.alphaVantageData, TwsIB.strategyData);
        Thread t1 = new Thread(adapterManager);
        t1.start();

        return true;
    }
    public static void initMktAdapter () {
        outputAdapterIB.onReqMktData();
    }

    public boolean stopMarketAdapter () {

        forceStop = true;
        if (outputAdapterIB == null){
            return false;
        }

        /* Stop thread */
        chronoLiveData.stop();
        adapterManager.stop();

        /* Stop market data */
        outputAdapterIB.onCancelMktData();

        /* Clear open orders */
        outputAdapterIB.onGlobalCancel();

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
            outputAdapterIB.onPlaceOrder(order);
        }

        return true;
    }

    public static void setNextValidID(int nextValidID) {
        TwsIB.nextValidID = nextValidID;
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
