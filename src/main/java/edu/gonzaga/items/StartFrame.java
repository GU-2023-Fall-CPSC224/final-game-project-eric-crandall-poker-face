/** Class Name: StartFrame
 *  Desc: This Class creates a JFrame which displays pre-game information and settings. Contains two "sections" of information: 
 *        Start and Name sections. Start section is where game version and player/round numbers are determined. Name section is 
 *        Where player names are determined. 
 *  Notes: Documented by Gabe Hoing
 */

package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.PlayerIcons;
import edu.gonzaga.utils.SoundThread;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartFrame {
    // Variables representing player-determined game values.
    private Integer numPlayers;
    private Integer numRounds;
    private Boolean isRoundMode;

    // Default and maximum values for the game.
    private final Integer DEFAULT_NUM_PLAYERS = 2;
    private final Integer DEFAULT_NUM_ROUNDS = 5;
    private final Integer MAX_NUM_PLAYERS = 7;
    private final Boolean DEFAULT_ROUND_MODE = true;

    
    private ArrayList<Player> players; // List of player objects.

    private final JFrame frame; // Pre-game GUI window.

    // JPanels displaying the GUI window's information.
    JPanel northPanel;

    JPanel startCenterPanel;
    JPanel nameCenterPanel;

    JPanel startSouthPanel;
    JPanel nameSouthPanel;

    // Widgets contained within the panel at the top of the screen. 
    JLabel northLabel = new JLabel("Eric Crandall Poker");

    // Widgets contained within the panel at the bottom of the screen's Start section. 
    JPanel settingsPanel;
    JMenuItem roundMode;
    JMenuItem bustMode;
    JButton nextButton = new JButton("Next");
    JButton backButton = new JButton("Back");
    JButton playButton = new JButton("Play Game");
    JLabel playersLabel = new JLabel("Players: ");
    JTextField playersField = new JTextField(1);
    JLabel roundsLabel = new JLabel("Rounds: ");
    JTextField roundsField = new JTextField(1);

    ArrayList<StartPlayerPanel> startPlayerPanels = new ArrayList<>(); // List of StartPlayerPanels which display associated player information of the Name section. 

    JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 35, 100, (int) SoundThread.DEFAULT_VOLUME); // Slider controlling the volume.

    /* Constructor For Player
     * Sets setting variables to default values and sets player list to accepted value, then initializes the GUI window. 
     */
    public StartFrame(ArrayList<Player> players) {
        this.numPlayers = DEFAULT_NUM_PLAYERS;
        this.numRounds = DEFAULT_NUM_ROUNDS;
        this.isRoundMode = DEFAULT_ROUND_MODE;
        this.players = players;
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    /* Method Name: getFrame()
     * Returns: A JFrame
     * Desc: Returns this.frame, the pre-game GUI window.
     * Events: N/A
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /* Method Name: getNumRounds()
     * Returns: The selected number of rounds. 
     * Desc: Returns this.numRounds, the number of rounds to be played to. 
     * Events: N/A
     */
    public Integer getNumRounds() {
        return this.numRounds;
    }

    /* Method Name: getIsRoundMode()
     * Returns: Whether the game will be round or bust mode.
     * Desc: Returns this.isRoundMode, representing which game mode is selected. 
     * Events: N/A
     */
    public Boolean getIsRoundMode() {
        return this.isRoundMode;
    }

    /* Method Name: getPlayers()
     * Returns: An ArrayList of Player objects. 
     * Desc: Returns the list of players who will be playing the game. 
     * Events: N/A
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /* Method Name: setupFrame()
     * Returns: N/A (void)
     * Desc: Sets up the GUI window's settings such as size and location.
     *       Then generates the panels which display information of the start section.
     * Events: N/A
     */
    private void setupStartFrame() {
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

    /* Method Name: setupNameFrame()
     * Returns: N/A (void)
     * Desc: Generates the panels which contain information for the Name section, adding these to the GUI window. 
     * Events: N/A
     */
    private void setupNameFrame() {
        players = new ArrayList<>();
        startPlayerPanels = new ArrayList<>();
        PlayerIcons icons = new PlayerIcons();

        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player();
            
            player.setIcon(icons.getIcons().get(i));
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

    /* Method Name: genNorthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the top of the screen. 
     * Events: N/A
     */
    private JPanel genNorthPanel() {
        JPanel newPanel = new JPanel();
        northLabel.setFont(northLabel.getFont().deriveFont(18.0f));
        newPanel.add(northLabel);

        return newPanel;
    }

    /* Method Name: genStartCenterPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the center of the Start section's screen. 
     * Events: N/A
     */
    private JPanel genStartCenterPanel() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("media/playerIcons/EricCrandall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel newPanel = new JPanel();
        JLabel centerLabel = null;
        if (image != null) {
            centerLabel = new JLabel(new ImageIcon(image));
        }

        newPanel.add(centerLabel);

        return newPanel;
    }

    /* Method Name: genNameCenterPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the center of the Name section's screen. 
     * Events: N/A
     */
    private JPanel genNameCenterPanel() {
        JPanel newPanel = new JPanel(new GridLayout(MAX_NUM_PLAYERS, 1));
        for (int i = 0; i < numPlayers; i++) {
            StartPlayerPanel panel = startPlayerPanels.get(i);
            newPanel.add(panel.getPanel());
        }

        return newPanel;
    }

    /* Method Name: genStartSouthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the bottom of the Start section's screen. 
     * Events: N/A
     */
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

    /* Method Name: genNameSouthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the bottom of the Name section's screen. 
     * Events: N/A
     */
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

    /* Method Name: updateNumPlayers()
     * Returns: N/A (void)
     * Desc: Updates this.numPlayer's value to the accepted integer value if it is valid
     * Events: N/A
     */
    public void updateNumPlayers(Integer playersValue) {
        if (playersValue > MAX_NUM_PLAYERS) {
            this.numPlayers = MAX_NUM_PLAYERS;
        } else if (playersValue >= DEFAULT_NUM_PLAYERS) {
            this.numPlayers = playersValue;
        } else {
            this.numPlayers = DEFAULT_NUM_PLAYERS;
        }
    }

    /* Method Name: updateNumRounds())
     * Returns: N/A (void)
     * Desc: Updates this.numRounds's value to the accepted integer value if it is valid
     * Events: N/A
     */
    public void updateNumRounds(Integer roundsValue) {
        if (roundsValue > 0) {
            this.numRounds = roundsValue;
        }
    }

    /* Method Name: getIntegerValueOfInput()
     * Returns: Integer value of String
     * Desc: Returns the integer value of an accepted String
     * Events: N/A
     */
    public Integer getIntegerValueOfInput(String s) {
        try {
            return Integer.valueOf(s);
        }
        catch (NumberFormatException er) {
            return -1;
        }
    }

    /* Method Name: addVolumeSliderHandler()
     * Returns: N/A (void)
     * Desc: Adds a change listener to volumeSlider which sets the SoundThread's volume equal to slider's current value.
     * Events: N/A
     */
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

    /* Method Name: addButtonHandlers()
     * Returns: N/A (void)
     * Desc: Adds Action Listeners to the various buttons used in the pre-game GUI window. 
     * Events: N/A
     */
    private void addButtonHandlers() {
        // Add Action Listener to roundMode toggle button
        roundMode.addActionListener(ae -> {
            if (!isRoundMode) {
                isRoundMode = true;
                settingsPanel.add(roundsLabel);
                settingsPanel.add(roundsField);
                frame.validate();
                frame.repaint();
            }
        });
    
        // Add Action Listener to bustMode toggle button
        bustMode.addActionListener(ae -> {
            if (isRoundMode) {
                isRoundMode = false;
                settingsPanel.remove(roundsLabel);
                settingsPanel.remove(roundsField);
                frame.validate();
                frame.repaint();
            }
        });
        
        // Add Action Listener to nextButton
        nextButton.addActionListener(ae -> {
            String playersInput = playersField.getText();
            updateNumPlayers(getIntegerValueOfInput(playersInput));

            String roundsInput = roundsField.getText();
            updateNumRounds(getIntegerValueOfInput(roundsInput));

            setupNameFrame();
        });
   
        // Add Action Listener to backButton
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

        // Add Action Listener to playButton
        playButton.addActionListener(ae -> {
            for (int i = 0; i < numPlayers; i++) {
                StartPlayerPanel panel = startPlayerPanels.get(i);
                panel.updatePlayerName();
            }

            frame.setVisible(false);
            new GameFrame(this);
        });
    }  

    /* Method Name: initFrame()
     * Returns: A JPanel
     * Desc: Sets up this.frame and adds handlers before setting the accepted JFrame's visibility to true. 
     * Events: N/A
     */
    private void initFrame(JFrame frame) {
        setupStartFrame();

        addVolumeSliderHandler();
        addButtonHandlers();

        frame.setVisible(true);
    }
}
