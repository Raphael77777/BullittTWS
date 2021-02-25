package UserInterface.Component.Panel;

import UserInterface.Component.Enum.InputTYPE;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class InputPanel extends JPanel {

    /* Values */
    private final String IDENTIFIER;
    private InputTYPE type = InputTYPE.DROPDOWN;
    private String text = "";
    private String [] dropdown;

    private JComboBox<String> jComboBox;
    private JTextField jTextField;

    public InputPanel(String IDENTIFIER, InputTYPE type, String text, String [] dropdown) {

        setBounds(0, 0, 260, 110);
        setOpaque(false);
        setLayout(null);

        this.IDENTIFIER = IDENTIFIER;
        this.type = type;
        this.text = text;
        this.dropdown = dropdown;

        if (type == InputTYPE.DROPDOWN && dropdown != null){
            jComboBox = new JComboBox<>(dropdown);
        }

        if (type == InputTYPE.DOUBLE){
            jTextField = new JTextField("0.00");
        }

        if (type == InputTYPE.INTEGER){
            jTextField = new JTextField("0");
        }

        init();
    }

    public void update (InputTYPE type, String text, String [] dropdown) {
        this.type = type;
        this.text = text;
        this.dropdown = dropdown;

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

        g.setFont(GraphicalTheme.font_header2);
        g.setColor(GraphicalTheme.light_color);
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (w-width)/2, (int ) (h*0.85));
    }

    private void init (){
        if (type == InputTYPE.DOUBLE || type == InputTYPE.INTEGER){
            jTextField.setFont(GraphicalTheme.font_header2);
            jTextField.setForeground(GraphicalTheme.light_color);
            jTextField.setOpaque(false);

            if (type == InputTYPE.DOUBLE){
                /**jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                 public void keyReleased(java.awt.event.KeyEvent evt) {
                 try {
                 double number = Double.parseDouble(jTextField.getText());
                 } catch (Exception e) {
                 JOptionPane.showMessageDialog(getRootPane(), "Only Numbers Allowed");
                 jTextField.setText("");
                 }
                 }
                 });*/
            }else if (type == InputTYPE.INTEGER){
                /**jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                 public void keyReleased(java.awt.event.KeyEvent evt) {
                 try {
                 Integer number = Integer.parseInteger(jTextField.getText());
                 } catch (Exception e) {
                 JOptionPane.showMessageDialog(getRootPane(), "Only Numbers Allowed");
                 jTextField.setText("");
                 }
                 }
                 });*/
            }

            //jTextField.setBorder(BorderFactory.createEmptyBorder());
            jTextField.setBounds(20, 10, 220, 50);
            add(jTextField);
        } else if (type == InputTYPE.DROPDOWN){
            jComboBox.setFont(GraphicalTheme.font_header2);
            jComboBox.setForeground(GraphicalTheme.light_color);
            jComboBox.setOpaque(false);
            jComboBox.setRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList list, Object value,
                                                              int index, boolean isSelected, boolean cellHasFocus) {
                    JComponent result = (JComponent)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    result.setOpaque(false);
                    return result;
                }});
            jComboBox.setBorder(BorderFactory.createEmptyBorder());
            jComboBox.setBounds(20, 10, 220, 50);
            add(jComboBox);
        }
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
