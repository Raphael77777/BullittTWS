package ExecutionHandling;

import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import IbAccountDataHandling.TwsThread;
import com.ib.client.Types;

import java.util.ArrayList;

public class EntrySignalA {

    private ArrayList<Double> prices;
    private OrderHandler orderHandler;

    public EntrySignalA(ArrayList<Double> prices, Integer parentOrderId) throws MissingApiKeyException, NoNetworkException, OverloadApiUseException, MarketClosedException {
        this.prices = prices;
        this.orderHandler = new OrderHandler();

        /**
        if(prices.size()>1) {
            if (prices.get(0) - prices.get(1) >= 0.00002) {
                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.BUY, 1, 1, 1, .5);
                System.out.println("BUY NOW");
            }
            if(prices.get(0) - prices.get(1) <= -0.00002) {
                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.SELL, 1, 1, 1, .5);
                System.out.println("SELL NOW");
            }
        }*/

        //TODO: Implement strategy
        if(prices.size()>1) {

            // TODO: Get technical analysis
            double SMA_200 = TwsThread.adapterSMA.get("USDEUR", "1min", "200", "open");
            double RSI_2 = TwsThread.adapterRSI.get("USDEUR", "1min", "2", "open");

            // TODO: price > SMA(200) && RSI(2) < 10
            // TODO: Adapt RSI_LIMIT_BUY to accuracy using strategy_data
            double accuracy = TwsThread.strategyData.getAccuracy();

            double RSI_LIMIT_BUY = 10;
            if (prices.get(0) > SMA_200 && RSI_2 < RSI_LIMIT_BUY){

                // TODO : Adapt quantity to exposure using strategy_data
                int exposure = TwsThread.strategyData.getExposure();
                int quantity = 1;
                double limitPrice = 1;

                // TODO : Adapt takeProfitLimitPrice to strategy_data
                double takeProfit = TwsThread.strategyData.getTake_profit();
                double takeProfitLimitPrice = 1;

                // TODO : Adapt stopLossPrice to strategy_data
                double stopLoss = TwsThread.strategyData.getStop_loss();
                // TODO : Adapt stop loss to SMA_5 => price > SMA(5)
                double SMA_5 = TwsThread.adapterSMA.get("USDEUR", "1min", "5", "open");
                double stopLossPrice = 0.5;

                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.BUY, quantity, limitPrice, takeProfitLimitPrice, stopLossPrice);
                System.out.println("> BUY ORDER HAS BEEN PLACED NOW");
            }

            // TODO: price < SMA(200) && RSI(2) > 90
            // TODO: Adapt RSI_LIMIT_SELL to accuracy using strategy_data
            accuracy = TwsThread.strategyData.getAccuracy();

            double RSI_LIMIT_SELL = 90;
            if (prices.get(0) < SMA_200 && RSI_2 > RSI_LIMIT_SELL){

                // TODO : Adapt quantity to exposure using strategy_data
                int exposure = TwsThread.strategyData.getExposure();
                int quantity = 1;
                double limitPrice = 1;

                // TODO : Adapt takeProfitLimitPrice to strategy_data
                double takeProfit = TwsThread.strategyData.getTake_profit();
                double takeProfitLimitPrice = 1;

                // TODO : Adapt stopLossPrice to strategy_data
                double stopLoss = TwsThread.strategyData.getStop_loss();
                // TODO : Adapt stop loss to SMA_5 => price < SMA(5)
                double SMA_5 = TwsThread.adapterSMA.get("USDEUR", "1min", "5", "open");
                double stopLossPrice = 0.5;

                orderHandler.placeBracketOrder(parentOrderId+5000, Types.Action.SELL, quantity, limitPrice, takeProfitLimitPrice, stopLossPrice);
                System.out.println("> SELL ORDER HAS BEEN PLACED NOW");
            }
        }
    }
}
