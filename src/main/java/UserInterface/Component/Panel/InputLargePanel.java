package UserInterface.Component.Panel;

import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputLargePanel extends JPanel {

    /* Values */
    private final String IDENTIFIER;
    private InfoTYPE type = InfoTYPE.POSITIVE;
    private String text = "";
    private String desc = "";

    private JTextField jTextField;

    public InputLargePanel(String IDENTIFIER, InfoTYPE type, String text, String desc) {

        setBounds(0, 0, 523, 110);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.type = type;
        this.text = text;
        this.desc = desc;

        jTextField = new JTextField("0.00");

        init();
    }

    public void update (InfoTYPE type, String text, String desc) {
        this.type = type;
        this.text = text;
        this.desc = desc;

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
        g2d.draw(new Line2D.Float(493, 110, 523, 110));
        g2d.draw(new Line2D.Float(523, 80, 523, 110));
        g2d.draw(new Line2D.Float(0, 80, 0, 110));
        g2d.draw(new Line2D.Float(0, 110, 30, 110));
        g2d.draw(new Line2D.Float(493, 0, 523, 0));
        g2d.draw(new Line2D.Float(523, 0, 523, 30));

        if (type == InfoTYPE.POSITIVE){
            g.setColor(GraphicalTheme.primary_color);
        }else if (type == InfoTYPE.NEGATIVE){
            g.setColor(GraphicalTheme.secondary_color);
        }else {
            g.setColor(GraphicalTheme.light_color);
        }

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(text, 20, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.drawString(desc, 20, (int ) (h*0.85));
    }

    private void init (){

            jTextField.setFont(GraphicalTheme.font_header2);
            jTextField.setForeground(GraphicalTheme.light_color);
            jTextField.setOpaque(false);
            /**jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyReleased(java.awt.event.KeyEvent evt) {
             try {
             double number = Long.parseLong(jTextField.getText());
             } catch (Exception e) {
             JOptionPane.showMessageDialog(getRootPane(), "Only Numbers Allowed");
             jTextField.setText("");
             }
             }
             });*/
            //jTextField.setBorder(BorderFactory.createEmptyBorder());
            jTextField.setBounds(283, 10, 220, 50);
            add(jTextField);

    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
