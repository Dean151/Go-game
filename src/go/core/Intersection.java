package go.core;

import java.util.*;

/**
 * Intersection is class describing where stones may be placed
 * it is to be used by Goban
 * Created by Thomas on 12/4/2014.
 */

public class Intersection {
    /**
     * The goban to which the intersection belongs.
     */
    private final Goban goban;

    /**
     * The coordinates of the intersection on a goban.
     */
    private final int x,y;

    /**
     * The chain to which the intersection belongs if applicable.
     */
    private StoneChain stoneChain;

    /**
     * Constructs an Intersection to a corresponding goban, with given (x,y) coordinates.
     * @param goban
     * @param x
     * @param y
     */
    public Intersection(Goban goban, int x, int y) {
        this.goban = goban;
        this.x = x;
        this.y = y;
        this.stoneChain = null;
    }

    /**
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return stoneChain
     */
    public StoneChain getStoneChain() {
        return stoneChain;
    }

    /**
     *
     * @param stoneChain can be null
     */
    public void setStoneChain(StoneChain stoneChain) {
        this.stoneChain = stoneChain;
    }

    /**
     * Checks if the intersection is empty or not
     * @return {@code true} if empty,
     * {@code false} otherwise.
     */
    public boolean isEmpty() {
        return stoneChain == null;
    }

    /**
     * Adjacent Stone Chains getter
     * @return List of adjacent stone chains
     */
    public Set<StoneChain> getAdjacentStoneChains() {
        Set<StoneChain> adjacentStoneChains = new HashSet<StoneChain>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length ; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.stoneChain != null) {
                    adjacentStoneChains.add(adjIntersection.stoneChain);
                }
            }
        }

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

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.isEmpty()) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }

        return emptyNeighbors;
    }

    /**
     * Empty or dead neighbors getter
     * @param deadChains the Set of dead chains, as referred by the user
     * @return the list of empty or dead neighbors
     */
    public List<Intersection> getEmptyOrDeadNeighbors(Set<StoneChain> deadChains) {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.isEmpty() || deadChains.contains(adjIntersection.getStoneChain())) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }
        return emptyNeighbors;
    }
}
