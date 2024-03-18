package uni.ldts.model;

import uni.ldts.physics.AABB;
import uni.ldts.physics.Body;
import uni.ldts.view.Drawable;

public class GameElement extends Body {

    private final Drawable artwork;
    private final IElementType type;
    private boolean visible;

    /* if set to true, then manager will
    remove the element from the world */
    public boolean selfDestruct;

    public GameElement(Drawable artwork, IElementType type, AABB aabb) {
        super(aabb.getMin(), aabb.getSize());
        this.artwork = artwork;
        this.visible = true;
        this.type = type;
    }

    public boolean isVisible() { return this.visible; }
    public void setVisible(boolean v) { this.visible = v; }
    public IElementType getType() { return this.type; }

    /**
     * Get element artwork, which will be used by
     * the renderer to draw the element into the screen
     * @return element artwork
     */
    public Drawable getArtwork() { return this.artwork; }

    /**
     * Every tick, this function is called and
     * each element decides what to do with it -
     * <p>
     * for example, an entity could check its
     * state and update its sprite frame
     */
    public void tick() {}
}
