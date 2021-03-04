package ExecutionHandling;

import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import IbAccountDataHandling.TwsThread;
import com.ib.client.Types;

public class EntrySignalA {

    private OrderHandler orderHandler;

    public EntrySignalA(double price) throws MissingApiKeyException, NoNetworkException, OverloadApiUseException, MarketClosedException {
        this.orderHandler = new OrderHandler();

        if(price != 0.0) {
            /* Get technical analysis */
            String asset = TwsThread.strategyData.getAsset().replace(".", "");
            double SMA_200 = TwsThread.adapterSMA.get(asset, "1min", "200", "open");
            double RSI_2 = TwsThread.adapterRSI.get(asset, "1min", "2", "open");

            // TODO: Show technical analysis
            System.out.println("PRICE : "+price);
            System.out.println("SMA200 : "+SMA_200);
            System.out.println("RSI2 : "+RSI_2);
            System.out.println("******************************************");

            /* price > SMA(200) && RSI(2) < 10 */
            /* Adapt RSI_LIMIT_BUY to accuracy using strategy_data */
            double accuracy = TwsThread.strategyData.getAccuracy();
            double RSI_LIMIT_BUY = 10 - (accuracy*5);
            if (price > SMA_200 && RSI_2 < RSI_LIMIT_BUY){

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
                double SMA_5 = TwsThread.adapterSMA.get(asset, "1min", "5", "open");
                if (stopLossPrice > SMA_5){
                    stopLossPrice = SMA_5;
                }

                // TODO : Show buy order
                System.out.println("**** BUY ORDER N°"+TwsThread.getNextValidID()+" ****"+
                                "\n > Quantity : "+quantity+" <"+
                                "\n > Limit Price : "+limitPrice+" <"+
                                "\n > TakeProfit Limit Price : "+takeProfitLimitPrice+" <"+
                                "\n > StopLoss Limit Price : "+stopLossPrice+" <"+
                                "\n ******* END ******* ");

                orderHandler.placeBracketOrder(TwsThread.getNextValidID(), Types.Action.BUY, quantity, limitPrice, takeProfitLimitPrice, stopLossPrice);
                System.out.println("> BUY ORDER HAS BEEN PLACED NOW");
            }

            /* price < SMA(200) && RSI(2) > 90 */
            /* Adapt RSI_LIMIT_SELL to accuracy using strategy_data */
            double RSI_LIMIT_SELL = 90 + (accuracy*5);
            if (price < SMA_200 && RSI_2 > RSI_LIMIT_SELL){

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
                        limitPrice = price*-1.02;
                        break;
                }

                /* Adapt takeProfitLimitPrice to strategy_data */
                double takeProfit = TwsThread.strategyData.getTake_profit();
                double takeProfitLimitPrice = price * (1.0-(takeProfit/100.0));

                /* Adapt stopLossPrice to strategy_data */
                double stopLoss = TwsThread.strategyData.getStop_loss();
                double stopLossPrice = price * (1.0+(stopLoss/100.0));

                /* Adapt stop loss to SMA_5 => stopLossPrice < SMA(5) */
                double SMA_5 = TwsThread.adapterSMA.get(asset, "1min", "5", "open");
                if (stopLossPrice < SMA_5){
                    stopLossPrice = SMA_5;
                }

                // TODO : Show sell order
                System.out.println("**** SELL ORDER N°"+TwsThread.getNextValidID()+" ****"+
                        "\n > Quantity : "+quantity+" <"+
                        "\n > Limit Price : "+limitPrice+" <"+
                        "\n > TakeProfit Limit Price : "+takeProfitLimitPrice+" <"+
                        "\n > StopLoss Limit Price : "+stopLossPrice+" <"+
                        "\n ******* END ******* ");

                orderHandler.placeBracketOrder(TwsThread.getNextValidID(), Types.Action.SELL, quantity, limitPrice, takeProfitLimitPrice, stopLossPrice);
                System.out.println("> SELL ORDER HAS BEEN PLACED NOW");
            }

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
        }
    }
}
