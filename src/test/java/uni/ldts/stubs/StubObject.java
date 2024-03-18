package uni.ldts.stubs;

import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;

public class StubObject extends Object {

    public StubObject(IElementType type, AABB boundingBox) {
        super(new StubSprite(), type, boundingBox,false, false);
    }
}
