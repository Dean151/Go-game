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
    private Player player;

    public PlayMove(GUI gui, int x, int y, Player player) {
        super();

        this.gui = gui;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO play a move
        System.out.println(player+" should play to ["+x+"-"+y+"]");

        try {
            if (gui.getGoban().play(x, y, player)) {
                System.out.println("Move played");
                gui.getGoban().nextPlayer();
            }
        } catch (Exception ex) {

        }
    }
}
