package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Texture;

import java.io.IOException;

public class BreakableBlock extends Object {

    // /!\ unused arguments are required for reflection in WorldFactory class /!\

    public BreakableBlock(Drawable a, IElementType t, AABB aabb, boolean ignore1, boolean ignore2) throws IOException {
        super(new Texture(WorldFactory.IMAGES_PATH+"breakable.png"), ElementType.BREAKABLE_BLOCK, aabb, false, false);
    }
}
