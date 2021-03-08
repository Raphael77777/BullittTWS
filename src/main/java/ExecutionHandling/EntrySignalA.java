package ExecutionHandling;

import IbAccountDataHandling.TwsThread;

public class EntrySignalA {

    public EntrySignalA(double price){
        OrderHandlerTWS orderHandler = new OrderHandlerTWS();

        /* Technical analysis */
        double SMA_200 = TwsThread.SMA_200;
        double RSI_2 = TwsThread.RSI_2;
        double SMA_5 = TwsThread.SMA_5;

        if(price != 0.0 && SMA_200 != 0.0 && RSI_2 != 0.0 && SMA_5 != 0.0) {

            long startMs = System.currentTimeMillis();

            TwsThread.liveData.setSumAnalysis(TwsThread.liveData.getSumAnalysis()+1);
            TwsThread.liveData.setCurrentPrice(price);
            TwsThread.liveData.update();

            if (TwsThread.liveData.getSumAnalysis() == 3){
                orderHandler.placeBuyOrder(price);
            }

            // TODO: Show technical analysis
            System.out.println("* START ******************************************");
            System.out.println("PRICE : "+price);
            System.out.println("SMA200 : "+SMA_200);
            System.out.println("RSI2 : "+RSI_2);
            System.out.println("SMA_5 : "+SMA_5);
            System.out.println("* END ********************************************");

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

            long stopMS = System.currentTimeMillis();
            TwsThread.liveData.setAnalysisTime(stopMS - startMs);
            TwsThread.liveData.update();
        }
    }
}
