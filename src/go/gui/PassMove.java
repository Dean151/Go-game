package go.gui;

import go.core.Goban;

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
        if(goban.getSuccessivePassCount() < 3) {
            goban.pass(goban.getPlayer());
            System.out.println("Pass applied");
            gui.updateGoban();
            if(goban.getSuccessivePassCount()==3) {
                gui.initScorer();
                gui.updateScore(null);
                gui.setPassEnabled(false);
                gui.setScoreEnabled(true);
            }
        }
    }
}