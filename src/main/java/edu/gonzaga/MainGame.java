/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 * 
 * 
 * Contributors:
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;

import edu.gonzaga.events.EventExecutor;
import edu.gonzaga.events.EventManager;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Main program class for launching your team's program. */
public class MainGame {

    public static boolean hydra = false;

    public static final SoundThread sound = SoundThread.getInstance();

    public static EventManager manager;

    public static void main(String[] args) {
        Boolean isMuted = true; //temporary button to determine if sound muted

        EventExecutor executor = new EventExecutor();
        manager = new EventManager(executor);

        System.out.println("Hello Team Game");
        JFrame frame1 = new JFrame("Hydra?");

        //don't allow resize
        frame1.setSize(520, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width / 2 - frame1.getSize().width / 2, dim.height / 2 - frame1.getSize().height / 2);
        frame1.addWindowListener(CloseWindowListener.getInstance());
        if (hydra) {
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.addWindowListener(HydraListener.getInstance());
        } else {
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        frame1.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame1.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame1.getContentPane().add(BorderLayout.CENTER, centerLabel);

        frame1.setVisible(true);


        //Testing muting sound
        soundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (isMuted) {
                    sound.start();
                } else {
                    // Jake how do you stop sound
                }
            }
        });

        //when clicking playButton go to the player specify page
        //          -> should this be its own frame or should we just replace the components of the start menu
        //Testing taking in input
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String playerValue = playerField.getText();
                System.out.println(playerValue + " Players");

                String roundsValue = roundsField.getText();
                System.out.println(roundsValue + " Rounds");
            }
        });
    }
}
