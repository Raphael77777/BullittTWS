package UserInterface.Screen;

import DataHandling.LiveData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Panel.InfoPanel;

public class MonitorScreen9003 extends AbstractScreen implements Observer {

    private InfoPanel [] infoPanels = new InfoPanel[15];

    private LiveData liveData;

    private final String [] IP_texts = new String[]{"Daily P&L", "Unrealized P&L", "#Order", "#Analysis", "Compelling analysis", "Realized P&L", "Analysis time", "Average analysis time", "Min. analysis time", "Max. analysis time", "Time since start", "Start time", "Start price", "Current price", "Var. start"};
    private String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--"};
    private InfoTYPE [] IP_types = new InfoTYPE[]{InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.GREEN, InfoTYPE.GREEN, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON};

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
                infoPanels[idx] = new InfoPanel("IP"+idx, IP_types[idx], IP_texts[idx], IP_values[idx]);
                infoPanels[idx].setBounds(20+(i*263), 20+j*113, 260, 110);
                add(infoPanels[idx]);
                idx++;
            }
        }

        repaint();
    }

    @Override
    public void update() {

        /* CALL METHOD OF liveData to update IP_values and IP_types */

        double dailyPNL = liveData.getDailyPNL();
        IP_values[0] = String.valueOf(dailyPNL);
        if (dailyPNL > 0){
            IP_types[0] = InfoTYPE.POSITIVE;
        }if (dailyPNL < 0){
            IP_types[0] = InfoTYPE.NEGATIVE;
        }

        double unrealizedPNL = liveData.getUnrealizedPNL();
        IP_values[1] = String.valueOf(unrealizedPNL);
        if (unrealizedPNL > 0){
            IP_types[1] = InfoTYPE.POSITIVE;
        }if (unrealizedPNL < 0){
            IP_types[1] = InfoTYPE.NEGATIVE;
        }

        IP_values[2] = liveData.getSumOrder()+"";
        IP_values[3] = liveData.getSumAnalysis()+"";
        IP_values[4] = liveData.getCompellingAnalysisPercentage()+" %";

        double realizedPNL = liveData.getRealizedPNL();
        IP_values[5] = String.valueOf(realizedPNL);
        if (realizedPNL > 0){
            IP_types[5] = InfoTYPE.POSITIVE;
        }if (realizedPNL < 0){
            IP_types[5] = InfoTYPE.NEGATIVE;
        }

        IP_values[6] = liveData.getAnalysisTime()+" ms";
        IP_values[7] = liveData.getAverageAnalysisTime()+" ms";
        IP_values[8] = liveData.getMinAnalysisTime()+" ms";
        IP_values[9] = liveData.getMaxAnalysisTime()+" ms";

        IP_values[10] = liveData.getTimeSinceStart()+"";
        IP_values[11] = liveData.getStartTime()+"";
        IP_values[12] = liveData.getStartPrice()+"";
        IP_values[13] = liveData.getCurrentPrice()+"";

        double varOpening = liveData.getVarOpening();
        IP_values[14] = varOpening+" %";
        if (varOpening > 0){
            IP_types[14] = InfoTYPE.POSITIVE;
        }if (varOpening < 0){
            IP_types[14] = InfoTYPE.NEGATIVE;
        }

        init();
    }
}
