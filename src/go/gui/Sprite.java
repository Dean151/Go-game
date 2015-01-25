package go.gui;

import go.core.Goban;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by thomas on 24/01/15.
 */
public class Sprite {
    public static final int TOKEN_INITIAL_SIZE = GUI.TOKEN_INITIAL_SIZE;

    // Background sprite, for color
    private static final ImageIcon background = new ImageIcon(new ImageIcon("sprites/background.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE, TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

    // All grid sprites
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

    // Player sprites
    private static final ImageIcon p1 = new ImageIcon(new ImageIcon("sprites/p1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    private static final ImageIcon p2 = new ImageIcon(new ImageIcon("sprites/p2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    private static final ImageIcon wrong = new ImageIcon(new ImageIcon("sprites/wrong.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    private static final ImageIcon mp1 = new ImageIcon(new ImageIcon("sprites/mp1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private static final ImageIcon mp2 = new ImageIcon(new ImageIcon("sprites/mp2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

    // Images that are frequently used need initialisation
    public static final ImageIcon grid_p1 = merge(new ArrayList<ImageIcon>(Arrays.asList(background, p1)));
    public static final ImageIcon grid_p2 = merge(new ArrayList<ImageIcon>(Arrays.asList(background, p2)));
    public static final ImageIcon grid_p1_c = merge(new ArrayList<ImageIcon>(Arrays.asList(background, p1, wrong)), new ArrayList<Float>(Arrays.asList(new Float(1.0), new Float(1.0), new Float(0.4))));
    public static final ImageIcon grid_p2_c = merge(new ArrayList<ImageIcon>(Arrays.asList(background, p2, wrong)), new ArrayList<Float>(Arrays.asList(new Float(1.0), new Float(1.0), new Float(0.4))));

    /**
     * Merge the images listed in ArrayList, all with a 1.0 alpha transparency
     * @param images list of ImageIcons to merge
     * @return ImageIcon result of the merge
     */
    public static ImageIcon merge(ArrayList<ImageIcon> images) {
        ArrayList<Float> transparency = new ArrayList<Float>();

        for (ImageIcon i : images) {
            transparency.add(new Float(1.0));
        }

        return merge(images, transparency);
    }

    /**
     * Merge the images listed in ArrayList, with the corresponding alpha transparency
     * @param images list of ImageIcons to merge
     * @param transparency list of alpha transparency
     * @return ImageIcon result of the merge
     */
    public static ImageIcon merge(ArrayList<ImageIcon> images, ArrayList<Float> transparency)
    {
        BufferedImage dest = null;
        Graphics2D destG = null;
        int rule; // This is SRC for the top image, and DST_OVER for the other ones
        float alpha;

        for (int i = 0, size = images.size(); i < size; i++)
        {
            Image image = images.get(i).getImage();

            rule = AlphaComposite.SRC_OVER; // Default value
            alpha = transparency.get(i);

            if (i == 0)
            {
                dest = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                destG = dest.createGraphics();

                rule = AlphaComposite.SRC; // Rule for 1st image
            }
            destG.setComposite(AlphaComposite.getInstance(rule, alpha));
            destG.drawImage(image, 0, 0, null);
        }

        return new ImageIcon(dest);
    }

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
        ImageIcon mp;

        switch (ownerID) {
            default:
                return getGridIcon(goban, x, y);
            case 1:
                mp = mp1;
                break;
            case 2:
                mp = mp2;
                break;
        }

        return merge(new ArrayList<ImageIcon>(Arrays.asList(getGridIcon(goban, x, y), mp)));
    }

    /**
     *
     * @param goban goban to get the size of this one
     * @param x x coord
     * @param y y coord
     * @return icon of grid
     */
    public static ImageIcon getGridIcon(Goban goban, int x, int y) {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();

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

    public static ImageIcon getPlayerIcon(int player) {
        switch (player) {
            case 1:
                return grid_p1;
            case 2:
                return grid_p2;
            default:
                return null;
        }
    }

    public static ImageIcon getPlayerIcon(int player, int owner, Goban goban, int x, int y) {
        if (player != owner) {
            switch (player) {
                case 1:
                    return merge(new ArrayList<ImageIcon>(Arrays.asList(getGridIcon(goban, x, y), p1, mp2)), new ArrayList<Float>(Arrays.asList(new Float(1), new Float(0.6), new Float(1))));
                case 2:
                    return merge(new ArrayList<ImageIcon>(Arrays.asList(getGridIcon(goban, x, y), p2, mp1)), new ArrayList<Float>(Arrays.asList(new Float(1), new Float(0.6), new Float(1))));
                default:
                    return null;
            }
        } else {
            return getPlayerIcon(player);
        }
    }
}
