package UserInterface.Component.Panel;

import UserInterface.Component.Enum.TransactionTYPE;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.sql.Date;

public class TransactionPanel extends JPanel {

    /* Values */
    private final String IDENTIFIER;
    private String asset = "MSFT";
    private String currency = "EUR";

    private TransactionTYPE type = TransactionTYPE.BUY;
    private Date date = new Date(System.currentTimeMillis());
    private Time time = new Time(System.currentTimeMillis());

    private double quantity = 1;
    private double fees = 3.00;
    private double price = 300.00;

    public TransactionPanel(String IDENTIFIER, String asset, String currency, TransactionTYPE type, Date date, Time time, double quantity, double fees, double price) {

        setBounds(0, 0, 766, 100);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.asset = asset;
        this.currency = currency;
        this.type = type;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.fees = fees;
        this.price = price;

        init();
    }

    public void update (String asset, String currency, TransactionTYPE type, Date date, Time time, double quantity, double fees, double price) {

        this.asset = asset;
        this.currency = currency;
        this.type = type;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.fees = fees;
        this.price = price;

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

        if (type == TransactionTYPE.BUY){
            g2d.setPaint(GraphicalTheme.primary_color);
            g2d.fillPolygon(new int[] {15+x, x, 30+x}, new int[] {y, 30+y, 30+y}, 3);
        } else if (type == TransactionTYPE.SELL){
            g2d.setPaint(GraphicalTheme.secondary_color);
            g2d.fillPolygon(new int[] {x, 15+x, 30+x}, new int[] {y, 30+y, y}, 3);
        }

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(asset, 60, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.setColor(GraphicalTheme.light_color);
        g.drawString(type +" "+quantity+" UNIT", 20, (int ) (h*0.85));

        int width = g.getFontMetrics().stringWidth("@"+price+" "+currency);
        g.drawString("@ "+price+" "+currency, (w-width)/2-20, (int ) (h*0.47));
        BigDecimal bigDecimal = new BigDecimal(Double.toString(price*quantity));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        g.drawString(bigDecimal.doubleValue()+" "+currency+" ["+fees+"]", (w-width)/2-20, (int ) (h*0.85));

        width = g.getFontMetrics().stringWidth(date.toString());
        g.drawString(date.toString(), w-width-20, (int ) (h*0.47));
        width = g.getFontMetrics().stringWidth(time.toString());
        g.drawString(time.toString(), w-width-20, (int ) (h*0.85));

    }

    private void init (){
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
