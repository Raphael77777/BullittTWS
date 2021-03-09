package ConnectionHandling;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;

import java.util.ArrayList;

public class OutputAdapterIB {

    /* TWS API ENDPOINT */
    private EClientSocket m_client;

    /* REQUEST ID */
    private int idOnRequestAccountSummary = 0;
    private int idOnReqMktData = 0;
    private int idOnReqPnL = 0;
    private int idOnReqPnLSingle = 0;

    /* REQUEST STATUS */
    private boolean reqPos = false;
    public static boolean statusReqPnLSingle = false;

    public void init() throws InterruptedException {
        InputAdapterIB inputAdapterIB = new InputAdapterIB();

        /* REQUEST onConnect */
        m_client = inputAdapterIB.onConnect();

        /* REQUEST onReqManagedAccts, onRequestPositions, onRequestAccountSummary */
        onReqManagedAccts();
        onRequestPositions();
        onRequestAccountSummary();

        /* REQUEST onReqPnL */
        boolean statusReqPnL = false;
        while (!statusReqPnL){
            Thread.sleep(3000);
            statusReqPnL = onReqPnL();
        }
    }

    /* API REQUEST METHOD */
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

        idOnRequestAccountSummary = TwsIB.getNextValidID();
        m_client.reqAccountSummary(idOnRequestAccountSummary, "All", "NetLiquidation,BuyingPower,AvailableFunds,Cushion,FullInitMarginReq");
    }
    public void onCancelAccountSummary() {
        m_client.cancelAccountSummary(idOnRequestAccountSummary);
    }

    public boolean onReqPnL() {
        if(TwsIB.accountData.getAccountId().equals("")){
            return false;
        }

        if (idOnReqPnL != 0){
            onCancelPnL();
        }

        idOnReqPnL = TwsIB.getNextValidID();
        m_client.reqPnL(idOnReqPnL, TwsIB.accountData.getAccountId(), "");
        return true;
    }
    public void onCancelPnL() {
        m_client.cancelPnL(idOnReqPnL);
    }

    public void onReqPnLSingle() {
        if(TwsIB.positionData.getAccount().equals("") || TwsIB.positionData.getContract() == null || TwsIB.positionData.getContract().conid() == 0){
            statusReqPnLSingle = false;
        }

        if (idOnReqPnLSingle != 0){
            onCancelPnLSingle();
        }

        if (m_client != null){
            idOnReqPnLSingle = TwsIB.getNextValidID();
            m_client.reqPnLSingle(idOnReqPnLSingle, TwsIB.positionData.getAccount(), "", TwsIB.positionData.getContract().conid());
            statusReqPnLSingle = true;
        }
    }
    public void onCancelPnLSingle() {
        m_client.cancelPnLSingle(idOnReqPnLSingle);
    }

    public void onReqMktData() {

        if (idOnReqMktData != 0){
            onCancelMktData();
        }

        idOnReqMktData = TwsIB.getNextValidID();
        m_client.reqMktData(idOnReqMktData, initializeContract(), "221", false, false, new ArrayList<>());
    }
    public void onCancelMktData() {
        m_client.cancelMktData(idOnReqMktData);
    }

    public void onGlobalCancel() {
        m_client.reqGlobalCancel();
    }

    public void onPlaceOrder(Order order) {
        m_client.placeOrder(TwsIB.getNextValidID(), initializeContract(), order );
    }

    /*  INITIALIZE CONTRACT */
    public static Contract initializeContract(){

        /* GET ASSET FROM strategy_data */
        String asset = TwsIB.strategyData.getAsset();

        Contract nq = new Contract();
        nq.localSymbol(asset);
        nq.secType("CASH");
        nq.exchange("IDEALPRO");
        return nq;
    }
}
