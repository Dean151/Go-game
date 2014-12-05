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
        assertEquals(true, goban.isInGoban(0,0));
        assertEquals(false, goban.isInGoban(-1,0));
        assertEquals(false, goban.isInGoban(0,-1));
        assertEquals(true, goban.isInGoban(9,2));
        assertEquals(true, goban.isInGoban(18,18));
        assertEquals(false, goban.isInGoban(19,4));
        assertEquals(false, goban.isInGoban(9,19));
        assertEquals(false, goban.isInGoban(21,24));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, goban.isEmpty(3, 2));
        assertEquals(false, goban.isEmpty(-1, -1));
    }
}