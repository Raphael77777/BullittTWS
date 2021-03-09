package ExecutionHandling;

import ConnectionHandling.TwsIB;
import com.ib.client.Order;
import com.ib.client.OrderType;

import java.util.List;

public class RiskManager {
    public static boolean verifyOrder (List<Order> bracketOrder) {

        /* Order Integrity Verification */
        if (bracketOrder.size() != 3){
            return false;
        }

        Order parent = bracketOrder.get(0);
        Order tp = bracketOrder.get(1);
        Order sl = bracketOrder.get(2);

        if (parent.orderId()+1 != tp.parentId()){
            return false;
        }

        if (parent.orderId()+1 != sl.parentId()){
            return false;
        }

        if (parent.totalQuantity() != tp.totalQuantity()
                || parent.totalQuantity() != sl.totalQuantity()){
            return false;
        }

        if (parent.orderType() == OrderType.LMT
                && TwsIB.strategyData.getOrder().equals("Market")){
            return false;
        }

        if (parent.orderType() == OrderType.MKT
                && TwsIB.strategyData.getOrder().equals("Limit")){
            return false;
        }

        if (tp.lmtPrice() == 0
                || sl.auxPrice() == 0){
            return false;
        }

        if (parent.getAction().equals("BUY")
                && tp.lmtPrice() <= sl.auxPrice()){
            return false;
        }

        if (parent.getAction().equals("SELL")
                && tp.lmtPrice() >= sl.auxPrice()){
            return false;
        }

        return true;
    }
}
