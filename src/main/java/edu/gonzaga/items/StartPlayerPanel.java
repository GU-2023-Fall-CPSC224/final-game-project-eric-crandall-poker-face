package edu.gonzaga.items;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class StartPlayerPanel {
    private final Player player;

    JPanel panel;

    // TODO: either this panel or the player itself knows which player number it is
    JLabel playerIconLabel;
    JLabel playerNumberLabel = new JLabel("Player x:");
    JTextField playerNameField;

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
        if (!nameInput.isEmpty()) {
            player.setName(nameInput);
        }
    }

    private JPanel genPanel() {
        JPanel newPanel = new JPanel();

        playerNumberLabel = new JLabel(player.getName());

        playerNameField = new JTextField(15);

        JPanel playerNamePanel = new JPanel();
        playerNamePanel.add(playerNumberLabel);
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
