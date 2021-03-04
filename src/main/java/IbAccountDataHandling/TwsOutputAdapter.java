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
    private boolean reqPos = false;
    public static boolean statusReqPnLSingle = false;

    public void init() throws InterruptedException {
        TwsInputAdapter twsInputAdapter = new TwsInputAdapter();

        /* onConnect() */
        m_client = twsInputAdapter.onConnect();

        /* onReqManagedAccts, onRequestPositions, onRequestAccountSummary */
        onReqManagedAccts();
        onRequestPositions();
        onRequestAccountSummary();

        /* onReqPnL */
        boolean statusReqPnL = false;
        while (!statusReqPnL){
            Thread.sleep(2000);
            statusReqPnL = onReqPnL();
        }
    }

    public void onReqManagedAccts() {
        m_client.reqManagedAccts();
    }

    public void onRequestPositions() {
        if (reqPos){
            onCancelPositions();
        }
        m_client.reqPositions();
        reqPos = true;
    }
    public void onCancelPositions() {
        m_client.cancelPositions();
        reqPos = false;
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

    public boolean onReqPnL() {
        if(TwsThread.accountData.getAccountId().equals("")){
            return false;
        }

        if (idOnReqPnL != 0){
            onCancelPnL();
        }

        idOnReqPnL = TwsThread.getNextValidID();
        m_client.reqPnL(idOnReqPnL, TwsThread.accountData.getAccountId(), "");
        return true;
    }
    public void onCancelPnL() {
        m_client.cancelPnL(idOnReqPnL);
    }

    public void onReqPnLSingle() {
        if(TwsThread.positionData.getAccount().equals("") || TwsThread.positionData.getContract() == null || TwsThread.positionData.getContract().conid() == 0){
            statusReqPnLSingle = false;
        }

        if (idOnReqPnLSingle != 0){
            onCancelPnLSingle();
        }

        idOnReqPnLSingle = TwsThread.getNextValidID();
        m_client.reqPnLSingle(idOnReqPnLSingle, TwsThread.positionData.getAccount(), "", TwsThread.positionData.getContract().conid());
        statusReqPnLSingle = true;
    }
    public void onCancelPnLSingle() {
        m_client.cancelPnLSingle(idOnReqPnLSingle);
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


    //TODO : reqAllOpenOrders();
    public void reqAllOpenOrders() {
        m_client.reqAllOpenOrders();
    }

    //TODO : onGlobalCancel();
    public void onGlobalCancel() {
        m_client.reqGlobalCancel();
    }

    //TODO : onPlaceOrder();
    private void onPlaceOrder() {

        //TODO : Initialize order
        Order order = new Order();
        m_client.placeOrder(TwsThread.getNextValidID(), initializeContract(), order );

    }


    /* Initialize Contract */
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
