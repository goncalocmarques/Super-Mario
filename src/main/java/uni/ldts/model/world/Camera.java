package uni.ldts.model.world;

public class Camera {

    public int x;
    public int y;

    /**
     * Even though a level is very extensive, only a portion of it
     * is drawn at a time. A camera is used for this functionality
     */
    public Camera() { this.reset(); }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public void move(int xAmount, int yAmount) {
        this.x += xAmount;
        this.y += yAmount;
    }
}