package ConnectionHandling;

import MarketDataHandling.TopMktDataHandlerImplementation;
import com.ib.client.Contract;
import com.ib.controller.ApiController;

public class Run {

    //We need instances of our logger implementation
    static LoggerImplementation inLogger = new LoggerImplementation();
    static LoggerImplementation outLogger = new LoggerImplementation();

    //We need an instance of our connection handler implementation
    static ConnectionHandlerImplementation connectionHandler = new ConnectionHandlerImplementation();

    //We need an instance of the ApiController
    public static ApiController apiController = new ApiController(connectionHandler, inLogger, outLogger);

    //We need an instance of the mkt data handler implemnetation
    static TopMktDataHandlerImplementation mktDataHandler = new TopMktDataHandlerImplementation();

    //We also need to initialize our contract
    static Contract initializeContract(){
        Contract nq = new Contract();
        nq.symbol("EUR");
        nq.secType("CASH");
        nq.currency("USD");
        nq.exchange("IDEALPRO");
        return nq;
    }

    public static void main(String _[]){
        apiController.connect("localhost", 7497, 7, "@Raphael");
        apiController.reqTopMktData(initializeContract(), "221",false, false, mktDataHandler);
    }
}
