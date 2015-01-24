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
                gui.setPassEnabled(true);
                gui.setScoreEnabled(false);
                if (!gui.getGoban().getGameRecord().hasPreceding()) {
                    gui.setUndoEnabled(false);
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