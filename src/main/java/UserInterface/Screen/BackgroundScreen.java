package UserInterface.Screen;

import UserInterface.Component.ImageLabel;
import UserInterface.JFrameBTWS;
import UserInterface.STATIC.GraphicalTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class BackgroundScreen extends JPanel {

    /* MENU */
    private final JButton [] jButtons = new JButton[7];

    /* COMPONENTS */
    private final ImageLabel logoImg ;
    private final ImageLabel emergency ;

    public void paintComponent(Graphics g) {
        /* PAINT BACKGROUND */
        try {
            Image image = ImageIO.read(getClass().getResource("/background2.jpg"));
            g.setColor(Color.black);
            g.fillRect(0,0, getWidth(), getHeight());
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            Graphics2D g2d=(Graphics2D) g;
            g2d.setColor(GraphicalTheme.primary_color);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(272,20,272,580);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BackgroundScreen(){
        setBounds(0, 0, 1100, 600);
        setLayout(null);

        /* CLEAN PANEL */
        removeAll();

        /* DISPLAY MENU */
        String[] label = new String[]{"HOME", "ENGINE", "MONITOR", "SETTINGS", "HISTORY", "ACCOUNTS", "POSITIONS"};
        for (int i = 0; i< label.length; i++){
            jButtons[i] = new JButton(label[i]);
            jButtons[i].setBackground(GraphicalTheme.primary_color);
            Font policeHeader = new Font("police", Font.BOLD, 22);
            jButtons[i].setFont(policeHeader);
            jButtons[i].setForeground(Color.white);
            jButtons[i].setBounds(13, 100+(i*70), 240, 65);
            jButtons[i].addActionListener(new buttonAction());
            add(jButtons[i]);
        }

        /* DISPLAY MINIMIZE BUTTON */
        ImageLabel minimizeImg = new ImageLabel("minimize.png");
        minimizeImg.setBounds(1015,0, 50,50);
        BackgroundScreen.minimizeAction minimizeAction = new minimizeAction();
        minimizeImg.addMouseListener(minimizeAction);
        add(minimizeImg);

        /* DISPLAY CLOSE BUTTON */
        ImageLabel closeImg = new ImageLabel("close.png");
        closeImg.setBounds(1050,0, 50,50);
        BackgroundScreen.closeAction closeAction = new closeAction();
        closeImg.addMouseListener(closeAction);
        add(closeImg);

        /* DISPLAY LOGO */
        logoImg = new ImageLabel("TITLE-1750.png");
        logoImg.setBounds(16, 20, 240, 69);
        BackgroundScreen.welcomeAction welcomeAction = new welcomeAction();
        logoImg.addMouseListener(welcomeAction);
        add(logoImg);

        /* DISPLAY EMERGENCY BUTTON */
        emergency = new ImageLabel("emergency.png");
        BackgroundScreen.emergencyAction emergencyAction = new emergencyAction();
        emergency.addMouseListener(emergencyAction);

        repaint();
    }

    public void engineStarted(){
        /* DISPLAY EMERGENCY BUTTON */
        remove(logoImg);

        logoImg.setBounds(85, 30, 170, 49);
        add(logoImg);

        emergency.setBounds(16, 20, 69, 69);
        add(emergency);

        repaint();
    }
    public void engineStopped(){
        /* HIDE EMERGENCY BUTTON */
        remove(logoImg);
        remove(emergency);

        logoImg.setBounds(16, 20, 240, 69);
        add(logoImg);

        repaint();
    }

    private static class closeAction extends MouseAdapter {
        /* CLOSE ACTION */
        @Override
        public void mouseClicked(MouseEvent e) {
            JFrameBTWS.getInstance().setVisible(false);
            System.exit(0);
        }
    }
    private static class minimizeAction extends MouseAdapter {
        /* MINIMIZE ACTION */
        @Override
        public void mouseClicked(MouseEvent e) {
            JFrameBTWS.getInstance().setState(Frame.ICONIFIED);
        }
    }
    private class welcomeAction extends MouseAdapter {
        /* WELCOME ACTION */
        @Override
        public void mouseClicked(MouseEvent e) {
            String target = "WELCOME";
            JFrameBTWS.getInstance().changeScreen(target);
            changeScreen(target);
        }
    }
    private class emergencyAction extends MouseAdapter {
        /* EMERGENCY ACTION */
        @Override
        public void mouseClicked(MouseEvent e) {
            String target = "EMERGENCY";
            JFrameBTWS.getInstance().changeScreen(target);
            changeScreen(target);
        }
    }

    public class buttonAction implements ActionListener {
        /* MENU ACTION */
        @Override
        public void actionPerformed(ActionEvent e) {
            String target = ((JButton)e.getSource()).getText();
            JFrameBTWS.getInstance().changeScreen(target);
            changeScreen(target);

        }
    }

    public void changeScreen(String target) {
        if (target.equals("EMERGENCY")){
            return;
        }

        /* RESET BUTTON COLOR */
        for (JButton jButton : jButtons) jButton.setBackground(GraphicalTheme.primary_color);

        /* SET PRESSED BUTTON COLOR */
        switch (target){
            case "HOME":
                jButtons[0].setBackground(GraphicalTheme.secondary_color);
                break;
            case "ENGINE":
                jButtons[1].setBackground(GraphicalTheme.secondary_color);
                break;
            case "MONITOR":
                jButtons[2].setBackground(GraphicalTheme.secondary_color);
                break;
            case "SETTINGS":
                jButtons[3].setBackground(GraphicalTheme.secondary_color);
                break;
            case "HISTORY":
                jButtons[4].setBackground(GraphicalTheme.secondary_color);
                break;
            case "ACCOUNTS":
                jButtons[5].setBackground(GraphicalTheme.secondary_color);
                break;
            case "POSITIONS":
                jButtons[6].setBackground(GraphicalTheme.secondary_color);
                break;
        }
    }
}
