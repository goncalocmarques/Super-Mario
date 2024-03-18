package uni.ldts.stubs;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.elements.BreakableBlock;
import uni.ldts.physics.AABB;

import java.io.IOException;

public class StubBrick extends BreakableBlock {

    public StubBrick(AABB aabb) throws IOException {
        super(null, ElementType.BREAKABLE_BLOCK, aabb, false, false);
    }
}
