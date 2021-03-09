package UserInterface.Component.Input;

import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputInteger extends JPanel implements InputInterface {

    /* VALUES */
    private final String DD_Header;

    /* COMPONENTS */
    private final JTextField jTextField;

    public InputInteger(String DD_Header) {
        setBounds(0, 0, 260, 110);
        setOpaque(false);
        setLayout(null);

        this.DD_Header = DD_Header;

        jTextField = new JTextField("0");

        init();
    }

    public void setNumber (int i){
        if (jTextField != null){
            jTextField.setText(String.valueOf(i));
        }
    }

    public int getNumber (){
        if (jTextField != null){
            return Integer.parseInt(jTextField.getText());
        }

        return 0;
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
        jTextField.setFont(GraphicalTheme.font_header2);
        jTextField.setForeground(GraphicalTheme.light_color);
        jTextField.setOpaque(false);
        jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    int number = Integer.parseInt(jTextField.getText());

                    if (number <= 1 || number > 10){
                        throw new Exception();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(getRootPane(), "Only numbers between 1 and 10 are allowed");
                    jTextField.setText("");
                }
            }
        });
        jTextField.setBounds(20, 10, 220, 50);
        add(jTextField);
    }
}
