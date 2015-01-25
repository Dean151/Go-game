package go.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * GameRecord is a class containing all the moves of a game.
 * The record has two stacks to track previous moves, and undone moves to implement a basic
 * Game history which can be replayed
 * Created by Thomas on 12/4/2014.
 */
public class GameRecord {
    /**
     * Stacks all the preceding moves, when a new move is made ir is applied from the head of the stack.
     */
    private final Stack<GameTurn> preceding;

    /**
     * Stacks all the following moves, needed to implement a basic undo()-redo().
     * Undone moves are popped from preceding and pushed into following.
     * Redone moves are popped from following and pushed into preceding.
     */
    private final Stack<GameTurn> following;

    /**
     * Number of initial handicap : represent the number of stones that black will set on the Goban before playing
     */
    private final int handicap;

    /**
     * Constructs a new game record of dimensions width x height.
     * @param width
     * @param height
     */
    public GameRecord(int width, int height) {
        this(width, height, 0);
    }

    public GameRecord(int width, int height, int handicap) {
        preceding = new Stack<GameTurn>();
        following = new Stack<GameTurn>();
        GameTurn first = new GameTurn(width, height);
        this.handicap = handicap;
        apply(first);
    }

    /**
     * Constructs a deep copy of a given record.
     * @param record the record to be copied.
     */
    public GameRecord(GameRecord record) {
        this(record.preceding, record.following, record.handicap);
    }

    /**
     * Constructs a GameRecord by copying stacks of GameTurns to fill the preceding
     * and following attributes.
     * @param preceding
     * @param following
     */
    private GameRecord(Stack<GameTurn> preceding, Stack<GameTurn> following, int handicap) {
        this.preceding = new Stack<GameTurn>();
        this.following = new Stack<GameTurn>();
        this.handicap = handicap;
        for (GameTurn turn : preceding) {
            this.preceding.add(turn);
        }
        for (GameTurn turn : following) {
            this.following.add(turn);
        }
    }

    public int getHandicap() {
        return handicap;
    }

    /**
     * Adds a GameTurn to the preceding stack and clears the following stack.
     * redo() can no longer be called after an apply().
     * @param turn the GameTurn to be added.
     */
    public void apply(GameTurn turn) {
        preceding.push(turn);
        following.clear();
    }

    /**
     * Checks if the GameRecord has preceding moves.
     * @return {@code true} if it does,
     * {@code false} otherwise.
     */
    public boolean hasPreceding() {
        return preceding.size() > 1;
    }

    /**
     * Return the number of preceding states, ignoring the first one
     * @return number of preceding states
     */
    public int nbrPreceding() { return preceding.size() - 1; }

    /**
     * Checks if the GameRecord has following moves,
     * if it can call redo().
     * @return {@code true} if it does,
     * {@code false} otherwise.
     */
    public boolean hasFollowing() {
        return following.size() > 0;
    }

    /**
     * Puts a GameTurn from the preceding stack to the following stack.
     * @throws EmptyStackException if the preceding stack is empty.
     */
    public void undo() throws EmptyStackException {
        if (preceding.size() > 1) {
            following.push(preceding.pop());
        } else {
            throw new EmptyStackException();
        }
    }

    /**
     * Puts a GameTurn from the following stack to the preceding stack.
     * @throws EmptyStackException if the following stack is empty.
     */
    public void redo() throws EmptyStackException {
        preceding.push(following.pop());
    }

    /**
     * Gets the turns in preceding as an iterable.
     * @return this.preceding as an iterable.
     */
    public Iterable<GameTurn> getTurns() {
        return preceding;
    }

    /**
     * Returns in the head of preceding stack by peeking.
     * @return the last turn in the GameRecord.
     */
    public GameTurn getLastTurn() {
        return preceding.peek();
    }

