package go.gui;

import go.core.Goban;
import go.core.StoneChain;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Thomas on 1/21/2015.
 */
public class HoverEffect extends MouseAdapter {
    int x,y;
    Goban goban;
    JButton intersection;
    GUI gui;

    public HoverEffect(int x, int y, JButton intersection , GUI gui) {
        this.x = x;
        this.y = y;
        this.goban = gui.getGoban();
        this.intersection = intersection;
        this.gui = gui;
    }

    /**
     * {@inheritDoc}
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(goban.getSuccessivePassCount()<3) {
            if (goban.getIntersection(x,y).getStoneChain()==null) {
                switch (goban.getPlayer().getIdentifier()) {
                    case 1:
                        intersection.setIcon(Sprite.grid_p1);
                        break;
                    case 2:
                        intersection.setIcon(Sprite.grid_p2);
                        break;
                }
            } else {
                StoneChain sc = goban.getIntersection(x,y).getStoneChain();
                switch (sc.getOwner().getIdentifier()) {
                    case 1:
                        intersection.setIcon(Sprite.grid_p1_c);
                        break;
                    case 2:
                        intersection.setIcon(Sprite.grid_p2_c);
                        break;

                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if(goban.getSuccessivePassCount()<3) {
            StoneChain sc = goban.getIntersection(x,y).getStoneChain();
            if (sc != null) {
                if (sc.getOwner().getIdentifier() == 1) intersection.setIcon(Sprite.grid_p1);
                else intersection.setIcon(Sprite.grid_p2);
            } else {
                intersection.setIcon(Sprite.getGridIcon(goban, x,y,0));
            }
        }
    }
}
