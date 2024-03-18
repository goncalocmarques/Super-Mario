package uni.ldts.physics;

/**
 * AABB stands for axis-aligned bounding box and depicts
 * a non-tilted rectangle, allowing for collision checks
 */
public class AABB {

    private final Vector2D min;
    private final Vector2D size;

    /**
     * Create AABB out of two vectors
     * @param min top left of box
     * @param size size of box
     */
    public AABB(Vector2D min, Vector2D size) {
        this.min = min;
        this.size = size;
    }

    /**
     * Create AABB out of the coordinates of
     * the top left corner and the box size
     */
    public AABB(float minX, float minY, float sizeX, float sizeY) {
        this.min = new Vector2D(minX, minY);
        this.size = new Vector2D(sizeX, sizeY);
    }

    public Vector2D getMin() { return this.min; }
    public Vector2D getSize() { return this.size; }

    public Vector2D getMax() { return min.add(size); }

    /**
     * Determine whether two AABBs overlap
     * @param other AABB to check for overlap
     * @return whether overlap occurred
     */
    public boolean overlap(AABB other) {
        boolean overlapX = min.x < other.min.x + other.size.x && other.min.x < min.x + size.x;
        boolean overlapY = min.y < other.min.y + other.size.y && other.min.y < min.y + size.y;
        return overlapX && overlapY;
    }

    @Override
    public final String toString() {
        return "AABB[" + min + ", " + size + "]";
    }

}
