package CustomException;

public class MarketClosedException extends Exception {
    public MarketClosedException() {
        super("Data is currently not available as the market is closed.");
    }
}
