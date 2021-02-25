package UserInterface.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageLabel extends JLabel {

    private String pathname;

    public ImageLabel(String pathname){
        this.pathname=pathname;
    }

    public void paintComponent (Graphics g){
        try {
            Image image = ImageIO.read(getClass().getResource("/"+pathname));
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
