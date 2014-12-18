package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest extends TestCase {

    Player player = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        player = new Player(1);
    }

    @Test
    public void testCaptureCount() {
        player.addCapturedStones(5);
        assertEquals(5,player.getCapturedStones());
        player.addCapturedStones(5);
        assertEquals(10,player.getCapturedStones());
        player.removeCapturedStones(7);
        assertEquals(3,player.getCapturedStones());
    }
}