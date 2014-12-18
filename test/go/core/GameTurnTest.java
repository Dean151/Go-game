package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameTurnTest extends TestCase {

    private Set<Intersection> emptySet;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        emptySet = new HashSet<Intersection>();
    }

    @Test
    public void testEmptyStatesEquality() throws Exception {
        GameTurn nine1 = new GameTurn(9,9);
        GameTurn nine2 = new GameTurn(9,9);
        GameTurn nineteen1 = new GameTurn(19,19);
        GameTurn nineteen2 = new GameTurn(19,19);

        assertEquals(nine1,nine2);
        assertEquals(nineteen1,nineteen2);
        assertFalse(nineteen1.equals(nine1));
    }

    @Test
    public void testTwoSequenceSameStateShouldBeEqual() throws Exception {
        GameTurn A = new GameTurn(9,9).toNext(1,1,1, emptySet)
                .toNext(1, 2, 2, emptySet)
                .toNext(3, 3, 1, emptySet)
                .toNext(8, 7, 2, emptySet)
                .toNext(7, 7, 1, emptySet)
                .toNext(5, 2, 2, emptySet);
        GameTurn B = new GameTurn(9,9).toNext(3,3,1, emptySet)
                .toNext(5, 2, 2, emptySet)
                .toNext(1, 1, 1, emptySet)
                .toNext(1, 2, 2, emptySet)
                .toNext(7, 7, 1, emptySet)
                .toNext(8, 7, 2, emptySet);

        assertEquals(A, B);
    }

    @Test(timeout=3000)
    public void testToNextIsFast() throws Exception {
        long start = System.currentTimeMillis(); //Timeout option not working, bypass  for now
        GameTurn A = new GameTurn(19,19);
        List<GameTurn> list = new ArrayList<GameTurn>();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                A = A.toNext(i%19,j%19,1, emptySet);
                A = A.toNext((i+8)%19,(j+8)%19,2, emptySet);
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("One million creation of game turns should take less than 5 seconds, took: "+delta+" milliseconds", delta < 5000 );
    }

    @Test
    public void testCapture() throws Exception {
        GameTurn expected = new GameTurn(9,9).toNext(5,7,1, emptySet);
        GameTurn toBeTaken = new GameTurn(9,9).toNext(5,5,1, emptySet)
                .toNext(5,6,1, emptySet)
                .toNext(5,7,1, emptySet);

        Set<Intersection> takenStones = new HashSet<Intersection>();

        takenStones.add(new Intersection(null,5,5));
        takenStones.add(new Intersection(null,5,6));

        toBeTaken = toBeTaken.toNext(-1,-1,1,takenStones);

        assertEquals(expected,toBeTaken);
    }

}