package go.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/4/2014.
 */

public class Intersection {
    private Goban goban;
    private int x,y;
    private StoneChain stoneChain;

    public Intersection(Goban goban, int x, int y) {
        this.goban = goban;
        this.x = x;
        this.y = y;
        this.stoneChain = null;
    }

    /**
     * x getter
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * y getter
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * stoneChain getter
     * @return stoneChain
     */
    public StoneChain getStoneChain() {
        return stoneChain;
    }

    /**
     * stoneChain setter
     * @param stoneChain can be null
     */
    public void setStoneChain(StoneChain stoneChain) {
        this.stoneChain = stoneChain;
    }

    /**
     * get to know if the intersection is empty or not
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return stoneChain == null;
    }

    /**
     * Adjacent Stone Chains getter
     * @return List of adjacents stone chains
     */
    public List<StoneChain> getAdjacentStoneChains() {
        List<StoneChain> adjacentStoneChains = new ArrayList<StoneChain>();

        // TODO get adjacent stone chains

        return adjacentStoneChains;
    }

    /**
     * Empty neighbors getter
     * @return List of empty neighbors intersections
     */
    public List<Intersection> getEmptyNeighbors() {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length - 1; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection newIntersection = goban.getIntersection(newX, newY);
                if (newIntersection.isEmpty()) {
                    emptyNeighbors.add(newIntersection);
                }
            }
        }

        return emptyNeighbors;
    }
}
