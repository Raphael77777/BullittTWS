package UserInterface;

import DataHandling.LiveData;
import DataHandling.StrategyData;
import DataHandling.TranactionData;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.STATIC.GraphicalTheme;
import UserInterface.Screen.*;

import javax.swing.*;

public class JFrameBTWS extends JFrame {

    private static JFrameBTWS jFrameBTWS = new JFrameBTWS();
    private static BackgroundScreen backgroundScreen;

    /* DATA */
    private TranactionData tranactionData;
    private StrategyData strategyData;
    private LiveData liveData = new LiveData();

    /* Screen 900X */
    private WelcomeScreen9000 welcomeScreen9000 = new WelcomeScreen9000();
    private HomeScreen9001 homeScreen9001;
    private EngineScreen9002 engineScreen9002;
    private MonitorScreen9003 monitorScreen9003 = new MonitorScreen9003(liveData);
    private SettingsScreen9004 settingsScreen9004;
    private TransactionsScreen9005 transactionsScreen9005;

    private JFrameBTWS (){
        setLayout(null);
        setUndecorated(true);
        setBounds(0, 0, 1100, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(GraphicalTheme.title);
        setResizable(false);

        ImageIcon img = new ImageIcon(getClass().getResource("/FAV-512.png"));
        setIconImage(img.getImage());
        setVisible(true);

        /* GET DATA FROM DISK */
        //TODO : DESERIALIZE TRANSACTION_DATA
        tranactionData = new TranactionData();
        //TODO : DESERIALIZE STRATEGY_DATA
        strategyData = new StrategyData();

        /* CREATE SCREEN WITH DATA */
        homeScreen9001 = new HomeScreen9001(tranactionData);
        engineScreen9002 = new EngineScreen9002(strategyData);
        settingsScreen9004 = new SettingsScreen9004(strategyData);
        transactionsScreen9005 = new TransactionsScreen9005(tranactionData);

        /* INIT BACKGROUND, MENU, LOGO */
        backgroundScreen = new BackgroundScreen();
        backgroundScreen.setVisible(true);
        add(backgroundScreen);
        repaint();

        /* SHOW WelcomeScreen9001 */
        showWelcomeScreen();
    }

    public static JFrameBTWS getInstance() {
        return jFrameBTWS;
    }

    public void showWelcomeScreen() {

        /* Disable others screens */
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);

        welcomeScreen9000.init();
        welcomeScreen9000.setVisible(true);
        backgroundScreen.add(welcomeScreen9000);

        repaint();
    }
    public void showHomeScreen() {

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);

        homeScreen9001.init();
        homeScreen9001.setVisible(true);
        backgroundScreen.add(homeScreen9001);

        repaint();
    }
    public void showEngineScreen() {

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);

        engineScreen9002.init();
        engineScreen9002.setVisible(true);
        backgroundScreen.add(engineScreen9002);

        repaint();
    }
    public void showMonitorScreen() {

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);

        monitorScreen9003.init();
        monitorScreen9003.setVisible(true);
        backgroundScreen.add(monitorScreen9003);

        repaint();
    }
    public void showSettingsScreen() {

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        transactionsScreen9005.setVisible(false);

        settingsScreen9004.init();
        settingsScreen9004.setVisible(true);
        backgroundScreen.add(settingsScreen9004);

        repaint();
    }
    public void showTransactionsScreen() {
        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);

        transactionsScreen9005.init();
        transactionsScreen9005.setVisible(true);
        backgroundScreen.add(transactionsScreen9005);

        repaint();
    }

    public void changeScreen(String target) {

        switch (target){
            case "HOME":
                showHomeScreen();
                break;
            case "ENGINE":
                showEngineScreen();
                break;
            case "MONITOR":
                showMonitorScreen();
                break;
            case "SETTINGS":
                showSettingsScreen();
                break;
            case "HISTORY":
                showTransactionsScreen();
                break;
        }

        if (target.equals("COMPIL")){
            settingsScreen9004.changeStrategy();
            return;
        }

        backgroundScreen.changeScreen(target);
    }
}
