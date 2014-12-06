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

    /**
     * State getter
     * @return 0 if empty, 1 or 2 for P1 or P2
     */
    public int getState() {
        return state;
    }

    /**
     * Allow to change the value of state
     * @param state new state (0 is empty, 1 for P1 stone, 2 for P2 stone
     */
    public void setState(int state) {
        if (state >= 0 && state <= 2) { // We make sure we have a valid State in parameter
            this.state = state;
        }
    }

    /**
     * Check if this intersection is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return state == EMPTY;
    }
}
