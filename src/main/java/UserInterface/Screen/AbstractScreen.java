package UserInterface.Screen;

import javax.swing.*;

public abstract class AbstractScreen extends JPanel {

    public AbstractScreen() {
        setOpaque(false);
        /* REGULAR DIMENSIONS : 828w * 600h */
        setBounds(272, 0, 828, 600);
        setLayout(null);
    }

    public abstract void init ();
}
