package go.score;

import go.core.Goban;
import go.core.Intersection;
import go.core.StoneChain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thomas on 1/23/2015.
 */
public class Scorer {
    private Set<StoneChain> aliveStones;
    private Set<StoneChain> deadStones;
    private Set<Island> islands;
    private Goban goban;

    public Scorer(Goban goban) {
        this.goban = goban;
    }

    public Set<StoneChain> getAliveStones() {
        return aliveStones;
    }

    public Set<StoneChain> getDeadStones() {
        return deadStones;
    }

    public Set<Island> getIslands() {
        return islands;
    }

    public void init() {
        this.deadStones = new HashSet<StoneChain>();
        this.aliveStones = new HashSet<StoneChain>();
        for (int i = 0; i < goban.getWidth(); i++) {
            for (int j = 0; j < goban.getHeight(); j++) {
                this.aliveStones.add(goban.getIntersection(i,j).getStoneChain());
            }
        }
        this.aliveStones.remove(null);
        this.islands = Island.IslandsBuilder(goban, Collections.<StoneChain>emptySet());
    }

    public void flipDeathStatus(StoneChain chain) {
        if(chain == null) return;
        if (deadStones.contains(chain)) {
            deadStones.remove(chain);
            aliveStones.add(chain);
        } else {
            deadStones.add(chain);
            aliveStones.remove(chain);
        }
        islands = Island.IslandsBuilder(goban,deadStones);
    }

    public int[] outputScore() {
        int[] score = new int[2];
        for (Island island : islands) {
            if(island.getOwner()==null) continue;
            score[island.getOwner().getIdentifier()-1] += island.getIntersections().size();
        }
        for (StoneChain chain : deadStones) {
            score[chain.getOwner().getIdentifier()-1]--;
        }
        score[0] -= goban.getPlayer(1).getCapturedStones();
        score[1] -= goban.getPlayer(2).getCapturedStones();
        return score;
    }
}
