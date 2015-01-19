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
        Player P1 = new Player(1);
        Player P2 = new Player(2);

        Goban goban = new Goban(19,19);
        GUI gui = new GUI(goban);


        // Should have a loop that can switch player
        // Should handle handicap at the beginning of the party
        boolean onesTurn = true;
        while (true) {
            boolean flag = false;
            while (!flag) {
                System.out.println("Please Enter Move:");
                String s = "";
                try {
                    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                    s = bufferRead.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] tokens = s.split(" ");
                if (tokens.length == 2) {
                    int x = -500;
                    int y = -500;
                    try {
                        x = Integer.parseInt(tokens[0], 10);
                        y = Integer.parseInt(tokens[1], 10);
                    } catch (Exception ex) {
                        System.out.println("Please enter integer coordinates!");
                    }
                    if (x >= 0) {
                        try {
                            if (onesTurn) {
                                flag = goban.play(goban.getIntersection(x, y), P1);
                            } else {
                                flag = goban.play(goban.getIntersection(x, y), P2);
                            }
                        } catch (Exception ex) {
                            System.out.println("Coordinates entered are out of Goban, try again");
                        }
                    } else if (x > -500) {
                        if (onesTurn) {
                            goban.pass(P1);
                        } else {
                            goban.pass(P2);
                        }
                        flag = true;
                    }
                }
            }
            onesTurn = !onesTurn;
            System.out.println(goban);
            gui.drawGoban();
        }
    }
}
