package go.core;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Thomas on 12/4/2014.
 */
public class GameTurn {
    private int[][] gobanState;
    private int x, y;
    private int hashCode;

    /**
     * Constructor for the first virtual gameTurn, which gives the correct size for the array representing the goban
     * @param width width of the corresponding goban
     * @param height height of the corresponding goban;
     */
    public GameTurn(int width, int height) {
        gobanState = new int[width][height];
        // Move is virtual, x and y are set to -1
        x = -1;
        y = -1;
        hashCode = Arrays.deepHashCode(gobanState);
    }

    /**
     * Constructor which uses the previous GameTurn to be able to record the new state based on the previous one,
     * And applying the potential modifications:  adding the played stone, and removing eaten stones
     * Cam also make a passing move by setting the coordinates of the played stone to (-1,-1)
     * @param prev The previous GameTurn
     * @param X The x coordinate of the played stone, this game turn, -1 if the player passes
     * @param Y The y coordinate of the played stone, this game turn, -1 if the player passes
     * @param player The player making the given game turn
     * @param freedIntersections A set of Intersections which may have been freed, due to being eaten
     */
    public GameTurn(GameTurn prev, int X, int Y, Player player , Set<Intersection> freedIntersections ) {
        gobanState = prev.gobanState.clone();

        x = X;
        y = Y;
        if ( x > 0 && y > 0 ) {
            gobanState[x][y] = player.getIdentifier();
        }

        for(Intersection freedIntersection : freedIntersections) {
            gobanState[freedIntersection.getX()][freedIntersection.getY()] = 0;
        }

        hashCode = Arrays.deepHashCode(gobanState);
    }

    /**
     * Overriding the equals function, first check against hash codes of the goban states for speed,
     * Then if the hashCodes are the same makes a deep comparison between the goban states.
     * @param obj
     * @return boolean stating if the two GameTurns end the same goban state
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameTurn castedObj = (GameTurn) obj;

        if(hashCode != castedObj.hashCode) return false;
        if(Arrays.deepEquals(this.gobanState,castedObj.gobanState)) return true ;

        return false ;
    }
}
