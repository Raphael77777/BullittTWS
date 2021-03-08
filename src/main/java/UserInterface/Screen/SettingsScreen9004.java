package UserInterface.Screen;

import StorageHandling.StrategyData;
import UserInterface.Component.Enum.EnumType;
import UserInterface.Component.Input.InputCombo;
import UserInterface.Component.Input.InputDouble;
import UserInterface.Component.Input.InputDoubleL;
import UserInterface.Component.Input.InputInteger;
import UserInterface.Component.Panel.ButtonPanel;
import UserInterface.Component.Panel.InfoPanel;

public class SettingsScreen9004 extends AbstractScreen implements Observer {

    private final StrategyData strategyData;

    private final String[] IN_Description = new String[]{"Asset", "Accuracy", "Timescale", "Multiplier", "% Take Profit", "% Stop Loss", "Order"};

    private final String [] IN_DropV0 = new String[]{"AUD.CAD", "AUD.CHF", "AUD.CNH", "AUD.HKD", "AUD.JPY", "AUD.NZD", "AUD.SGD", "AUD.USD", "AUD.ZAR", "CAD.CHF", "CAD.CNH", "CAD.JPY", "CHF.CNH", "CHF.CZK", "CHF.DKK", "CHF.HUF", "CHF.JPY", "CHF.NOK", "CHF.PLN", "CHF.SEK", "CHF.TRY", "CHF.ZAR", "CNH.HKD", "CNH.JPY", "DKK.JPY", "DKK.NOK", "DKK.SEK", "EUR.AUD", "EUR.CAD", "EUR.CHF", "EUR.CNH", "EUR.CZK", "EUR.DKK", "EUR.GBP", "EUR.HKD", "EUR.HUF", "EUR.ILS", "EUR.JPY", "EUR.MXN", "EUR.NOK", "EUR.NZD", "EUR.PLN", "EUR.RUB", "EUR.SEK", "EUR.SGD", "EUR.TRY", "EUR.USD", "EUR.ZAR", "GBP.AUD", "GBP.CAD", "GBP.CHF", "GBP.CNH", "GBP.CZK", "GBP.DKK", "GBP.HKD", "GBP.HUF", "GBP.JPY", "GBP.MXN", "GBP.NOK", "GBP.NZD", "GBP.PLN", "GBP.SEK", "GBP.SGD", "GBP.TRY", "GBP.USD", "GBP.ZAR", "HKD.JPY", "KRW.AUD", "KRW.CAD", "KRW.CHF", "KRW.EUR", "KRW.GBP", "KRW.HKD", "KRW.JPY", "KRW.USD", "MXN.JPY", "NOK.JPY", "NOK.SEK", "NZD.CAD", "NZD.CHF", "NZD.JPY", "NZD.USD", "SEK.JPY", "SGD.CNH", "SGD.HKD", "SGD.JPY", "USD.CAD", "USD.CHF", "USD.CNH", "USD.CZK", "USD.DKK", "USD.HKD", "USD.HUF", "USD.ILS", "USD.JPY", "USD.KRW", "USD.MXN", "USD.NOK", "USD.PLN", "USD.RUB", "USD.SEK", "USD.SGD", "USD.TRY", "USD.ZAR", "ZAR.JPY"};
    private final String [] IN_DropV2 = new String[]{"10 sec", "20 sec", "40 sec", "50 sec", "60 sec", "70 sec", "80 sec", "90 sec"};
    private final String [] IN_DropV6 = new String[]{"Market", "Limit"};

    private InputCombo asset;
    private InputCombo order;
    private InputCombo timescale;
    private InputDouble accuracy;
    private InputInteger multiplier;
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
        ButtonPanel strategy = new ButtonPanel(EnumType.NO_ICON, "RSI (2)", "Strategy", "NO BUTTON");
        strategy.setBounds(20, 20, 523, 110);
        add(strategy);

        asset = new InputCombo(IN_Description[0], IN_DropV0);
        asset.setBounds(546, 20, 260, 110);
        add(asset);

        accuracy = new InputDouble(IN_Description[1]);
        accuracy.setBounds(20, 133, 260, 110);
        add(accuracy);

        timescale = new InputCombo(IN_Description[2], IN_DropV2);
        timescale.setBounds(283, 133, 260, 110);
        add(timescale);

        multiplier = new InputInteger(IN_Description[3]);
        multiplier.setBounds(546, 133, 260, 110);
        add(multiplier);

        take_profit = new InputDoubleL(EnumType.POSITIVE, IN_Description[4], "Select a limit for take profit order");
        take_profit.setBounds(20, 246, 523, 110);
        add(take_profit);

        /* STATIC */
        InfoPanel infoPanelRMS = new InfoPanel(EnumType.NO_ICON, "RMS", "Active");
        infoPanelRMS.setBounds(546, 246, 260, 110);
        add(infoPanelRMS);

        stop_loss = new InputDoubleL(EnumType.NEGATIVE, IN_Description[5], "Select a limit for stop loss order");
        stop_loss.setBounds(20, 359, 523, 110);
        add(stop_loss);

        /* STATIC */
        InfoPanel infoPanelAKI = new InfoPanel(EnumType.NO_ICON, "Auto-kill", "Active");
        infoPanelAKI.setBounds(546, 359, 260, 110);
        add(infoPanelAKI);

        order = new InputCombo(IN_Description[6], IN_DropV6);
        order.setBounds(20, 472, 260, 110);
        add(order);

        /* STATIC */
        ButtonPanel save = new ButtonPanel(EnumType.NO_ICON, "COMPILATION", "Save and update the strategy", "COMPIL");
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
        multiplier.setNumber(strategyData.getMultiplier());
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
        strategyData.setMultiplier(multiplier.getNumber());
        strategyData.setTake_profit(take_profit.getNumber());
        strategyData.setStop_loss(stop_loss.getNumber());

        strategyData.compil();
    }
}
