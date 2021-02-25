package ExecutionHandling;

import com.ib.client.Types;

import java.util.ArrayList;

public class EntrySignalA {

    private ArrayList<Double> prices;
    private OrderHandler orderHandler;

    public EntrySignalA(ArrayList<Double> prices, Integer parentOrderId){
        this.prices = prices;
        this.orderHandler = new OrderHandler();
        if(prices.size()>1) {
            if (prices.get(0) - prices.get(1) >= 0.00002) {
                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.BUY, 1, 1, 1, .5);
                System.out.println("BUY NOW");
            }
            if(prices.get(0) - prices.get(1) <= -0.00002) {
                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.SELL, 1, 1, 1, .5);
                System.out.println("SELL NOW");
            }
        }
    }
}
