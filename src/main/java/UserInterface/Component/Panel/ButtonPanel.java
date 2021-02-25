package UserInterface.Component.Panel;

import UserInterface.Component.Enum.InfoTYPE;
import UserInterface.JFrameBTWS;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class ButtonPanel extends JPanel {

    /* Values */
    private final String IDENTIFIER;
    private InfoTYPE type = InfoTYPE.NEUTRAL;
    private String header = "";
    private String text = "";
    private String target = "HOME";

    public ButtonPanel(String IDENTIFIER, InfoTYPE type, String header, String text, String target) {

        setBounds(0, 0, 523, 110);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.type = type;
        this.header = header;
        this.text = text;
        this.target = target;

        init();
    }

    public void update (InfoTYPE type, String header, String text, String target) {
        this.type = type;
        this.header = header;
        this.text = text;
        this.target = target;

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
            g2d.setPaint(GraphicalTheme.primary_color);
        } else if (type == InfoTYPE.NEGATIVE){
            g2d.setPaint(GraphicalTheme.secondary_color);
        } else if (type == InfoTYPE.NEUTRAL || type == InfoTYPE.NO_ICON){
            g2d.setPaint(GraphicalTheme.light_color);
        }

        g.setFont(GraphicalTheme.font_header1);
        g.drawString(header, 20, (int ) (h*0.47));

        g.setFont(GraphicalTheme.font_header2);
        g.drawString(text, 20, (int ) (h*0.85));
    }

    private void init (){

        if (!target.equals("NO BUTTON")){
            JButton jButton = new JButton("GO");
            jButton.setBackground(GraphicalTheme.primary_color);
            jButton.setFont(GraphicalTheme.font_header1);
            jButton.setForeground(GraphicalTheme.light_color);
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrameBTWS.getInstance().changeScreen(target);
                }
            });
            jButton.setBounds(428, 15, 80, 80);
            add(jButton);
        }
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
