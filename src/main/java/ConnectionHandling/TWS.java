package ConnectionHandling;

import DataHandling.AlphaVantageData;
import DataHandling.LiveData;
import DataHandling.StrategyData;
import DataHandling.TransactionData;
import MarketDataHandling.TopMktDataHandlerImplementation;
import TechnicalAnalysisHandling.AdapterRSI;
import TechnicalAnalysisHandling.AdapterSMA;
import com.ib.client.Contract;
import com.ib.controller.ApiController;

public class TWS implements Runnable{

    public static StrategyData strategyData;
    public static TransactionData transactionData;
    public static LiveData liveData;
    public static AlphaVantageData alphaVantageData;

    public static AdapterSMA adapterSMA;
    public static AdapterRSI adapterRSI;

    //We need instances of our logger implementation
    static LoggerImplementation inLogger = new LoggerImplementation();
    static LoggerImplementation outLogger = new LoggerImplementation();

    //We need an instance of our connection handler implementation
    static ConnectionHandlerImplementation connectionHandler = new ConnectionHandlerImplementation();

    //We need an instance of the ApiController
    public static ApiController apiController = new ApiController(connectionHandler, inLogger, outLogger);

    //We need an instance of the mkt data handler implementation
    static TopMktDataHandlerImplementation mktDataHandler = new TopMktDataHandlerImplementation();

    //We also need to initialize our contract
    public static Contract initializeContract(){

        //TODO : Get contract from strategy_data
        String asset = TWS.strategyData.getAsset();

        Contract nq = new Contract();
        nq.symbol("EUR");
        nq.secType("CASH");
        nq.currency("USD");
        nq.exchange("IDEALPRO");
        return nq;
    }

    public TWS(StrategyData strategyData, TransactionData transactionData, LiveData liveData, AlphaVantageData alphaVantageData) {
        TWS.strategyData = strategyData;
        TWS.transactionData = transactionData;
        TWS.liveData = liveData;
        TWS.alphaVantageData = alphaVantageData;

        adapterSMA = new AdapterSMA(alphaVantageData);
        adapterRSI = new AdapterRSI(alphaVantageData);
    }

    @Override
    public void run() {
        apiController.connect("localhost", 7497, 7, "@Raphael");
        apiController.reqTopMktData(initializeContract(), "221",false, false, mktDataHandler);
    }
}
