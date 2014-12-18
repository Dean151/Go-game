package go.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class GameRecordTest extends TestCase {

    GameRecord record = null;

    @Before
    public void setUp() throws Exception {
        record = new GameRecord(9,9);
        for (int i = 0; i < 9 ; i++) {
            GameTurn current = record.getLastTurn().toNext(i,i,1, Collections.<Intersection>emptySet());
            record.apply(current);
        }
        record.undo();
        record.undo();
        record.undo();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWritingAndLoadingIsCoherent() throws Exception {
        record.save("test.json");
        GameRecord loaded = GameRecord.load("test.json");

        assertEquals(record,loaded);
    }

}