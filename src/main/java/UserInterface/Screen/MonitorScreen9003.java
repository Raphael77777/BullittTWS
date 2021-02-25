package UserInterface.Screen;

import DataHandling.LiveData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Panel.InfoPanel;

public class MonitorScreen9003 extends AbstractScreen implements Observer {

    private InfoPanel [] infoPanels = new InfoPanel[15];

    private LiveData liveData;

    private final String [] IP_texts = new String[]{"P&L", "P&L %", "#Order", "#Analysis", "Compelling analysis", "Time since start", "Start time", "Analysis time", "Average analysis time", "Next analysis", "Time to auto stop", "Auto stop time", "Start price", "Current price", "Average price"};
    private String [] IP_values = new String[]{"--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--"};
    private InfoTYPE [] IP_types = new InfoTYPE[]{InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.GREEN, InfoTYPE.GREEN, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.RED, InfoTYPE.RED, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON, InfoTYPE.NO_ICON};

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

        double profitLoss = liveData.getProfitLoss();
        IP_values[0] = profitLoss+" USD";
        if (profitLoss > 0){
            IP_types[0] = InfoTYPE.POSITIVE;
        }if (profitLoss < 0){
            IP_types[0] = InfoTYPE.NEGATIVE;
        }

        double profitLossPercentage = liveData.getProfitLossPercentage();
        IP_values[1] = profitLossPercentage+" %";
        if (profitLossPercentage > 0){
            IP_types[1] = InfoTYPE.POSITIVE;
        }if (profitLossPercentage < 0){
            IP_types[1] = InfoTYPE.NEGATIVE;
        }

        IP_values[2] = liveData.getSumOrder()+"";
        IP_values[3] = liveData.getSumAnalysis()+"";
        IP_values[4] = liveData.getCompellingAnalysisPercentage()+" %";

        IP_values[5] = liveData.getTimeSinceStart()+"";
        IP_values[6] = liveData.getStartTime()+"";
        IP_values[7] = liveData.getAnalysisTime()+" ms";
        IP_values[8] = liveData.getAverageAnalysisTime()+" ms";
        IP_values[9] = liveData.getNextAnalysis()+" ms";

        IP_values[10] = liveData.getTimeToAutoStop()+"";
        IP_values[11] = liveData.getAutoStopTime()+"";
        IP_values[12] = liveData.getStartPrice()+"";
        IP_values[13] = liveData.getCurrentPrice()+"";
        IP_values[14] = liveData.getAveragePrice()+"";

        init();
    }
}
