package edu.gonzaga.items;

import javax.swing.*;
import java.awt.*;

public class StartPlayerPanel {
    private Player player;

    JPanel panel;

    // TODO: either this panel or the player itself knows which player number it is
    JLabel playerNumberLabel = new JLabel("Player x:");
    JTextField playerNameField;

    // TODO: place in right of screen and when clicked, cycle through a list of available icons
    JButton placeholderPlayerIcon;

    public StartPlayerPanel(Player player) {
        this.player = player;
        panel = genPanel();
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

        newPanel.add(playerNamePanel);
        newPanel.add(placeholderPlayerIcon);

        return newPanel;
    }
}
