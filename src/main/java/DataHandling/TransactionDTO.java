package DataHandling;

import IbAccountDataHandling.TwsThread;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.ib.client.Types;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class TransactionDTO {

    /* PARENT ORDER */
    private int orderId;
    private Types.Action action;
    private String asset;
    private Double quantity;
    private OrderType type;
    private double limitPrice;
    private double avgFillPrice;
    private Date date;
    private Time time;
    private String status;

    /* TAKE PROFIT ORDER */
    private int orderId_tp;
    private OrderType type_tp;
    private double takeProfitLimitPrice;
    private String status_tp;
    private double avgFillPrice_tp;

    /* STOP LOSS ORDER */
    private int orderId_sl;
    private OrderType type_sl;
    private double stopLossPrice;
    private String status_sl;
    private double avgFillPrice_sl;

    public TransactionDTO (List<Order> bracketOrder){
        /* Get parent order */
        Order parentOrder = bracketOrder.get(0);

        orderId = parentOrder.orderId()+1;
        action = parentOrder.action();
        asset = TwsThread.strategyData.getAsset();
        quantity = parentOrder.totalQuantity();
        type = parentOrder.orderType();
        limitPrice = parentOrder.lmtPrice();
        avgFillPrice = 0;
        date = new Date(System.currentTimeMillis());
        time = new Time(System.currentTimeMillis());
        status = "";

        /* Get take profit order */
        Order takeProfitOrder = bracketOrder.get(1);
        orderId_tp = takeProfitOrder.orderId();
        type_tp = takeProfitOrder.orderType();
        takeProfitLimitPrice = takeProfitOrder.lmtPrice();
        status_tp = "";
        avgFillPrice_tp = 0;

        /* Get stop loss order */
        Order stopLossOrder = bracketOrder.get(2);
        orderId_sl = stopLossOrder.orderId();
        type_sl = stopLossOrder.orderType();
        stopLossPrice = stopLossOrder.auxPrice();
        status_sl = "";
        avgFillPrice_sl = 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Types.Action getAction() {
        return action;
    }

    public void setAction(Types.Action action) {
        this.action = action;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public double getAvgFillPrice() {
        return avgFillPrice;
    }

    public void setAvgFillPrice(double avgFillPrice) {
        this.avgFillPrice = avgFillPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId_tp() {
        return orderId_tp;
    }

    public void setOrderId_tp(int orderId_tp) {
        this.orderId_tp = orderId_tp;
    }

    public OrderType getType_tp() {
        return type_tp;
    }

    public void setType_tp(OrderType type_tp) {
        this.type_tp = type_tp;
    }

    public double getTakeProfitLimitPrice() {
        return takeProfitLimitPrice;
    }

    public void setTakeProfitLimitPrice(double takeProfitLimitPrice) {
        this.takeProfitLimitPrice = takeProfitLimitPrice;
    }

    public String getStatus_tp() {
        return status_tp;
    }

    public void setStatus_tp(String status_tp) {
        this.status_tp = status_tp;
    }

    public int getOrderId_sl() {
        return orderId_sl;
    }

    public void setOrderId_sl(int orderId_sl) {
        this.orderId_sl = orderId_sl;
    }

    public OrderType getType_sl() {
        return type_sl;
    }

    public void setType_sl(OrderType type_sl) {
        this.type_sl = type_sl;
    }

    public double getStopLossPrice() {
        return stopLossPrice;
    }

    public void setStopLossPrice(double stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public String getStatus_sl() {
        return status_sl;
    }

    public void setStatus_sl(String status_sl) {
        this.status_sl = status_sl;
    }

    public double getAvgFillPrice_tp() {
        return avgFillPrice_tp;
    }

    public void setAvgFillPrice_tp(double avgFillPrice_tp) {
        this.avgFillPrice_tp = avgFillPrice_tp;
    }

    public double getAvgFillPrice_sl() {
        return avgFillPrice_sl;
    }

    public void setAvgFillPrice_sl(double avgFillPrice_sl) {
        this.avgFillPrice_sl = avgFillPrice_sl;
    }
}
