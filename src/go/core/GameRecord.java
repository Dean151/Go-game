package go.core;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Thomas on 12/4/2014.
 */
public class GameRecord {
    private Stack<GameTurn> tail;
    private Stack<GameTurn> head;

    public GameRecord() {
        tail = new Stack<GameTurn>();
        head = new Stack<GameTurn>();
    }

    public GameRecord(GameRecord record) {
        this(record.head, record.tail);
    }

    private GameRecord(Stack<GameTurn> tail , Stack<GameTurn> head) {
        this.tail = tail;
        this.head = head;
    }

    public void apply(GameTurn turn) {
        tail.push(turn);
    }

    public void undo() throws EmptyStackException {
        head.push(tail.pop());
    }

    public void redo() throws EmptyStackException {
        tail.push(head.pop());
    }

    public Iterable<GameTurn> getPlayedGameTurns() {
        return tail;
    }

    public boolean save(String filepath) throws Exception {
        return false;
        //TODO Implement Save method
    }

    public boolean load(String filepath) throws Exception {
        return false;
        //TODO Implement load method
    }
}


