package go.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player(1);
    }

    @Test
    public void testCaptureCount() throws Exception {
        player.addCapturedStones(5);
        assertEquals(5,player.getCapturedStones());
        player.addCapturedStones(5);
        assertEquals(10,player.getCapturedStones());
        player.removeCapturedStones(7);
        assertEquals(3,player.getCapturedStones());
    }

    @Test
    public void testPlay() throws Exception {
        Goban A = new Goban(9,9) ;
        assertTrue(player.play(A,5,5));
        assertTrue(player.play(A, -1, -1));
        assertFalse(player.play(A,5,5));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Player 1",player.toString());
    }
}