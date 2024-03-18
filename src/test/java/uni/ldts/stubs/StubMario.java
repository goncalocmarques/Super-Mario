package uni.ldts.stubs;

import uni.ldts.mario.elements.Mario;
import uni.ldts.physics.AABB;

import java.io.IOException;

public class StubMario extends Mario {

    public StubMario(AABB boundingBox) throws IOException {
        super(new StubMarioDrawable(), boundingBox);
    }

    @Override
    public void updateAABB() {}
}
