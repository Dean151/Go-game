package go.gui;

import go.core.Goban;
import go.core.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 20/01/15.
 */
public class PlayMove implements ActionListener {
    private int x;
    private int y;

    private GUI gui;

    public PlayMove(GUI gui, int x, int y) {
        super();

        this.gui = gui;
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Player player = gui.getGoban().getPlayer();

        System.out.println(player+" want to play in ["+x+"-"+y+"]");

        try {
            if (gui.getGoban().play(x, y, player)) {
                System.out.println("Move applied");
                gui.getGoban().nextPlayer();
                // TODO place the black or white tile on [x,y]
            }
        } catch (Exception ex) {

        }
    }
}
