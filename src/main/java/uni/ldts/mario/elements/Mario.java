package uni.ldts.mario.elements;

import uni.ldts.mario.ElementType;
import uni.ldts.mario.elements.view.MarioDrawable;
import uni.ldts.model.world.Entity;
import uni.ldts.physics.AABB;

import java.awt.image.BufferedImage;

public class Mario extends Entity {

    private int lastFrame;
    private MarioState state;

    public Mario(MarioDrawable sprite, AABB aabb) {
        super(sprite, ElementType.MARIO, aabb);
        this.state = MarioState.SMALL;
    }

    public enum MarioState {
        SMALL(0), BIG(1), FIRE(2);
        public final int id;
        MarioState(int id) { this.id = id; }
    }

    /**
     * Update mario's sprite based on their
     * current action - idle, running, jumping
     */
    public void updateAnimation() {
        MarioDrawable aux = (MarioDrawable)this.getArtwork();
        // first select the right sprite img
        if(aux.getSelectedFrame() == 12) return;
        if (!this.isOnGround()) aux.select((state.id + 1)*4 - 1); // mario is jumping
        else {
            if (this.getVel().x == 0) aux.select(this.state.id * 4); // mario is idle
            else {
                lastFrame++;
                int k = aux.getSelectedFrame()%4;
                if (k == 0 || k == 3) aux.select(state.id*4 + 1);
                // mario is running
                if (lastFrame == 20) {
                    if (k== 2) aux.select(aux.getSelectedFrame()-1);
                    else if (k == 1) aux.select(aux.getSelectedFrame() + 1);
                    lastFrame = 0;
                }
            }
        }
        // now we mirror the drawable if needed
        if ((this.getVel().x > 0 && !aux.isMirrored()) || (this.getVel().x < 0 && aux.isMirrored())) aux.mirror();
    }

    /**
     * Update mario's bounding box based
     * on their currently selected sprite frame
     */
    public void updateAABB() {
        MarioDrawable aux = (MarioDrawable) this.getArtwork();
        BufferedImage img = aux.getFrames()[aux.getSelectedFrame()];
        this.getAABB().getMin().y += getAABB().getSize().y - img.getHeight();
        this.getAABB().getSize().y = img.getHeight();
        this.getAABB().getSize().x = img.getWidth();
    }

    public MarioState state() { return this.state; }
    public void setState(MarioState state) { this.state = state; }

    @Override
    public void tick() {
        MarioDrawable aux = (MarioDrawable) this.getArtwork();
        if(aux.getSelectedFrame() == 12) { // death frame
            lastFrame++;
            if(lastFrame == 310) {
                this.getPos().x = 22;
                this.getPos().y = 165;
                this.getAcc().y = 9.8f;
                this.state = MarioState.SMALL;
                aux.select(0);
                lastFrame = 0;
            }
        }
        updateAnimation();
        updateAABB();
    }
}
