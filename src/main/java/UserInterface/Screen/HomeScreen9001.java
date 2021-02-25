package UserInterface.Screen;

import UserInterface.Component.Panel.InfoButtonPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Enum.InfoTYPE;

public class HomeScreen9001 extends AbstractScreen {

    private InfoPanel[] infoPanels = new InfoPanel[5];
    private InfoButtonPanel [] infoButtonPanels = new InfoButtonPanel[5];

    private String [] texts = new String[]{"#BUY", "#SELL", "#OPEN", "Exposition", "Position"};
    private String [] values = new String[]{"5", "4", "1", "1000 USD", "LONG"};

    public HomeScreen9001() {
    }

    @Override
    public void init() {

        removeAll();

        for (int i = 0; i < infoPanels.length; i++){
            infoPanels[i] = new InfoPanel("MA"+i, InfoTYPE.NO_ICON, texts[i], values[i]);
            infoPanels[i].setBounds(546, 20+i*113, 260, 110);
            add(infoPanels[i]);
        }

        for (int i = 0; i<infoButtonPanels.length; i++){
            infoButtonPanels[i] = new InfoButtonPanel("MI"+i, InfoTYPE.NO_ICON, "1. STRATEGY", "Modify or view the strategy", "HOME");
            infoButtonPanels[i].setBounds(20, 20+i*113, 523, 110);
            add(infoButtonPanels[i]);
        }

    }
}
