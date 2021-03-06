package UserInterface.Component.Panel;

import DataHandling.TransactionDTO;
import UserInterface.STATIC.GraphicalTheme;
import com.ib.client.Types;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class TransactionPanel extends JPanel {

    /* Values */
    private String IDENTIFIER;
    private TransactionDTO transactionDTO;

    private Color color_parent;
    private Color color_child;

    public TransactionPanel(String IDENTIFIER, TransactionDTO transactionDTO) {

        setBounds(0, 0, 769, 363);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.transactionDTO = transactionDTO;

        if (transactionDTO.getAction() == Types.Action.BUY){
            color_parent = GraphicalTheme.primary_color;
            color_child = GraphicalTheme.secondary_color;
        }else {
            color_parent = GraphicalTheme.secondary_color;
            color_child = GraphicalTheme.primary_color;
        }

        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color_parent);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new Line2D.Float(0, 0, 30, 0));
        g2d.draw(new Line2D.Float(0, 0, 0, 30));
        g2d.draw(new Line2D.Float(0, 150, 0, 120));
        g2d.draw(new Line2D.Float(0, 150, 30, 150));
        g2d.draw(new Line2D.Float(766, 0, 736, 0));
        g2d.draw(new Line2D.Float(766, 0, 766, 30));
        g2d.draw(new Line2D.Float(766, 150, 766, 120));
        g2d.draw(new Line2D.Float(766, 150, 736, 150));

        g2d.setColor(color_child);
        g2d.draw(new Line2D.Float(50, 155, 80, 155));
        g2d.draw(new Line2D.Float(50, 155, 50, 185));
        g2d.draw(new Line2D.Float(50, 255, 50, 225));
        g2d.draw(new Line2D.Float(50, 255, 80, 255));
        g2d.draw(new Line2D.Float(766, 155, 736, 155));
        g2d.draw(new Line2D.Float(766, 155, 766, 185));
        g2d.draw(new Line2D.Float(766, 255, 766, 225));
        g2d.draw(new Line2D.Float(766, 255, 736, 255));

        g2d.draw(new Line2D.Float(50, 260, 80, 260));
        g2d.draw(new Line2D.Float(50, 260, 50, 290));
        g2d.draw(new Line2D.Float(50, 360, 50, 330));
        g2d.draw(new Line2D.Float(50, 360, 80, 360));
        g2d.draw(new Line2D.Float(766, 260, 736, 260));
        g2d.draw(new Line2D.Float(766, 260, 766, 290));
        g2d.draw(new Line2D.Float(766, 360, 766, 330));
        g2d.draw(new Line2D.Float(766, 360, 736, 360));

        g2d.setColor(GraphicalTheme.light_color);
        g2d.draw(new Line2D.Float(15, 155, 15, 310));
        g2d.draw(new Line2D.Float(15, 205, 50, 205));
        g2d.draw(new Line2D.Float(15, 310, 50, 310));

        int x = 20, y = 27;
        int x_TP = 70, y_TP = 175;
        int x_SL = 70, y_SL = 280;

        /* DRAW ICON */
        if (transactionDTO.getAction() == Types.Action.BUY){
            g2d.setPaint(GraphicalTheme.secondary_color);
            g2d.fillPolygon(new int[] {x_TP, 15+x_TP, 30+x_TP}, new int[] {y_TP, 30+y_TP, y_TP}, 3);
            g2d.fillPolygon(new int[] {x_SL, 15+x_SL, 30+x_SL}, new int[] {y_SL, 30+y_SL, y_SL}, 3);
            g2d.setPaint(GraphicalTheme.primary_color);
            g2d.fillPolygon(new int[] {15+x, x, 30+x}, new int[] {y, 30+y, 30+y}, 3);
        } else if (transactionDTO.getAction() == Types.Action.SELL){
            g2d.setPaint(GraphicalTheme.primary_color);
            g2d.fillPolygon(new int[] {15+x_TP, x_TP, 30+x_TP}, new int[] {y_TP, 30+y_TP, 30+y_TP}, 3);
            g2d.fillPolygon(new int[] {15+x_SL, x_SL, 30+x_SL}, new int[] {y_SL, 30+y_SL, 30+y_SL}, 3);
            g2d.setPaint(GraphicalTheme.secondary_color);
            g2d.fillPolygon(new int[] {x, 15+x, 30+x}, new int[] {y, 30+y, y}, 3);
        }

        /* parent */
        g.setFont(GraphicalTheme.font_header1);
        g.setColor(color_parent);
        g.drawString(transactionDTO.getAsset(), 60, 57);

        g.setColor(GraphicalTheme.light_color);
        int width = g.getFontMetrics().stringWidth(String.valueOf(transactionDTO.getOrderId()));
        g.drawString(String.valueOf(transactionDTO.getOrderId()), w-width-20, 57);

        int widthM = g.getFontMetrics().stringWidth(String.valueOf(transactionDTO.getType()));
        g.drawString(transactionDTO.getType().toString(), (w-widthM)/2, 57);

        double quantity = transactionDTO.getQuantity()/1000;
        g.setFont(GraphicalTheme.font_header2);
        g.drawString("@ "+transactionDTO.getLimitPrice(), (w-widthM)/2, (int ) (h*0.26));
        g.drawString("AVG: "+transactionDTO.getAvgFillPrice(), (w-widthM)/2, (int ) (h*0.36));
        g.drawString(transactionDTO.getAction() +" "+quantity+"K UNIT", 20, (int ) (h*0.26));
        g.drawString(transactionDTO.getStatus(), 20, (int ) (h*0.36));

        width = g.getFontMetrics().stringWidth(transactionDTO.getDate().toString());
        g.drawString(transactionDTO.getDate().toString(), w-width-20, (int ) (h*0.26));
        width = g.getFontMetrics().stringWidth(transactionDTO.getTime().toString());
        g.drawString(transactionDTO.getTime().toString(), w-width-20, (int ) (h*0.36));

        /* child tp */
        g.setFont(GraphicalTheme.font_header1);
        g.setColor(color_child);
        g.drawString("TP", 110, 205);

        g.setColor(GraphicalTheme.light_color);
        width = g.getFontMetrics().stringWidth(String.valueOf(transactionDTO.getOrderId_tp()));
        g.drawString(String.valueOf(transactionDTO.getOrderId_tp()), w-width-20, 205);
        g.drawString(transactionDTO.getType_tp().toString(), (w-widthM)/2, 205);

        g.setFont(GraphicalTheme.font_header2);
        g.drawString("AVG: "+transactionDTO.getAvgFillPrice_tp(), (w-widthM)/2, 240);
        g.drawString("@ "+transactionDTO.getTakeProfitLimitPrice(), (w-widthM)/2+65, 205);
        g.drawString(transactionDTO.getStatus_tp(), 160, 205);
        Types.Action action = ((transactionDTO.getAction() == Types.Action.BUY) ? Types.Action.SELL : Types.Action.BUY);
        g.drawString(action +" "+quantity+"K UNIT", 70, 240);

        /* child sl */
        g.setFont(GraphicalTheme.font_header1);
        g.setColor(color_child);
        g.drawString("SL", 110, 310);

        g.setColor(GraphicalTheme.light_color);
        width = g.getFontMetrics().stringWidth(String.valueOf(transactionDTO.getOrderId_sl()));
        g.drawString(String.valueOf(transactionDTO.getOrderId_sl()), w-width-20, 310);
        g.drawString(transactionDTO.getType_sl().toString(), (w-widthM)/2, 310);

        g.setFont(GraphicalTheme.font_header2);
        g.drawString("AVG: "+transactionDTO.getAvgFillPrice_sl(), (w-widthM)/2, 345);
        g.drawString("@ "+transactionDTO.getStopLossPrice(), (w-widthM)/2+65, 310);
        g.drawString(transactionDTO.getStatus_sl(), 160, 310);
        g.drawString(action +" "+quantity+"K UNIT", 70, 345);

    }

    private void init (){
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
