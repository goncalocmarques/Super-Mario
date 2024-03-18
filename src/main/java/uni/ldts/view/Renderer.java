package uni.ldts.view;

import uni.ldts.model.GameElement;
import uni.ldts.model.GameManager;
import uni.ldts.model.world.Camera;
import uni.ldts.model.world.Entity;
import uni.ldts.model.world.Object;
import uni.ldts.model.world.World;
import uni.ldts.physics.AABB;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    private final GameManager manager;
    private final int width, height;
    private final int scaleX, scaleY;

    /**
     * Create a renderer for a game
     * @param manager game manager
     * @param width screen width
     * @param height screen height
     * @param scaleX scale for x-axis
     * @param scaleY scale for y-axis
     */
    public Renderer(GameManager manager, int width, int height, int scaleX, int scaleY) {
        this.setPreferredSize(new Dimension(width*scaleX, height*scaleY));
        this.manager = manager;
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.scale(scaleX, scaleY);

        World world = manager.getWorld();
        Camera cam = manager.getCamera();

        if (world == null) return;
        // no world to draw ^

        if (cam == null) this.drawWorld(world, g2);
        else {
            // if camera is not null, camera mode
            // is on, and the camera will follow the
            // player around the world
            this.refocusCamera(cam, world);
            g2.translate(-cam.x, -cam.y);
            this.drawWorld(world, g2);
            g2.translate(cam.x, cam.y);
        }
    }

    /**
     * Draw the entire game world,
     * from background to elements
     * @param g2 game graphics
     */
    private void drawWorld(World w, Graphics2D g2) {
        drawBackground(w, g2);
        for (Object obj : w.getObjects()) drawElement(obj, g2);
        for (Entity ett : w.getEntities()) drawElement(ett, g2);
        drawElement(w.getCharacter(), g2);
    }

    private void drawBackground(World w, Graphics2D g2) {
        Texture bg = w.getBackground();
        if (bg != null) bg.draw(0, 0, g2);
    }

    private void drawElement(GameElement e, Graphics2D g2) {
        if (e == null || !e.isVisible()) return;
        e.getArtwork().draw((int)e.getPos().x, (int)e.getPos().y, g2);
    }

    private void refocusCamera(Camera cam, World w) {
        Entity p = w.getCharacter();
        if (p == null) return;
        // get screen midpoint
        int midW = width/2;
        int midH = height/2;
        // get player midpoint
        AABB box = p.getAABB();
        float midP = (box.getMin().x + box.getMax().x)/2;
        // set camera to correct pos
        if (midP > midW) cam.x = (int)(midP - midW);
        if (p.getPos().x == 22) cam.reset();
    }
}