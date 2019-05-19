package tests;

import org.junit.*;
import tools.Coordinate;
import tools.Hitbox;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class HitboxTest {

    @Test
    public void collideTest() {
        Hitbox hitbox0 = new Hitbox(new Coordinate(0, 0), 2);
        Hitbox hitbox1;

        for (int i = 0; i < 1000000; i++) {
            hitbox1 = new Hitbox(new Coordinate(randomSign() * ThreadLocalRandom.current().nextDouble(3), 0),
                    1); // X movement
            assertTrue(hitbox0.collide(hitbox1));
            hitbox1 = new Hitbox(new Coordinate(0, randomSign() * ThreadLocalRandom.current().nextDouble(3)),
                    1); // Y movement
            assertTrue(hitbox0.collide(hitbox1));
            hitbox1 = new Hitbox(new Coordinate(randomSign() * ThreadLocalRandom.current().nextDouble(Math.sqrt(4.5)),
                    randomSign() * ThreadLocalRandom.current().nextDouble(Math.sqrt(4.5))), 1); // X + Y movement
            assertTrue(hitbox0.collide(hitbox1));
        }

        hitbox1 = new Hitbox(new Coordinate(3, 0), 1); // X movement
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(0, 3), 1); // Y movement
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(Math.sqrt(4.5), Math.sqrt(4.5)), 1); // X + Y movement
        assertTrue(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(3.1, 0), 1); // X movement
        assertFalse(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(0, 3.1), 1); // Y movement
        assertFalse(hitbox0.collide(hitbox1));

        hitbox1 = new Hitbox(new Coordinate(Math.sqrt(4.6), Math.sqrt(4.6)), 1); // X + Y movement
        assertFalse(hitbox0.collide(hitbox1));
    }

    private int randomSign() {
        return (ThreadLocalRandom.current().nextDouble(2) < 1) ? -1 : 1;
    }


}