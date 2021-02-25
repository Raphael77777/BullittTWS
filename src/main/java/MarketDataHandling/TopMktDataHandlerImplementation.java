package MarketDataHandling;

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
            new EntrySignalA(prices, prices.size()+200); //Check for signal
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
