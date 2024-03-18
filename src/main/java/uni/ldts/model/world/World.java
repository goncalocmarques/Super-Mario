package uni.ldts.model.world;

import uni.ldts.view.Texture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {

    private Texture background;
    private final Entity character;

    private List<Object> objects;
    private List<Entity> entities;

    /**
     * Create a new world for your game
     * @param character player character
     */
    public World(Entity character) {
        this.character = character;
        this.objects = Collections.synchronizedList(new ArrayList<>());
        this.entities = new ArrayList<>();
    }

    public Entity getCharacter() { return this.character; }
    public Texture getBackground() { return this.background; }
    public void setBackground(Texture bg) { this.background = bg; }

    public synchronized List<Object> getObjects() { return this.objects; }
    public synchronized List<Entity> getEntities() { return this.entities; }

    /**
     * Remove an object safely from the world
     * (never modify the list itself, it will cause Concurrency issues)
     * @param o object to be removed
     */
    public synchronized void removeObject(Object o) {
        List<Object> copy = new ArrayList<>(objects);
        copy.remove(o);
        this.objects = copy;
    }

    /**
     * Remove an entity safely from the world
     * @param e entity to be removed
     */
    public synchronized void killEntity(Entity e) {
        List<Entity> copy = new ArrayList<>(entities);
        copy.remove(e);
        this.entities = copy;
    }
}
