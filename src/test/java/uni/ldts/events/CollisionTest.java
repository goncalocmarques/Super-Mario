package uni.ldts.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uni.ldts.mario.ElementType;
import uni.ldts.mario.GameState;
import uni.ldts.mario.events.CollisionListener;
import uni.ldts.model.DefaultGameManager;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;
import uni.ldts.stubs.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    private DefaultGameManager manager;

    @BeforeEach
    public void init() throws IOException {
        manager = new DefaultGameManager(false);
        manager.setState(GameState.LEVEL);
        manager.setWorld(new World(new StubMario(new AABB(0, 0, 16, 16))));
        // register collision listener and start engine
        manager.getEventManager().registerListener(new CollisionListener(manager));
        StubEngine engine = new StubEngine(manager);
        engine.start();
    }

    @Test
    public void collisionTest1() throws InterruptedException {
        StubObject block = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 17, 16, 16));
        manager.getWorld().getObjects().add(block);
        Thread.sleep(100);
        //  ^ wait for the next game tick
        assertEquals(1.0, manager.getWorld().getCharacter().getAABB().getMin().y);
    }

    @Test
    public void collisionTest2() throws InterruptedException {
        StubObject block = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 20, 16, 16));
        manager.getWorld().getObjects().add(block);
        Thread.sleep(100);
        assertEquals(4.0, manager.getWorld().getCharacter().getAABB().getMin().y);
    }

    @Test
    public void collisionTest3() throws InterruptedException {
        Object block = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 16, 16, 16));
        manager.getWorld().getObjects().add(block);
        manager.getWorld().getCharacter().getAABB().getMin().x = 15;
        Thread.sleep(100);
        assertEquals(0.0, manager.getWorld().getCharacter().getAABB().getMin().y);
    }

    @Test
    public void collisionTest4() throws InterruptedException {
        Object block1 = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 16, 200, 16));
        Object block2 = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(32, 0, 16, 16));
        manager.getWorld().getObjects().add(block1);
        manager.getWorld().getObjects().add(block2);
        manager.getWorld().getCharacter().getVel().x = 15;
        Thread.sleep(100);
        assertEquals(16.0, manager.getWorld().getCharacter().getAABB().getMin().x);
    }

    @Test
    public void collisionTest5() throws InterruptedException {
        Object block1 = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 16, 200, 16));
        Object block2 = new StubObject(ElementType.UNBREAKABLE_BLOCK, new AABB(0, 0, 16, 16));
        manager.getWorld().getObjects().add(block1);
        manager.getWorld().getObjects().add(block2);
        manager.getWorld().getCharacter().getAABB().getMin().x = 32;
        manager.getWorld().getCharacter().getVel().x = -15;
        Thread.sleep(100);
        assertEquals(16.0, manager.getWorld().getCharacter().getAABB().getMin().x);
    }


    @Test
    public void onMarioEnemyCollision() throws InterruptedException {
        Entity goomba = new StubGoomba(new AABB(0, 200, 20, 20));
        manager.getWorld().getEntities().add(goomba);
        manager.getWorld().getCharacter().getAABB().getMin().x = 16;
        manager.getWorld().getCharacter().getAABB().getMin().y = 180;
        manager.getWorld().getCharacter().getVel().y = 21;
        Thread.sleep(100);
        assertEquals(0, manager.getWorld().getObjects().size());
    }

    @Test
    public void onMarioPowerUpCollision() throws InterruptedException, IOException {
        Entity powerUp = new StubPowerUp(new AABB(20, 200, 16, 16));
        manager.getWorld().getEntities().add(powerUp);
        manager.getWorld().getCharacter().getAABB().getMin().x = 16;
        manager.getWorld().getCharacter().getAABB().getMin().y = 200;
        manager.getWorld().getCharacter().getAcc().y = 0;
        manager.getWorld().getCharacter().getVel().y = 0;
        manager.getWorld().getCharacter().getVel().x = 5;
        Thread.sleep(100);
        assertEquals(0, manager.getWorld().getObjects().size());
    }
    @Test
    public void onEntityEnemyCollision() throws InterruptedException, IOException {
        Entity goomba = new StubGoomba(new AABB(4, 200, 16, 16));
        Entity powerUp = new StubPowerUp(new AABB(20, 200, 16, 16));
        powerUp.getVel().x = -2;
        powerUp.getVel().y = 0;
        powerUp.getAcc().y = 0;
        goomba.getAcc().y = 0;
        goomba.getVel().x = 0;
        goomba.getVel().y = 0;
        manager.getWorld().getEntities().add(goomba);
        manager.getWorld().getEntities().add(powerUp);
        manager.getWorld().getCharacter().getPos().x = 500;
        Thread.sleep(100);
        assertEquals(0, goomba.getVel().x);
        assertTrue(manager.getWorld().getEntities().size() > 1);
    }

    @Test
    public void powerUpSpawn() throws InterruptedException, IOException {
        Object mystery = new StubMysteryBlock(new AABB(0, 0, 16, 16));
        manager.getWorld().getObjects().add(mystery);
        manager.getWorld().getCharacter().getAABB().getMin().y = 17;
        manager.getWorld().getCharacter().getVel().y = -20;
        Thread.sleep(700);
        assertFalse(manager.getWorld().getEntities().isEmpty());
    }

    @Test
    public void blockBreak() throws InterruptedException, IOException {
        Object block = new StubBrick(new AABB(0, 0, 16, 16));
        manager.getWorld().getObjects().add(block);
        manager.getWorld().getCharacter().getAABB().getMin().y = 17;
        manager.getWorld().getCharacter().getVel().y = -20;
        Thread.sleep(100);
        assertTrue(manager.getWorld().getObjects().isEmpty());
    }

    @Test
    public void collectCoin() throws InterruptedException, IOException {
        Object coin = new StubCoin(new AABB(0, 0, 16, 16));
        manager.getWorld().getObjects().add(coin);
        manager.getWorld().getCharacter().getAABB().getMin().y = 17;
        manager.getWorld().getCharacter().getVel().y = -20;
        Thread.sleep(100);
        assertTrue(manager.getWorld().getObjects().isEmpty());
    }
}
