import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    public BufferedImage img = new BufferedImage(4 * 28, 4 * 28, BufferedImage.TYPE_BYTE_GRAY);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}
