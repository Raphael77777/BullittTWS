package UserInterface.Component.Panel;

import DataHandling.TransactionDTO;
import UserInterface.Component.Enum.TransactionTYPE;
import UserInterface.STATIC.GraphicalTheme;
import com.ib.client.Types;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.sql.Date;

public class TransactionPanel extends JPanel {

    //TODO : TO UPDATE PANEL

    /* Values */
    private String IDENTIFIER;
    private TransactionDTO transactionDTO;

    public TransactionPanel(String IDENTIFIER, TransactionDTO transactionDTO) {

        setBounds(0, 0, 766, 100);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.transactionDTO = transactionDTO;

        init();
    }

    //TODO : TO REMOVE
    public TransactionPanel(String IDENTIFIER, String asset, String currency, Types.Action type, Date date, Time time, double quantity, double fees, double price) {

        setBounds(0, 0, 766, 100);
        setOpaque(false);
        setLayout(null);

        init();
    }

    public void update (TransactionDTO transactionDTO) {

        this.transactionDTO = transactionDTO;

        init();
    }

    //TODO : TO REMOVE
    public void update (String asset, String currency, Types.Action type, Date date, Time time, double quantity, double fees, double price) {

        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(GraphicalTheme.primary_color);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new Line2D.Float(0, 0, 30, 0));
        g2d.draw(new Line2D.Float(0, 0, 0, 30));
        g2d.draw(new Line2D.Float(736, 100, 766, 100));
        g2d.draw(new Line2D.Float(766, 70, 766, 100));
        g2d.draw(new Line2D.Float(0, 70, 0, 100));
        g2d.draw(new Line2D.Float(0, 100, 30, 100));
        g2d.draw(new Line2D.Float(736, 0, 766, 0));
        g2d.draw(new Line2D.Float(766, 0, 766, 30));

        int x = 20;
        int y = (int ) (h*0.47) - 30;

        if (transactionDTO.getAction() == Types.Action.BUY){
            g2d.setPaint(GraphicalTheme.primary_color);
            g2d.fillPolygon(new int[] {15+x, x, 30+x}, new int[] {y, 30+y, 30+y}, 3);
        } else if (transactionDTO.getAction() == Types.Action.SELL){
            g2d.setPaint(GraphicalTheme.secondary_color);
            g2d.fillPolygon(new int[] {x, 15+x, 30+x}, new int[] {y, 30+y, y}, 3);
        }

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(transactionDTO.getAsset(), 60, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.setColor(GraphicalTheme.light_color);
        g.drawString(transactionDTO.getAction() +" "+transactionDTO.getQuantity()+" UNIT", 20, (int ) (h*0.85));

        int width = g.getFontMetrics().stringWidth("@"+transactionDTO.getAvgFillPrice()+" "+0);
        g.drawString("@ "+0+" "+0, (w-width)/2-20, (int ) (h*0.47));
        BigDecimal bigDecimal = new BigDecimal(Double.toString(0*0));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        g.drawString(bigDecimal.doubleValue()+" "+0+" ["+0+"]", (w-width)/2-20, (int ) (h*0.85));

        width = g.getFontMetrics().stringWidth(transactionDTO.getDate().toString());
        g.drawString(transactionDTO.getDate().toString(), w-width-20, (int ) (h*0.47));
        width = g.getFontMetrics().stringWidth(transactionDTO.getTime().toString());
        g.drawString(transactionDTO.getTime().toString(), w-width-20, (int ) (h*0.85));

    }

    private void init (){
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
