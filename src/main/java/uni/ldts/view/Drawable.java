package uni.ldts.view;

import java.awt.*;

/**
 * A drawable is an object capable
 * of being drawn into a screen
 */
public interface Drawable {
    void draw(int x, int y, Graphics2D g);
}
