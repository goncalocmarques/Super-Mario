package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;
import java.io.IOException;

public class MysteryBlock extends Object {

    // ticks passed since last animation frame
    private int lastFrame = 0;

    public MysteryBlock(Drawable a, IElementType t, AABB boundingBox, boolean passable, boolean jumpThrough) throws IOException {
        super(new Sprite(WorldFactory.IMAGES_PATH + "mystery-block.png",5, 16, 16), ElementType.MYSTERY_BLOCK, boundingBox, false, false);
    }

    @Override
    public void tick() {
        Sprite sprite = (Sprite)this.getArtwork();
        if (sprite.getSelectedFrame() == 4) return;
        lastFrame++;
        if (lastFrame == 17) {
            // play mystery block rotation
            sprite.select((sprite.getSelectedFrame() + 1) % 4);
            lastFrame = 0;
        }
    }
}
