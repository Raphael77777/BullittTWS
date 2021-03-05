package IbAccountDataHandling;

import DataHandling.*;
import MarketDataHandling.MarketAdapter;
import TechnicalAnalysisHandling.AdapterRSI;
import TechnicalAnalysisHandling.AdapterSMA;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

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

        /* Stop chrono */
        chronoLiveData.stop();

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
