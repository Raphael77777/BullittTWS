package UserInterface.Screen;

import DataHandling.HistoryData;
import DataHandling.TransactionDTO;
import UserInterface.Component.Panel.TransactionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionsScreen9005 extends AbstractScreen implements Observer{

    private TransactionPanel[] transactionPanels;

    private HistoryData historyData;

    private List<TransactionDTO> transactionDTOS;

    public TransactionsScreen9005(HistoryData historyData) {
        this.historyData = historyData;
        this.historyData.registerObserver(this);

        update();
    }

    @Override
    public void init() {

        removeAll();

        if (transactionDTOS == null || transactionDTOS.size() == 0){
            return;
        }

        JPanel transactions = new JPanel();
        transactions.setLayout(null);
        transactions.setPreferredSize(new Dimension(766,transactionDTOS.size()*368+5));

        transactionPanels = new TransactionPanel[transactionDTOS.size()];
        for (int i = 0; i< transactionPanels.length; i++){
            TransactionPanel transactionPanel = new TransactionPanel("TR"+i, transactionDTOS.get(i));
            transactionPanel.setBounds(16, 5+(i*368), 769, 363);
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

        /* CALL METHOD OF historyDATA to update arraylist */
        transactionDTOS = historyData.getTransactions();

        init();
    }
}
