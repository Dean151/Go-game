package go.gui;

import go.core.Goban;
import go.core.Main;
import go.core.StoneChain;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 15/01/15.
 */
public class GUI extends JFrame {
    private static final int TOKEN_INITIAL_SIZE = 35;
    private static final int MENU_SIZE = 40;

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
    public static final ImageIcon grid_p1_p = new ImageIcon(new ImageIcon("sprites/p1_p.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p2_p = new ImageIcon(new ImageIcon("sprites/p2_p.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p1_c = new ImageIcon(new ImageIcon("sprites/p1_c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));
    public static final ImageIcon grid_p2_c = new ImageIcon(new ImageIcon("sprites/p2_c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_SMOOTH));

    private Goban goban;

    private int height;
    private int width;

    // Game Menu
    private JMenuBar jMenuBar;
    private JMenu jMenuGame;
    private JMenuItem jGameNew;
    private JMenuItem jGameOpen;
    private JMenuItem jGameSave;
    private JMenuItem jGameExit;

    // Edit menu
    private JMenu jMenuEdit;
    private JMenuItem jEditUndo;
    private JMenuItem jEditRedo;
    private JMenuItem jEditPass;

    // About Menu
    private JMenu jMenuAbout;
    private JMenuItem jAboutInfo;

    // Goban
    private JPanel jGoban;
    private JButton[][] jIntersections;

    /**
     *
     * @param goban goban that correspond to the actual game
     */
    public GUI(Goban goban) {
        this.goban = goban;

        this.height = TOKEN_INITIAL_SIZE * goban.getHeight() + MENU_SIZE;
        this.width = TOKEN_INITIAL_SIZE * goban.getWidth();

        // Creating menus
        this.initMenu();

        // Adding menus to jFrame
        setJMenuBar(jMenuBar);

        // creating window
        this.initWindow();

        // printing the goban on screen
        this.drawGoban();

        pack();
    }

    public Goban getGoban() {
        return goban;
    }

    /**
     * Init the window
     */
    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Go game");
        setPreferredSize(new Dimension(width, height));
        setResizable(false);
        setVisible(true);
    }

    /**
     * Init the menus
     */
    protected void initMenu() {
        // JMenuBar
        jMenuBar = new JMenuBar();

        // Game Menu
        jMenuGame = new JMenu("Game");

        // New Game
        jGameNew = new JMenuItem("New");
        jGameNew.setAccelerator(KeyStroke.getKeyStroke("control N"));
        jGameNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String[] gobanSizeChoises = {"9x9", "13x13", "19x19"};
                String[] handicapChoises = new String[9];
                for (int i = 0; i < handicapChoises.length; i++) {
                    handicapChoises[i] = Integer.toString(i);
                }

                String gobanSizeString = (String) JOptionPane.showInputDialog(null, "Choose the size of Goban", "New Game", JOptionPane.QUESTION_MESSAGE, null, gobanSizeChoises, gobanSizeChoises[2]);
                String handicapString = (String) JOptionPane.showInputDialog(null, "Choose the number of initial black stones before black can play (0 to 8)", "New Game", JOptionPane.QUESTION_MESSAGE, null, handicapChoises, handicapChoises[0]);

                try {
                    int gobanSize = Integer.parseInt(gobanSizeString.split("x")[0]);
                    int handicap = Integer.parseInt(handicapString);
                    Main.newGame(gobanSize, handicap);
                    GUI.this.setVisible(false);
                    GUI.this.dispose();  // We close the actual window
                } catch (Exception ex) {

                }
            }
        });

        // Open
        jGameOpen = new JMenuItem("Open");
        jGameOpen.setAccelerator(KeyStroke.getKeyStroke("control O"));
        jGameOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser openFile = new JFileChooser(".");
                    openFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
                    //openFile.setAcceptAllFileFilterUsed(false);
                    if (openFile.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = openFile.getSelectedFile().getCanonicalPath();
                        GUI.this.setVisible(false);
                        GUI.this.dispose(); // We close the actual window

                        Main.loadGame(file);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Save
        jGameSave = new JMenuItem("Save");
        jGameSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
        jGameSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser saveFile = new JFileChooser(".");
                    saveFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
                    saveFile.setAcceptAllFileFilterUsed(false);
                    if (saveFile.showSaveDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = saveFile.getSelectedFile().getCanonicalPath();
                        //Putting the correct extension
                        file = file.replaceAll("\\.save?$","");
                        file = file + ".save";

                        if (!GUI.this.getGoban().getGameRecord().save(file)) {
                            // there were a problem saving the game
                            JOptionPane.showMessageDialog(GUI.this,
                                    "The game could not be saved, try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Exit
        jGameExit = new JMenuItem("Exit");
        jGameExit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        jGameExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crude but effective
                System.exit(0);
            }
        });

        // Edit menu
        jMenuEdit = new JMenu("Edit");

        // Undo
        jEditUndo = new JMenuItem("Undo");
        jEditUndo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        jEditUndo.setEnabled(false);
        jEditUndo.addActionListener(new UndoRedo(this, true));

        // Redo
        jEditRedo = new JMenuItem("Redo");
        jEditRedo.setAccelerator(KeyStroke.getKeyStroke("control shift Z"));
        jEditRedo.setEnabled(false);
        jEditRedo.addActionListener(new UndoRedo(this, false));

        // Pass
        jEditPass = new JMenuItem("Pass");
        jEditPass.setAccelerator(KeyStroke.getKeyStroke("control P"));
        jEditPass.setEnabled(true);
        jEditPass.addActionListener(new PassMove(this));


        // About menu
        jMenuAbout = new JMenu("About");
        jAboutInfo = new JMenuItem("Informations");
        jAboutInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this, "Thomas BUICK, Thomas DURAND et Loic MOLLET-PADIER\nVersion 0.3.14", "Informations", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // Adding items to game menu
        jMenuGame.add(jGameNew);
        jMenuGame.add(jGameOpen);
        jMenuGame.add(jGameSave);
        jMenuGame.add(jGameExit);

        // Adding items to edit menu
        jMenuEdit.add(jEditUndo);
        jMenuEdit.add(jEditRedo);
        jMenuEdit.add(jEditPass);

        // Adding items to about menu
        jMenuAbout.add(jAboutInfo);

        // Adding Menus to Bar
        jMenuBar.add(jMenuGame);
        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuAbout);
    }

    public void setUndoEnabled(boolean state) {
        jEditUndo.setEnabled(state);
    }

    public void setRedoEnabled(boolean state) {
        jEditRedo.setEnabled(state);
    }

    /**
     * Allow to know if the intersection should be marked on the goban
     * @param x x coord
     * @param y y coord
     * @return true if the intersection is marked
     */
    private boolean shouldBeSpot(int x, int y) {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();
        int offset = 3;
        if (gobanWidth < 10) offset = 2;

        return (x == offset || x == (gobanWidth-1)/2 || x == gobanWidth-offset-1) && (y == offset || y == (gobanHeight-1)/2 || y == gobanHeight-offset-1);
    }

    public ImageIcon getGridIcon(int x, int y) {
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
                if (shouldBeSpot(x, y)) {
                    return grid_spot;
                } else {
                    return grid_c;
                }
            }
        }
    }

