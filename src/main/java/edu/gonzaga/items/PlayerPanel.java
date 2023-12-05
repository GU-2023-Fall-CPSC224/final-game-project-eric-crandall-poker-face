package edu.gonzaga.items;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import edu.gonzaga.utils.CardImages;

public class PlayerPanel {
    private Player player;

    private JPanel panel = new JPanel();
    private JLabel scoreLabel;
    private JButton showCardButton;

    // not functional buttons
    private ArrayList<JLabel> cardLabels;
    private CardImages cardImages;
    private Dimension smallCardDimension;

    public PlayerPanel(Player player, CardImages cardImages) {
        this.player = player;
        this.cardImages = cardImages;
        this.smallCardDimension = cardImages.getSmallImageDimension();
        genPanel();
        
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updateScoreLabel() {
        scoreLabel.setText("Score: " + player.getChips() + " Chips");
    }

    private void initCardLabels() {
        cardLabels = new ArrayList<>();
        for (Integer i = 0; i < 2; i++) {
            JLabel cardLabel = new JLabel(cardImages.getSmallFacedownImage());
            cardLabel.setPreferredSize(smallCardDimension);
            cardLabels.add(cardLabel);
        }
    }

    // implement with hand class
    private void addShowCardButtonHandler() {
        showCardButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JButton button = (JButton) e.getSource();
                ButtonModel model = button.getModel();
                if (model.isPressed()) {
                    // playerhand.setVisible(true)
                    cardLabels.get(0).setIcon(cardImages.getSmallCardImage(player.getCardOne()));
                    cardLabels.get(1).setIcon(cardImages.getSmallCardImage(player.getCardTwo()));
                } else {
                    // playerhand.setVisible(false)
                    cardLabels.get(0).setIcon(cardImages.getSmallFacedownImage());
                    cardLabels.get(1).setIcon(cardImages.getSmallFacedownImage());
                }
            }
        });
    }

    private void genPanel() {
        panel = new JPanel(new BorderLayout());

        JPanel playerInfoPanel = new JPanel(new GridLayout(1, 2));
        JPanel playerInfoPanel2 = new JPanel(new GridLayout(2, 1));

        JLabel playerLabel = new JLabel(player.getName());
        playerLabel.setFont(playerLabel.getFont().deriveFont(28.0f));

        scoreLabel = new JLabel("Score: " + player.getChips() + " Chips");
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(22.0f));

        Image Cimg = this.player.getIcon().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(Cimg);
        JLabel playerIcon = new JLabel(scaledImage);

        playerInfoPanel.add(playerLabel);
        playerInfoPanel.add(playerIcon);
        playerInfoPanel.setPreferredSize(new Dimension(220, smallCardDimension.height/2));
        
        playerInfoPanel2.add(playerInfoPanel);
        playerInfoPanel2.add(scoreLabel);
        playerInfoPanel2.setPreferredSize(new Dimension(220, smallCardDimension.height));


        showCardButton = new JButton("Show");
        showCardButton.setFont(showCardButton.getFont().deriveFont(20.0f));
        initCardLabels();

        JPanel cardPanel = new JPanel();
        cardPanel.add(showCardButton);
        cardPanel.add(cardLabels.get(0));
        cardPanel.add(cardLabels.get(1));

        playerInfoPanel.setBackground(new Color(0x643e36));
        playerInfoPanel2.setBackground(new Color(0x643e36));
        cardPanel.setBackground(new Color(0x643e36));

        playerInfoPanel2.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        panel.add(BorderLayout.WEST, playerInfoPanel2);
        panel.add(BorderLayout.EAST, cardPanel);

        panel.setBackground(new Color(0x35654d));

        addShowCardButtonHandler();
    }
}
