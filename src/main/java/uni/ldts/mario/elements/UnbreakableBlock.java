package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;

public class UnbreakableBlock extends Object {

    public UnbreakableBlock(Drawable artwork, IElementType t, AABB aabb, boolean passable, boolean jumpThrough) {
        super(artwork, ElementType.UNBREAKABLE_BLOCK, aabb, false, false);
    }
}
