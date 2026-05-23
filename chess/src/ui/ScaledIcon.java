package ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

// work in progress
public class ScaledIcon extends ImageIcon {
    private final int padding;

    public ScaledIcon(Image image, int padding) {
        super(image);
        this.padding = padding;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        int size = Math.min(c.getWidth(), c.getHeight()) - padding * 2;

        if (size <= 0) {
            return;
        }

        int drawX = (c.getWidth() - size) / 2;
        int drawY = (c.getHeight() - size) / 2;

        g.drawImage(getImage(), drawX, drawY, size, size, c);
    }
}
