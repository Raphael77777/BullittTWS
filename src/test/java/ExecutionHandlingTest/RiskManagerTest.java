package ExecutionHandlingTest;

import ConnectionHandling.TwsIB;
import ExecutionHandling.OrderManager;
import StorageHandling.StrategyData;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RiskManagerTest {

    @DisplayName("Verify buy order unit")
    @Test
    void testVerifyBuyOrder() {

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        assertEquals(3, orders.size());

        Order parent = orders.get(0);
        Order tp = orders.get(1);
        Order sl = orders.get(2);

        assertEquals(parent.orderId()+1, tp.parentId());
        assertEquals(parent.orderId()+1, sl.parentId());

        assertEquals(parent.totalQuantity(), tp.totalQuantity());
        assertEquals(parent.totalQuantity(), sl.totalQuantity());

        if (TwsIB.strategyData.getOrder().equals("Market")){
            assertEquals(parent.orderType(), OrderType.MKT);
        }else {
            assertEquals(parent.orderType(), OrderType.LMT);
        }

        if (TwsIB.strategyData.getOrder().equals("Limit")){
            assertEquals(parent.orderType(), OrderType.LMT);
        }else {
            assertEquals(parent.orderType(), OrderType.MKT);
        }

        assertNotEquals(0, tp.lmtPrice());
        assertNotEquals(0, sl.auxPrice());

        if (parent.getAction().equals("BUY")){
            assertFalse(tp.lmtPrice() <= sl.auxPrice());
        }else if (parent.getAction().equals("SELL")){
            assertFalse(tp.lmtPrice() >= sl.auxPrice());
        }
    }

    @DisplayName("Verify sell order unit")
    @Test
    void testVerifySellOrder() {

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.SELL, 54, 12, 11.2, 13.4);
        assertEquals(3, orders.size());

        Order parent = orders.get(0);
        Order tp = orders.get(1);
        Order sl = orders.get(2);

        assertEquals(parent.orderId()+1, tp.parentId());
        assertEquals(parent.orderId()+1, sl.parentId());

        assertEquals(parent.totalQuantity(), tp.totalQuantity());
        assertEquals(parent.totalQuantity(), sl.totalQuantity());

        if (TwsIB.strategyData.getOrder().equals("Market")){
            assertEquals(parent.orderType(), OrderType.MKT);
        }else {
            assertEquals(parent.orderType(), OrderType.LMT);
        }

        if (TwsIB.strategyData.getOrder().equals("Limit")){
            assertEquals(parent.orderType(), OrderType.LMT);
        }else {
            assertEquals(parent.orderType(), OrderType.MKT);
        }

        assertNotEquals(0, tp.lmtPrice());
        assertNotEquals(0, sl.auxPrice());

        if (parent.getAction().equals("BUY")){
            assertFalse(tp.lmtPrice() <= sl.auxPrice());
        }else if (parent.getAction().equals("SELL")){
            assertFalse(tp.lmtPrice() >= sl.auxPrice());
        }
    }
}
