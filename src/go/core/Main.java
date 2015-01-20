package go.core;

import go.gui.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A main running class
 * Created by thomas on 05/12/14.
 */
public class Main {

    // Main code goes here
    public static void main(String[] args) {
        newGame(19, 0);
    }

    /**
     * New game
     * @param size size of goban
     * @param handicap number of handicap
     */
    public static void newGame(int size, int handicap) {
        // TODO handle handicap
        Goban goban = new Goban(size,size);
        GUI gui = new GUI(goban);
    }

    public static void loadGame() {
        // TODO load game
    }

    public static void saveGame() {
        // TODO save game
    }
}
