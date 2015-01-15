package go.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by thomas on 15/01/15.
 */
public class GUI extends JFrame {
    protected int height;
    protected int width;

    // Game Menu
    protected JMenuBar jMenuBar;
    protected JMenu jMenuGame;
    protected JMenuItem jGameNew;
    protected JMenuItem jGameOpen;
    protected JMenuItem jGameSave;
    protected JMenuItem jGameExit;

    // Edit menu
    protected JMenu jMenuEdit;
    protected JMenuItem jEditUndo;
    protected JMenuItem jEditRedo;

    // About Menu
    protected JMenu jMenuAbout;
    protected JMenuItem jAboutInfo;

    public GUI() {
        this.width = 320;
        this.height = 240;

        this.initMenu();
        // Ajout du menu a la JFrame

        setJMenuBar(jMenuBar);
        this.initWindow();
        pack();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Go game");
        setPreferredSize(new Dimension(width, height));
        setResizable(false);
        setVisible(true);
    }

    protected void initMenu() {
        // JMenuBar
        jMenuBar = new JMenuBar();

        // Game Menu
        jMenuGame = new JMenu("Game");

        // New Game
        jGameNew = new JMenuItem("New");

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
        jEditRedo.setEnabled(false);
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
}