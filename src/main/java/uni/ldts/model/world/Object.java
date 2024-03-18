package uni.ldts.model.world;

import uni.ldts.model.IElementType;
import uni.ldts.model.GameElement;
import uni.ldts.physics.AABB;
import uni.ldts.view.Drawable;

public class Object extends GameElement {

    /*
    if an object is passable, then it's bounding box
    will be ignored, and players can pass through it
     */
    private boolean passable;

    /*
    an object is considered jump-through if a player
    can jump right through it without hitting their head
     */
    private final boolean jumpThrough;

    /**
     * Create a game object, which represents a
     * static body such as platforms, walls, etc
     */
    public Object(Drawable artwork, IElementType type, AABB boundingBox, boolean passable, boolean jumpThrough) {
        super(artwork, type, boundingBox);
        this.passable = passable;
        this.jumpThrough = !passable && jumpThrough;
    }

    public boolean isPassable() { return this.passable; }
    public void setPassable(boolean p) { this.passable = p; }

    public boolean isJumpThrough() { return this.jumpThrough; }
}
