package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Texture;

import java.io.IOException;

public class Cloud extends Object {

    public Cloud(Drawable a, IElementType t, AABB aabb, boolean ignore1, boolean ignore2) throws IOException {
        super(new Texture(WorldFactory.IMAGES_PATH + "cloud.png"), ElementType.CLOUD, aabb, false, true);
    }
}
