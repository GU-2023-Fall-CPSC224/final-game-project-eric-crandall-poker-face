/** Class Name: StartPlayerPanel
 *  Desc: This Class creates a single-line panel which displays default values of players for use in StartFrame.
 *        Contains the player's default name and icon, and a text field for giving them a custom name. 
 *  Notes: Documented by Gabe Hoing
 */

package edu.gonzaga.items;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class StartPlayerPanel { 
    private final Player player; // Player associated with this StartPlayerPanel

    JPanel panel; // JPanel representing this StartPlayerPanel

    JLabel playerIconLabel; // Label displaying the player's icon
    JLabel playerNameLabel; // Label displaying the player's name
    JTextField playerNameField; // Field for changing the player's name 

    /* Constructor For StartPlayerPanel
     * Initializes the player to the accepted Player object and generates the panel. 
     */
    public StartPlayerPanel(Player player) {
        this.player = player;
        panel = genPanel();
    }

    /* Method Name: getPanel()
     * Returns: A JPanel
     * Desc: Returns this.panel, the JPanel representation of StartPlayerPanel.
     * Events: N/A
     */
    public JPanel getPanel() {
        return panel;
    }

    /* Method Name: updatePlayerName()
     * Returns: N/A (void)
     * Desc: Updates this.player's name to playerNameField's text field value if the field is not empty. 
     * Events: N/A
     */
    public void updatePlayerName() {
        String nameInput = playerNameField.getText();
        if (!nameInput.isEmpty()) {
            player.setName(nameInput);
        }
    }

    /* Method Name: genPanel()
     * Returns: A JPanel
     * Desc: Generates and returns a JPanel representation of StartPlayerPanel by initializing necessary widgets and stylizing. 
     * Events: N/A
     */
    private JPanel genPanel() {
        JPanel newPanel = new JPanel();

        playerNameLabel = new JLabel(player.getName());

        playerNameField = new JTextField(15);

        JPanel playerNamePanel = new JPanel();
        playerNamePanel.add(playerNameLabel);
        playerNamePanel.add(playerNameField);

       
        BufferedImage newIcon = this.player.getIcon();
        Image Cimg = newIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(Cimg);

        playerIconLabel = new JLabel(scaledImage);

        newPanel.add(playerIconLabel);
        newPanel.add(playerNamePanel);

        return newPanel;
    }
}
