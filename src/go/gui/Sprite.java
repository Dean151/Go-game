package go.gui;

import go.core.Goban;

import javax.swing.*;
import java.awt.*;

/**
 * Created by thomas on 24/01/15.
 */
public class Sprite {
    public static final int TOKEN_INITIAL_SIZE = GUI.TOKEN_INITIAL_SIZE;

    // Initialing sprites
    public static final ImageIcon grid_ul = new ImageIcon(new ImageIcon("sprites/ul.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_u = new ImageIcon(new ImageIcon("sprites/u.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_ur = new ImageIcon(new ImageIcon("sprites/ur.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_l = new ImageIcon(new ImageIcon("sprites/l.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_c = new ImageIcon(new ImageIcon("sprites/c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_spot = new ImageIcon(new ImageIcon("sprites/spot.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_r = new ImageIcon(new ImageIcon("sprites/r.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_bl = new ImageIcon(new ImageIcon("sprites/bl.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_b = new ImageIcon(new ImageIcon("sprites/b.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_br = new ImageIcon(new ImageIcon("sprites/br.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

    public static final ImageIcon grid_p1 = new ImageIcon(new ImageIcon("sprites/p1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p2 = new ImageIcon(new ImageIcon("sprites/p2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p1_c = new ImageIcon(new ImageIcon("sprites/p1_c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p2_c = new ImageIcon(new ImageIcon("sprites/p2_c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p1_mp2 = new ImageIcon(new ImageIcon("sprites/p1_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p2_mp1 = new ImageIcon(new ImageIcon("sprites/p2_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));

    public static final ImageIcon grid_ul_mp1 = new ImageIcon(new ImageIcon("sprites/ul_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_u_mp1 = new ImageIcon(new ImageIcon("sprites/u_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_ur_mp1 = new ImageIcon(new ImageIcon("sprites/ur_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_l_mp1 = new ImageIcon(new ImageIcon("sprites/l_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_c_mp1 = new ImageIcon(new ImageIcon("sprites/c_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_r_mp1 = new ImageIcon(new ImageIcon("sprites/r_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_bl_mp1 = new ImageIcon(new ImageIcon("sprites/bl_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_b_mp1 = new ImageIcon(new ImageIcon("sprites/b_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_br_mp1 = new ImageIcon(new ImageIcon("sprites/br_mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

    public static final ImageIcon grid_ul_mp2 = new ImageIcon(new ImageIcon("sprites/ul_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_u_mp2 = new ImageIcon(new ImageIcon("sprites/u_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_ur_mp2 = new ImageIcon(new ImageIcon("sprites/ur_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_l_mp2 = new ImageIcon(new ImageIcon("sprites/l_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_c_mp2 = new ImageIcon(new ImageIcon("sprites/c_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_r_mp2 = new ImageIcon(new ImageIcon("sprites/r_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_bl_mp2 = new ImageIcon(new ImageIcon("sprites/bl_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_b_mp2 = new ImageIcon(new ImageIcon("sprites/b_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    public static final ImageIcon grid_br_mp2 = new ImageIcon(new ImageIcon("sprites/br_mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

    /**
     * Allow to know if the intersection should be marked on the goban
     * @param goban goban to get the size of this one
     * @param x x coord
     * @param y y coord
     * @return true if the intersection is marked
     */
    private static boolean shouldBeSpot(Goban goban, int x, int y) {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();
        int offset = 3;
        if (gobanWidth < 10) offset = 2;

        return (x == offset || x == (gobanWidth-1)/2 || x == gobanWidth-offset-1) && (y == offset || y == (gobanHeight-1)/2 || y == gobanHeight-offset-1);
    }

    /**
     *
     * @param goban goban to get the size of this one
     * @param x x coord
     * @param y y coord
     * @param ownerID if the intersection has owner, for score handling
     * @return icon of grid
     */
    public static ImageIcon getGridIcon(Goban goban, int x, int y, int ownerID) {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();

        switch (ownerID) {
            case 1:
                if (x == gobanWidth - 1) {
                    if (y == 0) {
                        return grid_ul_mp1;
                    } else if (y == gobanHeight - 1) {
                        return grid_ur_mp1;
                    } else {
                        return grid_u_mp1;
                    }
                } else if (x == 0) {
                    if (y == 0) {
                        return grid_bl_mp1;
                    } else if (y == gobanHeight - 1) {
                        return grid_br_mp1;
                    } else {
                        return grid_b_mp1;
                    }
                } else {
                    if (y == 0) {
                        return grid_l_mp1;
                    } else if (y == gobanHeight - 1) {
                        return grid_r_mp1;
                    } else {
                        return grid_c_mp1;
                    }
                }
            case 2:
                if (x == gobanWidth - 1) {
                    if (y == 0) {
                        return grid_ul_mp2;
                    } else if (y == gobanHeight - 1) {
                        return grid_ur_mp2;
                    } else {
                        return grid_u_mp2;
                    }
                } else if (x == 0) {
                    if (y == 0) {
                        return grid_bl_mp2;
                    } else if (y == gobanHeight - 1) {
                        return grid_br_mp2;
                    } else {
                        return grid_b_mp2;
                    }
                } else {
                    if (y == 0) {
                        return grid_l_mp2;
                    } else if (y == gobanHeight - 1) {
                        return grid_r_mp2;
                    } else {
                        return grid_c_mp2;
                    }
                }
            case 0:
            default:
                if (x == gobanWidth - 1) {
                    if (y == 0) {
                        return grid_ul;
                    } else if (y == gobanHeight - 1) {
                        return grid_ur;
                    } else {
                        return grid_u;
                    }
                } else if (x == 0) {
                    if (y == 0) {
                        return grid_bl;
                    } else if (y == gobanHeight - 1) {
                        return grid_br;
                    } else {
                        return grid_b;
                    }
                } else {
                    if (y == 0) {
                        return grid_l;
                    } else if (y == gobanHeight - 1) {
                        return grid_r;
                    } else {
                        if (shouldBeSpot(goban, x, y)) {
                            return grid_spot;
                        } else {
                            return grid_c;
                        }
                    }
                }
        }
    }

    public static ImageIcon getPlayerIcon(int player, int owner) {
        switch (player) {
            case 1:
                if (owner == 2) return grid_p1_mp2;
                else return grid_p1;
            case 2:
                if (owner == 1) return grid_p2_mp1;
                else return grid_p2;
            default:
                return null;
        }
    }
}
