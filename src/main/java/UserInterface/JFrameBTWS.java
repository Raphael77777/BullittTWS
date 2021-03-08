package UserInterface;

import DataHandling.*;
import IbAccountDataHandling.TwsThread;
import UserInterface.STATIC.GraphicalTheme;
import UserInterface.Screen.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JFrameBTWS extends JFrame {

    private static final JFrameBTWS jFrameBTWS = new JFrameBTWS();
    private static BackgroundScreen backgroundScreen;

    private boolean twsInit = false;
    private boolean twsStarted = false;

    /* DATA */
    private final HistoryData historyData = new HistoryData();
    private StrategyData strategyData;
    private AlphaVantageData alphaVantageData;
    private final LiveData liveData = new LiveData();
    private final AccountData accountData = new AccountData();
    public PositionData positionData = new PositionData();

    /* Screen 900X */
    private final WelcomeScreen9000 welcomeScreen9000 = new WelcomeScreen9000();
    private final HomeScreen9001 homeScreen9001 = new HomeScreen9001(historyData);
    private final EngineScreen9002 engineScreen9002;
    private final MonitorScreen9003 monitorScreen9003 = new MonitorScreen9003(liveData);
    private final SettingsScreen9004 settingsScreen9004;
    private final TransactionsScreen9005 transactionsScreen9005 = new TransactionsScreen9005(historyData);
    private final AccountsScreen9006 accountsScreen9006 = new AccountsScreen9006(accountData);
    private final PositionsScreen9007 positionsScreen9007 = new PositionsScreen9007(positionData, accountData);

    /* TWS THREAD */
    private TwsThread tws;

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

        /* DESERIALIZE STRATEGY_DATA */
        try {
            strategyData = loadStrategy();
        }catch (IOException e){
            strategyData = new StrategyData();
        }
        /* DESERIALIZE ALPHA_VANTAGE_DATA */
        try {
            alphaVantageData = loadAlphaVantage();
        }catch (IOException e){
            alphaVantageData = new AlphaVantageData();
        }

        /* CREATE SCREEN WITH DATA */
        engineScreen9002 = new EngineScreen9002(strategyData, alphaVantageData);
        settingsScreen9004 = new SettingsScreen9004(strategyData);

        /* INIT BACKGROUND, MENU, LOGO */
        backgroundScreen = new BackgroundScreen();
        backgroundScreen.setVisible(true);
        add(backgroundScreen);
        repaint();

        //TODO : TO REMOVE
        //transactionData.addTransactions("MSFT", "EUR", TransactionTYPE.BUY, new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 5, 3.45, 342.10);
        //alphaVantageData.setAPI_KEY_1("Q0QIVTR31CWPFT62");
        //alphaVantageData.setAPI_KEY_2("2L5CKCXQNF3JLODF");
        //AlphaVantageAdapter ava = new AlphaVantageAdapter(alphaVantageData);
        //AdapterSMA adapterSMA = new AdapterSMA(alphaVantageData);
        //AdapterRSI adapterRSI = new AdapterRSI(alphaVantageData);
        /*try {
            System.out.println("SMA200 : "+ adapterSMA.get("USDEUR", "1min", "200", "open"));
            System.out.println("SMA5 : "+adapterSMA.get("USDEUR", "1min", "5", "open"));
            System.out.println("RSI2 : "+adapterRSI.get("USDEUR", "1min", "10", "open"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //SimulatorLiveData simulatorLiveData = new SimulatorLiveData(liveData);
        //Thread t = new Thread(simulatorLiveData);
        //t.start();

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
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        welcomeScreen9000.init();
        welcomeScreen9000.setVisible(true);
        backgroundScreen.add(welcomeScreen9000);

        repaint();
    }
    public void showHomeScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        homeScreen9001.init();
        homeScreen9001.setVisible(true);
        backgroundScreen.add(homeScreen9001);

        repaint();
    }
    public void showEngineScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        engineScreen9002.init();
        engineScreen9002.setVisible(true);
        backgroundScreen.add(engineScreen9002);

        repaint();
    }
    public void showMonitorScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        monitorScreen9003.init();
        monitorScreen9003.setVisible(true);
        backgroundScreen.add(monitorScreen9003);

        repaint();
    }
    public void showSettingsScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        transactionsScreen9005.setVisible(false);
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        settingsScreen9004.setVisible(true);
        backgroundScreen.add(settingsScreen9004);

        repaint();
    }
    public void showTransactionsScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        accountsScreen9006.setVisible(false);
        positionsScreen9007.setVisible(false);

        transactionsScreen9005.init();
        transactionsScreen9005.setVisible(true);
        backgroundScreen.add(transactionsScreen9005);

        repaint();
    }

    public void showAccountsScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);
        positionsScreen9007.setVisible(false);

        accountsScreen9006.init();
        accountsScreen9006.setVisible(true);
        backgroundScreen.add(accountsScreen9006);

        repaint();
    }

    public void showPositionsScreen() {

        /* INIT TWS */
        init();

        /* Disable others screens */
        welcomeScreen9000.setVisible(false);
        homeScreen9001.setVisible(false);
        engineScreen9002.setVisible(false);
        monitorScreen9003.setVisible(false);
        settingsScreen9004.setVisible(false);
        transactionsScreen9005.setVisible(false);
        accountsScreen9006.setVisible(false);

        positionsScreen9007.init();
        positionsScreen9007.setVisible(true);
        backgroundScreen.add(positionsScreen9007);

        repaint();
    }

    public void changeScreen(String target) {

        switch (target){
            case "WELCOME":
                showWelcomeScreen();
                break;
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
            case "ACCOUNTS":
                showAccountsScreen();
                break;
            case "POSITIONS":
                showPositionsScreen();
                break;
            case "EMERGENCY":
                engineScreen9002.startAndStop();
                return;
        }

        if (target.equals("COMPIL")){
            if (twsStarted){
                JOptionPane.showMessageDialog(getRootPane(), "Cannot change the strategy while it is running. Stop the engine before making any changes.");
            }else {
                settingsScreen9004.changeStrategy();
            }
            return;
        }

        backgroundScreen.changeScreen(target);
    }

    public StrategyData loadStrategy () throws IOException {
        StrategyData strategyData = null;

        FileInputStream in = null;
        ObjectInputStream ois = null;
        try {
            String home = System.getProperty("user.home");
            File file = new File(home+"/strategy.bullitt");
            in = new FileInputStream(file);
            ois = new ObjectInputStream( in );
            strategyData = (StrategyData) ois.readObject();
        } catch(ClassNotFoundException w) {
            w.printStackTrace();
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException r) {
                r.printStackTrace();
            }
        }

        return strategyData;
    }

    public AlphaVantageData loadAlphaVantage() throws IOException {
        AlphaVantageData alphaVantageData = null;

        FileInputStream in = null;
        ObjectInputStream ois = null;
        try {
            String home = System.getProperty("user.home");
            File file = new File(home+"/alpha.bullitt");
            in = new FileInputStream(file);
            ois = new ObjectInputStream( in );
            alphaVantageData = (AlphaVantageData) ois.readObject();
        } catch(ClassNotFoundException w) {
            w.printStackTrace();
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException r) {
                r.printStackTrace();
            }
        }

        return alphaVantageData;
    }

    public void setAPIKEY() {

        String key = JOptionPane.showInputDialog(
                this,
                "Enter the secret API key",
                "AlphaVantage APIs",
                JOptionPane.INFORMATION_MESSAGE
        );

        if (key == null){
            return;
        }

        if (key.length() != 16){
            JOptionPane.showMessageDialog(getRootPane(), "Wrong APIs key !");
            return;
        }

        alphaVantageData.setAPI_KEY(key);
    }

    public void init() {

        //TODO : INIT TWS

        if (twsInit){
            return;
        }
        System.out.println("> TWS HAS BEEN INIT !");

        tws = new TwsThread(strategyData, historyData, liveData, alphaVantageData, accountData, positionData);
        /* THREAD TWS */
        Thread threadTWS = new Thread(tws);
        threadTWS.start();

        twsInit = true;
    }

    public boolean start() {

        if (tws.startMarketAdapter()){
            System.out.println("> TWS HAS BEEN STARTED !");
            backgroundScreen.engineStarted();

            twsStarted = true;
            return true;
        }

        return false;
    }

    public boolean stop() {

        if (tws.stopMarketAdapter()){
            System.out.println("> TWS HAS BEEN STOPPED !");
            backgroundScreen.engineStopped();

            twsStarted = false;
            return true;
        }

        return false;
    }
}
