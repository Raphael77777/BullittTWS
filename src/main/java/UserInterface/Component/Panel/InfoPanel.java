package UserInterface.Component.Panel;

import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InfoPanel extends JPanel {

    /* Values */
    private final String IDENTIFIER;
    private InfoTYPE type = InfoTYPE.NEUTRAL;
    private String text = "";
    private String value = "";

    public InfoPanel(String IDENTIFIER, InfoTYPE type, String text, String value) {

        setBounds(0, 0, 260, 110);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.type = type;
        this.text = text;
        this.value = value;

        init();
    }

    public void update (InfoTYPE type, String text, String value) {
        this.type = type;
        this.text = text;
        this.value = value;

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
        g2d.draw(new Line2D.Float(230, 110, 260, 110));
        g2d.draw(new Line2D.Float(260, 80, 260, 110));
        g2d.draw(new Line2D.Float(0, 80, 0, 110));
        g2d.draw(new Line2D.Float(0, 110, 30, 110));
        g2d.draw(new Line2D.Float(230, 0, 260, 0));
        g2d.draw(new Line2D.Float(260, 0, 260, 30));

        g.setFont(GraphicalTheme.font_header1);
        int width = g.getFontMetrics().stringWidth(value);

        int x = (w/2)-(width/2)-50;
        int y = (int ) (h*0.47) - 30;

        if (type == InfoTYPE.POSITIVE){
            g2d.setPaint(GraphicalTheme.primary_color);
            g2d.fillPolygon(new int[] {15+x, x, 30+x}, new int[] {y, 30+y, 30+y}, 3);
        } else if (type == InfoTYPE.NEGATIVE){
            g2d.setPaint(GraphicalTheme.secondary_color);
            g2d.fillPolygon(new int[] {x, 15+x, 30+x}, new int[] {y, 30+y, y}, 3);
        } else if (type == InfoTYPE.NEUTRAL){
            g2d.setPaint(GraphicalTheme.light_color);
            g2d.fillPolygon(new int[] {x, x, 30+x}, new int[] {y, 30+y, 15+y}, 3);
        } else if (type == InfoTYPE.NO_ICON){
            g2d.setPaint(GraphicalTheme.light_color);
        }else if (type == InfoTYPE.GREEN){
            g2d.setPaint(GraphicalTheme.primary_color);
        }else if (type == InfoTYPE.RED){
            g2d.setPaint(GraphicalTheme.secondary_color);
        }

        g.drawString(value, (w-width)/2, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (w-width)/2, (int ) (h*0.85));
    }

    private void init (){
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
