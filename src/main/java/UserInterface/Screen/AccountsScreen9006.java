package UserInterface.Screen;

import StorageHandling.AccountData;
import UserInterface.Component.Enum.EnumType;
import UserInterface.Component.Panel.AccountPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Panel.InfoPanelL;

public class AccountsScreen9006 extends AbstractScreen implements Observer {

    /* COMPONENTS */
    private final InfoPanel [] infoPanels = new InfoPanel[4];
    private final InfoPanelL[] infoPanelLS = new InfoPanelL[4];

    /* DATA SUBJECT */
    private final AccountData accountData;

    /* HEADER TO DISPLAY*/
    private String accountId ;
    private String currency ;

    /* DATA TO DISPLAY */
    private final String [] IP_texts = new String[]{"Available Funds", "Net Liquidation Value", "Buying Power", "Margin Requirement", "Daily P&L", "Unrealized P&L", "Realized P&L", "Cushion"};
    private final String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--"};
    private final EnumType[] IP_types = new EnumType[]{EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON};

    public AccountsScreen9006(AccountData accountData) {
        this.accountData = accountData;

        /* REGISTER TO RECEIVE UPDATE */
        this.accountData.registerObserver(this);

        update();
    }

    @Override
    public void init() {
        /* CLEAN PANEL */
        removeAll();

        /* DISPLAY COMPONENTS */
        AccountPanel accountPanel = new AccountPanel(accountId, currency);
        accountPanel.setBounds(20, 20, 786, 110);
        add(accountPanel);

        for (int i = 0; i<infoPanelLS.length; i++){
            infoPanelLS[i] = new InfoPanelL(IP_types[i], IP_texts[i], IP_values[i]);
            infoPanelLS[i].setBounds(20, 133+i*113, 523, 110);
            add(infoPanelLS[i]);
        }

        for (int i = 0; i < infoPanels.length; i++){
            infoPanels[i] = new InfoPanel(IP_types[i+4], IP_texts[i+4], IP_values[i+4]);
            infoPanels[i].setBounds(546, 133+i*113, 260, 110);
            add(infoPanels[i]);
        }

        repaint();
    }

    @Override
    public void update() {
        /* CALL METHOD OF accountData TO UPDATE IP_values AND IP_types */
        accountId = accountData.getAccountId();
        currency = accountData.getCurrency();

        IP_values[0] = String.valueOf(accountData.getAvailableFunds());
        IP_values[1] = String.valueOf(accountData.getNetLiquidationValue());
        IP_values[2] = String.valueOf(accountData.getBuyingPower());
        IP_values[3] = String.valueOf(accountData.getMarginReq());

        double dailyPNL = accountData.getDailyPNL();
        IP_types[4] = EnumType.NO_ICON;
        if (dailyPNL > 0){
            IP_types[4] = EnumType.POSITIVE;
        }if (dailyPNL < 0){
            IP_types[4] = EnumType.NEGATIVE;
        }
        IP_values[4] = String.valueOf(dailyPNL);

        double unrealizedPNL = accountData.getUnrealizedPNL();
        IP_types[5] = EnumType.NO_ICON;
        if (unrealizedPNL > 0){
            IP_types[5] = EnumType.POSITIVE;
        }if (unrealizedPNL < 0){
            IP_types[5] = EnumType.NEGATIVE;
        }
        IP_values[5] = String.valueOf(unrealizedPNL);

        double realizedPNL = accountData.getRealizedPNL();
        IP_types[6] = EnumType.NO_ICON;
        if (realizedPNL > 0){
            IP_types[6] = EnumType.POSITIVE;
        }if (realizedPNL < 0){
            IP_types[6] = EnumType.NEGATIVE;
        }
        IP_values[6] = String.valueOf(realizedPNL);

        IP_values[7] = accountData.getCushion()+"%";

        /* DISPLAY UPDATES ON SCREEN */
        init();
    }
}
