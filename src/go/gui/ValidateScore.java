package go.gui;

import go.core.Player;
import go.score.Scorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 1/24/2015.
 */
public class ValidateScore implements ActionListener {
    private final GUI gui;
    private final Scorer scorer;

    public ValidateScore(GUI gui) {
        this.gui = gui;
        this.scorer = gui.getScorer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Getting both players
        Player odd = gui.getGoban().getPlayer(1);
        Player even = gui.getGoban().getPlayer(2);
        int [] score = scorer.outputScore();
        double Wscore = (double) (score[1]-score[0]) + 6.5;

        JOptionPane.showMessageDialog(gui, "The number of stones taken from Black is " + odd.getCapturedStones() + "\n" +
                        "The number of stones taken from White is " + even.getCapturedStones() + "\n" +
                        "The Komi is set a a 6.5 bonus for White\n" +
                        "The final score is Black " + score[0] + " - White " + ((double) score[1] + 6.5) + "\n" +
                        (Wscore>0?"White":"Black") + " wins by " + Math.abs(Wscore) + " Points.\n",

                "Game Score",
                JOptionPane.PLAIN_MESSAGE);
    }
}
