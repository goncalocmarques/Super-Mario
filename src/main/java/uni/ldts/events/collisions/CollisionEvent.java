package uni.ldts.events.collisions;

import uni.ldts.events.Event;
import uni.ldts.model.GameElement;
import uni.ldts.physics.Vector2D;

/**
 * Triggered when a collision occurs. By default, if the collision
 * <p> 1. is entity<->object, then entity will be element A
 * <p> 2. involves the character, then character will be element A
 */
public class CollisionEvent implements Event {

    // the two elements that collided
    private final GameElement elementA;
    private final GameElement elementB;

    // direction normal to collision
    // (given collision was A -> B)
    private final Vector2D normal;

    public CollisionEvent(GameElement a, GameElement b, Vector2D normal) {
        this.elementA = a;
        this.elementB = b;
        this.normal = normal;
    }

    public GameElement getA() { return this.elementA; }
    public GameElement getB() { return this.elementB; }

    public Vector2D getNormal() { return this.normal; }
}
