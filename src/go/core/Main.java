package go.core;

/**
 * A main running class
 * Created by thomas on 05/12/14.
 */
public class Main {

    // Main code goes here
    public static void main(String[] args) {
        Player P1 = new Player(1);
        Player P2 = new Player(2);

        Goban goban = new Goban(19,19);
        // Should have a loop that can switch player
        // Should handle handicap at the beginning of the party
    }
}
