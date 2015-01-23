package go.core;

import java.util.Arrays;
import java.util.Set;

/**
 * GameTurn is a class listing all the important information of a given game turn
 * it is to be used by the GameRecord
 * Created by Thomas on 12/4/2014.
 */
public class GameTurn {
    /**
     * The state of the goban after the move applied in the turn.
     */
    private final int[][] gobanState;

    /**
     * The coordinates of the move applied in the turn.
     */
    private final int x, y;

    /**
     * A hashCode of the gobanState stored for quick comparison.
     */
    private final int hashCode;

    /**
     * The number of captured stones if applicable.
     */
    private final int countCapturedStones;

    /**
     * The pass count at the game turn level
     */
    private int passCount;

    /**
     * Copy Constructor
     */
    public GameTurn(GameTurn source) {
        int width = source.gobanState.length;
        int height = source.gobanState[0].length;
        x = source.x;
        y = source.y;
        hashCode = source.hashCode;
        countCapturedStones = source.countCapturedStones;
        passCount = source.passCount;
        gobanState = new int[width][height];
        for (int i = 0; i < width ; i++) {
            gobanState[i] = source.gobanState[i].clone();
        }
    }

    /**
     * Constructor for the first virtual gameTurn, which gives the correct size for the array representing the goban.
     * @param width width of the corresponding goban.
     * @param height height of the corresponding goban.
     */
    public GameTurn(int width, int height) {
        gobanState = new int[width][height];
        countCapturedStones = 0;
        passCount = 0;

        // Move is virtual, x and y are set to -1
        x = -1;
        y = -1;

        // Using Java Tools to make a pertinent hash on the goban state
        hashCode = Arrays.deepHashCode(gobanState);
    }

    /**
     * Constructor which uses the previous GameTurn to be able to record the new state based on the previous one,
     * And applying the potential modifications:  adding the played stone, and removing and counting captured stones.
     * Cam also make a passing move by setting the coordinates of the played stone to (-1,-1).
     * @param prev The previous GameTurn.
     * @param X The x coordinate of the played stone, this game turn, -1 if the player passes.
     * @param Y The y coordinate of the played stone, this game turn, -1 if the player passes.
     * @param playerId The id of the player making the given game turn.
     * @param freedIntersections A set of Intersections which may have been freed, due to being captured.
     */
    private GameTurn(GameTurn prev, int X, int Y, int playerId, int handicap, Set<Intersection> freedIntersections ) {
        int width = prev.gobanState.length;
        int height = prev.gobanState[0].length;

        gobanState = new int[width][height];
        for (int i = 0; i < width ; i++) {
            gobanState[i] = prev.gobanState[i].clone();
        }
        x = X;
        y = Y;

        // Applying the played stone change, if is not a pass move
        if ( x >= 0 && y >= 0 ) {
            gobanState[x][y] = playerId;
            passCount = 0;
        } else {
            passCount = prev.passCount + 1;
        }

        // Setting all the freed intersections to 0, and counting the number of captured stones
        for(Intersection freedIntersection : freedIntersections) {
            gobanState[freedIntersection.getX()][freedIntersection.getY()] = 0;
        }
        countCapturedStones = freedIntersections.size();;

        // Using Java Tools to make a pertinent hash on the goban state
        hashCode = Arrays.deepHashCode(gobanState);
    }

    /**
     * Wrapper for the private constructor used to build a new game turn based on a previous one.
     * @param X The x coordinate of the played stone, this game turn, -1 if the player passes.
     * @param Y The y coordinate of the played stone, this game turn, -1 if the player passes.
     * @param playerId The Id of the player making the given game turn.
     * @param freedIntersections A set of Intersections which may have been freed, due to being captured.
     */
    public GameTurn toNext(int X, int Y, int playerId, Set<Intersection> freedIntersections) {
        return toNext(X, Y, playerId, 0, freedIntersections);
    }

    public GameTurn toNext(int X, int Y, int playerId, int handicap, Set<Intersection> freedIntersections) {
        return new GameTurn(this,X,Y,playerId, handicap, freedIntersections);
    }

    /**
     *
     * @return the State of the goban after a game turn, matrix of 0, 1 and 2.
     */
    public int[][] getGobanState() {
        return gobanState;
    }

    /**
     *
     * @return the X coordinate of the played move in the game turn, -1 if pass or initialization move.
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the Y coordinate of the played move in the game turn, -1 if pass or initialization move.
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return the pass count at given game turn.
     */
    public int getPassCount() { return passCount; }

    /**
     *
     * @return a hashCode of the state of the goban after the game turn.
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     *
     * @return the number of captured stones this given game turn, between 0 and N²-1 where N is the size of the board.
     */
    public int getCountCapturedStones() {
        return countCapturedStones;
    }

    /**
     * Overriding the equals function, first check against hash codes of the goban states for speed,
     * Then if the hashCodes are the same makes a deep comparison between the goban states.
     * @param obj The object to be compared to this.
     * @return {@code true} if the two objects have the same GobanState,
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameTurn castedObj = (GameTurn) obj;

        return hashCode == castedObj.hashCode && Arrays.deepEquals(this.gobanState, castedObj.gobanState);
    }
}
