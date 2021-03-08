package MarketDataHandling.TechnicalAnalysisHandling.CustomException;

public class OverloadApiUseException extends Exception {
    public OverloadApiUseException() {
        super("The limit on the number of API requests has been reached");
    }
}