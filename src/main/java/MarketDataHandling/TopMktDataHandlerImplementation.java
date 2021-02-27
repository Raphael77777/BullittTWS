package MarketDataHandling;

import ConnectionHandling.TWS;
import CustomException.MarketClosedException;
import CustomException.MissingApiKeyException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import ExecutionHandling.EntrySignalA;
import com.ib.client.TickAttrib;
import com.ib.client.TickType;
import com.ib.controller.ApiController;

import java.util.ArrayList;

public class TopMktDataHandlerImplementation implements ApiController.ITopMktDataHandler {

    ArrayList<Double> prices = new ArrayList<>();

    @Override
    public void tickPrice(TickType tickType, double v, TickAttrib tickAttrib) {
        //Do something with the price response
        if(tickType.equals(TickType.ASK)) {
            prices.add(0, v);
            System.out.println("Current Price: "+v);

            //TODO : Only launch signal every 60sec or others using strategy_data
            String timescale = TWS.strategyData.getTimescale();

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
            }
        }
    }

    @Override
    public void tickSize(TickType tickType, int i) {
        //Do something with the volume response
    }

    @Override
    public void tickString(TickType tickType, String s) {
        //Do something with a specific tickType
    }

    @Override
    public void tickSnapshotEnd() {
        //Do something on the end of the snapshot
    }

    @Override
    public void marketDataType(int i) {
        //Do something with the type of market data
    }

    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }
}
