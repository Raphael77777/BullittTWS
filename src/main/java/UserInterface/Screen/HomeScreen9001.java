package UserInterface.Screen;

import StorageHandling.HistoryData;
import UserInterface.Component.Enum.EnumType;
import UserInterface.Component.Panel.ButtonPanel;
import UserInterface.Component.Panel.InfoPanel;

public class HomeScreen9001 extends AbstractScreen implements Observer{

    /* COMPONENTS */
    private final InfoPanel[] infoPanels = new InfoPanel[5];
    private final ButtonPanel[] buttonPanels = new ButtonPanel[5];

    /* DATA SUBJECT */
    private final HistoryData historyData;

    /* DATA TO DISPLAY */
    private final String [] IP_texts = new String[]{"#BUY", "#SELL", "DELTA", "Exposition", "Position"};
    private final String [] IP_values = new String[]{"5", "4", "1", "1000 USD", "LONG"};
    private final EnumType[] IP_types = new EnumType[]{EnumType.POSITIVE, EnumType.NEGATIVE, EnumType.POSITIVE, EnumType.NO_ICON, EnumType.POSITIVE};

    /* STATIC DATA TO DISPLAY*/
    private final String [] BT_Header = new String[]{"1. STRATEGY", "2. START", "3. MONITOR", "4. STOP", "5. HISTORY"};
    private final String [] BT_Description = new String[]{"Modify or view the strategy", "Launching the engine with the strategy", "View live data", "Stopping the engine with the strategy", "View transaction history"};
    private final String [] BT_Target = new String[]{"SETTINGS", "ENGINE", "MONITOR", "ENGINE", "HISTORY"};

    public HomeScreen9001(HistoryData historyData) {
        this.historyData = historyData;

        /* REGISTER TO RECEIVE UPDATE */
        this.historyData.registerObserver(this);

        update();
    }

    @Override
    public void init() {
        /* CLEAN PANEL */
        removeAll();

        /* DISPLAY COMPONENTS */
        for (int i = 0; i< buttonPanels.length; i++){
            buttonPanels[i] = new ButtonPanel(EnumType.NO_ICON, BT_Header[i], BT_Description[i], BT_Target[i]);
            buttonPanels[i].setBounds(20, 20+i*113, 523, 110);
            add(buttonPanels[i]);
        }

        for (int i = 0; i < infoPanels.length; i++){
            infoPanels[i] = new InfoPanel(IP_types[i], IP_texts[i], IP_values[i]);
            infoPanels[i].setBounds(546, 20+i*113, 260, 110);
            add(infoPanels[i]);
        }

        repaint();
    }

    @Override
    public void update() {
        /* CALL METHOD OF transactionData TO UPDATE IP_values AND IP_types */
        // #BUY
        int numberBuy = historyData.getNumberBuy();
        IP_values[0] = (String.valueOf(numberBuy));

        // #SELL
        int numberSell = historyData.getNumberSell();
        IP_values[1] = (String.valueOf(numberSell));

        // #OPEN
        int open = (numberBuy - numberSell);
        EnumType type = EnumType.NEUTRAL;
        if (open > 0){
            type = EnumType.POSITIVE;
        } else if (open < 0){
            type = EnumType.NEGATIVE;
        }
        IP_values[2] = (String.valueOf(Math.abs(open)));
        IP_types[2] = (type);

        // EXPOSITION
        double exposition = historyData.getExposition();
        IP_values[3] = (exposition +"");

        // POSITION
        EnumType expo = EnumType.NEUTRAL;
        String value = "NEUTRAL";
        if (exposition > 0){
            expo = EnumType.POSITIVE;
            value = "LONG";
        } else if (exposition < 0){
            expo = EnumType.NEGATIVE;
            value = "SHORT";
        }
        IP_values[4] = (value);
        IP_types[4] = (expo);

        /* DISPLAY UPDATES ON SCREEN */
        init();
    }
}
