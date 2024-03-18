package uni.ldts.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uni.ldts.mario.ElementType;
import uni.ldts.mario.GameState;
import uni.ldts.mario.elements.Mario;
import uni.ldts.mario.events.InputListener;
import uni.ldts.mario.events.MarioDeathEvent;
import uni.ldts.mario.events.MiscListener;
import uni.ldts.model.DefaultGameManager;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;
import uni.ldts.physics.Vector2D;
import uni.ldts.stubs.StubEngine;
import uni.ldts.stubs.StubMario;
import uni.ldts.stubs.StubObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventsTest {

    private DefaultGameManager manager;

    @BeforeEach
    public void init() throws IOException {
        manager = new DefaultGameManager(false);
        manager.setWorld(new World(new StubMario(new AABB(16, 84, 16, 16))));
        manager.getWorld().getObjects().add(new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 100, 200, 16)));
        manager.setState(GameState.LEVEL);
        StubEngine engine = new StubEngine(manager);
        engine.registerInputListener(new InputListener(manager));
        manager.getEventManager().registerListener(new MiscListener(manager));
        engine.start();
    }

    @Test
    public void movePlayer() {
        manager.getWorld().getCharacter().getPos().x = 0;
        manager.getEventManager().callEvent(new PlayerMoveEvent(manager.getWorld().getCharacter(), new Vector2D(16,84)));
        assertEquals(manager.getWorld().getCharacter().getPos().x, 0);
    }

    @Test
    public void playerDied() {
        manager.getWorld().getCharacter().getVel().y = 10;
        manager.getEventManager().callEvent(new MarioDeathEvent((Mario) manager.getWorld().getCharacter(), MarioDeathEvent.Reason.ENEMY));
        assertEquals(manager.getWorld().getCharacter().getPos().y, 84);
    }
}
