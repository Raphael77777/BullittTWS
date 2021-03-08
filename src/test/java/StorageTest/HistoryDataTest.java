package StorageTest;

import ConnectionHandling.TwsIB;
import ExecutionHandling.OrderManager;
import StorageHandling.HistoryData;
import StorageHandling.StrategyData;
import StorageHandling.TransactionDTO;
import com.ib.client.Order;
import com.ib.client.Types;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryDataTest {

    @DisplayName("Add Transactions")
    @Test
    void testAddTransactions() {

        HistoryData historyData = new HistoryData();

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        TransactionDTO transactionDTO = new TransactionDTO(orders);

        historyData.addTransactions(transactionDTO);

        List<TransactionDTO> transactions = historyData.getTransactions();
        assertEquals(1, transactions.size());
    }

    @DisplayName("Number of Buy")
    @Test
    void testGetNumberBuy() {

        HistoryData historyData = new HistoryData();

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        assertEquals(3, historyData.getNumberBuy());
    }

    @DisplayName("Number of Sell")
    @Test
    void testGetNumberSell() {

        HistoryData historyData = new HistoryData();

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        historyData.addTransactions(new TransactionDTO(orders));

        assertEquals(3, historyData.getNumberSell());
    }

    @DisplayName("Total Exposition")
    @Test
    void testGetExposition() {

        HistoryData historyData = new HistoryData();

        StrategyData strategyData = new StrategyData();
        strategyData.setOrder("Limit");
        TwsIB.strategyData = strategyData;

        List<Order> orders = OrderManager.BracketOrder(Types.Action.SELL, 100, 1.0, 1.5, 0.5);
        int ordersId = orders.get(0).orderId();
        historyData.addTransactions(new TransactionDTO(orders));
        historyData.getTransaction(ordersId+1).setAvgFillPrice(1.0);

        assertEquals(100.00, historyData.getExposition());

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        ordersId = orders.get(0).orderId();
        historyData.addTransactions(new TransactionDTO(orders));
        historyData.getTransaction(ordersId+1).setAvgFillPrice(1.0);

        assertEquals(200.00, historyData.getExposition());

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        ordersId = orders.get(0).orderId();
        historyData.addTransactions(new TransactionDTO(orders));
        historyData.getTransaction(ordersId+1).setAvgFillPrice(1.0);

        assertEquals(300.00, historyData.getExposition());

        orders = OrderManager.BracketOrder(Types.Action.BUY, 100, 1.0, 1.5, 0.5);
        ordersId = orders.get(0).orderId();
        historyData.addTransactions(new TransactionDTO(orders));
        historyData.getTransaction(ordersId+1).setAvgFillPrice(1.0);

        assertEquals(400.00, historyData.getExposition());
    }
}
