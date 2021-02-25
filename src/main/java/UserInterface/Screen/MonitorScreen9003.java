package UserInterface.Screen;

import DataHandling.LiveData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Panel.InfoPanel;

public class MonitorScreen9003 extends AbstractScreen implements Observer {

    private InfoPanel [] infoPanels = new InfoPanel[15];

    private LiveData liveData;

    private final String [] IP_texts = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--"};
    private InfoTYPE [] IP_types = new InfoTYPE[]{InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON};

    public MonitorScreen9003(LiveData liveData) {
        this.liveData = liveData;
        this.liveData.registerObserver(this);
    }

    @Override
    public void init() {

        removeAll();

        int idx = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                infoPanels[idx] = new InfoPanel("IP"+idx, IP_types[idx%3], IP_texts[idx], IP_values[idx%3]);
                infoPanels[idx].setBounds(20+(i*263), 20+j*113, 260, 110);
                add(infoPanels[idx]);
                idx++;
            }
        }

        repaint();
    }

    @Override
    public void update() {
        //TODO : CALL METHOD OF liveData to update IP_values and IP_types

        init();
    }
}
