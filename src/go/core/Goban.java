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

        for(int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                intersections[i][j] = new Intersection();
            }
        }

        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++){
                if (i-1>0) intersections[i-1][j].addNeighbour(intersections[i][j]);
                if (i+1<width) intersections[i+1][j].addNeighbour(intersections[i][j]);
                if (j-1<0) intersections[i][j-1].addNeighbour(intersections[i][j-1]);
                if (j>height) intersections[i][j+1].addNeighbour(intersections[i][j+1]);
            }
        }
    }
}
