package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;

public class StartFrame {

    private final JFrame frame;

    public StartFrame() {
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    // TODO: 11/13/2023 Pull object out of method and into variables with getters and setters for better accessibility. 

    private void initFrame(JFrame frame) {
        //don't allow resize
        frame.setSize(520, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.addWindowListener(CloseWindowListener.getInstance());
        if (MainGame.hydra) {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(HydraListener.getInstance());
        } else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        //north panel
        JPanel northPanel = new JPanel();
        JLabel word = new JLabel("Eric Crandall Poker");

        northPanel.add(word);


        //south panel
        JPanel southPanel = new JPanel(new BorderLayout());

        JButton soundButton = new JButton("Sound");
        JButton playButton = new JButton("Play Game");

        JLabel playerLabel = new JLabel("Players: ");
        JTextField playerField = new JTextField(1);

        //make hidden if playing until bust
        JLabel roundsLabel = new JLabel("Rounds: ");
        JTextField roundsField = new JTextField(1);

        JMenuBar settingsBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem roundMode = new JMenuItem("Play by Rounds");
        JMenuItem bustMode = new JMenuItem("Play Until Bust");

        settingsMenu.add(roundMode);
        settingsMenu.add(bustMode);
        settingsBar.add(settingsMenu);

        //panel for content set in center of south panel
        JPanel southPanel2 = new JPanel();

        //make playerLabel and playerField part of own panel
        southPanel2.add(playerLabel);
        southPanel2.add(playerField);

        //make roundsLabel and roundsField part of own panel
        southPanel2.add(roundsLabel);
        southPanel2.add(roundsField);
        southPanel2.add(settingsBar);

        //soundButton and playButton should have space between seld and border
        southPanel.add(BorderLayout.WEST, soundButton);
        southPanel.add(BorderLayout.CENTER, southPanel2);
        southPanel.add(BorderLayout.EAST, playButton);


        //placeholder button for center
        JLabel centerLabel = new JLabel("placeholder");


        //add panels to frame
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerLabel);

        frame.setVisible(true);


        //Testing muting sound
        soundButton.addActionListener(ae -> {
            SoundThread sound = SoundThread.getInstance();
            if (sound.isPlaying()) {
                sound.stopSong();
            } else {
                sound.startSong();
            }
        });

        //when clicking playButton go to the player specify page
        //          -> should this be its own frame or should we just replace the components of the start menu
        //Testing taking in input
        playButton.addActionListener(ae -> {
            String playerValue = playerField.getText();
            System.out.println(playerValue + " Players");

            String roundsValue = roundsField.getText();
            System.out.println(roundsValue + " Rounds");
        });
    }
}
