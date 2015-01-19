package go.gui;

import go.core.Goban;

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
    private ImageIcon grid_ul = new ImageIcon(new ImageIcon("sprites/ul.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_u = new ImageIcon(new ImageIcon("sprites/u.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_ur = new ImageIcon(new ImageIcon("sprites/ur.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_l = new ImageIcon(new ImageIcon("sprites/l.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_c = new ImageIcon(new ImageIcon("sprites/c.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_spot = new ImageIcon(new ImageIcon("sprites/spot.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_r = new ImageIcon(new ImageIcon("sprites/r.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_bl = new ImageIcon(new ImageIcon("sprites/bl.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_b = new ImageIcon(new ImageIcon("sprites/b.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_br = new ImageIcon(new ImageIcon("sprites/br.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_p1 = new ImageIcon(new ImageIcon("sprites/p1.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));
    private ImageIcon grid_p2 = new ImageIcon(new ImageIcon("sprites/p2.png").getImage().getScaledInstance(TOKEN_INITIAL_SIZE,TOKEN_INITIAL_SIZE, Image.SCALE_FAST));

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
        this.loadGoban();

        pack();
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
        jGameNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO request goban size to user and set the handicap
                // TODO create new game
            }
        });

        // Open
        jGameOpen = new JMenuItem("Open");
        jGameOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser openFile = new JFileChooser(".");
                    openFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
                    openFile.setAcceptAllFileFilterUsed(false);
                    if (openFile.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = openFile.getSelectedFile().getCanonicalPath();
                        System.out.println(file);
                        dispose(); // We close the actual window

                        // TODO Open loaded game
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Save
        jGameSave = new JMenuItem("Save");
        jGameSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser saveFile = new JFileChooser(".");
                    saveFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
                    saveFile.setAcceptAllFileFilterUsed(false);
                    if (saveFile.showSaveDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
                        String file = saveFile.getSelectedFile().getCanonicalPath();

                        // TODO Save actual game
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Exit
        jGameExit = new JMenuItem("Exit");
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
        jEditUndo.setEnabled(false);
        jEditUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO undo last action
                // TODO activate redo button
                // TODO desactivate undo button if no action left to undo
            }
        });

        // Redo
        jEditRedo = new JMenuItem("Redo");
        jEditRedo.setEnabled(false);
        jEditRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO redo last action
                // TODO desactivate redo button if no action left to redo
            }
        });

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

        // Adding items to about menu
        jMenuAbout.add(jAboutInfo);

        // Adding Menus to Bar
        jMenuBar.add(jMenuGame);
        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuAbout);
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
        if (gobanWidth == 9) offset = 2;

        return (x == offset || x == (gobanWidth-1)/2 || x == gobanWidth-offset-1) && (y == offset || y == (gobanHeight-1)/2 || y == gobanHeight-offset-1);
    }


    /**
     * Load Goban
     */
    private void loadGoban() {
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
                if (x == gobanWidth - 1) {
                    if (y == 0) {
                        jIntersections[x][y] = new JButton(grid_ul);
                    } else if (y == gobanHeight - 1) {
                        jIntersections[x][y] = new JButton(grid_ur);
                    } else {
                        jIntersections[x][y] = new JButton(grid_u);
                    }
                } else if (x == 0) {
                    if (y == 0) {
                        jIntersections[x][y] = new JButton(grid_bl);
                    } else if (y == gobanHeight - 1) {
                        jIntersections[x][y] = new JButton(grid_br);
                    } else {
                        jIntersections[x][y] = new JButton(grid_b);
                    }
                } else {
                    if (y == 0) {
                        jIntersections[x][y] = new JButton(grid_l);
                    } else if (y == gobanHeight - 1) {
                        jIntersections[x][y] = new JButton(grid_r);
                    } else {
                        if (shouldBeSpot(x,y)) {
                            jIntersections[x][y] = new JButton(grid_spot);
                        } else {
                            jIntersections[x][y] = new JButton(grid_c);
                        }
                    }
                }

                jIntersections[x][y].setEnabled(true);
                jIntersections[x][y].setBorder(BorderFactory.createEmptyBorder());
                jIntersections[x][y].setContentAreaFilled(false);

                jIntersections[x][y].setMaximumSize(new java.awt.Dimension(100, 100));
                jIntersections[x][y].setMinimumSize(new java.awt.Dimension(10, 10));
                jIntersections[x][y].setPreferredSize(new java.awt.Dimension(TOKEN_INITIAL_SIZE, TOKEN_INITIAL_SIZE));

                // Adding button
                jGoban.add(jIntersections[x][y],x,y);
            }
        }
        // Adding goban in Main content panel
        getContentPane().add(jGoban, BorderLayout.CENTER);
    }
}