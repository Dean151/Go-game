package go.core;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class IntersectionTest {

    private Intersection intersection,intersection2;

    @Before
    public void setUp() throws Exception {
        Goban goban = new Goban(3,3);
        intersection = goban.getIntersection(1,1);
        Goban goban2 = new Goban(3,3);
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                goban2.getIntersection(i,j).setStoneChain(new StoneChain(goban2.getIntersection(i,j),new Player(i+3*j)));
            }
        }
        intersection2 = goban2.getIntersection(1,1);
    }

    @Test
    public void testGetState() throws Exception {
        assertNull(intersection.getStoneChain());
        assertEquals(4,intersection2.getStoneChain().getOwner().getIdentifier());
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(intersection.isEmpty());
        assertFalse(intersection2.isEmpty());
    }

    @Test
    public void testGetAdjacentStoneChains() throws Exception {
        Set<StoneChain> adjacent = intersection.getAdjacentStoneChains();
        Set<StoneChain> adjacent2 = intersection2.getAdjacentStoneChains();
        assertTrue(adjacent.isEmpty());
        assertEquals(4,adjacent2.size());
        Set<Integer> set = new HashSet<Integer>();
        set.add(new Integer(1));
        set.add(new Integer(3));
        set.add(new Integer(5));
        set.add(new Integer(7));
        Set<Integer> chainSet = new HashSet<Integer>();
        for(StoneChain chain : intersection2.getAdjacentStoneChains()) {
            chainSet.add(new Integer(chain.getOwner().getIdentifier()));
        }
        assertEquals(set,chainSet);
    }

    @Test
    public void testGetEmptyNeighbours() throws Exception {
        assertEquals(0,intersection2.getEmptyNeighbors().size());
        assertEquals(4,intersection.getEmptyNeighbors().size());
        List<Intersection> liberty = intersection.getEmptyNeighbors();
        liberty.get(2).setStoneChain(new StoneChain(liberty.get(2),new Player(1)));
        Set<StoneChain> adjacent = intersection2.getAdjacentStoneChains();
        for (StoneChain chain : adjacent ) {
            for (Intersection cross : chain.getStones() ) {
                cross.setStoneChain(null);
            }
            break;
        }
        assertEquals(1,intersection2.getEmptyNeighbors().size());
        assertEquals(3,intersection.getEmptyNeighbors().size());
    }
}