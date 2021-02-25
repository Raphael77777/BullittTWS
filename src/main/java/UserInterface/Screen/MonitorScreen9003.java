package UserInterface.Screen;

import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Enum.InfoTYPE;

public class MonitorScreen9003 extends AbstractScreen {

    private InfoPanel [] infoPanels = new InfoPanel[15];

    private InfoTYPE [] infoTYPES = new InfoTYPE[]{InfoTYPE.POSITIVE, InfoTYPE.NEUTRAL, InfoTYPE.NEGATIVE};
    private String [] texts = new String[]{"Profit", "Revenue", "Speed"};
    private String [] values = new String[]{"29.5%", "450 EUR", "23 MS"};

    public MonitorScreen9003() {
    }

    @Override
    public void init() {

        removeAll();

        int idx = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                infoPanels[idx] = new InfoPanel("MA"+idx, infoTYPES[idx%3], texts[idx%3], values[idx%3]);
                infoPanels[idx].setBounds(20+(i*263), 20+j*113, 260, 110);
                add(infoPanels[idx]);
                idx++;
            }
        }
    }

    public InfoPanel getInfoPanel (String IDENTIFIER){
        for (InfoPanel infoPanel : infoPanels){
            if (infoPanel != null){
                if (infoPanel.getIDENTIFIER().equals(IDENTIFIER)){
                    return infoPanel;
                }
            }
        }
        return null;
    }
}
