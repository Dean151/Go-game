package go.gui;

import go.core.Goban;
import go.core.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 1/21/2015.
 */
public class PassMove implements ActionListener {
    /**
     * The GUI on which this action is applied
     *
     */
    private GUI gui;

    /**
     * Constructor
     * @param gui
     */
    public PassMove(GUI gui) {
        this.gui = gui;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Goban goban = gui.getGoban();
        goban.pass(goban.getPlayer());
        System.out.println("Pass applied");
        if (goban.getSuccessivePassCount() > 2 ) {

            // Getting both players
            Player odd = goban.getPlayer(1);
            Player even = goban.getPlayer(2);

            JOptionPane.showMessageDialog(gui,
                    "The number of stones taken from " + odd + " is " + odd.getCapturedStones() +"\n" +
                    "The number of stones taken from " + even + " is " + even.getCapturedStones() +"\n" ,
                    "The game is over",
                    JOptionPane.PLAIN_MESSAGE);

        }

    }
}
