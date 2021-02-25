package UserInterface.Screen;

import DataHandling.TransactionData;
import UserInterface.Component.Panel.ButtonPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Enum.InfoTYPE;

public class HomeScreen9001 extends AbstractScreen implements Observer{

    private InfoPanel[] infoPanels = new InfoPanel[5];
    private ButtonPanel[] buttonPanels = new ButtonPanel[5];

    private TransactionData transactionData;

    private final String [] BT_Header = new String[]{"1. STRATEGY", "2. START", "3. MONITOR", "4. STOP", "5. HISTORY"};
    private final String [] BT_Description = new String[]{"Modify or view the strategy", "Launching the engine with the strategy", "View live data", "Stopping the engine with the strategy", "View transaction history"};
    private final String [] BT_Target = new String[]{"SETTINGS", "ENGINE", "MONITOR", "ENGINE", "HISTORY"};

    private final String [] IP_texts = new String[]{"#BUY", "#SELL", "#OPEN", "Exposition", "Position"};
    private String [] IP_values = new String[]{"5", "4", "1", "1000 USD", "LONG"};
    private InfoTYPE [] IP_types = new InfoTYPE[]{InfoTYPE.POSITIVE, InfoTYPE.NEGATIVE, InfoTYPE.POSITIVE, InfoTYPE.NO_ICON, InfoTYPE.POSITIVE};

    public HomeScreen9001(TransactionData transactionData) {
        this.transactionData = transactionData;
        this.transactionData.registerObserver(this);

        update();
    }

    @Override
    public void init() {

        removeAll();

        for (int i = 0; i< buttonPanels.length; i++){
            buttonPanels[i] = new ButtonPanel("BT"+i, InfoTYPE.NO_ICON, BT_Header[i], BT_Description[i], BT_Target[i]);
            buttonPanels[i].setBounds(20, 20+i*113, 523, 110);
            add(buttonPanels[i]);
        }

        for (int i = 0; i < infoPanels.length; i++){
            infoPanels[i] = new InfoPanel("IP"+i, IP_types[i], IP_texts[i], IP_values[i]);
            infoPanels[i].setBounds(546, 20+i*113, 260, 110);
            add(infoPanels[i]);
        }

        repaint();
    }

    @Override
    public void update() {

        /* CALL METHOD OF tranactionData to update IP_values and IP_types */
        /* #BUY */
        int numberBuy = transactionData.getNumberBuy();
        IP_values[0] = (String.valueOf(numberBuy));

        /* #SELL */
        int numberSell = transactionData.getNumberSell();
        IP_values[1] = (String.valueOf(numberSell));

        /* #OPEN */
        int open = (numberBuy - numberSell);
        InfoTYPE type = InfoTYPE.NEUTRAL;
        if (open > 0){
            type = InfoTYPE.POSITIVE;
        } else if (open < 0){
            type = InfoTYPE.NEGATIVE;
        }
        IP_values[2] = (String.valueOf(Math.abs(open)));
        IP_types[2] = (type);

        /* EXPOSITION */
        double exposition = transactionData.getExposition();
        IP_values[3] = (exposition +" USD");

        /* POSITION */
        InfoTYPE expo = InfoTYPE.NEUTRAL;
        String value = "NEUTRAL";
        if (exposition > 0){
            expo = InfoTYPE.POSITIVE;
            value = "LONG";
        } else if (exposition < 0){
            expo = InfoTYPE.NEGATIVE;
            value = "SHORT";
        }
        IP_values[4] = (value);
        IP_types[4] = (expo);

        init();
    }
}
