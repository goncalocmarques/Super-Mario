package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;

import java.io.IOException;

public class Coin extends Object {

    // tracks last tick when animation occurred
    private int lastFrame = 0;

    public Coin(Drawable a, IElementType t, AABB aabb, boolean ignore1, boolean ignore2) throws IOException {
        super(new Sprite(WorldFactory.IMAGES_PATH + "coin.png", 5, 14, 16), ElementType.COIN, aabb, true, true);
    }

    @Override
    public void tick() {
        this.lastFrame++;
        if (lastFrame == 18) {
            // player coin animation
            Sprite sprite = (Sprite)this.getArtwork();
            sprite.selectNext();
            lastFrame = 0;
        }
    }
}
