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
        this.intersections = new Intersection[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intersections[i][j] = new Intersection();
            }
        }
    }
}
