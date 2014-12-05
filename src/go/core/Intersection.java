package go.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 12/4/2014.
 */

//Should Record the state of an intersection Empty, stone Player 1, stone Player 2 (Possibly extensible to stone Player 3)
public class Intersection {
    public final static int EMPTY = 0;

    int state;

    public Intersection() {
        this.state = EMPTY;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isEmpty() {
        return state == EMPTY;
    }
}
