package CustomException;

public class MissingApiKeyException extends Exception {
    public MissingApiKeyException() {
        super("The API key for AlphaVantage API's is not valid.");
    }
    public MissingApiKeyException(String message) {
        super(message);
    }
}
