package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntersectionTest extends TestCase {

    private Intersection intersection;

    @Before
    public void setUp() throws Exception {
        intersection = new Intersection();
    }

    @Test
    public void testGetState() throws Exception {
        assertEquals(Intersection.EMPTY, intersection.getState());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, intersection.isEmpty());
    }
}