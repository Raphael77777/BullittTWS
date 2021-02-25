package UserInterface.Screen;

import DataHandling.StrategyData;
import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Input.*;
import UserInterface.Component.Panel.ButtonPanel;
import UserInterface.Component.Panel.InfoPanel;

import javax.swing.*;

public class SettingsScreen9004 extends AbstractScreen implements Observer {

    private StrategyData strategyData;

    private JPanel[] inputs = new JPanel[7];
    private final String[] IN_Description = new String[]{"Asset", "Accuracy", "Timescale", "Exposure", "% Take Profit", "% Stop Loss", "Order"};

    private String [] IN_DropV0 = new String[]{"USD/EUR", "CAD/EUR", "CHF/JPY"};
    private String [] IN_DropV2 = new String[]{"60 sec", "120 sec", "180 sec", "240 sec"};
    private String [] IN_DropV6 = new String[]{"Market", "Limit"};

    private InputCombo asset;
    private InputCombo order;
    private InputCombo timescale;
    private InputDouble accuracy;
    private InputInteger exposure;
    private InputDoubleL take_profit;
    private InputDoubleL stop_loss;

    public SettingsScreen9004(StrategyData strategyData) {
        this.strategyData = strategyData;
        this.strategyData.registerObserver(this);

        update();
    }

    @Override
    public void init() {

        removeAll();

        /* STATIC */
        ButtonPanel strategy = new ButtonPanel("STY", InfoTYPE.NO_ICON, "RSI (2)", "Strategy", "NO BUTTON");
        strategy.setBounds(20, 20, 523, 110);
        add(strategy);

        asset = new InputCombo("VA0", IN_Description[0], IN_DropV0);
        asset.setBounds(546, 20, 260, 110);
        add(asset);

        accuracy = new InputDouble("VA1", IN_Description[1]);
        accuracy.setBounds(20, 133, 260, 110);
        add(accuracy);

        timescale = new InputCombo("VA2", IN_Description[2], IN_DropV2);
        timescale.setBounds(283, 133, 260, 110);
        add(timescale);

        exposure = new InputInteger("VA3", IN_Description[3]);
        exposure.setBounds(546, 133, 260, 110);
        add(exposure);

        take_profit = new InputDoubleL("VA4", InfoTYPE.POSITIVE, IN_Description[4], "Select a limit for take profit order");
        take_profit.setBounds(20, 246, 523, 110);
        add(take_profit);

        /* STATIC */
        InfoPanel infoPanelRMS = new InfoPanel("RMS", InfoTYPE.NO_ICON, "RMS", "Active");
        infoPanelRMS.setBounds(546, 246, 260, 110);
        add(infoPanelRMS);

        stop_loss = new InputDoubleL("VA5", InfoTYPE.NEGATIVE, IN_Description[5], "Select a limit for stop loss order");
        stop_loss.setBounds(20, 359, 523, 110);
        add(stop_loss);

        /* STATIC */
        InfoPanel infoPanelAKI = new InfoPanel("AKI", InfoTYPE.NO_ICON, "Auto-kill", "Active");
        infoPanelAKI.setBounds(546, 359, 260, 110);
        add(infoPanelAKI);

        order = new InputCombo("VA6", IN_Description[6], IN_DropV6);
        order.setBounds(20, 472, 260, 110);
        add(order);

        /* STATIC */
        ButtonPanel save = new ButtonPanel("COMPIL", InfoTYPE.NO_ICON, "COMPILATION", "Save and update the strategy", "COMPIL");
        save.setBounds(283, 472, 523, 110);
        add(save);

        repaint();
    }

    @Override
    public void update() {

        init();

        /* CALL METHOD OF strategyData to update following */
        asset.setSelected(strategyData.getAsset());
        order.setSelected(strategyData.getOrder());
        timescale.setSelected(strategyData.getTimescale());
        accuracy.setNumber(strategyData.getAccuracy());
        exposure.setNumber(strategyData.getExposure());
        take_profit.setNumber(strategyData.getTake_profit());
        stop_loss.setNumber(strategyData.getStop_loss());

        repaint();
    }

    public void changeStrategy() {

        /* UPDATE STRATEGY WITHIN strategyData with data following */
        strategyData.setAsset(asset.getSelected());
        strategyData.setOrder(order.getSelected());
        strategyData.setTimescale(timescale.getSelected());
        strategyData.setAccuracy(accuracy.getNumber());
        strategyData.setExposure(exposure.getNumber());
        strategyData.setTake_profit(take_profit.getNumber());
        strategyData.setStop_loss(stop_loss.getNumber());

        strategyData.compil();
    }
}
