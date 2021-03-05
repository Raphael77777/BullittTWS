package ExecutionHandling;

import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import IbAccountDataHandling.TwsThread;

public class EntrySignalA {

    private OrderHandlerTWS orderHandler;

    private long startMs;
    private long stopMS;

    public EntrySignalA(double price) throws MissingApiKeyException, NoNetworkException, OverloadApiUseException, MarketClosedException {
        this.orderHandler = new OrderHandlerTWS();

        if(price != 0.0) {

            startMs = System.currentTimeMillis();

            // TODO : TO REMOVE
            orderHandler.placeBuyOrder(price);

            /* Get technical analysis */
            String asset = TwsThread.strategyData.getAsset().replace(".", "");
            double SMA_200 = TwsThread.adapterSMA.get(asset, "1min", "200", "open");
            double RSI_2 = TwsThread.adapterRSI.get(asset, "1min", "2", "open");

            TwsThread.liveData.setSumAnalysis(TwsThread.liveData.getSumAnalysis()+1);
            TwsThread.liveData.setCurrentPrice(price);
            TwsThread.liveData.update();

            // TODO: Show technical analysis
            //System.out.println("* START ******************************************");
            //System.out.println("PRICE : "+price);
            //System.out.println("SMA200 : "+SMA_200);
            //System.out.println("RSI2 : "+RSI_2);
            //System.out.println("* END ********************************************");

            /* price > SMA(200) && RSI(2) < 10 */
            /* Adapt RSI_LIMIT_BUY to accuracy using strategy_data */
            double accuracy = TwsThread.strategyData.getAccuracy();
            double RSI_LIMIT_BUY = 10 - (accuracy*5);
            if (price > SMA_200 && RSI_2 < RSI_LIMIT_BUY){

                TwsThread.liveData.setSumOrder(TwsThread.liveData.getSumOrder()+1);
                TwsThread.liveData.update();

                orderHandler.placeBuyOrder(price);
                System.out.println("> BUY ORDER HAS BEEN PLACED NOW");
            }

            /* price < SMA(200) && RSI(2) > 90 */
            /* Adapt RSI_LIMIT_SELL to accuracy using strategy_data */
            double RSI_LIMIT_SELL = 90 + (accuracy*5);
            if (price < SMA_200 && RSI_2 > RSI_LIMIT_SELL){

                TwsThread.liveData.setSumOrder(TwsThread.liveData.getSumOrder()+1);
                TwsThread.liveData.update();

                orderHandler.placeSellOrder(price);
                System.out.println("> SELL ORDER HAS BEEN PLACED NOW");
            }

            stopMS = System.currentTimeMillis();
            TwsThread.liveData.setAnalysisTime(stopMS - startMs);
            TwsThread.liveData.update();

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
