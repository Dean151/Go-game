package go.core;

import go.core.exceptions.InvalidGameTurnEncounteredException;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Set;

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

    @Test
    public void testUndoRedo() throws Exception {
        assertFalse(goban9.redo());
        assertTrue(goban9.undo());
        assertTrue(goban9.redo());
    }

    @Test
    public void testGetCapturedStones() throws Exception {
        assertTrue(goban9.play(goban9.getIntersection(5,7),one));
        Set<Intersection> set = goban9.getLastCaptured();
        assertEquals(1,set.size());
        assertTrue(set.contains(goban9.getIntersection(5, 6)));
    }

    @Test
    public void nextPreviousPlayer() throws Exception {
        Player even = goban9.getPlayer();
        goban9.nextPlayer();
        Player odd = goban9.getPlayer();
        assertNotEquals(odd,even);
        goban9.nextPlayer();
        assertEquals(even,goban9.getPlayer());
        goban9.nextPlayer();
        goban9.nextPlayer();
        assertEquals(even,goban9.getPlayer());
        goban9.nextPlayer();
        assertEquals(odd,goban9.getPlayer());
        goban9.precedentPlayer();
        assertEquals(even,goban9.getPlayer());
    }

    @Test
    public void testChangePlayer() throws Exception {
        Goban A = new Goban(9,9,4);
        assertEquals(4,A.getHandicap());
        GameRecord record = A.getGameRecord();

        record.apply(null); assertFalse(A.changePlayer(false)); // first handicap stone player is not changed
        record.undo();      assertFalse(A.changePlayer(true)); // undo , player is not changed
        record.apply(null); assertFalse(A.changePlayer(false)); // 1
        record.apply(null); assertFalse(A.changePlayer(false)); // 2
        record.apply(null); assertFalse(A.changePlayer(false)); // 3
        record.apply(null); assertFalse(A.changePlayer(false)); // 4
        record.apply(null); assertTrue(A.changePlayer(false)); // 5
        record.apply(null); assertTrue(A.changePlayer(true)); // 6
        record.undo();      assertTrue(A.changePlayer(true)); // UNDO
        record.undo();      assertTrue(A.changePlayer(true)); // UNDO
        record.undo();      assertFalse(A.changePlayer(true)); // UNDO but with handicap stones again
    }

    @Test
    public void badGameTurns() throws Exception {
        GameTurn A = new GameTurn(15,15);
        GameTurn B = null ;
        GameTurn D = new GameTurn(9,9).toNext(2,2,3,Collections.<Intersection>emptySet());
        try {
            goban9.takeGameTurn(A,one,two);
            fail("Exception not thrown");
        } catch (InvalidGameTurnEncounteredException ex) {

        }
        try {
            goban9.takeGameTurn(B,one,two);
            fail("Exception not thrown");
        } catch (InvalidParameterException ex) {

        }
        try {
            goban9.takeGameTurn(D,one,two);
            fail("Exception not thrown");
        } catch (InvalidGameTurnEncounteredException ex) {

        }
    }

    @Test
    public void nullOutsideGoban() throws Exception {
        assertNull(goban9.getIntersection(10,10));
    }

    @Test
    public void getDimensions() throws Exception {
        assertEquals(9,goban9.getWidth());
        assertEquals(9,goban9.getHeight());
    }

    @Test
    public void testPassCount() throws Exception {
        assertEquals(0,goban9.getSuccessivePassCount());
        goban9.pass(goban9.getPlayer());
        goban9.pass(goban9.getPlayer());
        assertEquals(2,goban9.getSuccessivePassCount());
        goban9.play(1,1,goban9.getPlayer());
        assertEquals(0,goban9.getSuccessivePassCount());

        goban9.updatePassCount(true);
        goban9.updatePassCount(true);
        goban9.updatePassCount(true);
        goban9.updatePassCount(true);
        assertEquals(4,goban9.getSuccessivePassCount());
        goban9.updatePassCount(false);
        assertEquals(0,goban9.getSuccessivePassCount());
    }
}
