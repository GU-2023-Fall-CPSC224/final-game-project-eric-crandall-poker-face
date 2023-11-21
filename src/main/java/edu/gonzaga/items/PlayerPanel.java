package edu.gonzaga.items;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.util.ArrayList;

public class PlayerPanel {
    private Player player;

    JPanel panel = new JPanel();
    JLabel playerLabel;
    JButton showCardButton;

    //not functional buttons
    ArrayList<JButton> cardButtons;

    public PlayerPanel(Player player) {
        this.player = player;
        initFrame();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initCardButtons() {
        cardButtons = new ArrayList<>();
        for (Integer i = 0; i < 2; i++) {
            JButton cardButton = new JButton("hidden");
            cardButtons.add(cardButton);
        }
    }

    //make a cardbutton class (works for river and for the show cards. 
    // they're not functional buttons but when (shown) == true display assosiated card value, else show back of card image)

    private boolean cardsShown = false;
    private void addShowCardButtonHandler() {
        showCardButton.addChangeListener(e -> {
            JButton button = (JButton)e.getSource();
            ButtonModel model = button.getModel();
            if (model.isPressed()) {
                cardsShown = !cardsShown;
            }
            if (cardsShown) {
                cardButtons.get(0).setText("c1");
                cardButtons.get(1).setText("c2");
            } else {
                cardButtons.get(0).setText("hidden");
                cardButtons.get(1).setText("hidden");
            }
        });
    }

    public void toggleCardsShown(boolean showCards) {
        cardsShown = showCards;
        if (cardsShown) {
            cardButtons.get(0).setText("c1");
            cardButtons.get(1).setText("c2");
        } else {
            cardButtons.get(0).setText("hidden");
            cardButtons.get(1).setText("hidden");
        }
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
