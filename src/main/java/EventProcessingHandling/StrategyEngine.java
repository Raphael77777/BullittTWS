package EventProcessingHandling;

import ConnectionHandling.TwsIB;
import ExecutionHandling.OrderManager;

public class StrategyEngine {

    private final double price;
    private final double SMA_200;
    private final double RSI_2;
    private final double SMA_5;

    public StrategyEngine(double price){
        this.price = price;
        OrderManager orderHandler = new OrderManager();

        /* Technical analysis */
        SMA_200 = TwsIB.SMA_200;
        RSI_2 = TwsIB.RSI_2;
        SMA_5 = TwsIB.SMA_5;

        if(price != 0.0 && SMA_200 != 0.0 && RSI_2 != 0.0 && SMA_5 != 0.0) {

            long startMs = System.currentTimeMillis();

            TwsIB.liveData.setSumAnalysis(TwsIB.liveData.getSumAnalysis()+1);
            TwsIB.liveData.setCurrentPrice(price);
            TwsIB.liveData.update();

            //TODO : TO REMOVE
            if (TwsIB.liveData.getSumAnalysis() == 3){
                orderHandler.placeBuyOrder(price);
            }

            /* price > SMA(200) && RSI(2) < 10 */
            /* Adapt RSI_LIMIT_BUY to accuracy using strategy_data */
            double accuracy = TwsIB.strategyData.getAccuracy();
            double RSI_LIMIT_BUY = 10 - (accuracy*5);
            if (price > SMA_200 && RSI_2 < RSI_LIMIT_BUY){

                TwsIB.liveData.setSumOrder(TwsIB.liveData.getSumOrder()+1);
                TwsIB.liveData.update();

                orderHandler.placeBuyOrder(price);
                System.out.println("> BUY ORDER HAS BEEN PLACED NOW");
                print();
            }

            /* price < SMA(200) && RSI(2) > 90 */
            /* Adapt RSI_LIMIT_SELL to accuracy using strategy_data */
            double RSI_LIMIT_SELL = 90 + (accuracy*5);
            if (price < SMA_200 && RSI_2 > RSI_LIMIT_SELL){

                TwsIB.liveData.setSumOrder(TwsIB.liveData.getSumOrder()+1);
                TwsIB.liveData.update();

                orderHandler.placeSellOrder(price);
                System.out.println("> SELL ORDER HAS BEEN PLACED NOW");
                print();
            }

            long stopMS = System.currentTimeMillis();
            TwsIB.liveData.setAnalysisTime(stopMS - startMs);
            TwsIB.liveData.update();
        }
    }

    public void print(){
        // TODO: Show technical analysis
        System.out.println("* START ******************************************");
        System.out.println("PRICE : "+price);
        System.out.println("SMA200 : "+SMA_200);
        System.out.println("RSI2 : "+RSI_2);
        System.out.println("SMA_5 : "+SMA_5);
        System.out.println("* END ********************************************");
    }
}
