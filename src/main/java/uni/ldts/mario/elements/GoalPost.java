package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.model.IElementType;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;
import uni.ldts.view.Sprite;

import java.io.IOException;

public class GoalPost extends Object {

    public GoalPost(Drawable a, IElementType t, AABB aabb, boolean ignore1, boolean ignore2) throws IOException {
        super(new Sprite(WorldFactory.IMAGES_PATH + "goal-post.png", 2, 48, 144), ElementType.GOAL_POST, aabb, true, false);
        ((Sprite)this.getArtwork()).select(1);
    }
}
