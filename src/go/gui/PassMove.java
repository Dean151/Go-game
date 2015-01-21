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
            Player odd = goban.getPlayer();
            int oddCount = odd.getCapturedStones();
            String oddPlural = (oddCount != 1 ? "s" : "" );

            goban.nextPlayer();

            Player even = goban.getPlayer();
            int evenCount = even.getCapturedStones();
            String evenPlural = (evenCount != 1 ? "s" : "" );

            goban.precedentPlayer();

            JOptionPane.showMessageDialog(gui,
                    oddCount + " pierre" + oddPlural + " de " + odd + " ont été capturée" + oddPlural + "\n" +
                    evenCount + " pierre" + evenPlural + " de " + even + " ont été capturée" + evenPlural + "\n" ,
                    "Fin de partie",
                    JOptionPane.PLAIN_MESSAGE);

        }

    }
}
