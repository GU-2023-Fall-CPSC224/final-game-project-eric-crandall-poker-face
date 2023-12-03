package edu.gonzaga.items;

import edu.gonzaga.utils.PlayerIcons;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class StartPlayerPanel {
    private Player player;
    private PlayerIcons playerIcons;

    JPanel panel;

    // TODO: either this panel or the player itself knows which player number it is
    JLabel playerIconLabel;
    JLabel playerNumberLabel = new JLabel("Player x:");
    JTextField playerNameField;

    // TODO: place in right of screen and when clicked, cycle through a list of available icons
    JButton placeholderPlayerIcon;

    public StartPlayerPanel(Player player) {
        this.player = player;
        panel = genPanel();
        this.playerIcons = new PlayerIcons();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updatePlayerName() {
        //TODO: add checking for valid player names;
        String nameInput = playerNameField.getText();
        player.setName(nameInput);
    }

    private JPanel genPanel() {
        JPanel newPanel = new JPanel();

        // TODO: randomly generate name for each player to start
        playerNameField = new JTextField(15);
        playerNameField.setText(player.getName());

        JPanel playerNamePanel = new JPanel();
        playerNamePanel.add(playerNumberLabel);
        playerNamePanel.add(playerNameField);

        placeholderPlayerIcon = new JButton("Icon");
       
        // BufferedImage newIcon = this.playerIcons.getIcons().get(0);
        // Image Cimg = newIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        // ImageIcon scaledImage = new ImageIcon(Cimg);

        // playerIconLabel = new JLabel(scaledImage);

        // newPanel.add(playerIconLabel);
        newPanel.add(playerNamePanel);
        newPanel.add(placeholderPlayerIcon);

        return newPanel;
    }
}
