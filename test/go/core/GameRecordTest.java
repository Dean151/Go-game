package go.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;
import java.util.EmptyStackException;

public class GameRecordTest {

    private static final boolean deleteTestFile = true;
    private static final String savePath = "./test/test.json";
    private GameRecord record1, record2, record3, record0;

    @Before
    public void setUp() throws Exception {
        record0 = new GameRecord(9,9);
        record1 = new GameRecord(9,9);
        record2 = new GameRecord(9,9);
        record3 = new GameRecord(9,9);
        GameTurn current1;
        GameTurn current2;
        for (int i = 0; i < 9 ; i++) {
            current1 = record1.getLastTurn().toNext(i,i,(i)%2+1, Collections.<Intersection>emptySet());
            record1.apply(current1);
            current2 = record2.getLastTurn().toNext(i,i,(i)%2+1, Collections.<Intersection>emptySet());
            record2.apply(current2);
            current2 = record3.getLastTurn().toNext(i,i,(i)%2+1, Collections.<Intersection>emptySet());
            record3.apply(current2);
        }
        record1.undo();
        record1.undo();
        record1.undo();
        record2.undo();
        record2.undo();
        record2.undo();
    }

    @After
    public void tearDown() throws Exception {
        if (deleteTestFile) {
            File testFile = new File(savePath);
            testFile.delete();
        }
    }

    @Test
    public void WritingAndLoadingIsCoherent() throws Exception {
        assertTrue(record1.save(savePath));
        GameRecord loaded = GameRecord.load(savePath);
        assertEquals(record1, loaded);
    }

    @Test
    public void SavingDoesNotCorruptRecord() throws Exception {
        record1.save(savePath);
        assertEquals(record1,record2);
    }

    @Test
    public void testHasPrecedingAndFollowing() throws Exception {
        assertTrue(record1.hasFollowing());
        assertTrue(record1.hasPreceding());
        record1.apply(record1.getLastTurn().toNext(5,2,1,Collections.<Intersection>emptySet()));
        assertFalse(record1.hasFollowing());
        assertTrue(record1.hasPreceding());

        assertFalse(record0.hasPreceding());
        assertFalse(record0.hasFollowing());
        record0.apply(record0.getLastTurn().toNext(1, 1, 1, Collections.<Intersection>emptySet()));
        assertTrue(record0.hasPreceding());
        record0.undo();
        assertFalse(record0.hasPreceding());
        assertTrue(record0.hasFollowing());

        assertFalse(record3.hasFollowing());
        record3.undo();
        assertTrue(record3.hasFollowing());
        record3.redo();
        assertFalse(record3.hasFollowing());
    }

    @Test
    public void UndoRedoIsCoherent() throws Exception {
        record2.undo();
        record2.redo();
        assertEquals(record1,record2);
    }

    @Test (expected = EmptyStackException.class)
    public void noMoreUndo() throws Exception {
        for (int i = 0; i < 99 ; i++) {
            record1.undo();
        }
    }

    @Test (expected = EmptyStackException.class)
    public void noMoreRedo() throws Exception {
        for (int i = 0; i < 99 ; i++) {
            record1.redo();
        }
    }

    @Test
    public void copyWorks() throws Exception {
        GameRecord copy = new GameRecord(record1);
        assertEquals(record1,copy);
    }
}