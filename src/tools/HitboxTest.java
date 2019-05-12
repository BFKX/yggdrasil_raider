package tools;

import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class HitboxTest {

    @Test
    public void collideTest() {

        // Equal Hitboxes
        Hitbox hitbox0 = new Hitbox(new Coordinate(0, 0), 1);
        Hitbox hitbox1 = new Hitbox(new Coordinate(0, 0), 1);
        assertTrue(hitbox0.collide(hitbox1));

        // X movement
        hitbox1 = new Hitbox(new Coordinate(1, 0), 1);
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(2 * 999/1000, 0), 1);
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(2, 0), 1);
        assertFalse(hitbox0.collide(hitbox1));

        // Y movement
        hitbox1 = new Hitbox(new Coordinate(0, 1), 1);
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(0, 2), 1);
        assertFalse(hitbox0.collide(hitbox1));

        // X + Y movement
        hitbox1 = new Hitbox(new Coordinate(1, 1), 1);
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(2, 2), 1);
        assertFalse(hitbox0.collide(hitbox1));

        // Different sizes
        hitbox1 = new Hitbox(new Coordinate(2, 0), 2);
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(3, 0), 2);
        assertFalse(hitbox0.collide(hitbox1));
    }
}