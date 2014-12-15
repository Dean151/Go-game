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

    /**
     * Check if (x,y) has liberties
     * @param x x coord
     * @param y y coord
     * @return true if the position has liberties, false otherwise
     */
    public boolean hasLiberties(int x, int y) {
        return libertyCount(x, y) != 0;
    }

    /**
     * check the number of liberties for (x,y)
     * @param x x coord
     * @param y y coord
     * @return the number of liberties of this intersection
     */
    public int libertyCount(int x, int y) {
        int libertyCount = 0;

        int[] dx = {-1,0,1,0};
        int[] dy = {0,-1,0,1};

        for (int i = 0; i < 3; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (isEmpty(newX, newY)) {
                libertyCount++;
            }
        }

        return libertyCount;
    }

    /**
     * Get the Goban actual state in text
     * @return String with Goban actual stage
     */
    @Override
    public String toString() {
        String string = "";
        for(int y=0; y<this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                string += Integer.toString(intersections[x][y].getState());
            }
            string += "\n";
        }

        return string;
    }
}
