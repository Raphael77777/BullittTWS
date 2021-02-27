package RiskHandling;

import com.ib.client.Order;

import java.util.List;

public class RiskManagementSystem {
    public static boolean verifyOrder (List<Order> bracketOrder) {

        //TODO : Implement verification condition for order
        if (bracketOrder.size() == 3){
            return true;
        }

        return false;
    }
}
