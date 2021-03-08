package ConnectionHandlingTest;

import ConnectionHandling.InputAdapterIB;
import ConnectionHandling.OutputAdapterIB;
import ConnectionHandling.TwsIB;
import ExecutionHandling.OrderManager;
import StorageHandling.*;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.Types;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputAdapterIBTest {

    @DisplayName("Order Status")
    @Test
    void testOrderStatus() {

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        HistoryData historyData = new HistoryData();
        TwsIB.historyData = historyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        TwsIB.historyData.addTransactions(new TransactionDTO(orders));

        int parentId = orders.get(0).orderId()+1;

        InputAdapterIB inputAdapterIB = new InputAdapterIB();

        inputAdapterIB.orderStatus(parentId, "Submitted", 0.0, 100.0, 1.345, 1468146411, 0, 0.0, 2, null, 0.0);
        assertEquals("Submitted", historyData.getTransaction(parentId).getStatus());
        assertEquals(1.345, historyData.getTransaction(parentId).getAvgFillPrice());

        inputAdapterIB.orderStatus(parentId+1, "Submitted", 100.0, 0.0, 1.56, 1468146411, parentId, 0.0, 2, null, 0.0);
        assertEquals("Submitted", historyData.getTransaction(parentId).getStatus_tp());
        assertEquals(1.56, historyData.getTransaction(parentId).getAvgFillPrice_tp());

        inputAdapterIB.orderStatus(parentId+2, "Cancelled", 100.0, 0.0, 0.00, 1468146411, parentId, 0.0, 2, null, 0.0);
        assertEquals("Cancelled", historyData.getTransaction(parentId).getStatus_sl());
        assertEquals(0.00, historyData.getTransaction(parentId).getAvgFillPrice_sl());
    }

    @DisplayName("Next Valid Id")
    @Test
    void testNextValidId() {

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.nextValidId(4);
        assertEquals(4, TwsIB.getNextValidID());
        assertEquals(5, TwsIB.getNextValidID());
        assertEquals(6, TwsIB.getNextValidID());
        assertEquals(7, TwsIB.getNextValidID());
        assertEquals(8, TwsIB.getNextValidIDOrder());
        assertEquals(11, TwsIB.getNextValidID());
    }

    @DisplayName("Managed Accounts")
    @Test
    void testManagedAccounts() {
        AccountData accountData = new AccountData();
        TwsIB.accountData = accountData;

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.managedAccounts("DU907856");

        assertEquals("DU907856", accountData.getAccountId());
    }

    @DisplayName("Position")
    @Test
    void testPosition() {

        StrategyData strategyData = new StrategyData();
        strategyData.setAsset("EUR.USD");
        TwsIB.strategyData = strategyData;

        TwsIB.positionData = new PositionData();

        Contract nq = OutputAdapterIB.initializeContract();

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.position("DU907856", nq, 90.0, 1.78);

        assertEquals("DU907856", TwsIB.positionData.getAccount());
        assertEquals(nq, TwsIB.positionData.getContract());
        assertEquals(90.0, TwsIB.positionData.getPos());
        assertEquals(1.78, TwsIB.positionData.getAvgCost());
    }

    @DisplayName("Account Summary")
    @Test
    void testAccountSummary() {

        TwsIB.accountData = new AccountData();

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.accountSummary(13, "DU099009", "NetLiquidation", "1240.00", "USD");
        assertEquals(1240.00, TwsIB.accountData.getNetLiquidationValue());

        inputAdapterIB.accountSummary(13, "DU099009", "BuyingPower", "2000.00", "USD");
        assertEquals(2000.00, TwsIB.accountData.getBuyingPower());

        inputAdapterIB.accountSummary(13, "DU099009", "AvailableFunds", "1300.00", "USD");
        assertEquals(1300.00, TwsIB.accountData.getAvailableFunds());
        assertEquals("USD", TwsIB.accountData.getCurrency());

        inputAdapterIB.accountSummary(13, "DU099009", "Cushion", "0.990001", "USD");
        assertEquals(99.00, TwsIB.accountData.getCushion());

        inputAdapterIB.accountSummary(13, "DU099009", "FullInitMarginReq", "500.00", "USD");
        assertEquals(500.00, TwsIB.accountData.getMarginReq());
    }

    @DisplayName("Pnl")
    @Test
    void testPnl() {

        TwsIB.accountData = new AccountData();
        TwsIB.liveData = new LiveData();

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.pnl(12, -23.987, 2.4563, 21.765);

        assertEquals(-23.99, TwsIB.accountData.getDailyPNL());
        assertEquals(-23.99, TwsIB.liveData.getDailyPNL());

        assertEquals(2.46, TwsIB.accountData.getUnrealizedPNL());
        assertEquals(2.46, TwsIB.liveData.getUnrealizedPNL());

        assertEquals(21.77, TwsIB.accountData.getRealizedPNL());
        assertEquals(21.77, TwsIB.liveData.getRealizedPNL());
    }

    @DisplayName("Pnl Single")
    @Test
    void testPnlSingle() {

        TwsIB.positionData = new PositionData();

        InputAdapterIB inputAdapterIB = new InputAdapterIB();
        inputAdapterIB.pnlSingle(14, 345, 34.998, -20.2345, 59.098, 20.1444);

        assertEquals(35.00, TwsIB.positionData.getDailyPnL());
        assertEquals(-20.23, TwsIB.positionData.getUnrealizedPnL());
        assertEquals(59.10, TwsIB.positionData.getRealizedPnL());
        assertEquals(20.14, TwsIB.positionData.getValue());
    }
}
