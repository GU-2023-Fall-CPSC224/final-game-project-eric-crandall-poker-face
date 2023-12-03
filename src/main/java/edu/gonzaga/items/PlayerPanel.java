package edu.gonzaga.items;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.util.ArrayList;
import edu.gonzaga.utils.CardImages;

public class PlayerPanel {
    private Player player;

    JPanel panel = new JPanel();
    JLabel playerLabel;
    JButton showCardButton;

    //not functional buttons
    ArrayList<JLabel> cardLabels;
    CardImages cardImages;

    public PlayerPanel(Player player, CardImages cardImages) {
        this.player = player;
        this.cardImages = cardImages;
        genPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initCardButtons() {
        cardLabels = new ArrayList<>();
        for (Integer i = 0; i < 2; i++) {
            JLabel cardLabel = new JLabel(cardImages.getSmallFacedownImage());
            cardLabel.setPreferredSize(new Dimension(45, 60));
            cardLabels.add(cardLabel);
        }
    }

    //implement with hand class
    private void addShowCardButtonHandler() {
        showCardButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JButton button = (JButton)e.getSource();
                ButtonModel model = button.getModel();
                if (model.isPressed()) {
                    //playerhand.setVisible(true)
                    cardLabels.get(0).setIcon(cardImages.getSmallCardImage(player.getCardOne()));
                    cardLabels.get(1).setIcon(cardImages.getSmallCardImage(player.getCardTwo()));
                } else {
                    //playerhand.setVisible(false)
                    cardLabels.get(0).setIcon(cardImages.getSmallFacedownImage());
                    cardLabels.get(1).setIcon(cardImages.getSmallFacedownImage());
                }
            }
        });
    }

    private void genPanel() {
        playerLabel = new JLabel(player.getName());
        showCardButton = new JButton("Show Cards");
        initCardButtons();

        panel.add(playerLabel);
        panel.add(showCardButton);
        panel.add(cardLabels.get(0));
        panel.add(cardLabels.get(1));

        addShowCardButtonHandler();
    }
}
