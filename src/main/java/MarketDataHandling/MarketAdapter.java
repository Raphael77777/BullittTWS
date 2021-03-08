package MarketDataHandling;

import ExecutionHandling.EntrySignalA;
import IbAccountDataHandling.TwsThread;

import java.sql.Time;
import java.time.LocalTime;

public class MarketAdapter {

    private long referenceMS;
    private final long delayMS;

    public MarketAdapter() {
        referenceMS = System.currentTimeMillis();

        String timescale = TwsThread.strategyData.getTimescale();
        String delayString = timescale.replace(" sec","");
        delayMS = Long.parseLong(delayString)*1000;

        LocalTime startTime = new Time(System.currentTimeMillis()).toLocalTime();
        TwsThread.liveData.setStartTime(startTime);
        TwsThread.liveData.update();
    }

    public void tickPrice(double price) {

        /* Launch signal according to timescale using strategy_data */
        if (price == -1.0){
            return;
        }

        long currentMS = System.currentTimeMillis();
        if ((currentMS - referenceMS) >= delayMS){
            referenceMS = currentMS;
            new EntrySignalA(price); //Check for signal
        }
    }
}
