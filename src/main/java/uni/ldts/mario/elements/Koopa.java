package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;

import java.io.IOException;

public class Koopa extends Entity {

    // koopa walking animation
    private int lastFrame = 0;

    public Koopa(Drawable a, IElementType t, AABB boundingBox, boolean ignored1, boolean ignored2) throws IOException {
        super(new Sprite(WorldFactory.IMAGES_PATH + "koopa.png", 4, 24, 32), ElementType.KOOPA, boundingBox);
        this.getVel().x = 3f;
    }

    @Override
    public void tick() {
        lastFrame++;
        if (lastFrame == 10) {
            Sprite sprite = (Sprite)this.getArtwork();
            int frame = (sprite.getSelectedFrame() + 1) % 2;
            if (this.getVel().x > 0) frame = frame + 2;
            sprite.select(frame);
            lastFrame = 0;
        }
    }
}
