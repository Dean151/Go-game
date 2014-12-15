package go.core;

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
    public void addCapturedStone(int nb) {
        capturedStones += nb;
    }

    /**
     * Method to make the player play
     */
    public void play() {
        // TODO play method
    }
}
