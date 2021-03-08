package UserInterface.Component.Panel;

import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class AccountPanel extends JPanel {

    /* Values */
    private String account;
    private String currency;

    public AccountPanel(String account, String currency) {

        setBounds(0, 0, 786, 110);
        setOpaque(false);
        setLayout(null);

        this.account = account;
        this.currency = currency;

    }

    public void update (String account, String currency) {

        this.account = account;
        this.currency = currency;

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
        g2d.draw(new Line2D.Float(756, 110, 786, 110));
        g2d.draw(new Line2D.Float(786, 80, 786, 110));
        g2d.draw(new Line2D.Float(0, 80, 0, 110));
        g2d.draw(new Line2D.Float(0, 110, 30, 110));
        g2d.draw(new Line2D.Float(756, 0, 786, 0));
        g2d.draw(new Line2D.Float(786, 0, 786, 30));

        int x = 42;
        int y = (int ) (h*0.47) - 30;

        g.setColor(GraphicalTheme.light_color);
        g2d.fillPolygon(new int[] {x, x, 30+x}, new int[] {y, 30+y, 15+y}, 3);

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(account, 82, (int ) (h*0.47));
        int width = g.getFontMetrics().stringWidth("Currency");
        g.drawString(currency, w-width-45, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.drawString("Account identifier", 40, (int ) (h*0.83));
        g.drawString("Currency", w-width-45, (int ) (h*0.83));
    }
}
