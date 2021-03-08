package UserInterface.Component.Input;

import UserInterface.Component.Enum.EnumType;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputDoubleL extends JPanel implements InputInterface {

    /* Values */
    private String DD_Header;
    private String DD_Description;
    private EnumType DD_Type;

    /* Components */
    private JTextField jTextField;

    public InputDoubleL(EnumType DD_Type, String DD_Header, String DD_Description) {

        setBounds(0, 0, 523, 110);
        setOpaque(false);
        setLayout(null);

        this.DD_Type = DD_Type;
        this.DD_Header = DD_Header;
        this.DD_Description = DD_Description;

        jTextField = new JTextField("0.00");

        init();
    }

    public void setNumber (double d){
        if (jTextField != null){
            jTextField.setText(String.valueOf(d));
        }
    }

    public double getNumber (){
        if (jTextField != null){
            return Double.parseDouble(jTextField.getText());
        }

        return 0.00d;
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
        g2d.draw(new Line2D.Float(493, 110, 523, 110));
        g2d.draw(new Line2D.Float(523, 80, 523, 110));
        g2d.draw(new Line2D.Float(0, 80, 0, 110));
        g2d.draw(new Line2D.Float(0, 110, 30, 110));
        g2d.draw(new Line2D.Float(493, 0, 523, 0));
        g2d.draw(new Line2D.Float(523, 0, 523, 30));

        if (DD_Type == EnumType.POSITIVE){
            g.setColor(GraphicalTheme.primary_color);
        }else if (DD_Type == EnumType.NEGATIVE){
            g.setColor(GraphicalTheme.secondary_color);
        }else {
            g.setColor(GraphicalTheme.light_color);
        }

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(DD_Header, 20, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.drawString(DD_Description, 20, (int ) (h*0.85));
    }

    @Override
    public void init (){
        jTextField.setFont(GraphicalTheme.font_header2);
        jTextField.setForeground(GraphicalTheme.light_color);
        jTextField.setOpaque(false);

        jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double number = Double.parseDouble(jTextField.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(getRootPane(), "Only Numbers Allowed");
                    jTextField.setText("");
                }
            }
        });

        jTextField.setBounds(283, 10, 220, 50);
        add(jTextField);
    }
}
