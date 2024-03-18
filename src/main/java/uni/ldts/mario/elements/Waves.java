package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Texture;

import java.io.IOException;

public class Waves extends Object {

    public Waves(Drawable a, IElementType t, AABB boundingBox, boolean ignore1, boolean ignore2) throws IOException {
        super(new Texture(WorldFactory.IMAGES_PATH + "waves.png"), ElementType.WAVES, boundingBox, true, false);
    }
}
