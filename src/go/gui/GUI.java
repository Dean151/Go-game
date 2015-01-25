package go.gui;

import go.core.Goban;
import go.core.Intersection;
import go.core.Main;
import go.core.StoneChain;
import go.score.Island;
import go.score.Scorer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 15/01/15.
 */
public class GUI extends JFrame {
    public static final int TOKEN_INITIAL_SIZE = 35;
    private static final int MENU_SIZE = 42;

    private Goban goban;
    private Scorer scorer;

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

    // Score Menu
    private JMenu jMenuScore;
    private JMenuItem jScoreHelp;
    private JMenuItem jScoreValidate;

    // Goban
    private JPanel jGoban;
    private JButton[][] jIntersections;

    /**
     *
     * @param goban goban that correspond to the actual game
     */
    public GUI(Goban goban) {
        this.goban = goban;
        this.scorer = new Scorer(goban);

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

    /**
     *
     * @return associated goban
     */
    public Goban getGoban() {
        return goban;
    }

    public Scorer getScorer() {
        return scorer;
    }

    public void initScorer() {
        scorer.init();
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
                    // TODO support exception
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

        // Optional counting menu
        jMenuScore = new JMenu("Score");
        jScoreHelp = new JMenuItem("Help");
        jScoreHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this, "To get the score of the game please click on dead stone chains inside an enemy's territory.\n" +
                                                        "Clicking a StoneChain toggles it's 'life' status.\n"+
                                                        "Once this user input complete, please use the validate command","Score Help", JOptionPane.PLAIN_MESSAGE);
            }
        });
        jScoreHelp.setEnabled(false);
        jScoreValidate = new JMenuItem("Validate");
        jScoreValidate.setAccelerator(KeyStroke.getKeyStroke("control V"));
        jScoreValidate.addActionListener(new ValidateScore(this));
        jScoreValidate.setEnabled(false);
        jScoreValidate.setEnabled(false);
        jMenuScore.add(jScoreHelp);
        jMenuScore.add(jScoreValidate);

        // Adding Menus to Bar
        jMenuBar.add(jMenuGame);
        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuAbout);
        jMenuBar.add(jMenuScore);
    }

    public void setUndoEnabled(boolean state) {
        jEditUndo.setEnabled(state);
    }

    public void setRedoEnabled(boolean state) {
        jEditRedo.setEnabled(state);
    }

    public void setPassEnabled(boolean state) {
        jEditPass.setEnabled(state);
    }

    public void setScoreEnabled(boolean state) {
        jScoreValidate.setEnabled(state);
        jScoreHelp.setEnabled(state);
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
                    jIntersections[x][y] = new JButton(Sprite.getPlayerIcon(chain.getOwner().getIdentifier()));
                } else {
                    jIntersections[x][y] = new JButton(Sprite.getGridIcon(goban, x, y, 0));
                }

                jIntersections[x][y].setEnabled(true);
                jIntersections[x][y].setBorder(BorderFactory.createEmptyBorder());
                jIntersections[x][y].setContentAreaFilled(false);

                jIntersections[x][y].setPreferredSize(new java.awt.Dimension(TOKEN_INITIAL_SIZE, TOKEN_INITIAL_SIZE));

                // Adding action
                jIntersections[x][y].addActionListener(new PlayMove(this, x, y));
                jIntersections[x][y].addMouseListener(new HoverEffect(x, y, jIntersections[x][y],this));

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
                    jIntersections[x][y].setIcon(Sprite.getPlayerIcon(sc.getOwner().getIdentifier()));
                } else {
                    jIntersections[x][y].setIcon(Sprite.getGridIcon(goban, x, y, 0));
                }
            }
        }
    }

    public void updateScore(StoneChain chain) {
        int owner;
        scorer.flipDeathStatus(chain);
        for(Island island : scorer.getIslands() ) {
            if(island.getOwner()==null) {
                owner = 0;
            } else {
                owner = island.getOwner().getIdentifier();
            }
            for(Intersection cross : island.getIntersections()) {
                jIntersections[cross.getX()][cross.getY()].setIcon(Sprite.getGridIcon(goban, cross.getX(),cross.getY(),owner));
            }
        }
        for(StoneChain lchain : scorer.getDeadStones()) {
            owner = lchain.getOwner().getIdentifier();
            for(Intersection cross : lchain.getStones()) {
                jIntersections[cross.getX()][cross.getY()].setIcon(Sprite.getPlayerIcon(owner, 3-owner, goban, cross.getX(), cross.getY()));
            }
        }
        for(StoneChain lchain : scorer.getAliveStones()) {
            owner = lchain.getOwner().getIdentifier();
            for(Intersection cross : lchain.getStones()) {
                jIntersections[cross.getX()][cross.getY()].setIcon(Sprite.getPlayerIcon(owner));
            }
        }
    }
}