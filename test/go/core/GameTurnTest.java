package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameTurnTest extends TestCase {

    private Player one;
    private Player two;
    private Set<Intersection> captured;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        one = new Player(1);
        two = new Player(2);
        captured = new HashSet<Intersection>();
    }

    @Test
    public void testTwoSequenceSameStateShouldBeEqual() throws Exception {
        GameTurn A = new GameTurn(9,9).toNext(1,1,one,captured)
                .toNext(1, 2, two, captured)
                .toNext(3, 3, one, captured)
                .toNext(8, 7, two, captured)
                .toNext(7, 7, one, captured)
                .toNext(5, 2, two, captured);
        GameTurn B = new GameTurn(9,9).toNext(3,3,one,captured)
                .toNext(5, 2, two, captured)
                .toNext(1, 1, one, captured)
                .toNext(1, 2, two, captured)
                .toNext(7, 7, one, captured)
                .toNext(8, 7, two, captured);

        assertEquals(A, B);
    }

    @Test(timeout=3000)
    public void testToNextIsFast() {
        long start = System.currentTimeMillis(); //Timeout option not working, bypass  for now
        GameTurn A = new GameTurn(19,19);
        List<GameTurn> list = new ArrayList<GameTurn>();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                A = A.toNext(i%19,j%19,one,captured);
                A = A.toNext((i+8)%19,(j+8)%19,two,captured);
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("One million creation of game turns should take less than 5 seconds, took: "+delta+" milliseconds", delta < 5000 );
    }

}