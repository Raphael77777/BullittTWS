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

    ArrayList<Double> prices = new ArrayList<>();

    public void tickPrice(int tickerId, int field, double price, TickAttrib attribs) {

        prices.add(0, price);
        System.out.println("CP : "+price);

        /**
        //TODO : Only launch signal every 60sec or others using strategy_data
        String timescale = TwsThread.strategyData.getTimescale();

        try {
            new EntrySignalA(prices, prices.size()+200); //Check for signal
        } catch (MissingApiKeyException e) {
            e.printStackTrace();
        } catch (NoNetworkException e) {
            e.printStackTrace();
        } catch (OverloadApiUseException e) {
            e.printStackTrace();
        } catch (MarketClosedException e) {
            e.printStackTrace();
        }*/

    }
}
