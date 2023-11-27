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
            JButton cardButton = new JButton(cardImages.getFacedownImage());
            cardButton.setPreferredSize(new Dimension(60, 80));
            cardButtons.add(cardButton);
        }
    }

    //make a cardbutton class (works for river and for the show cards. 
    // they're not functional buttons but when (shown) == true display assosiated card value, else show back of card image)
    private void addShowCardButtonHandler() {
        showCardButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JButton button = (JButton)e.getSource();
                ButtonModel model = button.getModel();
                if (model.isPressed()) {
                    //cardButtons.get(0).setIcon(cardImages.getCardImage(player.getCardOne()));
                    //cardButtons.get(1).setIcon(cardImages.getCardImage(player.getCardTwo()));
                    cardButtons.get(0).setIcon(cardImages.getCardImage(player.getCardOne()));
                    cardButtons.get(1).setIcon(cardImages.getCardImage(player.getCardTwo()));
                } else {
                    cardButtons.get(0).setIcon(cardImages.getFacedownImage());
                    cardButtons.get(1).setIcon(cardImages.getFacedownImage());
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
