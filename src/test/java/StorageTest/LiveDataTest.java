package StorageTest;

import StorageHandling.LiveData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LiveDataTest {

    @DisplayName("Analysis Time")
    @Test
    void testSetAnalysisTime() {

        LiveData liveData = new LiveData();

        liveData.setAnalysisTime(5.00);

        assertEquals(5.00, liveData.getMinAnalysisTime());
        assertEquals(5.00, liveData.getMaxAnalysisTime());
        assertEquals(5.00, liveData.getAnalysisTime());
        assertEquals(5.00, liveData.getAverageAnalysisTime());

        liveData.setAnalysisTime(2.00);

        assertEquals(2.00, liveData.getMinAnalysisTime());
        assertEquals(5.00, liveData.getMaxAnalysisTime());
        assertEquals(2.00, liveData.getAnalysisTime());
        assertEquals(3.50, liveData.getAverageAnalysisTime());

        liveData.setAnalysisTime(8.00);

        assertEquals(2.00, liveData.getMinAnalysisTime());
        assertEquals(8.00, liveData.getMaxAnalysisTime());
        assertEquals(8.00, liveData.getAnalysisTime());
        assertEquals(5.00, liveData.getAverageAnalysisTime());

        liveData.setAnalysisTime(0.00);

        assertEquals(2.00, liveData.getMinAnalysisTime());
        assertEquals(8.00, liveData.getMaxAnalysisTime());
        assertEquals(5.00, liveData.getAnalysisTime());
        assertEquals(5.00, liveData.getAverageAnalysisTime());
    }

    @DisplayName("Increment Analysis")
    @Test
    void testIncrementAnalysis() {

        LiveData liveData = new LiveData();
        int iOrder = 0;

        for (int i = 0; i<=20; i++){
            assertEquals(i, liveData.getSumAnalysis());
            liveData.incrementAnalysis();

            if (i%2==1){
                iOrder++;
                liveData.incrementOrder();

                double d = (1.0*iOrder)/(1.0*i+1);
                BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
                bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                assertEquals(bigDecimal.doubleValue(), liveData.getCompellingAnalysisPercentage());
            }
        }
    }

    @DisplayName("Variation Opening")
    @Test
    void testVarOpening() {

        LiveData liveData = new LiveData();

        liveData.setCurrentPrice(1.0);
        assertEquals(0.00, liveData.getVarOpening());

        liveData.setCurrentPrice(1.0);
        assertEquals(0.00, liveData.getVarOpening());

        liveData.setCurrentPrice(2.0);
        BigDecimal var = new BigDecimal(Double.toString((100.0*2.0/1.0)-100));
        var = var.setScale(4, RoundingMode.HALF_UP);
        assertEquals( var.doubleValue(), liveData.getVarOpening());

        liveData.setCurrentPrice(0.5);
        var = new BigDecimal(Double.toString((100.0*0.5/1.0)-100));
        var = var.setScale(4, RoundingMode.HALF_UP);
        assertEquals( var.doubleValue(), liveData.getVarOpening());
    }
}
