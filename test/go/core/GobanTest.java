package go.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class GobanTest {

    private Goban goban,goban9;
    private Player one , two ;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(3, 3);
        goban9 = new Goban(9, 9);
        one = new Player(1);
        two = new Player(2);
        goban9.play(goban9.getIntersection(5,5),one);
        goban9.play(goban9.getIntersection(5,6),two);
        goban9.play(goban9.getIntersection(6,6),one);
        goban9.play(goban9.getIntersection(6,7),two);
        goban9.play(goban9.getIntersection(4,6),one);
        goban9.play(goban9.getIntersection(4,7),two);
        goban9.play(goban9.getIntersection(0,0),one);
        goban9.play(goban9.getIntersection(5,8),two);

    }


    @Test
    public void testIsInGoban() throws Exception {
        assertTrue(goban.isInGoban(0, 0));
        assertFalse(goban.isInGoban(-1, 0));
        assertFalse(goban.isInGoban(0, -1));
        assertTrue(goban.isInGoban(2, 2));
        assertTrue(goban.isInGoban(0, 2));
        assertFalse(goban.isInGoban(3, 1));
        assertFalse(goban.isInGoban(1, 4));
        assertFalse(goban.isInGoban(5, 4));

        Intersection intersection = new Intersection(goban, 1, 1);
        assertTrue(goban.isInGoban(intersection));
        intersection = new Intersection(goban, 4, 2);
        assertFalse(goban.isInGoban(intersection));
    }

    @Test
    public void testIsValidMove() throws Exception {
        Intersection intersection = new Intersection(goban, 1, 1);
        Player player = new Player(1);
        assertTrue(goban.play(intersection, player));
        intersection = new Intersection(goban, 1, 3);
        assertFalse(goban.play(intersection, player));
    }

    @Test
    public void testPlayWithKo() throws Exception {
        assertTrue(goban9.play(goban9.getIntersection(5,7),one));
        assertFalse(goban9.play(goban9.getIntersection(5,6),two));
        assertTrue(goban9.play(goban9.getIntersection(2,2),two));
        assertTrue(goban9.play(goban9.getIntersection(1,1),one));
        assertTrue(goban9.play(goban9.getIntersection(5,6),two));
    }

    @Test
    public void testTakeGameTurn() throws Exception {
        GameTurn turn = new GameTurn(9,9);
        turn = turn.toNext(5, 5, 1, Collections.<Intersection>emptySet())
            .toNext(5,6,2,Collections.<Intersection>emptySet())
            .toNext(6,6,1,Collections.<Intersection>emptySet())
            .toNext(6,7,2,Collections.<Intersection>emptySet())
            .toNext(4,6,1,Collections.<Intersection>emptySet())
            .toNext(4,7,2,Collections.<Intersection>emptySet())
            .toNext(0,0,1,Collections.<Intersection>emptySet())
            .toNext(5,8,2,Collections.<Intersection>emptySet());

        Goban fromTurn = new Goban(9,9);
        fromTurn.takeGameTurn(turn,one,two);
        assertEquals(goban9.toString(),fromTurn.toString());
    }

    @Test
    public void testPass() throws Exception {
        goban9.pass(one);
        int x = goban9.getGameRecord().getLastTurn().getX();
        int y = goban9.getGameRecord().getLastTurn().getY();
        GameTurn state = goban9.getGameRecord().getLastTurn();
        goban9.getGameRecord().undo();
        GameTurn prevState = goban9.getGameRecord().getLastTurn();
        assertEquals(-1,x);
        assertEquals(-1,y);
        assertEquals(prevState, state);
        assertFalse(prevState.toString()==state.toString());
    }

    @Test
    public void testPlayEquivalent() throws Exception {
        Goban gobanTest = new Goban(9, 9);
        gobanTest.play(5,5,one);
        gobanTest.play(5,6,two);
        gobanTest.play(6,6,one);
        gobanTest.play(6,7,two);
        gobanTest.play(4,6,one);
        gobanTest.play(4,7,two);
        gobanTest.play(0,0,one);
        gobanTest.play(5,8,two);
        assertEquals(goban9.toString(),gobanTest.toString());
    }
}
