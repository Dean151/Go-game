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
        Goban goban = new Goban(size,size, handicap);
        GUI gui = new GUI(goban);
    }

    /**
     * Loading game
     * @param filepath the location of the file we want to load
     */
    public static void loadGame(String filepath) {
        GameRecord gr = GameRecord.load(filepath);
        Goban goban = new Goban(gr);
        GUI gui = new GUI(goban);
    }
}
