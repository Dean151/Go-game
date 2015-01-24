package go.score;

import go.core.Goban;
import go.core.Intersection;
import go.core.Player;
import go.core.StoneChain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thomas on 1/23/2015.
 */
public class Island {
    private final Set<Intersection> intersections;
    private final Set<StoneChain> neighbours;
    private Player owner;

    public Island() {
        intersections = new HashSet<Intersection>();
        neighbours = new HashSet<StoneChain>();
        owner = null;
    }

    public Set<Intersection> getIntersections() {
        return intersections;
    }

    public Player getOwner() {
        return owner;
    }

    public Player findOwner() {
        Set<Player> adjStoneChainOwners = new HashSet<Player>();
        for(StoneChain chain : neighbours) {
            adjStoneChainOwners.add(chain.getOwner());
            if (adjStoneChainOwners.size()>1) break;
        }
        return (adjStoneChainOwners.size() == 1 ? (Player) adjStoneChainOwners.toArray()[0] : null);
    }

    public static Set<Island> IslandsBuilder(Goban goban, Set<StoneChain> deadChains) {
        int w = goban.getWidth();
        int h = goban.getHeight();
        Set<Intersection> toBeTreated = new HashSet<Intersection>();
        Set<Intersection> toBeTreatedLocally;
        Set<Island> islands = new HashSet<Island>();
        Intersection cross;
        Island newIsland;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                cross = goban.getIntersection(i,j);
                if (cross.isEmpty()||deadChains.contains(cross.getStoneChain())) {
                    toBeTreated.add(cross);
                }
            }
        }

        while (toBeTreated.size()>0) {

            cross = toBeTreated.iterator().next();
            newIsland = new Island();

            toBeTreatedLocally = new HashSet<Intersection>();
            toBeTreatedLocally.add(cross);
            int prev_size;
            int curr_size;
            do {
                prev_size= toBeTreatedLocally.size();
                toBeTreatedLocally =setGrowth(toBeTreatedLocally,deadChains);
                curr_size= toBeTreatedLocally.size();
            } while (curr_size>prev_size);

            newIsland.intersections.addAll(toBeTreatedLocally);

            for(Intersection lcross: toBeTreatedLocally) {
                newIsland.neighbours.addAll(lcross.getAdjacentStoneChains());
            }

            newIsland.neighbours.removeAll(deadChains);
            toBeTreated.removeAll(toBeTreatedLocally);
            islands.add(newIsland);
        }

        for (Island island : islands) {
            island.owner = island.findOwner();
        }
        return islands;
    }

    public static Set<Intersection> setGrowth(Set<Intersection> toBeGrown, Set<StoneChain> deadStones) {
        Set<Intersection> grown = new HashSet();
        grown.addAll(toBeGrown);
        for(Intersection cross : toBeGrown) {
            grown.addAll(cross.getEmptyOrDeadNeighbors(deadStones));
        }
        return grown;
    }
}
