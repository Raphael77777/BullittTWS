package UserInterface.Screen;

import StorageHandling.AccountData;
import StorageHandling.PositionData;
import UserInterface.Component.Enum.EnumType;
import UserInterface.Component.Panel.AccountPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Panel.InfoPanelL;

public class PositionsScreen9007 extends AbstractScreen implements Observer{

    private final InfoPanel[] infoPanels = new InfoPanel[4];
    private final InfoPanelL[] infoPanelLS = new InfoPanelL[4];

    private final PositionData positionData;
    private final AccountData accountData;

    private String accountId = "DU985632";
    private String currency = "USD";

    private final String [] IP_texts = new String[]{"Position", "Average Cost", "Value", "Type", "Symbol", "Daily P&L", "Unrealized P&L", "Realized P&L"};
    private final String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--"};
    private final EnumType[] IP_types = new EnumType[]{EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON, EnumType.NO_ICON};


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

        /* CALL METHOD OF positionData to update IP_values and IP_types */

        accountId = accountData.getAccountId();
        currency = accountData.getCurrency();

        IP_values[0] = String.valueOf(positionData.getPos());
        IP_values[1] = String.valueOf(positionData.getAvgCost());
        IP_values[2] = String.valueOf(positionData.getValue());
        if (positionData.getContract() != null){
            IP_values[3] = positionData.getContract().secType().name();
            IP_values[4] = positionData.getContract().localSymbol();
        }else {
            IP_values[3] = "--";
            IP_values[4] = "--";
        }

        double dailyPNL = positionData.getDailyPnL();
        IP_types[5] = EnumType.NO_ICON;
        if (dailyPNL > 0){
            IP_types[5] = EnumType.POSITIVE;
        }if (dailyPNL < 0){
            IP_types[5] = EnumType.NEGATIVE;
        }
        IP_values[5] = String.valueOf(dailyPNL);

        double unrealizedPNL = positionData.getUnrealizedPnL();
        IP_types[6] = EnumType.NO_ICON;
        if (unrealizedPNL > 0){
            IP_types[6] = EnumType.POSITIVE;
        }if (unrealizedPNL < 0){
            IP_types[6] = EnumType.NEGATIVE;
        }
        IP_values[6] = String.valueOf(unrealizedPNL);

        double realizedPNL = positionData.getRealizedPnL();
        IP_types[7] = EnumType.NO_ICON;
        if (realizedPNL > 0){
            IP_types[7] = EnumType.POSITIVE;
        }if (realizedPNL < 0){
            IP_types[7] = EnumType.NEGATIVE;
        }
        IP_values[7] = String.valueOf(realizedPNL);

        init();
    }
}
