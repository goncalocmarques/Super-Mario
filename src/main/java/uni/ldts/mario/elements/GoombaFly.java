package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Texture;

import java.io.IOException;
import java.util.Random;

public class GoombaFly extends Entity {

    private final int maxHeight;

    public GoombaFly(Drawable a, IElementType t, AABB boundingBox, boolean ignore1, boolean ignore2) throws IOException {
        super(new Texture(WorldFactory.IMAGES_PATH + "goomba-fly.png"), ElementType.GOOMBA_FLY, boundingBox);
        maxHeight = new Random().nextInt((160-70) + 1) + 70;
    }

    @Override
    public void tick() {
        // fly and switch dir when max, min occur
        if (isOnGround()) {
            getAcc().y = -0.1f;
            getVel().y = -1.5f;
        } else if (getPos().y <= maxHeight) {
            getAcc().y = 0.1f;
            getVel().y = 1.5f;
        }
    }
}
