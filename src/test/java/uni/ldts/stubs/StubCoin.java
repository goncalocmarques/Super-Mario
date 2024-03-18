package uni.ldts.stubs;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.elements.Coin;
import uni.ldts.physics.AABB;

import java.io.IOException;

public class StubCoin extends Coin {

    public StubCoin(AABB boundingBox) throws IOException {
        super(null, ElementType.COIN, boundingBox, false, false);
    }
}
