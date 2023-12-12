/** Class Name: EndFrame
 *  Desc: This Class creates a JFrame which displays end-game information such as winning player and each player's final chips. 
 *        Contains buttons to play again or quit the program. 
 *  Notes: Documented by Gabe Hoing
 */

package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndFrame {
    
    private final ArrayList<Player> players; // List of players 

    // End game GUI window and panels displaying its information 
    private final JFrame frame; 
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;

    // Widgets and buttons contained in various panels
    JLabel northLabel;
    JButton playButton = new JButton("Play Again");
    JButton exitButton = new JButton("Exit Game");


    /* Constructor For EndFrame
     * Sets list of players equal to list contained in accepted gameFrame object and initializes the frame. 
     */
    public EndFrame(GameFrame gameFrame) {
        this.players = gameFrame.getPlayers();
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    /* Method Name: getFrame()
     * Returns: A JFrame
     * Desc: Returns this.frame, the end-game GUI window.
     * Events: N/A
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /* Method Name: setupFrame()
     * Returns: N/A (void)
     * Desc: Sets up the GUI window's settings such as size and location, then generates the panels which display its information. 
     * Events: N/A
     */
    private void setupFrame() {
        frame.setSize(320, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.addWindowListener(CloseWindowListener.getInstance());
        if (MainGame.hydra) {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(HydraListener.getInstance());
        } else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        // intitialize panels
        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();
        this.southPanel = genSouthPanel();

        // add panels to start frame
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
    }

    /* Method Name: addButtonHandlers()
     * Returns: N/A (void)
     * Desc: Adds handlers to playButton and endButton
     * Events: N/A
     */
    private void addButtonHandlers() {
        // "Returns" to the starting window by creating a new StartFrame, then disposing of the current window.
        playButton.addActionListener(ae -> {
            new StartFrame(players);
            frame.dispose();
            SoundThread.getInstance().restartAudio();
        });

        // "Exits" the game by disposing the current window and then exiting the program. 
        exitButton.addActionListener(ae -> {
            frame.dispose();
            System.exit(0);
        });
    }

    /* Method Name: genNorthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the top of the screen. 
     * Events: N/A
     */
    private JPanel genNorthPanel() {
        JPanel newPanel = new JPanel();
        northLabel = new JLabel("Tie!");
        northLabel.setFont(northLabel.getFont().deriveFont(24.0f));

        Player winningPlayer = players.get(0);
        boolean isTie = false;
        for (int i = 1; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getChips() == winningPlayer.getChips()) {
                isTie = true;
            } else if (p.getChips() > winningPlayer.getChips()) {
                winningPlayer = p;
                isTie = false;
            }
        }

        if (!isTie) {
            northLabel.setText(winningPlayer.getName() + " Won!");
        }

        newPanel.add(northLabel);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return newPanel;
    }

    /* Method Name: genCenterPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the center of the screen. 
     * Events: N/A
     */
    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel(new GridLayout(7, 1));

        for (Player p : players) {
            JPanel panel = new JPanel();
            JLabel playerLabel = new JLabel(p.getName() + ":  " + p.getChips() + " Chips");
            playerLabel.setFont(playerLabel.getFont().deriveFont(18.0f));

            panel.add(playerLabel);
            newPanel.add(panel);
        }

        return newPanel;
    }

    /* Method Name: genSouthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the bottom of the screen. 
     * Events: N/A
     */
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel();

        playButton.setFont(playButton.getFont().deriveFont(16.0f));
        exitButton.setFont(exitButton.getFont().deriveFont(16.0f));

        newPanel.add(playButton);
        newPanel.add(exitButton);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return newPanel;
    }

    /* Method Name: initFrame()
     * Returns: A JPanel
     * Desc: Sets up this.frame and adds handlers before setting the accepted JFrame's visibility to true. 
     * Events: N/A
     */
    private void initFrame(JFrame frame) {
        setupFrame();
        addButtonHandlers();

        frame.setVisible(true);
    }
}
