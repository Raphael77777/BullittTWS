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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderManagerTest {

    @DisplayName("Create Buy Bracket Order")
    @Test
    void testBuyBracketOrder() {

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);

        assertEquals(3, orders.size());

        Order parent = orders.get(0);
        Order tp = orders.get(1);
        Order sl = orders.get(2);

        /* PARENT ORDER */
        int parentId = parent.orderId();
        assertEquals(Types.Action.BUY.toString(), parent.getAction());
        assertEquals(100, parent.totalQuantity());
        assertEquals(OrderType.LMT, parent.orderType());
        assertEquals(1.0, parent.lmtPrice());

        /* TP ORDER */
        assertEquals(parentId+1, tp.parentId());
        assertEquals(parentId+2, tp.orderId());
        assertEquals(Types.Action.SELL, tp.action());
        assertEquals(100, tp.totalQuantity());
        assertEquals(OrderType.LMT, tp.orderType());
        assertEquals(1.5, tp.lmtPrice());

        /* SL ORDER */
        assertEquals(parentId+1, sl.parentId());
        assertEquals(parentId+3, sl.orderId());
        assertEquals(Types.Action.SELL, sl.action());
        assertEquals(100, sl.totalQuantity());
        assertEquals(OrderType.STP, sl.orderType());
        assertEquals(0.5, sl.auxPrice());
    }

    @DisplayName("Create Sell Bracket Order")
    @Test
    void testSellBracketOrder() {

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Market");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 0, 0.5, 1.5);

        assertEquals(3, orders.size());

        Order parent = orders.get(0);
        Order tp = orders.get(1);
        Order sl = orders.get(2);

        /* PARENT ORDER */
        int parentId = parent.orderId();
        assertEquals(Types.Action.SELL.toString(), parent.getAction());
        assertEquals(100, parent.totalQuantity());
        assertEquals(OrderType.MKT, parent.orderType());

        /* TP ORDER */
        assertEquals(parentId+1, tp.parentId());
        assertEquals(parentId+2, tp.orderId());
        assertEquals(Types.Action.BUY, tp.action());
        assertEquals(100, tp.totalQuantity());
        assertEquals(OrderType.LMT, tp.orderType());
        assertEquals(0.5, tp.lmtPrice());

        /* SL ORDER */
        assertEquals(parentId+1, sl.parentId());
        assertEquals(parentId+3, sl.orderId());
        assertEquals(Types.Action.BUY, sl.action());
        assertEquals(100, sl.totalQuantity());
        assertEquals(OrderType.STP, sl.orderType());
        assertEquals(1.5, sl.auxPrice());
    }

    @DisplayName("Round 0.05")
    @Test
    void testRound() {
        OrderManager orderManager = new OrderManager();

        assertEquals(0.00, orderManager.round(0.000));
        assertEquals(0.05, orderManager.round(0.025));
        assertEquals(0.10, orderManager.round(0.075));
        assertEquals(0.10, orderManager.round(0.100));
        assertEquals(0.05, orderManager.round(0.074999999999));
        assertEquals(1.00, orderManager.round(0.999999));

    }
}
