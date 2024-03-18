package uni.ldts.physics;

import uni.ldts.events.PlayerMoveEvent;
import uni.ldts.events.collisions.CollisionEvent;
import uni.ldts.model.GameElement;
import uni.ldts.model.GameManager;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.model.world.World;

public class PhysicsEngine {

    private long lastUpdate = 0;
    private final GameManager manager;
    private static final float DELTA = 0.0119f;

    public PhysicsEngine(GameManager manager) { this.manager = manager; }

    /**
     * Update motion variables of world
     * entities based on the laws of physics
     */
    public void update() {

        long now = System.currentTimeMillis();
        if (lastUpdate == 0) lastUpdate = now;
        // calculate dt since last update
        float dt = (now - lastUpdate) * DELTA;

        World world = manager.getWorld();
        if (world == null) return;

        // update world entities
        Entity p = world.getCharacter();
        updateEntity(world.getCharacter(), dt);

        for (Entity e : world.getEntities()) {
            if (p != null) {
                // physics are only updated for
                // entities at < 400 blocks dist from player
                float d = p.getPos().x - e.getPos().x;
                if (Math.abs(d) > 400) continue;
            }
            updateEntity(e, dt);
        }
        this.lastUpdate = System.currentTimeMillis();
    }

    /**
     * Update physics of a game entity
     * @param e physics entity
     * @param dt change in time
     */
    public void updateEntity(Entity e, float dt) {

        if (e == null) return;
        Vector2D oldPos = new Vector2D(e.getPos());

        // update vel. based on acceleration
        Vector2D accMultiplier = e.getAcc().multiply(dt);
        e.getVel().addi(accMultiplier);

        // update position based on velocity
        Vector2D velMultiplier = e.getVel().multiply(dt);
        e.getPos().addi(velMultiplier);

        // handle entity collisions
        this.handleCollisions(e, dt);

        if (e == manager.getWorld().getCharacter()) {
            // trigger player move event
            if (oldPos.equals(e.getPos())) return;
            manager.getEventManager().callEvent(new PlayerMoveEvent(e,oldPos));
        }
    }

    /**
     * Handle collisions involving an entity -
     * including all other objects and entities
     * @param a entity involved
     * @param dt change in time
     */
    private void handleCollisions(Entity a, float dt) {

        World world = manager.getWorld();

        // if player is going up, assume a jump, otherwise assume
        // a fall - if a top collision, then set onGround = true
        if (a.getVel().y < 0) {
            a.setJumping(true);
            a.setFalling(false);
        } else {
            a.setJumping(false);
            a.setFalling(true);
        }

        // entity <-> entity collisions
        for (Entity b : world.getEntities()) {
            if (a == b) continue;
            if (a.getAABB().overlap(b.getAABB())) {
                this.handleVerticalCollision(a, b, dt, false);
                this.handleHorizontalCollision(a, b, dt, false);
            }
        }

        // entity <-> object collisions
        for (Object b : world.getObjects()) {
            if (a.getAABB().overlap(b.getAABB())) {
                boolean p = b.isPassable();
                if (!b.isJumpThrough()) {
                    this.handleVerticalCollision(a, b, dt, p);
                    this.handleHorizontalCollision(a, b, dt, p);
                }
                else if (a.getVel().y > 0) {
                    this.handleVerticalCollision(a, b, dt, p);
                }
                // ^ jump through check for objects
            }
        }
    }

    /**
     * Checks for and resolves vertical collisions
     * and calls a collision event if necessary
     * @param dt change in time
     * @param ignore coll. is detected but not resolved
     */
    private void handleVerticalCollision(Entity a, GameElement b, float dt, boolean ignore) {

        AABB boxA = a.getAABB();
        AABB boxB = b.getAABB();

        float k = Math.max(boxA.getMin().x, boxB.getMin().x);
        float l = Math.min(boxA.getMax().x, boxB.getMax().x);
        if (k > l) return;

        Vector2D velMultiplier = a.getVel().multiply(dt);

        if (boxA.getMax().y - velMultiplier.y <= boxB.getMin().y) {
            if (!ignore) {
                a.getPos().y = boxB.getMin().y - boxA.getSize().y;
                a.getVel().y = -0.001f;
                // set onGround > true
                a.setFalling(false);
                a.setJumping(false);
            }
            // top collision, call event
            manager.getEventManager().callEvent(new CollisionEvent(a,b,new Vector2D(0,-1)));
        }
        else if (boxA.getMin().y - velMultiplier.y >= boxB.getMax().y) {
            if (!ignore) {
                a.getPos().y = boxB.getMin().y + boxB.getSize().y;
                a.getVel().y = 0.001f;
            }
            // bottom collision, call event
            manager.getEventManager().callEvent(new CollisionEvent(a,b,new Vector2D(0,1)));
        }
    }

    /**
     * Checks for and resolves horizontal collisions
     * and calls a collision event if necessary
     * @param dt change in time
     * @param ignore coll. is detected but not resolved
     */
    private void handleHorizontalCollision(Entity a, GameElement b, float dt, boolean ignore) {

        AABB boxA = a.getAABB();
        AABB boxB = b.getAABB();
        if (!boxA.overlap(boxB)) return;

        Vector2D velMultiplier = a.getVel().multiply(dt);

        boolean s = boxA.getMin().x - boxA.getSize().y - velMultiplier.x <= boxB.getMin().x;
        boolean s2 = boxA.getMin().x - velMultiplier.x >= boxB.getMax().x;

        boolean t = boxA.getMax().x >= boxB.getMin().x;
        boolean t2 = boxA.getMin().x <= boxB.getMax().x;

        boolean u = boxA.getMin().x <= boxB.getMin().x;
        boolean u2 = boxA.getMax().x >= boxB.getMax().x;

        if (s && t && u && a.getVel().x > 0) {
            if (!ignore) {
                a.getPos().x = boxB.getMin().x - boxA.getSize().x;
                // a.getVel().x = 0;
            }
            // left collision, call event
            manager.getEventManager().callEvent(new CollisionEvent(a,b,new Vector2D(-1,0)));
        }
        else if (s2 && t2 && u2 && a.getVel().x < 0) {
            if (!ignore) {
                a.getPos().x = boxB.getMax().x;
                // a.getVel().x = 0;
            }
            // right collision, call event
            manager.getEventManager().callEvent(new CollisionEvent(a,b,new Vector2D(1,0)));
        }
    }
}
