package uni.ldts.stubs;

import uni.ldts.mario.ElementType;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;

public class StubGoomba extends Entity {

    public StubGoomba(AABB boundingBox) {
        super(new StubSprite(), ElementType.GOOMBA, boundingBox);
    }
}
