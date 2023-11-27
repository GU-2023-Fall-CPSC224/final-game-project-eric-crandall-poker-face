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
    ArrayList<JButton> cardButtons;
    CardImages cardImages;

    public PlayerPanel(Player player, CardImages cardImages) {
        this.player = player;
        this.cardImages = cardImages;
        initFrame();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initCardButtons() {
        cardButtons = new ArrayList<>();
        for (Integer i = 0; i < 2; i++) {
            JButton cardButton = new JButton(cardImages.getSmallFacedownImage());
            cardButton.setPreferredSize(new Dimension(45, 60));
            cardButtons.add(cardButton);
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
                    cardButtons.get(0).setIcon(cardImages.getSmallCardImage(player.getCardOne()));
                    cardButtons.get(1).setIcon(cardImages.getSmallCardImage(player.getCardTwo()));
                } else {
                    //playerhand.setVisible(false)
                    cardButtons.get(0).setIcon(cardImages.getSmallFacedownImage());
                    cardButtons.get(1).setIcon(cardImages.getSmallFacedownImage());
                }
            }
        });
    }

    private void initFrame() {
        playerLabel = new JLabel(player.getName());
        showCardButton = new JButton("Show Cards");
        initCardButtons();

        panel.add(playerLabel);
        panel.add(showCardButton);
        panel.add(cardButtons.get(0));
        panel.add(cardButtons.get(1));
        addShowCardButtonHandler();
    }
}
