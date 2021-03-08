package MarketDataHandling.TechnicalAnalysisHandling.CustomException;

public class MissingApiKeyException extends Exception {
    public MissingApiKeyException() {
        super("The API key for AlphaVantage API's is not valid.");
    }
}
