package TechnicalAnalysisHandlingTest;

import MarketDataHandling.TechnicalAnalysisHandling.AdapterRSI;
import StorageHandling.AlphaVantageData;
import UserInterface.JFrameBTWS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdapterRSITest {

    @DisplayName("Relative Strength Index")
    @Test
    void testGetRelativeStrengthIndex() {

        AlphaVantageData alphaVantageData = null;

        try {
            JFrameBTWS.getInstance().setVisible(false);
            alphaVantageData = JFrameBTWS.getInstance().loadAlphaVantage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdapterRSI adapterRSI = new AdapterRSI(alphaVantageData);

        double RSI_2 = 0.0;

        try {
            RSI_2 = adapterRSI.get("EURUSD", "1min", "2", "open");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotEquals(0, RSI_2);
    }
}
