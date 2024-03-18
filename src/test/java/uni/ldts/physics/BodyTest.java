package uni.ldts.physics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BodyTest {

    @Test
    public void build1() {
        Vector2D pos = new Vector2D(2, 2);
        Vector2D size = new Vector2D(3, 3);
        Body body = new Body(pos, size);
        assertEquals(body.getPos().x, 2);
        assertEquals(body.getPos().y, 2);
        assertEquals(body.getAABB().getMax().x, 5);
        assertEquals(body.getAABB().getMax().y, 5);
        assertEquals(body.getAABB().getSize().x, 3);
        assertEquals(body.getAABB().getSize().y, 3);
    }

    @Test
    public void build2() {
        Body body = new Body(2,2,3,3);
        assertEquals(body.getPos().x, 2);
        assertEquals(body.getPos().y, 2);
        assertEquals(body.getAABB().getMax().x, 5);
        assertEquals(body.getAABB().getMax().y, 5);
        assertEquals(body.getAABB().getSize().x, 3);
        assertEquals(body.getAABB().getSize().y, 3);
    }

    @Test
    public void build3() {
        Body aux = new Body(2,2,3,3);
        Body body = new Body(aux);
        assertEquals(body.getPos().x, 2);
        assertEquals(body.getPos().y, 2);
        assertEquals(body.getAABB().getMax().x, 5);
        assertEquals(body.getAABB().getMax().y, 5);
        assertEquals(body.getAABB().getSize().x, 3);
        assertEquals(body.getAABB().getSize().y, 3);
    }

    @Test
    public void size() {
        Body aux = new Body(2,2,3,3);
        Vector2D size = new Vector2D(3, 3);
        assertEquals(size, aux.size());
    }
}
