package uni.ldts.events;

import uni.ldts.model.world.Entity;
import uni.ldts.physics.Vector2D;

public class PlayerMoveEvent implements Event {

    private final Entity player; // < character with new pos
    private final Vector2D oldPos; // < old position

    public PlayerMoveEvent(Entity player, Vector2D oldPos) {
        this.player = player;
        this.oldPos = oldPos;
    }

    public Entity getPlayer() { return this.player; }
    public Vector2D getOldPos() { return this.oldPos; }

    // cancel event
    public void cancel() {
        player.getPos().x = oldPos.x;
        player.getPos().y = oldPos.y;
    }
}
