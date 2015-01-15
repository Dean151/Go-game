package go.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stone chain, of horizontally and vertically connected stones of a same color
 * It also keeps track ov the horizontal and vertical vacancies;
 * Created by Thomas on 12/4/2014.
 */
public class StoneChain {
    /**
     * The intersections where the stones of a stone chain are.
     */
    private final Set<Intersection> stones;

    /**
     * The intersections where the liberties of a stone chain are.
     */
    private final Set<Intersection> liberties;

    /**
     * The player to which the stone chain belongs.
     */
    private final Player owner;

    /**
     * Constructor with all attributes.
     * @param stones
     * @param liberties
     * @param owner
     */
    public StoneChain(Set<Intersection> stones, Set<Intersection> liberties, Player owner) {
        this.stones = stones;
        this.liberties = liberties;
        this.owner = owner;
    }

    /**
     * Constructs a stone Chain comprising of one stone.
     * @param intersection is the intersection where the stone is.
     * @param owner is the owner of the stone chain.
     */
    public StoneChain(Intersection intersection, Player owner) {
        stones = new HashSet<Intersection>();
        stones.add(intersection);
        this.owner = owner;
        liberties = new HashSet<Intersection>(intersection.getEmptyNeighbors());
    }

    /**
     * Copy Constructor
     * @param stoneChain
     */
    public StoneChain(StoneChain stoneChain) {
        this.stones = new HashSet<Intersection>(stoneChain.stones);
        this.liberties = new HashSet<Intersection>(stoneChain.liberties);
        this.owner = stoneChain.owner;
    }

    /**
     *
     * @return owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *
     * @return Set of liberties
     */
    public Set<Intersection> getLiberties() {
        return liberties;
    }

    /**
     *
     * @return Set of stones
     */
    public Set<Intersection> getStones() {
        return stones;
    }

    /**
     * Adds a second StoneChain to the current StoneChain taking into account the stone that was played.
     * @param stoneChain the second StoneChain.
     * @param playedStone the stone played this turn.
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
     * Removes a played Stone from the liberties of a StoneChain.
     * @param playedStone the stone played this turn.
     * @return a new StoneChain without playedStone as a liberty.
     */
    public StoneChain removeLiberty(Intersection playedStone) {
        StoneChain newStoneChain = new StoneChain(stones, liberties, owner);
        newStoneChain.liberties.remove(playedStone);
        return newStoneChain;
    }

    /**
     * Removes a StoneChain and updates liberties of neighboring chains and capture stone count.
     * 
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
