package uni.ldts.mario.elements.view;

import uni.ldts.physics.Vector2D;
import uni.ldts.view.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MysteryDrawable implements Drawable {

    private final BufferedImage main;
    private BufferedImage cover;
    private Vector2D coverPos;

    /**
     * Create a mystery drawable - in a game world, entities are always drawn
     * on top of objects, but we might not want that for specific cases.
     * <p>
     * For the mario mushroom spawn animation, the mushrooms needs to be hidden
     * behind the broken block and slowly rise. In that case, the main image is
     * the mushroom and the cover is a dummy image mimicking the block's texture
     * @param coverPos fixed position for cover img
     */
    public MysteryDrawable(String mainPath, String coverPath, Vector2D coverPos) throws IOException {
        this.main = ImageIO.read(new File(mainPath));
        this.cover = ImageIO.read(new File(coverPath));
        this.coverPos = coverPos;
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        g.drawImage(main, x, y, null);
        if (coverPos != null) g.drawImage(cover, (int)coverPos.x, (int)coverPos.y, null);
    }

    /**
     * By "hiding the cover", the img that was being
     * drawn on top of the main img is no longer shown
     */
    public void hideCover() {
        this.cover = null;
        this.coverPos = null;
    }
}
