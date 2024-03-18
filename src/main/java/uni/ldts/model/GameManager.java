package uni.ldts.model;

import uni.ldts.events.EventManager;
import uni.ldts.model.world.Camera;
import uni.ldts.model.world.World;

/**
 * A game manager will handle all game logic.
 * <p>
 * By using an interface, it allows games to implement their
 * own manager if DefaultGameManager does not fit their needs
 */
public interface GameManager {
    World getWorld();
    Camera getCamera();
    IGameState getState();
    EventManager getEventManager();
    void setWorld(World w);
    void tick();
}
