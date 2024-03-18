package uni.ldts.physics;

public class Vector2D {

    public float x;
    public float y;

    public Vector2D() {
        this.x = 0f;
        this.y = 0f;
    }

    /**
     * Create a 2d vector
     * @param x component in x-axis
     * @param y component in y-axis
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * @param v vector to copy
     */
    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Get magnitude/ length of vector
     * @return vector length
     */
    public float length() {
        return (float)Math.sqrt(x*x + y*y);
    }

    /**
     * Invert the sign/ negate vector
     * @return negated vector
     */
    public Vector2D negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    /**
     * Add vector to this vector instance
     * @param other vector to be added
     * @return this vector instance
     */
    public Vector2D addi(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Add vector to a copy of this vector
     * @param other vector to be added
     * @return addition vector
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x+other.x, y+other.y);
    }

    public Vector2D subtract(Vector2D other) {
        Vector2D vec = new Vector2D(other);
        vec.negate();
        return this.add(vec);
    }

    /**
     * Multiply copy of this vector by a scalar
     * @param scalar scalar to multiply by
     * @return multiplication vector
     */
    public Vector2D multiply(float scalar) {
        return new Vector2D(x*scalar, y*scalar);
    }

    @Override
    public String toString() { return "Vector2D{" + this.x + ", " + this.y + "}"; }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Vector2D vec)) return false;
        return this.x == vec.x && this.y == vec.y;
    }

    @Override
    public int hashCode() {
        return ((int)this.x ^ (int)this.y ^ (int)this.length());
    }
}
