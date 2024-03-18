package uni.ldts.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uni.ldts.GameEngine;
import uni.ldts.mario.ElementType;
import uni.ldts.mario.WorldFactory;
import uni.ldts.mario.elements.view.MarioDrawable;
import uni.ldts.model.DefaultGameManager;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;
import uni.ldts.view.Renderer;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    private final int tps = 60;
    private GameEngine engine;

    @BeforeEach
    public void setUp() throws Exception {
        String sep = File.separator;
        String path = "src"+sep+"main"+sep+"resources"+sep+"maps"+sep;
        DefaultGameManager manager = Mockito.mock(DefaultGameManager.class);
        // build test world
        WorldFactory f = new WorldFactory();
        f.setCharacter(new Entity(Mockito.mock(MarioDrawable.class), ElementType.MARIO, new AABB(0,0, 16, 16)));
        f.loadWorld(path + "map1.tmx");
        manager.setWorld(f.getWorld());
        Renderer renderer = new Renderer(manager, 100 , 100, 4,4);
        engine = new GameEngine("TEST", manager, renderer, tps, 60);
    }

    @Test
    public void start() throws InterruptedException {
        engine.start();
        // let a tick pass
        Thread.sleep((long)(1000.0/tps));
        assertNotNull(engine.getManager());
        engine.stop();
    }
}
