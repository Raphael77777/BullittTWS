package UserInterface.Screen;

import DataHandling.AccountData;
import DataHandling.LiveData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Panel.AccountPanel;
import UserInterface.Component.Panel.ButtonPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Panel.InfoPanelL;

public class AccountsScreen9006 extends AbstractScreen implements Observer {

    private AccountPanel accountPanel;
    private InfoPanel [] infoPanels = new InfoPanel[4];
    private InfoPanelL[] infoPanelLS = new InfoPanelL[4];

    private AccountData accountData;

    private String accountId ;
    private String currency ;

    private final String [] IP_texts = new String[]{"Available Funds", "Net Liquidation Value", "Buying Power", "Margin Requirement", "Daily P&L", "Unrealized P&L", "Realized P&L", "Cushion"};
    private String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--"};
    private InfoTYPE [] IP_types = new InfoTYPE[]{InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON};

    public AccountsScreen9006(AccountData accountData) {
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

        /* CALL METHOD OF accountData to update IP_values and IP_types */

        accountId = accountData.getAccountId();
        currency = accountData.getCurrency();

        IP_values[0] = String.valueOf(accountData.getAvailableFunds());
        IP_values[1] = String.valueOf(accountData.getNetLiquidationValue());
        IP_values[2] = String.valueOf(accountData.getBuyingPower());
        IP_values[3] = String.valueOf(accountData.getMarginReq());

        double dailyPNL = accountData.getDailyPNL();
        if (dailyPNL > 0){
            IP_types[4] = InfoTYPE.POSITIVE;
        }if (dailyPNL < 0){
            IP_types[4] = InfoTYPE.NEGATIVE;
        }
        IP_values[4] = String.valueOf(dailyPNL);

        double unrealizedPNL = accountData.getUnrealizedPNL();
        if (unrealizedPNL > 0){
            IP_types[5] = InfoTYPE.POSITIVE;
        }if (unrealizedPNL < 0){
            IP_types[5] = InfoTYPE.NEGATIVE;
        }
        IP_values[5] = String.valueOf(unrealizedPNL);

        double realizedPNL = accountData.getRealizedPNL();
        if (realizedPNL > 0){
            IP_types[6] = InfoTYPE.POSITIVE;
        }if (realizedPNL < 0){
            IP_types[6] = InfoTYPE.NEGATIVE;
        }
        IP_values[6] = String.valueOf(realizedPNL);

        IP_values[7] = accountData.getCushion()+"%";

        init();
    }
}
