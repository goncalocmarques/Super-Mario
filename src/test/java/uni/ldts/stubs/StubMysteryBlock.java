package uni.ldts.stubs;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.elements.MysteryBlock;
import uni.ldts.physics.AABB;

import java.io.IOException;

public class StubMysteryBlock extends MysteryBlock {

    public StubMysteryBlock(AABB boundingBox) throws IOException {
        super(null, ElementType.MYSTERY_BLOCK, boundingBox, false, false);
    }
}