    /**
     * Draw Goban
     */
    public void drawGoban() {
        // Central layout is a grid
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();

        jGoban = new JPanel(new GridLayout(gobanWidth,gobanHeight));

        // Creating the board
        jIntersections = new JButton[gobanWidth][gobanHeight];

        // Creating the buttons
        for(int x=0;x<gobanWidth;x++) {
            for(int y=0;y<gobanHeight;y++) {
                // creating button at [x,y]

                StoneChain chain = goban.getIntersection(x, y).getStoneChain();
                if (chain != null) {
                    if (chain.getOwner().getIdentifier() == 1) {
                        jIntersections[x][y] = new JButton(grid_p1);
                    } else {
                        jIntersections[x][y] = new JButton(grid_p2);
                    }
                } else {
                    jIntersections[x][y] = new JButton(getGridIcon(x, y));
                }

                jIntersections[x][y].setEnabled(true);
                jIntersections[x][y].setBorder(BorderFactory.createEmptyBorder());
                jIntersections[x][y].setContentAreaFilled(false);

                jIntersections[x][y].setMaximumSize(new java.awt.Dimension(100, 100));
                jIntersections[x][y].setMinimumSize(new java.awt.Dimension(10, 10));
                jIntersections[x][y].setPreferredSize(new java.awt.Dimension(TOKEN_INITIAL_SIZE, TOKEN_INITIAL_SIZE));

                // Adding action
                jIntersections[x][y].addActionListener(new PlayMove(this, x, y));
                jIntersections[x][y].addMouseListener(new HoverEffect(x,y,jIntersections[x][y],this));

                // Adding button
                jGoban.add(jIntersections[x][y],x,y);
            }
        }
        // Adding goban in Main content panel
        getContentPane().add(jGoban, BorderLayout.CENTER);
    }

    /**
     * Update goban
     */
    public void updateGoban() {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();
        for(int x=0;x<gobanWidth;x++) {
            for (int y = 0; y < gobanHeight; y++) {
                StoneChain sc = goban.getIntersection(x,y).getStoneChain();
                if (sc != null) {
                    if (sc.getOwner().getIdentifier() == 1) jIntersections[x][y].setIcon(grid_p1);
                    else jIntersections[x][y].setIcon(grid_p2);
                } else {
                    jIntersections[x][y].setIcon(getGridIcon(x,y));
                }
            }
        }
    }
}