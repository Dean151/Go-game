package go.core;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Thomas on 12/4/2014.
 */
public class GameRecord {
    private Stack<GameTurn> preceding;
    private Stack<GameTurn> following;

    public GameRecord(int width, int height) {
        preceding = new Stack<GameTurn>();
        following = new Stack<GameTurn>();
        GameTurn first = new GameTurn(width,height);
        apply(first);
    }

    public GameRecord(GameRecord record) {
        this(record.following, record.preceding);
    }

    private GameRecord(Stack<GameTurn> preceding, Stack<GameTurn> following) {
        this.preceding = preceding;
        this.following = following;
    }

    public void apply(GameTurn turn) {
        preceding.push(turn);
        following.clear();
    }

    public boolean hasPreceding() {
        return preceding.size() > 1;
    }

    public boolean hasFollowing() {
        return following.size() > 0;
    }

    public void undo() throws EmptyStackException {
        following.push(preceding.pop());
    }

    public void redo() throws EmptyStackException {
        preceding.push(following.pop());
    }

    public Iterable<GameTurn> getTurns() {
        return preceding;
    }

    public GameTurn getLastTurn() {
        return preceding.peek();
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


