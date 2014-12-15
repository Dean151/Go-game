package go.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a stone chain, of horizontally and vertically connected stones of a same color
 * It also keeps track ov the horizontal and vertical vacancies;
 * Created by Thomas on 12/4/2014.
 */
public class StoneChain {
    Set<Intersection> stones;
    Set<Intersection> liberties;
    Player owner;

    public StoneChain(Set<Intersection> stones, Set<Intersection> liberties, Player owner) {
        this.stones = stones;
        this.liberties = liberties;
        this.owner = owner;
    }

    public StoneChain(Intersection intersection, Player owner) {
        stones = new HashSet<Intersection>();
        stones.add(intersection);
        this.owner = owner;
        liberties = new HashSet<Intersection>();
        liberties.addAll(intersection.getEmptyNeighbors());
    }

    /**
     * Creates a new StoneChain by joining two stoneChains
     * @param stoneChain the second StoneChain
     * @param playedStone the stone played this turn
     * @return a new StoneChain created by fusing both StoneChains
     */
    public StoneChain add(StoneChain stoneChain, Intersection playedStone) {
        // Create new StoneChain
        StoneChain newStoneChain = new StoneChain(stones, liberties, owner);
        // Fuse stone sets
        newStoneChain.stones.addAll(stoneChain.stones);
        // Fuse liberties
        newStoneChain.liberties.addAll(stoneChain.liberties);
        // remove played stone from liberties
        newStoneChain.liberties.remove(playedStone);
        return newStoneChain;
    }

    /**
     * Removes a played Stone from the liberties of a StoneChain
     * @param playedStone the stone played this turn
     * @return a new StoneChain without playedStone as a liberty
     */
    public StoneChain removeLiberty(Intersection playedStone) {
        StoneChain newStoneChain = new StoneChain(stones, liberties, owner);
        newStoneChain.liberties.remove(playedStone);
        return newStoneChain;
    }

    /**
     * Removes a StoneChain and updates liberties of neighboring chains and capture stone count
     */
    public void die() {
        for (Intersection rollingStone : this.stones) {
            rollingStone.setStoneChain(null);
            Set<StoneChain> adjacentStoneChains = rollingStone.getAdjacentStoneChains();
            for (StoneChain stoneChain : adjacentStoneChains) {
                stoneChain.liberties.add(rollingStone);
            }
        }
        this.owner.addCapturedStones(this.stones.size());
    }
}
