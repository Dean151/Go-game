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
        Goban goban = new Goban(19,19);
        GUI gui = new GUI(goban);
    }
}
