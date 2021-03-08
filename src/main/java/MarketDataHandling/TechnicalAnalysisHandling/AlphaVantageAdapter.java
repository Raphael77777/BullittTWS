package MarketDataHandling.TechnicalAnalysisHandling;

import MarketDataHandling.TechnicalAnalysisHandling.CustomException.MissingApiKeyException;
import MarketDataHandling.TechnicalAnalysisHandling.CustomException.MarketClosedException;
import MarketDataHandling.TechnicalAnalysisHandling.CustomException.NoNetworkException;
import MarketDataHandling.TechnicalAnalysisHandling.CustomException.OverloadApiUseException;
import StorageHandling.AlphaVantageData;
import UserInterface.Screen.Observer;

public abstract class AlphaVantageAdapter implements Observer {

    private final AlphaVantageData alphaVantageData;

    protected String API_KEY = "";

    public AlphaVantageAdapter(AlphaVantageData alphaVantageData) {
        this.alphaVantageData = alphaVantageData;
        this.alphaVantageData.registerObserver(this);

        update();
    }

    public abstract double get (String symbol, String interval, String time_period, String series_type) throws MarketClosedException, OverloadApiUseException, NoNetworkException, MissingApiKeyException;

    @Override
    public void update() {
        API_KEY = alphaVantageData.getAPI_KEY();
    }
}
