package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class StartFrame {
    private Integer numPlayers;

    private Integer numRounds;
    private Boolean isRoundMode;
    ArrayList<Player> players;

    private final Integer DEFAULT_NUM_PLAYERS = 2;
    private final Integer DEFAULT_NUM_ROUNDS = 5;
    private final Integer MAX_NUM_PLAYERS = 7;
    private final Boolean DEFAULT_ROUND_MODE = true;

    private final JFrame frame;

    JPanel northPanel;

    JPanel startCenterPanel;
    JPanel nameCenterPanel;

    JPanel startSouthPanel;
    JPanel nameSouthPanel;

    JLabel northLabel = new JLabel("Eric Crandall Poker");

    JPanel settingsPanel;
    JMenuItem roundMode;
    JMenuItem bustMode;

    JButton nextButton = new JButton("Next");
    JButton backButton = new JButton("Back");
    JButton playButton = new JButton("Play Game");

    JLabel playersLabel = new JLabel("Players: ");
    JTextField playersField = new JTextField(1);

    // TODO: make hidden if playing until bust
    JLabel roundsLabel = new JLabel("Rounds: ");
    JTextField roundsField = new JTextField(1);

    ArrayList<StartPlayerPanel> startPlayerPanels = new ArrayList<>();

    // TODO: 12/1/2023 Add this to frame...
    JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) SoundThread.DEFAULT_VOLUME);

    public StartFrame(ArrayList<Player> players) {
        this.numPlayers = DEFAULT_NUM_PLAYERS;
        this.numRounds = DEFAULT_NUM_ROUNDS;
        this.isRoundMode = DEFAULT_ROUND_MODE;
        this.players = players;
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Integer getNumRounds() {
        return this.numRounds;
    }

    public Boolean getIsRoundMode() {
        return this.isRoundMode;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
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

    // TODO: preserve player data when going back
    private void setupNameFrame() {
        players = new ArrayList<>();
        startPlayerPanels = new ArrayList<>();

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
        northLabel.setFont(northLabel.getFont().deriveFont(18.0f));
        newPanel.add(northLabel);

        return newPanel;
    }

    // TODO: add image for center of start frame
    private JPanel genStartCenterPanel() {
        JPanel newPanel = new JPanel();
        JLabel centerLabel = new JLabel("placeholder");

        newPanel.add(centerLabel);

        return newPanel;
    }

    // TODO: have panels display top down instead of evenly displaced
    private JPanel genNameCenterPanel() {

        JPanel newPanel = new JPanel(new GridLayout(7, 1));
        //JPanel newPanel = new JPanel(new GridLayout(MAX_NUM_PLAYERS, 1));
        for (int i = 0; i < numPlayers; i++) {
            StartPlayerPanel panel = startPlayerPanels.get(i);
            newPanel.add(panel.getPanel());
        }

        return newPanel;
    }

    private JPanel genStartSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

        JMenuBar settingsBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        roundMode = new JMenuItem("Play by Rounds");
        bustMode = new JMenuItem("Play Until Bust");

        settingsMenu.add(roundMode);
        settingsMenu.add(bustMode);
        settingsBar.add(settingsMenu);

        volumeSlider.setPreferredSize(new Dimension(100, 16));
        volumeSlider.setBorder(BorderFactory.createLineBorder(Color.gray));

        settingsPanel = new JPanel();

        settingsPanel.add(settingsBar);
        settingsPanel.add(playersLabel);
        settingsPanel.add(playersField);
        settingsPanel.add(roundsLabel);
        settingsPanel.add(roundsField);

        settingsPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        nextButton.setPreferredSize(new Dimension(100, 26));
        nextButton.setBorder(BorderFactory.createLineBorder(Color.gray));

        newPanel.add(BorderLayout.WEST, volumeSlider);
        newPanel.add(BorderLayout.CENTER, settingsPanel);
        newPanel.add(BorderLayout.EAST, nextButton);

        Dimension d = newPanel.getPreferredSize();
        newPanel.setPreferredSize(new Dimension(d.width, 35));

        return newPanel;
    }

    private JPanel genNameSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

        JPanel playButtonPanel = new JPanel();
        playButton.setPreferredSize(new Dimension(150, 26));
        playButtonPanel.add(playButton);
        playButtonPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        backButton.setPreferredSize(new Dimension(100, 26));
        backButton.setBorder(BorderFactory.createLineBorder(Color.gray));

        newPanel.add(BorderLayout.WEST, volumeSlider);
        newPanel.add(BorderLayout.CENTER, playButtonPanel);
        newPanel.add(BorderLayout.EAST, backButton);

        Dimension d = newPanel.getPreferredSize();
        newPanel.setPreferredSize(new Dimension(d.width, 35));

        return newPanel;
    }

    public void updateNumPlayers(Integer playersValue) {
        if (playersValue > MAX_NUM_PLAYERS) {
            this.numPlayers = MAX_NUM_PLAYERS;
        } else if (playersValue >= DEFAULT_NUM_PLAYERS) {
            this.numPlayers = playersValue;
        } else {
            this.numPlayers = DEFAULT_NUM_PLAYERS;
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

    private void addVolumeSliderHandler() {
        volumeSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            if (slider.getValue() == slider.getMinimum()) {
                SoundThread.getInstance().setVolume(0);
                return;
            }
            SoundThread.getInstance().setVolume(slider.getValue());
        });
    }

    private void addRoundModeHandler() {
        roundMode.addActionListener(ae -> {
            if (!isRoundMode) {
                isRoundMode = true;
                settingsPanel.add(roundsLabel);
                settingsPanel.add(roundsField);
                frame.validate();
                frame.repaint();
            }
        });
    }

    private void addBustModeHandler() {
        bustMode.addActionListener(ae -> {
            if (isRoundMode) {
                isRoundMode = false;
                settingsPanel.remove(roundsLabel);
                settingsPanel.remove(roundsField);
                frame.validate();
                frame.repaint();
            }
        });
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

    private void addBackButtonHandler() {
        backButton.addActionListener(ae -> {
            startCenterPanel = genStartCenterPanel();
            startSouthPanel = genStartSouthPanel();

            frame.getContentPane().remove(nameCenterPanel);
            frame.getContentPane().remove(nameSouthPanel);

            frame.getContentPane().add(BorderLayout.CENTER, startCenterPanel);
            frame.getContentPane().add(BorderLayout.SOUTH, startSouthPanel);

            frame.validate();
            frame.repaint();
        });
    }

    /*
     * private void addSoundButtonHandler() {
     * soundButton.addActionListener(ae -> {
     * SoundThread sound = SoundThread.getInstance();
     * if (sound.isPlaying()) {
     * sound.stopSong();
     * } else {
     * sound.startSong();
     * }
     * });
     * }
     */

    private void addPlayButtonHandler() {
        playButton.addActionListener(ae -> {
            for (int i = 0; i < numPlayers; i++) {
                StartPlayerPanel panel = startPlayerPanels.get(i);
                panel.updatePlayerName();
            }

            frame.setVisible(false);
            new GameFrame(this);
        });
    }

    private void initFrame(JFrame frame) {
        setupStartFrame();

        addVolumeSliderHandler();
        addRoundModeHandler();
        addBustModeHandler();

        addNextButtonHandler();
        addBackButtonHandler();
        addPlayButtonHandler();

        frame.setVisible(true);
    }
}
