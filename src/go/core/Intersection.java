package go.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/4/2014.
 */
public class Intersection {
    //Should Record the state of an intersection Empty, stone Player 1, stone Player 2 (Possibly extensible to stone Player 3)
    int state;
    List<Intersection> neighbours;

    public Intersection() {
        state=0;
        neighbours = new ArrayList<Intersection>();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void addNeighbour(Intersection n) {
        neighbours.add(n);
    }

    public List<Intersection> getNeighbours() {
        return neighbours;
    }
}
