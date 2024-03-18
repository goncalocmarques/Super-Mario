package uni.ldts.mario.elements.view;

import uni.ldts.view.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MarioDrawable extends Sprite {

    // y-axis is axis of reflection
    private boolean mirrored;

    public MarioDrawable(BufferedImage[] img) throws IOException {
        super(img);
        this.mirrored = true;
    }

    public boolean isMirrored() { return this.mirrored; }
    public void mirror() { this.mirrored = !mirrored; }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        BufferedImage img = this.getFrames()[getSelectedFrame()];
        g.drawImage(mirrored ? getMirrored(img) : img, x, y, null);
    }

    /**
     * Get reflected image, y-axis is the axis of reflection
     * @param img image to mirror
     * @return mirrored image
     */
    private BufferedImage getMirrored(BufferedImage img) {
        AffineTransform reverseX = new AffineTransform(-1, 0, 0, 1, img.getWidth(null), 0);
        AffineTransformOp flipper = new AffineTransformOp(reverseX, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return flipper.filter(img, null);
    }
}
