package go.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stone chain, of horizontally and vertically connected stones of a same color
 * It also keeps track ov the horizontal and vertical vacancies;
 * Created by Thomas on 12/4/2014.
 */
public class StoneChain {
    private Set<Intersection> stones;
    private Set<Intersection> liberties;

    private Player owner;

    public StoneChain(Set<Intersection> stones, Set<Intersection> liberties, Player owner) {
        this.stones = stones;
        this.liberties = liberties;
        this.owner = owner;
    }

    public StoneChain(Intersection intersection, Player owner) {
        stones = new HashSet<Intersection>();
        stones.add(intersection);
        this.owner = owner;
        liberties = new HashSet<Intersection>(intersection.getEmptyNeighbors());
    }

    public StoneChain(StoneChain stoneChain) {
        this.stones = new HashSet<Intersection>(stoneChain.stones);
        this.liberties = new HashSet<Intersection>(stoneChain.liberties);
        this.owner = stoneChain.owner;
    }

    /**
     * owner getter
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * liberties getter
     * @return Set of liberties
     */
    public Set<Intersection> getLiberties() {
        return liberties;
    }

    /**
     * stones getter
     * @return Set of stones
     */
    public Set<Intersection> getStones() {
        return stones;
    }

    /**
     * Creates a new StoneChain by joining two stoneChains
     * @param stoneChain the second StoneChain
     * @param playedStone the stone played this turn
     * @return a new StoneChain created by fusing both StoneChains
     */
    public void add(StoneChain stoneChain, Intersection playedStone) {
        // Fuse stone sets
        this.stones.addAll(stoneChain.stones);
        // Fuse liberties
        this.liberties.addAll(stoneChain.liberties);
        // remove played stone from liberties
        this.liberties.remove(playedStone);
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
