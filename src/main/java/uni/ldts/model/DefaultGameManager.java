package uni.ldts.model;

import uni.ldts.events.EventManager;
import uni.ldts.model.world.Camera;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.model.world.World;
import uni.ldts.physics.PhysicsEngine;

import java.lang.reflect.InvocationTargetException;

public class DefaultGameManager implements GameManager {

    private World world;
    private IGameState state;
    private final Camera camera;
    private final EventManager events;
    private final PhysicsEngine physics;

    /**
     * Create a default game manager, which
     * supports event handling and entity physics
     */
    public DefaultGameManager(boolean enableCamera) {
        this.events = new EventManager(this);
        this.physics = new PhysicsEngine(this);
        this.camera = enableCamera ? new Camera() : null;
    }

    @Override
    public void tick() {
        this.physics.update();
        // tick player character
        if (world.getCharacter() != null) {
            world.getCharacter().tick();
        }
        // tick objects
        for (Object obj : world.getObjects()) {
            if (obj.selfDestruct) world.removeObject(obj);
            else obj.tick();
        }
        // tick entities
        for (Entity ett : world.getEntities()) {
            if (ett.selfDestruct) world.killEntity(ett);
            else ett.tick();
        }
    }

    @Override public World getWorld() { return this.world; }
    @Override public Camera getCamera() { return this.camera; }
    @Override public IGameState getState() { return this.state; }

    @Override public EventManager getEventManager() { return this.events; }
    public PhysicsEngine getPhysics() { return this.physics; }

    @Override public void setWorld(World w) { this.world = w; }
    public void setState(IGameState s) { this.state = s; }
}
