package CustomException;

public class NoNetworkException extends Exception {
    public NoNetworkException() {
        super("The data is currently not available as no internet connection could be established.");
    }
}
