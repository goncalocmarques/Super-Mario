package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.elements.view.MysteryDrawable;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;
import uni.ldts.physics.Vector2D;

import java.io.IOException;

public class Mystery extends Entity {

    private boolean stoppedRising;
    private final boolean isMushroom;
    private final Vector2D initialPos;

    /**
     * Create the "mystery" entity
     * @param mushroom if true, mystery is a mushroom, else it's a fire flower
     */
    public Mystery(AABB aabb, boolean mushroom) throws IOException {
        super(new MysteryDrawable(WorldFactory.IMAGES_PATH + (mushroom ? "mushroom.png" : "fire-flower.png"),
        WorldFactory.IMAGES_PATH + "broken-mystery.png", new Vector2D(aabb.getMin())), ElementType.MYSTERY, aabb);
        this.initialPos = new Vector2D(aabb.getMin());
        this.isMushroom = mushroom;
        this.getVel().y = -2.2f;
        this.getAcc().y = 0;
    }

    @Override
    public void tick() {
        if (!stoppedRising && initialPos.y - getPos().y >= 16) {
            stoppedRising = true;
            ((MysteryDrawable)getArtwork()).hideCover();
            // generate random direction based on time
            if (isMushroom) {
                boolean k = System.currentTimeMillis() % 2 == 0;
                this.getVel().x = 3 * (k?1:-1);
            }
            this.getAcc().y = 9.8f;
            this.getVel().y = 0;
        }
    }

    public boolean isMushroom() { return this.isMushroom; }
}
