package ExecutionHandling;

import DataHandling.TransactionDTO;
import IbAccountDataHandling.TwsThread;
import RiskHandling.RiskManagementSystem;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

import java.util.ArrayList;
import java.util.List;

public class OrderHandlerTWS {

    /* Create bracket orders */
    private static List<Order> BracketOrder(Types.Action action, int quantity, double limitPrice, double takeProfitLimitPrice, double stopLossPrice) {

        int parentOrderId = TwsThread.getNextValidIDOrder();

        /* PARENT ORDER */
        Order parent = new Order();
        parent.orderId(parentOrderId);
        parent.action(action);
        parent.totalQuantity(quantity);

        /* Adapt order type using strategy_data */
        switch (TwsThread.strategyData.getOrder()){
            case "Market":
                parent.orderType(OrderType.MKT);
                break;
            case "Limit":
                parent.orderType(OrderType.LMT);
                parent.lmtPrice(limitPrice);
                break;
        }
        parent.transmit(false);

        /* TP ORDER */
        Order takeProfit = new Order();
        takeProfit.parentId(parentOrderId+1);
        takeProfit.orderId(parentOrderId+2);
        takeProfit.action(action.equals(Types.Action.BUY) ? Types.Action.SELL : Types.Action.BUY);
        takeProfit.totalQuantity(quantity);
        takeProfit.orderType(OrderType.LMT);
        takeProfit.lmtPrice(takeProfitLimitPrice);
        takeProfit.transmit(false);

        /* SL ORDER */
        Order stopLoss = new Order();
        stopLoss.parentId(parentOrderId+1);
        stopLoss.orderId(parentOrderId+3);
        stopLoss.action(action.equals(Types.Action.SELL) ? Types.Action.BUY : Types.Action.SELL);
        stopLoss.totalQuantity(quantity);
        stopLoss.orderType(OrderType.STP);
        stopLoss.auxPrice(stopLossPrice);
        stopLoss.transmit(true);

        /* SET OF ORDERS */
        List<Order> orders = new ArrayList<>();
        orders.add(parent);
        orders.add(takeProfit);
        orders.add(stopLoss);
        return orders;
    }

    /* Create buy order */
    public void placeBuyOrder(double price) {

        /* Adapt quantity to multiplier using strategy_data */
        int multiplier = TwsThread.strategyData.getMultiplier();
        int quantity = 20000 * multiplier;

        /* Adapt order to type using strategy_data */
        String order = TwsThread.strategyData.getOrder();
        double limitPrice = 0;
        switch (order){
            case "Market":
                limitPrice = -1;
                break;
            case "Limit":
                limitPrice = price*1.02;
                break;
        }

        /* Adapt takeProfitLimitPrice to strategy_data */
        double takeProfit = TwsThread.strategyData.getTake_profit();
        double takeProfitLimitPrice = price * (1.0+(takeProfit/100.0));

        /* Adapt stopLossPrice to strategy_data */
        double stopLoss = TwsThread.strategyData.getStop_loss();
        double stopLossPrice = price * (1.0-(stopLoss/100.0));

        /* Adapt stop loss to SMA_5 => stopLossPrice > SMA(5) */
        double SMA_5 = TwsThread.SMA_5;
        if (stopLossPrice > SMA_5){
            stopLossPrice = SMA_5;
        }

        // TODO : Show buy order
        System.out.println("\n\n**** BUY ORDER ****"+
                "\n > Quantity : "+quantity+" <"+
                "\n > Limit Price : "+limitPrice+" <"+
                "\n > TakeProfit Limit Price : "+takeProfitLimitPrice+" <"+
                "\n > StopLoss Limit Price : "+stopLossPrice+" <"+
                "\n******* END ******* ");

        List<Order> orders = BracketOrder(Types.Action.BUY, quantity, round(limitPrice), round(takeProfitLimitPrice), round(stopLossPrice));
        placeBracketOrder(orders);
    }

    /* Create sell order */
    public void placeSellOrder(double price){

        /* Adapt quantity to multiplier using strategy_data */
        int multiplier = TwsThread.strategyData.getMultiplier();
        int quantity = 20000 * multiplier;

        /* Adapt order to type using strategy_data */
        String order = TwsThread.strategyData.getOrder();
        double limitPrice = 0;
        switch (order){
            case "Market":
                limitPrice = -1;
                break;
            case "Limit":
                limitPrice = price*0.98;
                break;
        }

        /* Adapt takeProfitLimitPrice to strategy_data */
        double takeProfit = TwsThread.strategyData.getTake_profit();
        double takeProfitLimitPrice = price * (1.0-(takeProfit/100.0));

        /* Adapt stopLossPrice to strategy_data */
        double stopLoss = TwsThread.strategyData.getStop_loss();
        double stopLossPrice = price * (1.0+(stopLoss/100.0));

        /* Adapt stop loss to SMA_5 => stopLossPrice < SMA(5) */
        double SMA_5 = TwsThread.SMA_5;
        if (stopLossPrice < SMA_5){
            stopLossPrice = SMA_5;
        }

        // TODO : Show sell order
        System.out.println("\n\n**** SELL ORDER ****"+
                "\n > Quantity : "+quantity+" <"+
                "\n > Limit Price : "+limitPrice+" <"+
                "\n > TakeProfit Limit Price : "+takeProfitLimitPrice+" <"+
                "\n > StopLoss Limit Price : "+stopLossPrice+" <"+
                "\n******* END ******* ");

        List<Order> orders = BracketOrder(Types.Action.SELL, quantity, round(limitPrice), round(takeProfitLimitPrice), round(stopLossPrice));
        placeBracketOrder(orders);
    }

    /* Place order on market */
    private void placeBracketOrder (List<Order> bracketOrder) {
        if (RiskManagementSystem.verifyOrder(bracketOrder)) {
            for (Order o : bracketOrder) {
                TwsThread.twsOutputAdapter.onPlaceOrder(o);
            }
            /* ADD TRANSACTIONS TO HISTORY */
            TwsThread.historyData.addTransactions(new TransactionDTO(bracketOrder));
            System.out.println("> The risk management system agreed a transaction.");
        } else {
            System.out.println("> The risk management system blocked a transaction.");
        }
    }

    private double round (double d){
        return Math.round(d * 20.0) / 20.0;
    }
}
