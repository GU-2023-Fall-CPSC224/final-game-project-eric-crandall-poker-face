package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class StartFrame {
    private Integer numPlayers;
    private Integer numRounds;
    private final Integer DEFAULT_NUM_PLAYERS = 2;
    private final Integer DEFAULT_NUM_ROUNDS = 5;
    private final JFrame frame;

    JPanel northPanel;

    JPanel startCenterPanel;
    JPanel nameCenterPanel;

    JPanel startSouthPanel;
    JPanel nameSouthPanel;

    JLabel northLabel = new JLabel("Eric Crandall Poker");

    JButton soundButton = new JButton("Sound");

    JButton nextButton = new JButton("Next");
    JButton playButton = new JButton("Play Game");

    JLabel playersLabel = new JLabel("Players: ");
    JTextField playersField = new JTextField(1);

    // TODO: make hidden if playing until bust
    JLabel roundsLabel = new JLabel("Rounds: ");
    JTextField roundsField = new JTextField(1);

    ArrayList<Player> players;
    ArrayList<StartPlayerPanel> startPlayerPanels = new ArrayList<>();

    public StartFrame(ArrayList<Player> players) {
        this.numPlayers = DEFAULT_NUM_PLAYERS;
        this.numRounds = DEFAULT_NUM_ROUNDS;
        this.players = players;
        this.frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private void setupStartFrame() {
        // don't allow resize
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

        // initialize starting frames
        northPanel = genNorthPanel();
        startCenterPanel = genStartCenterPanel();
        startSouthPanel = genStartSouthPanel();

        // add panels to frame
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, startCenterPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, startSouthPanel);
    }

    private void setupNameFrame() {
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player();

            // TODO: change to randomly generated name
            player.setName("Player " + (i + 1));
            players.add(player);

            StartPlayerPanel panel = new StartPlayerPanel(player);
            startPlayerPanels.add(panel);
        }

        nameCenterPanel = genNameCenterPanel();
        nameSouthPanel = genNameSouthPanel();

        frame.getContentPane().remove(startCenterPanel);
        frame.getContentPane().remove(startSouthPanel);

        frame.getContentPane().add(BorderLayout.CENTER, nameCenterPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, nameSouthPanel);

        frame.validate();
        frame.repaint();
    }

    private JPanel genNorthPanel() {
        JPanel newPanel = new JPanel();
        newPanel.add(northLabel);

        return newPanel;
    }

    private JPanel genStartCenterPanel() {
        JPanel newPanel = new JPanel();
        JLabel centerLabel = new JLabel("placeholder");

        newPanel.add(centerLabel);

        return newPanel;
    }

    // TODO: add image for center of start frame
    private JPanel genNameCenterPanel() {
        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < numPlayers; i++) {
            StartPlayerPanel panel = startPlayerPanels.get(i);
            newPanel.add(panel.getPanel());
        }

        return newPanel;
    }

    // TODO: add functionality for settings buttons
    private JPanel genStartSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

        JMenuBar settingsBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem roundMode = new JMenuItem("Play by Rounds");
        JMenuItem bustMode = new JMenuItem("Play Until Bust");

        settingsMenu.add(roundMode);
        settingsMenu.add(bustMode);
        settingsBar.add(settingsMenu);

        // panel for content set in center of south panel
        JPanel panel = new JPanel();

        // TODO: make playerLabel and playerField part of own panel
        panel.add(playersLabel);
        panel.add(playersField);

        // TODO: make roundsLabel and roundsField part of own panel
        panel.add(roundsLabel);
        panel.add(roundsField);
        panel.add(settingsBar);

        // TODO: soundButton and playButton should have space between self and border
        newPanel.add(BorderLayout.WEST, soundButton);
        newPanel.add(BorderLayout.CENTER, panel);
        newPanel.add(BorderLayout.EAST, nextButton);

        return newPanel;
    }

    private JPanel genNameSouthPanel() {
        JPanel newPanel = new JPanel();

        newPanel.add(soundButton);
        newPanel.add(playButton);

        return newPanel;
    }

    public void updateNumPlayers(Integer playersValue) {
        // TODO: add upper bounds
        if (playersValue > DEFAULT_NUM_PLAYERS) {
            this.numPlayers = playersValue;
        }
    }

    public void updateNumRounds(Integer roundsValue) {
        // TODO: add upper bounds
        if (roundsValue > 0) {
            this.numRounds = roundsValue;
        }
    }

    public Integer getIntegerValueOfInput(String s) {
        try {
            Integer i = Integer.valueOf(s);
            return i;
        }

        catch (NumberFormatException er) {
            return -1;
        }
    }

    // TODO: 11/13/2023 Pull object out of method and into variables with getters
    // and setters for better accessibility.

    private void addNextButtonHandler() {
        nextButton.addActionListener(ae -> {
            String playersInput = playersField.getText();
            updateNumPlayers(getIntegerValueOfInput(playersInput));

            String roundsInput = roundsField.getText();
            updateNumRounds(getIntegerValueOfInput(roundsInput));

            setupNameFrame();
        });
    }

    private void addSoundButtonHandler() {
        soundButton.addActionListener(ae -> {
            SoundThread sound = SoundThread.getInstance();
            if (sound.isPlaying()) {
                sound.stopSong();
            } else {
                sound.startSong();
            }
        });
    }

    private void addPlayButtonHandler() {
        playButton.addActionListener(ae -> {
            for (int i = 0; i < numPlayers; i++) {
                StartPlayerPanel panel = startPlayerPanels.get(i);
                panel.updatePlayerName();
            }

            new GameFrame(players);
        });
    }

    private void initFrame(JFrame frame) {
        setupStartFrame();

        addNextButtonHandler();
        addSoundButtonHandler();

        addPlayButtonHandler();

        frame.setVisible(true);
    }
}
