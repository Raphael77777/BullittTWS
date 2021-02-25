package UserInterface.Component;

import UserInterface.STATIC.GraphicalTheme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JButtonRounded extends JButton {

    /* Values */
    private final String string;
    private Font font = GraphicalTheme.font_header2;
    private Color color = GraphicalTheme.primary_color;

    public JButtonRounded(String string) {
        this.string = string;
        setContentAreaFilled(false);
    }

    public JButtonRounded(String string, Font font) {
        this.string = string;
        this.font = font;
        setContentAreaFilled(false);
    }

    public JButtonRounded(String string, Font font, Color color) {
        this.string = string;
        this.font = font;
        this.color = color;
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(1, 1, w - 2, h - 2, 40, 40);
        g2d.clip(r2d);
        g2d.setPaint(color);
        g2d.fillRect(0, 0, w, h);
        g2d.setPaint(GraphicalTheme.light_color);
        g.setFont(font);
        int width = g.getFontMetrics().stringWidth(string);
        g.drawString(string, (w-width)/2, (int ) (h*0.60));
    }

    @Override
    public String getText() { return string; }
}
