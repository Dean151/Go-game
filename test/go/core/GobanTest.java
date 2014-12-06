package go.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GobanTest extends TestCase {

    private Goban goban;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(19, 19);
    }

    @Test
    public void testIsInGoban() throws Exception {
        assertTrue(goban.isInGoban(0, 0));
        assertFalse(goban.isInGoban(-1, 0));
        assertFalse(goban.isInGoban(0, -1));
        assertTrue(goban.isInGoban(9, 2));
        assertTrue(goban.isInGoban(18, 18));
        assertFalse(goban.isInGoban(19, 4));
        assertFalse(goban.isInGoban(9, 19));
        assertFalse(goban.isInGoban(21, 24));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(goban.isEmpty(3, 2));
        assertFalse(goban.isEmpty(-1, -1));
    }
}