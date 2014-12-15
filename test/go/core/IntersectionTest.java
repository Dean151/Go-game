package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class IntersectionTest extends TestCase {

    private Goban goban;
    private Intersection intersection;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(3,3);
        intersection = goban.getIntersection(1,1);
    }

    @Test
    public void testGetState() throws Exception {
        assertNull(intersection.getStoneChain());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(intersection.isEmpty());
    }
}