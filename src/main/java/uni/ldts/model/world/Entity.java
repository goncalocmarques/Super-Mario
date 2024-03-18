package uni.ldts.model.world;

import uni.ldts.model.IElementType;
import uni.ldts.model.GameElement;
import uni.ldts.physics.AABB;
import uni.ldts.physics.Vector2D;
import uni.ldts.view.Drawable;

public class Entity extends GameElement {

    private final Vector2D vel;
    private final Vector2D acc;

    private boolean falling;
    private boolean jumping;

    /**
     * Create an entity, an element that moves
     * around the world and abides by physics
     */
    public Entity(Drawable artwork, IElementType type, AABB boundingBox) {
        super(artwork, type, boundingBox);
        this.vel = new Vector2D();
        this.acc = new Vector2D();
        this.acc.y = 9.8f; // <- default accel.
    }

    public Vector2D getVel() { return this.vel; }
    public Vector2D getAcc() { return this.acc; }

    public boolean isJumping() { return this.jumping; }
    public boolean isFalling() { return this.falling; }

    public void setJumping(boolean k) { this.jumping = k; }
    public void setFalling(boolean k) { this.falling = k; }

    public boolean isOnGround() { return !this.falling && !this.jumping; }
}
