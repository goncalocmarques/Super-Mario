package uni.ldts.eventTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uni.ldts.GameEngine;
import uni.ldts.mario.ElementType;
import uni.ldts.mario.GameState;
import uni.ldts.mario.events.CollisionListener;
import uni.ldts.mario.events.InputListener;
import uni.ldts.model.DefaultGameManager;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;
import uni.ldts.stubs.StubEngine;
import uni.ldts.stubs.StubMario;
import uni.ldts.stubs.StubObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputTest {
    StubEngine engine;
    DefaultGameManager manager;
    Robot robot;

    @BeforeEach
    public void init() throws IOException, AWTException {
        manager = new DefaultGameManager(false);
        manager.setWorld(new World(new StubMario(new AABB(16, 84, 16, 16))));
        manager.getWorld().getObjects().add(new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 100, 200, 16)));
        manager.setState(GameState.LEVEL);
        engine = new StubEngine(manager);
        engine.registerInputListener(new InputListener(manager));
        engine.start();

        // Create a Robot object
        robot = new Robot();
    }

    @Test
    public void space() throws InterruptedException {
        engine.getListener().keyPressed(new KeyEvent(new Component() {}, 0, 0, 0, KeyEvent.VK_SPACE, ' '));
        Thread.sleep(200);

        assertTrue(manager.getWorld().getCharacter().isJumping());
    }

    @Test
    public void right() throws InterruptedException {
        engine.getListener().keyPressed(new KeyEvent(new Component() {}, 0, 0, 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        Thread.sleep(10);
        float vel = manager.getWorld().getCharacter().getVel().x;

        assertTrue(vel > 0);
    }

    @Test
    public void left() throws InterruptedException {
        engine.getListener().keyPressed(new KeyEvent(new Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        Thread.sleep(10);
        float vel = manager.getWorld().getCharacter().getVel().x;

        assertTrue(vel < 0);
    }

    @Test
    public void released() throws InterruptedException {
        engine.getListener().keyPressed(new KeyEvent(new Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        Thread.sleep(10);

        engine.getListener().keyReleased(new KeyEvent(new Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        Thread.sleep(10);

        float vel = manager.getWorld().getCharacter().getVel().x;

        assertEquals(0, vel);
    }


}
