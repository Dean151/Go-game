package go.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;

public class StoneChainTest {

    private Goban goban;
    private Player one, two;
    private StoneChain chain1,chain2;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(9,9);
        one = new Player(1);
        two = new Player(2);

        chain1 = new StoneChain(goban.getIntersection(2,2),one);
            chain1.getStones().add(goban.getIntersection(2,3));
            for(Intersection stone : chain1.getStones()) stone.setStoneChain(chain1);
            chain1.getLiberties().add(goban.getIntersection(1,2));
            chain1.getLiberties().add(goban.getIntersection(1,3));
            chain1.getLiberties().add(goban.getIntersection(2,1));
            chain1.getLiberties().add(goban.getIntersection(2,4));


        chain2 = new StoneChain(goban.getIntersection(3,2),two);
            chain2.getStones().add(goban.getIntersection(3,3));
            chain2.getStones().add(goban.getIntersection(3,4));
            for(Intersection stone : chain2.getStones()) stone.setStoneChain(chain2);
            chain2.getLiberties().add(goban.getIntersection(4,2));
            chain2.getLiberties().add(goban.getIntersection(4,3));
            chain2.getLiberties().add(goban.getIntersection(4,4));
            chain2.getLiberties().add(goban.getIntersection(3,1));
            chain2.getLiberties().add(goban.getIntersection(3,5));
            chain2.getLiberties().add(goban.getIntersection(2,4));
    }

    @Test
    public void testAdd() throws Exception {
        assertFalse(chain1.getStones().contains(goban.getIntersection(2, 4)));
        assertTrue(chain1.getLiberties().contains(goban.getIntersection(2, 4)));
        chain1.add(new StoneChain(goban.getIntersection(2,4),one),goban.getIntersection(2,4));
        assertTrue(chain1.getStones().contains(goban.getIntersection(2,4)));
        assertFalse(chain1.getLiberties().contains(goban.getIntersection(2,4)));
    }

    @Test
    public void testRemoveLiberty() throws Exception {
        assertTrue(chain1.getLiberties().contains(goban.getIntersection(2,4)));
        chain1.removeLiberty(goban.getIntersection(2,4));
        assertFalse(chain1.getLiberties().contains(goban.getIntersection(2,4)));
    }

    @Test
    public void testDie() throws Exception {

        Set<Intersection> positions = chain1.getStones();

        for (Intersection stone : positions ) {
            assertFalse(chain2.getLiberties().contains(stone));
            assertTrue(stone.getStoneChain()==chain1);
        }

        chain1.die();

        for (Intersection stone : positions ) {
            assertTrue(chain2.getLiberties().contains(stone));
            assertFalse(stone.getStoneChain()==chain1);
        }
    }

    @Test
    public void testDeepConstructor() throws Exception {
        StoneChain chain3 = new StoneChain(chain1);
        assertEquals(chain3.getLiberties(), chain1.getLiberties());
        assertEquals(chain3.getOwner(), chain1.getOwner());
        assertEquals(chain3.getStones(), chain1.getStones());
    }

    @Test
    public void testGetOwner() throws Exception {
        assertEquals(one, chain1.getOwner());
    }
}
