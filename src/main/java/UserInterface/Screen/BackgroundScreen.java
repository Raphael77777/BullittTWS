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

    private JButton [] jButtons = new JButton[5];

    public void paintComponent(Graphics g) {
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

        String[] label = new String[]{"HOME", "ENGINE", "MONITOR", "SETTINGS", "HISTORY"};
        for (int i = 0; i< label.length; i++){
            jButtons[i] = new JButton(label[i]);
            jButtons[i].setBackground(GraphicalTheme.primary_color);
            Font policeHeader = new Font("police", Font.BOLD, 22);
            jButtons[i].setFont(policeHeader);
            jButtons[i].setForeground(Color.white);
            jButtons[i].setBounds(13, 240+(i*70), 240, 65);
            jButtons[i].addActionListener(new buttonAction());
            add(jButtons[i]);
        }

        ImageLabel minimizeImg = new ImageLabel("minimize.png");
        minimizeImg.setBounds(1015,0, 50,50);
        minimizeImg.addMouseListener(new minimizeAction());
        add(minimizeImg);

        ImageLabel closeImg = new ImageLabel("close.png");
        closeImg.setBounds(1050,0, 50,50);
        closeImg.addMouseListener(new closeAction());
        add(closeImg);

        ImageLabel logoImg = new ImageLabel("LOGO-1750.png");
        logoImg.setBounds(16, 0, 240, 240);
        add(logoImg);
    }

    private class closeAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JFrameBTWS.getInstance().setVisible(false);
            System.exit(0);
        }
    }

    private class minimizeAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JFrameBTWS.getInstance().setState(Frame.ICONIFIED);
        }
    }

    public class buttonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            for (JButton jButton : jButtons) jButton.setBackground(GraphicalTheme.primary_color);

            switch (((JButton)e.getSource()).getText()){
                case "HOME":
                    jButtons[0].setBackground(GraphicalTheme.secondary_color);
                    JFrameBTWS.getInstance().showHomeScreen();
                    break;
                case "ENGINE":
                    jButtons[1].setBackground(GraphicalTheme.secondary_color);
                    JFrameBTWS.getInstance().showEngineScreen();
                    break;
                case "MONITOR":
                    jButtons[2].setBackground(GraphicalTheme.secondary_color);
                    JFrameBTWS.getInstance().showMonitorScreen();
                    break;
                case "SETTINGS":
                    jButtons[3].setBackground(GraphicalTheme.secondary_color);
                    JFrameBTWS.getInstance().showSettingsScreen();
                    break;
                case "HISTORY":
                    jButtons[4].setBackground(GraphicalTheme.secondary_color);
                    JFrameBTWS.getInstance().showTransactionsScreen();
                    break;
            }
        }
    }
}
