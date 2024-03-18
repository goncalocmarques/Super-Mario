package uni.ldts.mario;

import uni.ldts.GameEngine;
import uni.ldts.mario.elements.Mario;
import uni.ldts.mario.events.CollisionListener;
import uni.ldts.mario.events.InputListener;
import uni.ldts.mario.events.MiscListener;
import uni.ldts.mario.elements.view.MarioDrawable;
import uni.ldts.model.DefaultGameManager;
import uni.ldts.physics.AABB;
import uni.ldts.view.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SuperMario {

    /**
     * Load up and start a super mario game
     * using our custom-made game engine
     */
    public static void main(String[] args) {

        String sep = File.separator;
        String path = "src"+sep+"main"+sep+"resources"+sep+"maps"+sep;

        // build world from .tmx file
        WorldFactory factory = new WorldFactory();
        try {
            Mario mario = new Mario(getMarioSprite(13), new AABB(22, 165, 16, 16));
            factory.setCharacter(mario).loadWorld(path+"map1.tmx");
        } catch (Exception e) {
            System.err.println("Could not load game resources, aborting");
            throw new IllegalArgumentException(e);
        }

        DefaultGameManager manager = new DefaultGameManager(true);
        manager.setWorld(factory.getWorld()); // <- set world
        manager.setState(GameState.LEVEL);

        // init engine and renderer
        Renderer renderer = new Renderer(manager, 384, 216, 4, 4);
        GameEngine engine = new GameEngine("Super Mario", manager, renderer, 100, 60);

        // register input and world events
        engine.registerInputListener(new InputListener(manager));
        manager.getEventManager().registerListener(new CollisionListener(manager));
        manager.getEventManager().registerListener(new MiscListener(manager));

        new Sound("level.wav").play();
        engine.start();
    }

    public static MarioDrawable getMarioSprite(int n) throws IOException {
        String s = File.separator;
        String path = WorldFactory.IMAGES_PATH + "mario"+s+"mario-";
        // load all mario sprite imgs
        BufferedImage[] img = new BufferedImage[n];
        for (int i = 0; i < n; i++) {
            img[i] = ImageIO.read(new File(path+i+".png"));
        }
        return new MarioDrawable(img);
    }
}
