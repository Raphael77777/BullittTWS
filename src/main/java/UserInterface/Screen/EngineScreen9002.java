package UserInterface.Screen;

import UserInterface.Component.ImageLabel;
import UserInterface.Component.Panel.InfoButtonPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Enum.InfoTYPE;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EngineScreen9002 extends AbstractScreen {

    private MouseListener mouseListener = new startAndStop();
    private String state = "stop";

    private InfoPanel[] infoPanels = new InfoPanel[9];
    private InfoButtonPanel [] infoButtonPanels = new InfoButtonPanel[2];

    private String [] texts = new String[]{"Asset", "Strategy", "Accuracy", "Timescale", "Exposure", "Order", "Take profit", "Stop loss", "Auto-kill"};
    private String [] values = new String[]{"JPY/USD", "RSI(2)", "0.7", "60 sec", "90%", "Limit", "7.5%", "5%", "Active"};

    public EngineScreen9002() {}

    @Override
    public void init() {
        removeAll();

        int idx = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (idx < infoPanels.length){
                    infoPanels[idx] = new InfoPanel("MA"+idx, InfoTYPE.NO_ICON, texts[idx], values[idx]);
                    infoPanels[idx].setBounds(20+(i*263), 20+j*113, 260, 110);
                    add(infoPanels[idx]);
                    idx++;
                }
            }
        }

        for (int i = 0; i<infoButtonPanels.length; i++){
            infoButtonPanels[i] = new InfoButtonPanel("MI"+i, InfoTYPE.NO_ICON, "1. STRATEGY", "Modify or view the strategy", "HOME");
            infoButtonPanels[i].setBounds(20, 359+i*113, 523, 110);
            add(infoButtonPanels[i]);
        }

        ImageLabel logoImg = new ImageLabel(state+".png");
        logoImg.setBounds(583, 380, 200, 200);
        logoImg.addMouseListener(mouseListener);
        add(logoImg);

        repaint();
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

    private class startAndStop extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (state.equals("stop")){
                state = "start";
                display("ENGINE STARTED! Please do not switch off the computer and check the strategy settings.");
                //TODO: START ENGINE IN ANOTHER THREAD
            } else if (state.equals("start")){
                state = "stop";
                display("ENGINE STOPPED! You can turn off the computer and check the results of the strategy.");
                //TODO: STOP ENGINE IN ANOTHER THREAD
            }

            init();
        }
    }

    private void display (String message){
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("FAV-512.png");

                TrayIcon trayIcon = new TrayIcon(image, "BULLITT TWS");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("BULLITT TWS");

                tray.add(trayIcon);
                trayIcon.displayMessage("BULLITT TWS ALERT", message, TrayIcon.MessageType.NONE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("System tray not supported!");
        }
    }
}
