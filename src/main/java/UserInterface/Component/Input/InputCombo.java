package UserInterface.Component.Input;

import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputCombo extends JPanel implements InputInterface {

    /* VALUES */
    private final String DD_Header;
    private final String [] DD_Values;

    /* COMPONENTS */
    private final JComboBox<String> jComboBox;

    public InputCombo(String DD_Header, String [] DD_Values) {
        setBounds(0, 0, 260, 110);
        setOpaque(false);
        setLayout(null);

        this.DD_Header = DD_Header;
        this.DD_Values = DD_Values;

        jComboBox = new JComboBox<>(DD_Values);

        init();
    }

    public void setSelected (String s){
        if (jComboBox != null){
            jComboBox.setSelectedItem(s);
        }
    }

    public String getSelected (){
        if (jComboBox != null){
            return DD_Values[jComboBox.getSelectedIndex()];
        }

        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        /* PAINT BORDERS AND TEXTS */
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

        g.setFont(GraphicalTheme.font_header2);
        g.setColor(GraphicalTheme.light_color);
        int width = g.getFontMetrics().stringWidth(DD_Header);
        g.drawString(DD_Header, (w-width)/2, (int ) (h*0.85));
    }

    @Override
    public void init(){
        /* INIT COMPONENT */
        jComboBox.setFont(GraphicalTheme.font_header2);
        jComboBox.setForeground(GraphicalTheme.dark_color);
        jComboBox.setOpaque(false);
        jComboBox.setBorder(BorderFactory.createEmptyBorder());
        jComboBox.setBounds(20, 10, 220, 50);
        add(jComboBox);
    }
}
