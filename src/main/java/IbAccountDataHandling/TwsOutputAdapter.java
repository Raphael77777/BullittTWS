package IbAccountDataHandling;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import com.ib.client.TagValue;

import java.util.ArrayList;

public class TwsOutputAdapter {

    private EClientSocket m_client;

    public void init() throws InterruptedException {
        TwsInputAdapter twsInputAdapter = new TwsInputAdapter();

        /* onConnect() */
        m_client = twsInputAdapter.onConnect();

        /* onReqManagedAccts, onRequestPositions, onRequestAccountSummary */
        onReqManagedAccts();
        onRequestPositions();
        onRequestAccountSummary();

        /* onReqPnL */
        Thread.sleep(3000);
        onReqPnL();

        /* onReqPnLSingle */
        Thread.sleep(3000);
        onReqPnLSingle();

        /** ACCOUNTS SCREEN */
        //TODO : reqAllOpenOrders();
        //TODO : reqAllOpenOrders();

        /** TRADING ENGINE */
        //TODO : onReqMktData();
        //TODO : onReqMktData();

        //TODO : onPlaceOrder();
        //onPlaceOrder();

        /** BACKGROUND SCREEN */
        //TODO : onGlobalCancel();
        //TODO : onGlobalCancel();
    }

    public void onRequestAccountSummary() {
        m_client.reqAccountSummary( 5, "All", "NetLiquidation,BuyingPower,AvailableFunds,Cushion,FullInitMarginReq");
    }
    public void onCancelAccountSummary() {
        m_client.cancelAccountSummary(5);
    }

    public void onReqMktData() {
        m_client.reqMktData( 101, initializeContract(), "221", false, false, new ArrayList<TagValue>());
    }
    public void onCancelMktData() {
        m_client.cancelMktData( 101);
    }

    public void onReqManagedAccts() {
        m_client.reqManagedAccts();
    }

    public void reqAllOpenOrders() {
        m_client.reqAllOpenOrders();
    }

    public void onRequestPositions() {
        m_client.reqPositions();
    }
    public void onCancelPositions() {
        m_client.cancelPositions();
    }

    public void onGlobalCancel() {
        m_client.reqGlobalCancel();
    }

    public void onReqPnL() {
        if(TwsThread.accountData.getAccountId().equals("")){
            return;
        }
        m_client.reqPnL(125, TwsThread.accountData.getAccountId(), "");
    }
    public void onCancelPnL() {
        m_client.cancelPnL(125);
    }

    private void onReqPnLSingle() {
        if(TwsThread.positionData.getAccount().equals("") || TwsThread.positionData.getContract() == null){
            return;
        }
        m_client.reqPnLSingle(126, TwsThread.positionData.getAccount(), "", TwsThread.positionData.getContract().conid());
    }
    public void onCancelPnLSingle() {
        m_client.cancelPnLSingle(126);
    }

    private void onPlaceOrder() {

        //TODO : INIT ORDER & CONTRACT
        Order order = new Order();
        Contract contract = new Contract();

        m_client.placeOrder( 126, contract, order );

    }

    public static Contract initializeContract(){

        //TODO : Get contract from strategy_data
        //String asset = OLDTWS.strategyData.getAsset();

        Contract nq = new Contract();
        nq.localSymbol("EUR.USD");
        //nq.symbol("EUR");
        nq.secType("CASH");
        //nq.currency("USD");
        nq.exchange("IDEALPRO");
        return nq;
    }
}
