package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;

import java.io.IOException;

public class Goomba extends Entity {

    // goomba walking animation
    private int lastFrame = 0;

    public Goomba(Drawable a, IElementType t, AABB aabb, boolean ignore1, boolean ignore2) throws IOException {
        super(new Sprite(WorldFactory.IMAGES_PATH + "goomba.png", 3, 16, 16), ElementType.GOOMBA, aabb);
        this.getVel().x = -2.3f;
    }

    @Override
    public void tick() {
        lastFrame++;
        if (lastFrame == 10) {
            Sprite sprite = (Sprite)this.getArtwork();
            sprite.select((sprite.getSelectedFrame() + 1) % 2);
            lastFrame = 0;
        }
    }
}
