package go.core;

/**
 * Created by Thomas on 12/4/2014.
 */
public class Goban {
    private int width;
    private int height;
    private Intersection[][] intersections;
    private GameRecord gameRecord;

    public Goban(int width, int height) {
        this.width = width;
        this.height = height;
        intersections = new Intersection[width][height];

        for(int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }

        gameRecord = new GameRecord();
    }

    /**
     * Check if (x,y) is inside the Goban
     * @param x x coord
     * @param y y coord
     * @return boolean true if in Goban , false otherwise
     */
    public boolean isInGoban(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public boolean isInGoban(Intersection intersection) {
        int x = intersection.getX();
        int y = intersection.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public Intersection getIntersection(int x, int y) {
        if (isInGoban(x, y)) {
            return intersections[x][y];
        } else {
            return null;
        }
    }

    /**
     * check if intersection is a valid move for the player
     * @param intersection position where the player want to play
     * @param player
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Intersection intersection, Player player) {

        // TODO check if the move is valid

        // Should be in goban
        if (!isInGoban(intersection)) return false;

        // Should prevent play over another stone
        if (intersection.getStoneChain() != null) return false;

        // Should prevent suicide

        // Should avoid ko

        return true;
    }
}
