package UserInterface.Screen;

import DataHandling.TranactionData;
import UserInterface.Component.Panel.TransactionPanel;
import UserInterface.Component.Enum.TransactionTYPE;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TransactionsScreen9005 extends AbstractScreen implements Observer{

    private TransactionPanel[] transactionPanels;

    private TranactionData tranactionData;

    private ArrayList<String> assets;
    private ArrayList<String> currencies;
    private ArrayList<TransactionTYPE> types;
    private ArrayList<Date> dates;
    private ArrayList<Time> times;
    private ArrayList<Double> quantities;
    private ArrayList<Double> fees;
    private ArrayList<Double> prices;

    public TransactionsScreen9005(TranactionData tranactionData) {
        this.tranactionData = tranactionData;
        this.tranactionData.registerObserver(this);
    }

    @Override
    public void init() {

        removeAll();

        if (assets == null || assets.size() == 0){
            return;
        }

        JPanel transactions = new JPanel();
        transactions.setLayout(null);
        transactions.setPreferredSize(new Dimension(766,assets.size()*105+5));

        transactionPanels = new TransactionPanel[assets.size()];
        for (int i = 0; i< transactionPanels.length; i++){
            TransactionPanel transactionPanel = new TransactionPanel("TR"+i, assets.get(i), currencies.get(i), types.get(i), dates.get(i), times.get(i), quantities.get(i), fees.get(i), prices.get(i));
            transactionPanel.setBounds(16, 5+(i*105), 766, 100);
            transactions.add(transactionPanel);
        }

        transactions.setOpaque(false);
        transactions.repaint();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setViewportView(transactions);
        JScrollBar jScrollBar = new JScrollBar();
        scrollPane.add(jScrollBar);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(0, 15, 828,575);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);
        scrollPane.repaint();
        scrollPane.revalidate();
    }

    @Override
    public void update() {
        //TODO : CALL METHOD OF tranactionData to update arraylist

        init();
    }
}
