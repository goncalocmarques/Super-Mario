package uni.ldts.mario.events;

import uni.ldts.events.EventHandler;
import uni.ldts.events.controller.IKeyListener;
import uni.ldts.mario.Sound;
import uni.ldts.mario.elements.Mario;
import uni.ldts.mario.elements.view.MarioDrawable;
import uni.ldts.model.GameManager;
import uni.ldts.model.world.Entity;

import java.awt.event.KeyEvent;

@EventHandler(state = 1)
public class InputListener implements IKeyListener {

    private final GameManager manager;
    public InputListener(GameManager manager) { this.manager = manager; }

    @Override
    public void onKeyPress(KeyEvent e) {

        Mario p = (Mario) manager.getWorld().getCharacter();
        if (p == null) return;

        MarioDrawable sprite = (MarioDrawable) p.getArtwork();
        if (sprite.getSelectedFrame() == 12) return;
        // player dead, ignore movement ^

        if (e.getKeyChar() == ' ') {
            // jumped
            if (!p.isOnGround()) return;
            new Sound("small-jump.wav").play();
            p.getVel().y = -33;
        }
        else if (e.getKeyCode() == 39) {
            // went right
            p.getVel().x = 10;
        }
        else if (e.getKeyCode() == 37) {
            // went left
            p.getVel().x = -10;
        }
    }

    @Override
    public void onKeyRelease(KeyEvent e) {
        Entity p = manager.getWorld().getCharacter();
        if (p == null) return;
        // stopped moving sideways
        if (e.getKeyCode() == 39 || e.getKeyCode() == 37) {
            p.getVel().x = 0;
        }
    }
}
