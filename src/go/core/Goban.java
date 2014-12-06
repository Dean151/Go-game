package go.core;

/**
 * Created by Thomas on 12/4/2014.
 */
public class Goban {
    private int width;
    private int height;
    private Intersection[][] intersections;

    public Goban(int width, int height) {
        this.width = width;
        this.height = height;
        intersections = new Intersection[width][height];

        for(int i=0; i<this.width; i++) {
            for (int j=0; j<this.height; j++) {
                intersections[i][j] = new Intersection();
            }
        }
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

    /**
     * Check if (x,y) is a valid empty position
     * @param x x coord
     * @param y y coord
     * @return boolean true if empty and valid, false otherwise
     */
    public boolean isEmpty(int x, int y) {
        if (isInGoban(x, y)) {
            return intersections[x][y].isEmpty();
        } else {
            return false;
        }
    }
}
