package MarketDataHandling;

import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import ExecutionHandling.EntrySignalA;
import IbAccountDataHandling.TwsThread;
import com.ib.client.TickAttrib;
import com.ib.client.TickType;
import com.ib.controller.ApiController;

import java.util.ArrayList;

public class MarketAdapter {

    private long referenceMS;
    private long delayMS;

    public MarketAdapter() {
        referenceMS = System.currentTimeMillis();

        String timescale = TwsThread.strategyData.getTimescale();
        String delayString = timescale.replace(" sec","");
        delayMS = Long.parseLong(delayString)*1000;
    }

    public void tickPrice(int tickerId, int field, double price, TickAttrib attribs) {

        /* Launch signal according to timescale using strategy_data */
        long currentMS = System.currentTimeMillis();
        if ((currentMS - referenceMS) >= delayMS){
            referenceMS = currentMS;

            try {
                new EntrySignalA(price); //Check for signal
            } catch (MissingApiKeyException e) {
                e.printStackTrace();
            } catch (NoNetworkException e) {
                e.printStackTrace();
            } catch (OverloadApiUseException e) {
                e.printStackTrace();
            } catch (MarketClosedException e) {
                e.printStackTrace();
            }
        }
    }
}
