package uni.ldts.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uni.ldts.mario.WorldFactory;
import uni.ldts.physics.AABB;
import uni.ldts.stubs.StubMario;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class WorldFactoryTest {

    private WorldFactory factory;

    @BeforeEach
    public void setup() throws Exception {
        String sep = File.separator;
        String path = "src"+sep+"main"+sep+"resources"+sep+"maps"+sep;
        StubMario mario = new StubMario(new AABB(0, 0, 16, 16));
        // load world test
        factory = new WorldFactory();
        factory.setCharacter(mario);
        factory.loadWorld(path + "map1.tmx");
    }

    @Test
    public void backgroundCheck() {
        assertNotEquals(factory.getWorld().getBackground(), null);
    }

    @Test
    public void entityCheck() {
        assertNotEquals(factory.getWorld().getEntities(), null);
    }

    @Test
    public void objectCheck() {
        assertNotEquals(factory.getWorld().getObjects(), null);
    }

    @Test
    public void characterCheck() { assertNotEquals(factory.getWorld().getCharacter(), null); }
}
