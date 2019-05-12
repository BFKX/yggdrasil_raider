package tools;

import org.junit.*;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class HitboxTest {

    @Test
    public void collideTest() {
        Hitbox hitbox0 = new Hitbox(new Coordinate(0, 0), 1);
        Hitbox hitbox1;

        for (int i = 0; i < 1000000; i++) {
            double z = randomSign() * ThreadLocalRandom.current().nextDouble(2);;
            hitbox1 = new Hitbox(new Coordinate(z, 0), 1); // X movement
            assertTrue(hitbox0.collide(hitbox1));
            hitbox1 = new Hitbox(new Coordinate(0, z), 1); // Y movement
            assertTrue(hitbox0.collide(hitbox1));

            double x = randomSign() * ThreadLocalRandom.current().nextDouble(Math.sqrt(2));
            double y = randomSign() * ThreadLocalRandom.current().nextDouble(Math.sqrt(2));
            hitbox1 = new Hitbox(new Coordinate(x, y), 1); // X + Y movement
            assertTrue(hitbox0.collide(hitbox1));
        }

        hitbox1 = new Hitbox(new Coordinate(2, 0), 1); // X movement
        assertFalse(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(2, 0), 2); // Modified size
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(0, 2), 1); // Y movement
        assertFalse(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(Math.sqrt(2), Math.sqrt(2)), 1); // X + Y movement
        assertFalse(hitbox0.collide(hitbox1));
    }

    private int randomSign() {
        if (ThreadLocalRandom.current().nextDouble(2) < 1) {
            return -1;
        }
        return 1;
    }
}