package UserInterface.Screen;

import UserInterface.Component.ImageLabel;
import UserInterface.STATIC.GraphicalTheme;

import javax.swing.*;

public class WelcomeScreen9000 extends AbstractScreen {

    public WelcomeScreen9000() {}

    @Override
    public void init() {

        ImageLabel logoImg = new ImageLabel("FAV-1750.png");
        logoImg.setBounds(114, -50, 600, 600);
        add(logoImg);

        JLabel title_version = new JLabel(GraphicalTheme.title+" "+GraphicalTheme.version);
        title_version.setFont(GraphicalTheme.font_header2);
        title_version.setForeground(GraphicalTheme.light_color);
        title_version.setBounds(64, 490, 200, 50);
        add(title_version);

        JLabel author = new JLabel(GraphicalTheme.author);
        author.setFont(GraphicalTheme.font_header2);
        author.setForeground(GraphicalTheme.light_color);
        author.setBounds(64, 530, 200, 50);
        add(author);

        JLabel copyright = new JLabel(GraphicalTheme.copyright);
        copyright.setFont(GraphicalTheme.font_header3);
        copyright.setForeground(GraphicalTheme.light_color);
        copyright.setBounds(464, 535, 300, 50);
        add(copyright);

        repaint();
    }
}
