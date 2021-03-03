package IbAccountDataHandling;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import com.ib.client.TagValue;

import java.util.ArrayList;

public class TwsOutputAdapter {

    private EClientSocket m_client;

    private int idOnRequestAccountSummary = 0;
    private int idOnReqMktData = 0;
    private int idOnReqPnL = 0;
    private int idOnReqPnLSingle = 0;

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

        if (idOnRequestAccountSummary != 0){
            onCancelAccountSummary();
        }

        idOnRequestAccountSummary = TwsThread.getNextValidID();
        m_client.reqAccountSummary(idOnRequestAccountSummary, "All", "NetLiquidation,BuyingPower,AvailableFunds,Cushion,FullInitMarginReq");
    }
    public void onCancelAccountSummary() {
        m_client.cancelAccountSummary(idOnRequestAccountSummary);
    }

    public void onReqMktData() {

        if (idOnReqMktData != 0){
            onCancelMktData();
        }

        idOnReqMktData = TwsThread.getNextValidID();
        m_client.reqMktData(idOnReqMktData, initializeContract(), "221", false, false, new ArrayList<TagValue>());
    }
    public void onCancelMktData() {
        m_client.cancelMktData(idOnReqMktData);
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

        if (idOnReqPnL != 0){
            onCancelPnL();
        }

        idOnReqPnL = TwsThread.getNextValidID();
        m_client.reqPnL(idOnReqPnL, TwsThread.accountData.getAccountId(), "");
    }
    public void onCancelPnL() {
        m_client.cancelPnL(idOnReqPnL);
    }

    private void onReqPnLSingle() {
        if(TwsThread.positionData.getAccount().equals("") || TwsThread.positionData.getContract() == null){
            return;
        }

        if (idOnReqPnLSingle != 0){
            onCancelPnLSingle();
        }

        idOnReqPnLSingle = TwsThread.getNextValidID();
        m_client.reqPnLSingle(idOnReqPnLSingle, TwsThread.positionData.getAccount(), "", TwsThread.positionData.getContract().conid());
    }
    public void onCancelPnLSingle() {
        m_client.cancelPnLSingle(idOnReqPnLSingle);
    }

    private void onPlaceOrder() {

        //TODO : INIT ORDER & CONTRACT
        Order order = new Order();
        Contract contract = new Contract();

        m_client.placeOrder(TwsThread.getNextValidID(), contract, order );

    }

    public static Contract initializeContract(){

        /* Get asset from strategy_data */
        String asset = TwsThread.strategyData.getAsset();

        Contract nq = new Contract();
        nq.localSymbol(asset);
        nq.secType("CASH");
        nq.exchange("IDEALPRO");
        return nq;
    }
}
