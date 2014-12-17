package go.core;

import java.util.Set;

/**
 * Created by Thomas on 12/4/2014.
 */
public class Goban {
    private int width;
    private int height;
    private Intersection[][] intersections;
    private GameRecord gameRecord;

    public Goban(int width, int height) {
        this.width = width;
        this.height = height;
        intersections = new Intersection[width][height];

        for(int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }

        gameRecord = new GameRecord();
    }

    /**
     * Check if (x,y) is inside the Goban
     * @param x x coord
     * @param y y coord
     * @return boolean true if in Goban , false otherwise
     */
    public boolean isInGoban(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Check if intersection is inside the goban
     * @param intersection intersection to check
     * @return
     */
    public boolean isInGoban(Intersection intersection) {
        int x = intersection.getX();
        int y = intersection.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Intersection getter at (x,y)
     * @param x x coord
     * @param y y coord
     * @return
     */
    public Intersection getIntersection(int x, int y) {
        if (isInGoban(x, y)) {
            return intersections[x][y];
        } else {
            return null;
        }
    }

    /**
     * check if intersection is a valid move for the player and record the move if valid
     * @param intersection position where the player want to play
     * @param player the player playing this move
     * @param koCheck flag to enable or disable ko checking
     * @return true if the move is valid, false otherwise
     */
    public boolean play(Intersection intersection, Player player, boolean koCheck) {

        // Should be in goban
        if (!isInGoban(intersection)) return false;

        // Preventing playing over another stone
        if (intersection.getStoneChain() != null) return false;

        // TODO avoid ko

        Set<StoneChain> adjStoneChains = intersection.getAdjacentStoneChains();
        StoneChain newStoneChain = new StoneChain(intersection, player);
        for (StoneChain stoneChain : adjStoneChains) {
            if (stoneChain.getOwner() == player) {
                newStoneChain.add(stoneChain, intersection);
            } else {
                stoneChain.removeLiberty(intersection);
                if (stoneChain.getLiberties().size() == 0) {
                    stoneChain.die();
                }
            }
        }

        // Preventing suicide
        if (newStoneChain.getLiberties().size() == 0) {
            return false;
        }



        for (Intersection stone : newStoneChain.getStones()) {
            stone.setStoneChain(newStoneChain);
        }
        return true;
    }

    /**
     * check if intersection is a valid move for the player and record the move if valid
     * always handles ko
     * @param intersection position where the player plays
     * @param player player playing this move
     * @return true if move is valid, false otherwise
     */
    public boolean play(Intersection intersection, Player player) {
        return play(intersection,player,true);
    }
}
