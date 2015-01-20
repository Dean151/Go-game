package go.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 20/01/15.
 */
public class UndoRedo implements ActionListener {
    private boolean undo;
    private GUI gui;

    public UndoRedo(GUI gui, boolean undo) {
        this.gui = gui;
        this.undo = undo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (undo) {
            // Undo action
            gui.setRedoEnabled(true);
            if (gui.getGoban().undo() == 1) {
                gui.setUndoEnabled(false);
            }
        } else {
            // Redo action
            gui.setUndoEnabled(true);
            if (gui.getGoban().redo() == 1) {
                gui.setUndoEnabled(true);
            }
        }

        gui.updateGoban();
    }
}
