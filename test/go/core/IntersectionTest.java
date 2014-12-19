package go.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntersectionTest {

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