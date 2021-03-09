package MarketDataHandling;

import EventProcessingHandling.StrategyEngine;
import ConnectionHandling.TwsIB;

import java.sql.Time;
import java.time.LocalTime;

public class MarketAdapter {

    /* MS VALUES */
    private long referenceMS;
    private final long delayMS;

    public MarketAdapter() {
        referenceMS = System.currentTimeMillis();

        /* SET TIMESCALE */
        String timescale = TwsIB.strategyData.getTimescale();
        String delayString = timescale.replace(" sec","");
        delayMS = Long.parseLong(delayString)*1000;

        /* SET START TIME */
        LocalTime startTime = new Time(System.currentTimeMillis()).toLocalTime();
        TwsIB.liveData.setStartTime(startTime);
        TwsIB.liveData.update();
    }

    public void tickPrice(double price) {
        /* RETURN IF PRICE IS WRONG */
        if (price == -1.0){
            return;
        }

        /* FORWARD PRICE ACCORDING TO TIMESCALE */
        long currentMS = System.currentTimeMillis();
        if ((currentMS - referenceMS) >= delayMS){
            referenceMS = currentMS;
            System.out.println("> ANALYSIS DONE AT "+new Time(System.currentTimeMillis()).toString());
            new StrategyEngine(price); //Will check for signal
        }
    }
}
