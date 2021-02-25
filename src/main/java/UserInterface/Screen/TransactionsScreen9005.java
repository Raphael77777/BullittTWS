package UserInterface.Screen;

import UserInterface.Component.Panel.TransacPanel;
import UserInterface.Component.Enum.TransacTYPE;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;

public class TransactionsScreen9005 extends AbstractScreen {

    private TransacPanel [] transacPanels = new TransacPanel[10];
    private TransacTYPE [] transacTYPES = new TransacTYPE[]{TransacTYPE.BUY, TransacTYPE.SELL};

    public TransactionsScreen9005() {
    }

    @Override
    public void init() {

        removeAll();

        JPanel transactions = new JPanel();
        transactions.setLayout(null);
        transactions.setPreferredSize(new Dimension(766,transacPanels.length*105+5));

        for (int i = 0; i<transacPanels.length; i++){
            TransacPanel transacPanel = new TransacPanel("MA", "EUR/USD", "USD", transacTYPES[i%2], new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), 1.9872, 32.02, 1.07672);
            transacPanel.setBounds(16, 5+(i*105), 766, 100);
            transactions.add(transacPanel);
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
}
