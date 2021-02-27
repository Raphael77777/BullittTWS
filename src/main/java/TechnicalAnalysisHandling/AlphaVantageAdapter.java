package TechnicalAnalysisHandling;

import CustomException.MissingApiKeyException;
import CustomException.MarketClosedException;
import CustomException.NoNetworkException;
import CustomException.OverloadApiUseException;
import DataHandling.AlphaVantageData;
import UserInterface.Screen.Observer;

public abstract class AlphaVantageAdapter implements Observer {

    private AlphaVantageData alphaVantageData;

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
