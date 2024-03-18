package uni.ldts.mario.events;

import uni.ldts.events.EventHandler;
import uni.ldts.events.IEventListener;
import uni.ldts.events.collisions.CollisionEvent;
import uni.ldts.events.collisions.CollisionFilter;
import uni.ldts.mario.ElementType;
import uni.ldts.mario.Sound;
import uni.ldts.mario.elements.Mario;
import uni.ldts.mario.elements.Mystery;
import uni.ldts.model.GameElement;
import uni.ldts.model.GameManager;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.physics.AABB;
import uni.ldts.physics.Vector2D;
import uni.ldts.view.Sprite;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@EventHandler(state = 1)
public class CollisionListener implements IEventListener {

    private final GameManager manager;
    public CollisionListener(GameManager manager) { this.manager = manager; }

    /*
     mario <-> mystery block collision
     */
    @CollisionFilter(typeA = 0, typeB = -3)
    public void onMysteryBlockBreak(CollisionEvent event) throws IOException {

        Vector2D hitFromBottom = new Vector2D(0, 1);
        if (!event.getNormal().equals(hitFromBottom)) return;

        Sprite sprite = (Sprite) event.getB().getArtwork();
        if (sprite.getSelectedFrame() == 4) return;

        // spawn powerup
        sprite.select(4);
        new Sound("powerup-spawn.wav").play();
        this.spawnMystery((Mario)event.getA(), event.getB());
    }

    /*
     mario <-> brick collision
     */
    @CollisionFilter(typeA = 0, typeB = -4)
    public void onBrickBreak(CollisionEvent event) {
        Vector2D hitFromBottom = new Vector2D(0, 1);
        if (!event.getNormal().equals(hitFromBottom)) return;
        new Sound("break-block.wav").play();
        event.getB().selfDestruct = true;
    }

    /*
     mario <-> coin collision
     */
    @CollisionFilter(typeA = 0, typeB = -1)
    public void onCoinCollect(CollisionEvent event) {
        event.getB().selfDestruct = true;
        new Sound("coin.wav").play();
    }

    /*
     enemy <-> object collision
     */
    public void onEnemyToObjectCollide(CollisionEvent event) {
        if (!(event.getA() instanceof Entity a)) return;
        if (!(event.getB() instanceof Object b)) return;
        if (b.isPassable()) return;
        if (a.equals(manager.getWorld().getCharacter())) return;
        if (event.getNormal().x == 0) return; // non horizontal coll.
        a.getVel().x *= -1;
    }


    /*
     entity <-> entity collision
     */
    public void onEntityToEntityCollide(CollisionEvent event) throws InvocationTargetException, IllegalAccessException {
        if (!(event.getA() instanceof Entity a)) return;
        if (!(event.getB() instanceof Entity b)) return;
        if (a.equals(manager.getWorld().getCharacter())) {
            // separate function handles player collisions
            this.onMarioEnemyCollide(event);
        } else {
            boolean v = event.getNormal().x == 0;
            if (v) {
                // vertical collision
                a.getVel().y *= -1;
                b.getVel().y *= -1;
            } else {
                a.getVel().x *= -1;
                b.getVel().x *= -1;
            }
        }
    }

    private void onMarioEnemyCollide(CollisionEvent event) throws InvocationTargetException, IllegalAccessException {
        Mario mario = (Mario)event.getA();
        Entity enemy = (Entity)event.getB();
        if (enemy.getType() == ElementType.MYSTERY) return;
        // check if player hit enemy from the top
        Vector2D hitFromTop = new Vector2D(0, -1);
        if (event.getNormal().equals(hitFromTop)) {
            // player killed enemy
            enemy.selfDestruct = true;
            mario.getVel().y = -14;
        } else {
            // enemy killed player
            manager.getEventManager().callEvent(new MarioDeathEvent(mario, MarioDeathEvent.Reason.ENEMY));
        }
    }

    /*
     mario <-> mystery collision
     */
    @CollisionFilter(typeA = 0, typeB = 4)
    public void onMysteryCollect(CollisionEvent event) {
        Mario mario = (Mario)event.getA();
        Mystery mystery = (Mystery) event.getB();
        event.getB().selfDestruct = true;
        // set correct state
        if(mystery.isMushroom()) mario.setState(Mario.MarioState.BIG);
        else mario.setState(Mario.MarioState.FIRE);
        new Sound("powerup.wav").play();
    }

    /**
     * Spawn mystery in the world
     * @param m mario - player character
     * @param b block where mystery will spawn
     */
    private void spawnMystery(Mario m, GameElement b) throws IOException {
        Vector2D initialPos = new Vector2D(b.getPos());
        Vector2D size = new Vector2D(16,16);
        AABB aabb = new AABB(initialPos, size);
        // create and add entity to the world
        boolean v = m.state() == Mario.MarioState.SMALL;
        Entity e = new Mystery(aabb, v);
        manager.getWorld().getEntities().add(e);
    }
}
