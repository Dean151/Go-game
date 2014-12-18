package go.core;

import java.util.Collections;

/**
 * Created by Thomas on 12/4/2014.
 */
public class Player {
    private int identifier;
    private int capturedStones; // Counts the stones captured by the enemy

    public Player(int identifier) {
        this.identifier = identifier;
        this.capturedStones = 0;
    }

    /**
     * identifier getter
     * @return identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * capturedStones getter
     * @return capturedStones
     */
    public int getCapturedStones() {
        return capturedStones;
    }

    /**
     * add nb to capturedStones
     * @param nb number of stones to add in captured stones
     */
    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    /**
     * remove nb from capturedStones
     * @param nb number of stones to remove from captured stones
     */
    public void removeCapturedStones(int nb) { capturedStones -= nb; }

    /**
     * Method to make the player play
     */
    public boolean play(Goban goban, int x, int y) {
        if (x == -1 && y == -1) {
            GameRecord record = goban.getGameRecord();
            record.apply(record.getLastTurn().toNext(-1, -1, this.getIdentifier(), Collections.<Intersection>emptySet()));
            return true;
        } else {
            return goban.play(goban.getIntersection(x,y),this);
        }
    }
}
