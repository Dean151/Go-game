package go.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class GobanTest extends TestCase {

    private Goban goban;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(3, 3);
    }

    @Test
    public void testIsInGoban() throws Exception {
        assertTrue(goban.isInGoban(0, 0));
        assertFalse(goban.isInGoban(-1, 0));
        assertFalse(goban.isInGoban(0, -1));
        assertTrue(goban.isInGoban(2, 2));
        assertTrue(goban.isInGoban(0, 2));
        assertFalse(goban.isInGoban(3, 1));
        assertFalse(goban.isInGoban(1, 4));
        assertFalse(goban.isInGoban(5, 4));

        Intersection intersection = new Intersection(goban, 1, 1);
        assertTrue(goban.isInGoban(intersection));
        intersection = new Intersection(goban, 4, 2);
        assertFalse(goban.isInGoban(intersection));
    }

    @Test
    public void testIsValidMove() throws Exception {
        Intersection intersection = new Intersection(goban, 1, 1);
        Player player = new Player(1);
        assertTrue(goban.play(intersection, player));
        intersection = new Intersection(goban, 1, 3);
        assertFalse(goban.play(intersection, player));
    }
}