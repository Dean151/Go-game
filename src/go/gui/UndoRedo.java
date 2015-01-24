package go.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 20/01/15.
 */
public class UndoRedo implements ActionListener {
    /**
     * {@code true} this is an undo, {@code false} this is a redo
     *
     */
    private boolean undo;

    /**
     * The GUI on which this action is applied
     *
     */
    private GUI gui;

    /**
     * Constructor
     * @param gui
     * @param undo
     */
    public UndoRedo(GUI gui, boolean undo) {
        this.gui = gui;
        this.undo = undo;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gui.getGoban().getSuccessivePassCount()<3 || undo) {
            if (undo) {
                // Undo action
                gui.setRedoEnabled(true);
                gui.getGoban().undo();
                if (!gui.getGoban().getGameRecord().hasPreceding()) {
                    gui.setUndoEnabled(false);
                    gui.setPassOrScoreEnabled(true);
                }
            } else {
                // Redo action
                gui.setUndoEnabled(true);
                gui.getGoban().redo();
                if (!gui.getGoban().getGameRecord().hasFollowing()) {
                    gui.setRedoEnabled(false);
                }
            }
            gui.updateGoban();
        }
    }
}

/*if (gui.getGoban().getSuccessivePassCount() > 2 ) {
                // Getting both players
                Player odd = gui.getGoban().getPlayer(1);
                Player even = gui.getGoban().getPlayer(2);

                JOptionPane.showMessageDialog(gui,
                        "The number of stones taken from " + odd + " is " + odd.getCapturedStones() + "\n" +
                                "The number of stones taken from " + even + " is " + even.getCapturedStones() + "\n",
                        "The game is over",
                        JOptionPane.PLAIN_MESSAGE);

                gui.getGoban().undo();
}*/