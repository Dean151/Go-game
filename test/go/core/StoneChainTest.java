package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoneChainTest extends TestCase {

    private Goban goban;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(3,3);
    }

    @Test
    public void testAddStoneChain() throws Exception {
        // Should test the fusion of stone chains
    }

    @Test
    public void testAddIntersection() throws Exception {
        // Should test stone addition
    }
}