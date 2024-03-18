package uni.ldts.mario.events;

import uni.ldts.events.EventHandler;
import uni.ldts.events.IEventListener;
import uni.ldts.events.PlayerMoveEvent;
import uni.ldts.mario.Sound;
import uni.ldts.mario.elements.Mario;
import uni.ldts.mario.elements.view.MarioDrawable;
import uni.ldts.model.GameManager;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.Vector2D;

import java.lang.reflect.InvocationTargetException;

/**
 * The miscellaneous listener handles various
 * events, but focuses mainly on movement-related ones
 */
@EventHandler(state = 1)
public class MiscListener implements IEventListener {

    private final GameManager manager;
    public MiscListener(GameManager manager) { this.manager = manager; }

    public void onPlayerMove(PlayerMoveEvent event) throws InvocationTargetException, IllegalAccessException {

        Entity p = event.getPlayer();
        Vector2D newPos = p.getPos();

        if (newPos.x < 0 || newPos.x > 2958) {
            // prohibit player to exit map bounds
            event.cancel();
        }
        if (p.getAABB().getMax().y >= 220) {
            // player reached the void
            manager.getEventManager().callEvent(new MarioDeathEvent((Mario)p, MarioDeathEvent.Reason.VOID));
            event.cancel();
        }
    }

    public void onMarioDeath(MarioDeathEvent event) {
        Mario mario = event.getMario();
        MarioDrawable sprite = (MarioDrawable) mario.getArtwork();
        if (sprite.getSelectedFrame() == 12) return;
        // make mario stop moving
        // select death sprite
        mario.getVel().x = 0;
        mario.getVel().y = 0;
        mario.getAcc().y = 0;
        sprite.select(12);
        new Sound("death.wav").play();
    }
}
