package go.core;

import java.util.Collections;

/**
 * Player is a class describing the players which may play the game
 * Created by Thomas on 12/4/2014.
 */
public class Player {
    /**
     * An identifier for discrimination in goban states.
     */
    private final int identifier;

    /**
     * Counts the stones captured by the enemy.
     */
    private int capturedStones;

    /**
     * Constructs a player with a given identifier.
     * @param identifier
     */
    public Player(int identifier) {
        this.identifier = identifier;
        this.capturedStones = 0;
    }

    /**
     *
     * @return identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     *
     * @return capturedStones
     */
    public int getCapturedStones() {
        return capturedStones;
    }

    /**
     * Adds nb to capturedStones.
     * @param nb number of stones to add in captured stones.
     */
    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    /**
     * Removes nb from capturedStones.
     * @param nb number of stones to remove from captured stones.
     */
    public void removeCapturedStones(int nb) { capturedStones -= nb; }

    /**
     * Method to make a player play.
     * @param goban is the goban on which the player plays.
     * @param x the x coordinate of the move.
     * @param y the y coordinate of the move.
     * @return {@code true} if move is valid,
     * {@code false} otherwise.
     */
    public boolean play(Goban goban, int x, int y) {
        if (x == -1 && y == -1) {
            GameRecord record = goban.getGameRecord();
            record.apply(record.getLastTurn().toNext(-1, -1, this.getIdentifier(), goban.getHandicap(), Collections.<Intersection>emptySet()));
            goban.updatePassCount(true);
            return true;
        } else {
            return goban.play(goban.getIntersection(x,y),this);
        }
    }

    @Override
    public String toString() {
        return "Player "+identifier;
    }
}
