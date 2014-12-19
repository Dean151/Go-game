package go.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    Player player;

    @Before
    public void setUp() throws Exception {
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