package TechnicalAnalysisHandlingTest;

import MarketDataHandling.TechnicalAnalysisHandling.AdapterRSI;
import MarketDataHandling.TechnicalAnalysisHandling.AdapterSMA;
import StorageHandling.AlphaVantageData;
import UserInterface.JFrameBTWS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdapterSMATest {

    @DisplayName("Simple Moving Average")
    @Test
    void testGetSimpleMovingAverage() {

        AlphaVantageData alphaVantageData = null;

        try {
            JFrameBTWS.getInstance().setVisible(false);
            alphaVantageData = JFrameBTWS.getInstance().loadAlphaVantage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdapterSMA adapterSMA = new AdapterSMA(alphaVantageData);

        double SMA_200 = 0.0;
        double SMA_5 = 0.0;

        try {
            SMA_200 = adapterSMA.get("EURUSD", "1min", "200", "open");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotEquals(0, SMA_200);

        try {
            SMA_5 = adapterSMA.get("EURUSD", "1min", "5", "open");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotEquals(0, SMA_5);
    }
}
