package go.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class GameTurnTest {

    private Set<Intersection> emptySet;

    @Before
    public void setUp() throws Exception {
        emptySet = new HashSet<Intersection>();
    }

    @Test
    public void EmptyStatesEquality() throws Exception {
        GameTurn nine1 = new GameTurn(9,9);
        GameTurn nine2 = new GameTurn(9,9);
        GameTurn nineteen1 = new GameTurn(19,19);
        GameTurn nineteen2 = new GameTurn(19,19);

        assertEquals(nine1,nine2);
        assertEquals(nineteen1,nineteen2);
        assertFalse(nineteen1.equals(nine1));
    }

    @Test
    public void twoSequenceSameStateShouldBeEqual() throws Exception {
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

    @Test(timeout=10000)
    public void toNextIsFast() throws Exception {
        GameTurn A = new GameTurn(19,19);
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                A = A.toNext(i%19,j%19,1, emptySet);
                A = A.toNext((i+8)%19,(j+8)%19,2, emptySet);
            }
        }
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

    @Test
    public void copyWorks() throws Exception {
        GameTurn A = new GameTurn(9,9).toNext(1,1,1, emptySet)
                .toNext(1, 2, 2, emptySet)
                .toNext(3, 3, 1, emptySet)
                .toNext(8, 7, 2, emptySet)
                .toNext(7, 7, 1, emptySet)
                .toNext(5, 2, 2, emptySet);
        GameTurn copy = new GameTurn(A);
        assertEquals(A,copy);
    }

    @Test
    public void passCount() throws Exception {
        GameTurn A = new GameTurn(9,9);

        assertEquals(0,A.getPassCount());
        A = A.toNext(-1,-1,1,emptySet).toNext(-1,-1,1,emptySet);
        assertEquals(2,A.getPassCount());
        A = A.toNext(5,5,1,emptySet);
        assertEquals(0,A.getPassCount());
    }
}