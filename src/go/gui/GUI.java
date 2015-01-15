package go.gui;

import go.core.Goban;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * Created by thomas on 15/01/15.
 */
public class GUI extends JFrame {
    private static final int TOKEN_SIZE = 35;
    private static final int MENU_SIZE = 60;

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

    /**
     *
     * @param goban goban that correspond to the actual game
     */
    public GUI(Goban goban) {
        this.goban = goban;

        this.height = TOKEN_SIZE * goban.getHeight() + MENU_SIZE;
        this.width = TOKEN_SIZE * goban.getWidth();

        // Creating menus
        this.initMenu();

        // Adding menus to jFrame
        setJMenuBar(jMenuBar);

        // creating window
        this.initWindow();

        // printing the goban on screen
        this.reloadGoban();

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

    private void reloadGoban() {
        // TODO draw goban in window
    }
}