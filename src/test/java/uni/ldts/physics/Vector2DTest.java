package uni.ldts.physics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2DTest {

    private Vector2D vec;

    @BeforeEach
    public void setup() {
        vec = new Vector2D(5, 3);
    }

    @Test
    public void build() {
        assertEquals(vec.x, 5);
        assertEquals(vec.y, 3);
    }

    @Test
    public void addi() {
        Vector2D toAdd = new Vector2D(2, 2);
        this.vec.addi(toAdd);
        assertEquals(vec.x, 7);
        assertEquals(vec.y, 5);
    }

    @Test
    public void length() {
        this.vec.x = 4;
        this.vec.y = 3;
        assertEquals(this.vec.length(), 5);
    }

    @Test
    public void multiply() {
        Vector2D newVec = vec.multiply(2);
        assertEquals(newVec.x, 10);
        assertEquals(newVec.y, 6);
    }

    @Test
    public void negate() {
        this.vec.negate();
        assertEquals(vec.x, -5);
        assertEquals(vec.y, -3);
    }

    @Test
    public void subtract() {
        Vector2D toSub = new Vector2D(2, 2);
        Vector2D newVec = this.vec.subtract(toSub);
        assertEquals(newVec.x, 3);
        assertEquals(newVec.y, 1);
    }
}
