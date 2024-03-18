package uni.ldts.stubs;

import uni.ldts.mario.elements.Mystery;
import uni.ldts.physics.AABB;

import java.io.IOException;

public class StubPowerUp extends Mystery {

    public StubPowerUp(AABB boundingBox) throws IOException { super(boundingBox, true); }
}
