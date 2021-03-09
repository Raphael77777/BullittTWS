package ConnectionHandling;

import StorageHandling.*;
import MarketDataHandling.MarketAdapter;
import MarketDataHandling.TechnicalAnalysisHandling.AdapterManager;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

public class TwsIB implements Runnable {

    /* STORAGE OBJECTS */
    public static StrategyData strategyData;
    public static HistoryData historyData;
    public static LiveData liveData;
    public static AlphaVantageData alphaVantageData;
    public static AccountData accountData;
    public static PositionData positionData;
    private ChronoLiveData chronoLiveData;

    /* TECHNICAL INDICATORS */
    public static double SMA_200 = 0.0;
    public static double RSI_2 = 0.0;
    public static double SMA_5 = 0.0;

    /* TWS ADAPTER */
    public static MarketAdapter marketAdapter;
    public static OutputAdapterIB outputAdapterIB = new OutputAdapterIB();

    /* TECHNICAL INDICATORS ADAPTER */
    private AdapterManager adapterManager;

    /* STATUS AND NEXT ID */
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
        /* REQUEST CONNECTION TO IB */
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

        /* START CHRONO */
        TwsIB.marketAdapter = new MarketAdapter();
        chronoLiveData = new ChronoLiveData(TwsIB.liveData);
        Thread t = new Thread(chronoLiveData);
        t.start();

        /* REQUEST TECHNICAL INDICATORS */
        adapterManager = new AdapterManager(TwsIB.alphaVantageData, TwsIB.strategyData);
        Thread t1 = new Thread(adapterManager);
        t1.start();

        return true;
    }
    public static void initMktAdapter () {
        /* REQUEST MARKET DATA */
        outputAdapterIB.onReqMktData();
    }

    public boolean stopMarketAdapter () {
        forceStop = true;
        if (outputAdapterIB == null){
            return false;
        }

        /* STOP THREAD */
        chronoLiveData.stop();
        adapterManager.stop();

        /* STOP MARKET DATA */
        outputAdapterIB.onCancelMktData();

        /* CLEAR OPEN ORDERS */
        outputAdapterIB.onGlobalCancel();

        /* CLEAR OPEN POSITIONS */
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

    /* NextValidId METHOD(GETTER AND SETTER) */
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
