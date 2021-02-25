package UserInterface.Component.Input;

import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputDouble extends JPanel implements InputInterface {

    /* Values */
    private final String IDENTIFIER;
    private String DD_Header = "";

    private JTextField jTextField;

    public InputDouble(String IDENTIFIER, String DD_Header) {

        setBounds(0, 0, 260, 110);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.DD_Header = DD_Header;

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

        //jTextField.setBorder(BorderFactory.createEmptyBorder());
        jTextField.setBounds(20, 10, 220, 50);
        add(jTextField);
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