    /**
     * Saves the GameRecord as a JSON object.
     * @param filepath the path where to save.
     * @return {@code true} if the save was successful,
     * {@code false} otherwise.
     */
    public boolean save(String filepath) {
        BufferedWriter writer;
        try {
            // BufferedWriter Creation
            writer = new BufferedWriter(new FileWriter(filepath));

            writer.write("{");
            writer.newLine();
            writer.write("  \"width\": " + preceding.peek().getGobanState().length + ",");
            writer.newLine();
            writer.write("  \"height\": " + preceding.peek().getGobanState()[0].length + ",");
            writer.newLine();
            writer.write("  \"handicap\": " + handicap + ",");
            writer.newLine();

            //Insanely Java Iterates the stack bottom to top

            writer.write("  \"preceding\": [");
            writer.newLine();

            for (GameTurn turn : preceding) {
                writer.write("            [ " + turn.getX() + " , " + turn.getY() + " ],");
                writer.newLine();
            }
            writer.write("            null");
            writer.newLine();
            writer.write("         ],");
            writer.newLine();

            Stack<GameTurn> followForWrite = new Stack<GameTurn>();
            for (int i = following.size() - 1 ; i >= 0 ; i--) {
                followForWrite.push(following.get(i));
            }

            writer.write("  \"following\": [");
            writer.newLine();
            for (GameTurn turn : followForWrite) {
                writer.write("            [ " + turn.getX() + " , " + turn.getY() + " ],");
                writer.newLine();
            }
            writer.write("            null");
            writer.newLine();
            writer.write("         ]");
            writer.newLine();

            writer.write("}");
            writer.newLine();

            writer.close();

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * Loads a GameRecord from a JSON object file.
     * @param filepath the path to the file to be read.
     * @return the loaded GameRecord.
     */
    public static GameRecord load(String filepath) {
        BufferedReader reader;
        GameRecord record = null;
        try {
            //TODO maybe implement as real Json parser
            reader = new BufferedReader(new FileReader(filepath));
            String delim = "\\s*[\\[\\]\\{\\},:]\\s*|\\s+|\\s*null\\s*";

            reader.readLine();

            int width  = Integer.parseInt(reader.readLine().split(delim)[2]);
            int height = Integer.parseInt(reader.readLine().split(delim)[2]);
            int handicap = Integer.parseInt(reader.readLine().split(delim)[2]);

            Goban goban = new Goban(width, height, handicap);
            Player one = new Player(1);
            Player two = new Player(2);
            Player actualPlayer = one;

            int x,y;
            String[] line;

            reader.readLine(); //Skipping first virtual move;
            reader.readLine();

            int i = 0;
            int precedingCount = 0;
            boolean precedingCompleted = false;
            while(true) {
                line = reader.readLine().split(delim);
                if(line.length==0) {
                    if (precedingCompleted) {
                        break;
                    } else {
                        precedingCompleted = true;
                        precedingCount = i;
                        reader.readLine();
                        reader.readLine();
                        continue;
                    }
                }

                x = Integer.parseInt(line[1]);
                y = Integer.parseInt(line[2]);

                actualPlayer.play(goban, x, y);

                if (handicap > 0) {
                    handicap--;
                } else {
                    if (actualPlayer == one) {
                        actualPlayer = two;
                    } else {
                        actualPlayer = one;
                    }
                }
                i++;
            }

            reader.close();

            record = goban.getGameRecord();
            for (int j = i; j > precedingCount ; j--) {
                record.undo();
            }

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }

        return record;
    }

    /**
     * Equals function
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        GameRecord castedObj = (GameRecord) obj;

        if (preceding.size() != castedObj.preceding.size() |following.size() != following.size()) return false;

        for (int i = 0; i < preceding.size(); i++) {
            if (!preceding.get(i).equals(castedObj.preceding.get(i))) return false;
        }
        for (int i = 0; i < following.size(); i++) {
            if (!following.get(i).equals(castedObj.following.get(i))) return false;
        }

        return true;
    }
}
