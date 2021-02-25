package UserInterface.Screen;

import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.Component.Enum.InputTYPE;
import UserInterface.Component.Panel.InfoButtonPanel;
import UserInterface.Component.Panel.InfoPanel;
import UserInterface.Component.Panel.InputLargePanel;
import UserInterface.Component.Panel.InputPanel;

public class SettingsScreen9004 extends AbstractScreen {

    private InputPanel[] inputPanels = new InputPanel[3];
    private String[] strings = new String[]{"Accuracy", "Timescale", "Exposure"};
    private InputTYPE[] inputTYPES = new InputTYPE[] {InputTYPE.DOUBLE, InputTYPE.DROPDOWN, InputTYPE.INTEGER};
    private String [][] dropdowns = new String[][]{null, new String[]{"60 seconds", "120 seconds", "180 seconds", "240 seconds"}, null};

    public SettingsScreen9004() {
    }

    @Override
    public void init() {

        removeAll();

        InfoButtonPanel strategy = new InfoButtonPanel("MI"+1, InfoTYPE.NO_ICON, "RSI (2)", "Strategy", "NO BUTTON");
        strategy.setBounds(20, 20, 523, 110);
        add(strategy);

        InputPanel asset = new InputPanel("MA1", InputTYPE.DROPDOWN, "Asset", new String[]{"USD/EUR", "CAD/EUR", "CHF/JPY"});
        asset.setBounds(546, 20, 260, 110);
        add(asset);

        for (int i = 0; i<inputPanels.length; i++){
            inputPanels[i] = new InputPanel("MA"+i, inputTYPES[i], strings[i], dropdowns[i]);
            inputPanels[i].setBounds(20+(i*263), 133, 260, 110);
            add(inputPanels[i]);
        }

        InputLargePanel take_profit = new InputLargePanel("MA1", InfoTYPE.POSITIVE, "% Take Profit", "Select a limit for take profit order");
        take_profit.setBounds(20, 246, 523, 110);
        add(take_profit);

        InfoPanel infoPanel1 = new InfoPanel("MA3", InfoTYPE.NO_ICON, "RMS", "Active");
        infoPanel1.setBounds(546, 246, 260, 110);
        add(infoPanel1);

        InputLargePanel stop_loss = new InputLargePanel("MA1", InfoTYPE.NEGATIVE, "% Stop Loss", "Select a limit for stop loss order");
        stop_loss.setBounds(20, 359, 523, 110);
        add(stop_loss);

        InfoPanel infoPanel2 = new InfoPanel("MA3", InfoTYPE.NO_ICON, "Auto-kill", "Active");
        infoPanel2.setBounds(546, 359, 260, 110);
        add(infoPanel2);

        InputPanel order = new InputPanel("MA1", InputTYPE.DROPDOWN, "Order", new String[]{"Market", "Limit"});
        order.setBounds(20, 472, 260, 110);
        add(order);

        InfoButtonPanel save = new InfoButtonPanel("MI"+1, InfoTYPE.NO_ICON, "COMPILATION", "Save and update the strategy", "COMPIL");
        save.setBounds(283, 472, 523, 110);
        add(save);
    }
}
