package UserInterface.Screen;

import DataHandling.AccountData;
import DataHandling.PositionData;
import DataHandling.TransactionData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Enum.TransactionTYPE;
import UserInterface.Component.Panel.AccountPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Panel.InfoPanelL;
import UserInterface.Component.Panel.TransactionPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class PositionsScreen9007 extends AbstractScreen implements Observer{

    private AccountPanel accountPanel;
    private InfoPanel[] infoPanels = new InfoPanel[4];
    private InfoPanelL[] infoPanelLS = new InfoPanelL[4];

    private PositionData positionData;
    private AccountData accountData;

    private String accountId = "DU985632";
    private String currency = "USD";

    private final String [] IP_texts = new String[]{"Position", "Average Cost", "Value", "Type", "Symbol", "Daily P&L", "Unrealized P&L", "Realized P&L"};
    private String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--"};
    private InfoTYPE[] IP_types = new InfoTYPE[]{InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON};


    public PositionsScreen9007(PositionData positionData, AccountData accountData) {
        this.positionData = positionData;
        this.positionData.registerObserver(this);

        this.accountData = accountData;
        this.accountData.registerObserver(this);

        update();
    }

    @Override
    public void init() {

        removeAll();

        accountPanel = new AccountPanel("ACC", accountId, currency);
        accountPanel.setBounds(20, 20, 786, 110);
        add(accountPanel);

        for (int i = 0; i<infoPanelLS.length; i++){
            infoPanelLS[i] = new InfoPanelL("IPL"+i, IP_types[i], IP_texts[i], IP_values[i]);
            infoPanelLS[i].setBounds(20, 133+i*113, 523, 110);
            add(infoPanelLS[i]);
        }

        for (int i = 0; i < infoPanels.length; i++){
            infoPanels[i] = new InfoPanel("IP"+i, IP_types[i+4], IP_texts[i+4], IP_values[i+4]);
            infoPanels[i].setBounds(546, 133+i*113, 260, 110);
            add(infoPanels[i]);
        }

        repaint();
    }

    @Override
    public void update() {

        /* CALL METHOD OF positionData to update IP_values and IP_types */

        accountId = accountData.getAccountId();
        currency = accountData.getCurrency();

        IP_values[0] = String.valueOf(positionData.getPos());
        IP_values[1] = String.valueOf(positionData.getAvgCost());
        IP_values[2] = String.valueOf(positionData.getValue());
        if (positionData.getContract() != null){
            IP_values[3] = positionData.getContract().secType().name();
            IP_values[4] = positionData.getContract().localSymbol();
        }

        double dailyPNL = positionData.getDailyPnL();
        if (dailyPNL > 0){
            IP_types[5] = InfoTYPE.POSITIVE;
        }if (dailyPNL < 0){
            IP_types[5] = InfoTYPE.NEGATIVE;
        }
        IP_values[5] = String.valueOf(dailyPNL);

        double unrealizedPNL = positionData.getUnrealizedPnL();
        if (unrealizedPNL > 0){
            IP_types[6] = InfoTYPE.POSITIVE;
        }if (unrealizedPNL < 0){
            IP_types[6] = InfoTYPE.NEGATIVE;
        }
        IP_values[6] = String.valueOf(unrealizedPNL);

        double realizedPNL = positionData.getRealizedPnL();
        if (realizedPNL > 0){
            IP_types[7] = InfoTYPE.POSITIVE;
        }if (realizedPNL < 0){
            IP_types[7] = InfoTYPE.NEGATIVE;
        }
        IP_values[7] = String.valueOf(realizedPNL);

        init();
    }
}
