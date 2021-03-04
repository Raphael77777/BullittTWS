package IbAccountDataHandling;

import com.ib.client.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TwsInputAdapter implements EWrapper {

    private final EJavaSignal m_signal = new EJavaSignal();
    private final EClientSocket m_client = new EClientSocket( this, m_signal);

    private EReader m_reader;

    public EClientSocket onConnect() {

        if(m_client.isConnected())
            return m_client;

        m_client.eConnect( "127.0.0.1",7497, 2);
        if (m_client.isConnected()) {
            String msg = "Connected to Tws server version " +
                    m_client.serverVersion() + " at " +
                    m_client.getTwsConnectionTime();
            System.out.println(msg);
        }

        m_reader = new EReader(m_client, m_signal);

        m_reader.start();

        new Thread(() -> {
            processMessages();
        }).start();

        return m_client;
    }

    private void processMessages() {

        while (m_client.isConnected()) {
            m_signal.waitForSignal();
            try {
                m_reader.processMsgs();
            } catch (Exception e) {
                error(e);
            }
        }
    }

    @Override
    public void tickPrice(int tickerId, int field, double price, TickAttrib attribs) {

        if (field == 2){ // askPrice

            TwsThread.marketAdapter.tickPrice(tickerId, field, price, attribs);

            //String msg = EWrapperMsgGenerator.tickPrice( tickerId, field, price, attribs);
            //System.out.println(msg);
        }
    }

    @Override
    public void tickSize(int i, int i1, int i2) {

    }

    @Override
    public void tickOptionComputation(int i, int i1, double v, double v1, double v2, double v3, double v4, double v5, double v6, double v7) {

    }

    @Override
    public void tickGeneric(int i, int i1, double v) {

    }

    @Override
    public void tickString(int i, int i1, String s) {

    }

    @Override
    public void tickEFP(int i, int i1, double v, String s, double v1, int i2, String s1, double v2, double v3) {

    }

    @Override
    public void orderStatus( int orderId, String status, double filled, double remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {

        //TODO : FORWARD TO ORDER_DATA
        String msg = EWrapperMsgGenerator.orderStatus( orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice);
        System.out.println(msg);

        //TODO : SAVE THIS VALUE IN IB_CONNECTOR
        //( orderId + 1)
    }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {

        //TODO : FORWARD TO ORDER_DATA
        String msg = EWrapperMsgGenerator.openOrder( orderId, contract, order, orderState);
        System.out.println(msg);
    }

    @Override
    public void openOrderEnd() {

        //TODO : TO REMOVE
        String msg = EWrapperMsgGenerator.openOrderEnd();
        System.out.println(msg);
    }

    @Override
    public void updateAccountValue(String s, String s1, String s2, String s3) {

    }

    @Override
    public void updatePortfolio(Contract contract, double v, double v1, double v2, double v3, double v4, double v5, String s) {

    }

    @Override
    public void updateAccountTime(String s) {

    }

    @Override
    public void accountDownloadEnd(String s) {

    }

    @Override
    public void nextValidId(int i) {

        TwsThread.setNextValidID(i);

        //System.out.println("nextValidID : "+i);
    }

    @Override
    public void contractDetails(int i, ContractDetails contractDetails) {

    }

    @Override
    public void bondContractDetails(int i, ContractDetails contractDetails) {

    }

    @Override
    public void contractDetailsEnd(int i) {

    }

    @Override
    public void execDetails(int i, Contract contract, Execution execution) {

    }

    @Override
    public void execDetailsEnd(int i) {

    }

    @Override
    public void updateMktDepth(int i, int i1, int i2, int i3, double v, int i4) {

    }

    @Override
    public void updateMktDepthL2(int i, int i1, String s, int i2, int i3, double v, int i4, boolean b) {

    }

    @Override
    public void updateNewsBulletin(int i, int i1, String s, String s1) {

    }

    @Override
    public void managedAccounts(String accountsList) {

        TwsThread.accountData.setAccountId(accountsList);
        TwsThread.accountData.update();

        //String msg = EWrapperMsgGenerator.managedAccounts(accountsList);
        //System.out.println(msg);
    }

    @Override
    public void receiveFA(int i, String s) {

    }

    @Override
    public void historicalData(int i, Bar bar) {

    }

    @Override
    public void scannerParameters(String s) {

    }

    @Override
    public void scannerData(int i, int i1, ContractDetails contractDetails, String s, String s1, String s2, String s3) {

    }

    @Override
    public void scannerDataEnd(int i) {

    }

    @Override
    public void realtimeBar(int i, long l, double v, double v1, double v2, double v3, long l1, double v4, int i1) {

    }

    @Override
    public void currentTime(long l) {

    }

    @Override
    public void fundamentalData(int i, String s) {

    }

    @Override
    public void deltaNeutralValidation(int i, DeltaNeutralContract deltaNeutralContract) {

    }

    @Override
    public void tickSnapshotEnd(int i) {

    }

    @Override
    public void marketDataType(int i, int i1) {

    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {

    }

    @Override
    public void position(String account, Contract contract, double pos, double avgCost) {

        TwsThread.positionData.setAccount(account);
        TwsThread.positionData.setContract(contract);
        TwsThread.positionData.setPos(pos);
        TwsThread.positionData.setAvgCost(avgCost);
        TwsThread.positionData.update();

        if (!TwsOutputAdapter.statusReqPnLSingle){
            TwsThread.twsOutputAdapter.onReqPnLSingle();
        }

        //String msg = EWrapperMsgGenerator.position(account, contract, pos, avgCost);
        //System.out.println(msg);
    }

    @Override
    public void positionEnd() {

        //String msg = EWrapperMsgGenerator.positionEnd();
        //System.out.println(msg);
    }

    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {

        switch (tag){
            case "NetLiquidation":
                TwsThread.accountData.setNetLiquidationValue(Double.parseDouble(value));
                break;
            case "BuyingPower":
                TwsThread.accountData.setBuyingPower(Double.parseDouble(value));
                break;
            case "AvailableFunds":
                TwsThread.accountData.setAvailableFunds(Double.parseDouble(value));
                TwsThread.accountData.setCurrency(currency);
                break;
            case "Cushion":
                BigDecimal cushion = new BigDecimal(value);
                cushion = cushion.setScale(2, RoundingMode.HALF_UP);
                TwsThread.accountData.setCushion(cushion.doubleValue()*100);
                break;
            case "FullInitMarginReq":
                TwsThread.accountData.setMarginReq(Double.parseDouble(value));
                break;
        }

        TwsThread.accountData.update();

        //String msg = EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency);
        //System.out.println(msg);
    }

    @Override
    public void accountSummaryEnd(int reqId) {

        //String msg = EWrapperMsgGenerator.accountSummaryEnd(reqId);
        //System.out.println(msg);
    }

    @Override
    public void verifyMessageAPI(String s) {

    }

    @Override
    public void verifyCompleted(boolean b, String s) {

    }

    @Override
    public void verifyAndAuthMessageAPI(String s, String s1) {

    }

    @Override
    public void verifyAndAuthCompleted(boolean b, String s) {

    }

    @Override
    public void displayGroupList(int i, String s) {

    }

    @Override
    public void displayGroupUpdated(int i, String s) {

    }

    @Override
    public void error(Exception e) {

        //TODO : CATCH ERROR
        e.printStackTrace();
    }

    @Override
    public void error(String s) {

        //TODO : CATCH ERROR
        System.out.println(s);
    }

    @Override
    public void error(int i, int i1, String s) {

        //TODO : CATCH ERROR
        System.out.println(i+" / "+i1+" / "+s);
    }

    @Override
    public void connectionClosed() {

        //TODO : SHOW MESSAGE IF CONNECTION IS CLOSED
        String msg = EWrapperMsgGenerator.connectionClosed();
        System.out.println(msg);
    }

    @Override
    public void connectAck() {
        if (m_client.isAsyncEConnect())
            m_client.startAPI();
    }

    @Override
    public void positionMulti(int i, String s, String s1, Contract contract, double v, double v1) {

    }

    @Override
    public void positionMultiEnd(int i) {

    }

    @Override
    public void accountUpdateMulti( int reqId, String account, String modelCode, String key, String value, String currency) {

    }

    @Override
    public void accountUpdateMultiEnd( int reqId) {

    }

    @Override
    public void securityDefinitionOptionalParameter(int i, String s, int i1, String s1, String s2, Set<String> set, Set<Double> set1) {

    }

    @Override
    public void securityDefinitionOptionalParameterEnd(int i) {

    }

    @Override
    public void softDollarTiers(int i, SoftDollarTier[] softDollarTiers) {

    }

    @Override
    public void familyCodes(FamilyCode[] familyCodes) {

    }

    @Override
    public void symbolSamples(int i, ContractDescription[] contractDescriptions) {

    }

    @Override
    public void historicalDataEnd(int i, String s, String s1) {

    }

    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

    }

    @Override
    public void tickNews(int i, long l, String s, String s1, String s2, String s3) {

    }

    @Override
    public void smartComponents(int i, Map<Integer, Map.Entry<String, Character>> map) {

    }

    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }

    @Override
    public void newsProviders(NewsProvider[] newsProviders) {

    }

    @Override
    public void newsArticle(int i, int i1, String s) {

    }

    @Override
    public void historicalNews(int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void historicalNewsEnd(int i, boolean b) {

    }

    @Override
    public void headTimestamp(int i, String s) {

    }

    @Override
    public void histogramData(int i, List<HistogramEntry> list) {

    }

    @Override
    public void historicalDataUpdate(int i, Bar bar) {

    }

    @Override
    public void rerouteMktDataReq(int i, int i1, String s) {

    }

    @Override
    public void rerouteMktDepthReq(int i, int i1, String s) {

    }

    @Override
    public void marketRule(int i, PriceIncrement[] priceIncrements) {

    }

    @Override
    public void pnl(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL) {

        BigDecimal dPNL = new BigDecimal(Double.toString(dailyPnL));
        dPNL = dPNL.setScale(2, RoundingMode.HALF_UP);
        TwsThread.accountData.setDailyPNL(dPNL.doubleValue());

        BigDecimal uPNL = new BigDecimal(Double.toString(unrealizedPnL));
        uPNL = uPNL.setScale(2, RoundingMode.HALF_UP);
        TwsThread.accountData.setUnrealizedPNL(uPNL.doubleValue());

        BigDecimal rPNL = new BigDecimal(Double.toString(realizedPnL));
        rPNL = rPNL.setScale(2, RoundingMode.HALF_UP);
        TwsThread.accountData.setRealizedPNL(rPNL.doubleValue());
        TwsThread.accountData.update();

        //String msg = EWrapperMsgGenerator.pnl(reqId, dailyPnL, unrealizedPnL, realizedPnL);
        //System.out.println(msg);
    }

    @Override
    public void pnlSingle(int reqId, int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {

        if (dailyPnL == Double.MAX_VALUE){
            TwsThread.positionData.setDailyPnL(0.0);
        }else {
            BigDecimal dPNL = new BigDecimal(Double.toString(dailyPnL));
            dPNL = dPNL.setScale(2, RoundingMode.HALF_UP);
            TwsThread.positionData.setDailyPnL(dPNL.doubleValue());
        }

        if (unrealizedPnL == Double.MAX_VALUE){
            TwsThread.positionData.setUnrealizedPnL(0.0);
        }else {
            BigDecimal uPNL = new BigDecimal(Double.toString(unrealizedPnL));
            uPNL = uPNL.setScale(2, RoundingMode.HALF_UP);
            TwsThread.positionData.setUnrealizedPnL(uPNL.doubleValue());
        }

        if (realizedPnL == Double.MAX_VALUE){
            TwsThread.positionData.setRealizedPnL(0.0);
        }else {
            BigDecimal rPNL = new BigDecimal(Double.toString(realizedPnL));
            rPNL = rPNL.setScale(2, RoundingMode.HALF_UP);
            TwsThread.positionData.setRealizedPnL(rPNL.doubleValue());
        }

        if (value == Double.MAX_VALUE){
            TwsThread.positionData.setValue(0.0);
        }else {
            BigDecimal val = new BigDecimal(Double.toString(value));
            val = val.setScale(2, RoundingMode.HALF_UP);
            TwsThread.positionData.setValue(val.doubleValue());
        }
        TwsThread.positionData.update();

        String msg = EWrapperMsgGenerator.pnlSingle(reqId, pos, dailyPnL, unrealizedPnL, realizedPnL, value);
        System.out.println(msg);
    }

    @Override
    public void historicalTicks(int i, List<HistoricalTick> list, boolean b) {

    }

    @Override
    public void historicalTicksBidAsk(int i, List<HistoricalTickBidAsk> list, boolean b) {

    }

    @Override
    public void historicalTicksLast(int i, List<HistoricalTickLast> list, boolean b) {

    }

    @Override
    public void tickByTickAllLast(int i, int i1, long l, double v, int i2, TickAttribLast tickAttribLast, String s, String s1) {

    }

    @Override
    public void tickByTickBidAsk(int i, long l, double v, double v1, int i1, int i2, TickAttribBidAsk tickAttribBidAsk) {

    }

    @Override
    public void tickByTickMidPoint(int i, long l, double v) {

    }

    @Override
    public void orderBound(long l, int i, int i1) {

    }

    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {

    }

    @Override
    public void completedOrdersEnd() {

    }
}
