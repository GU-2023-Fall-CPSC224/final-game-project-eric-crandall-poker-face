package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;
import edu.gonzaga.utils.CardImages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame {
    // we need to move images and stuff like players, cards, etc.. to a main class
    // where we impliment everything
    CardImages cardImages = new CardImages("media/");

    private ArrayList<Player> players;
    private Deck deck = new Deck();

    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private ArrayList<Card> tempCards = new ArrayList<Card>();

    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;

    JPanel cardsPanel;
    private final JFrame frame;

    // TODO: handle number rounds/bustmode
    public GameFrame(ArrayList<Player> players) {
        this.players = players;
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private void initFrame(JFrame frame) {
        deck.shuffle();
        // don't allow resize
        frame.setSize(520, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.addWindowListener(CloseWindowListener.getInstance());
        if (MainGame.hydra) {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(HydraListener.getInstance());
        } else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        // temporary adding cards to players
        for (int i = 0; i < players.size(); i++) {
            Card c1 = deck.drawCard();
            Card c2 = deck.drawCard();
            Player p = players.get(i);
            p.setCards(c1, c2);
        }

        // move into own method
        for (int i = 0; i < players.size(); i++) {
            PlayerPanel panel = new PlayerPanel(players.get(i), cardImages);
            playerPanels.add(panel);
        }

        // move into own method
        northPanel = new JPanel();
        PlayerPanel p = playerPanels.get(0);
        northPanel.add(p.getPanel());

        // move into own method
        centerPanel = new JPanel();
        cardsPanel = new JPanel(new GridLayout(1, 8, 2, 1));
        JButton potButton = new JButton("Pot");
        JButton deckButton = new JButton(cardImages.getFacedownImage());
        deckButton.setPreferredSize(new Dimension(60, 80));
        cardsPanel.add(potButton);
        cardsPanel.add(deckButton);
        for (Integer index = 0; index < 5; index++) {
            Card card = deck.drawCard();
            JLabel cardLabel = new JLabel(cardImages.getCardImage(card));
            cardLabel.setPreferredSize(new Dimension(60, 80));
            cardsPanel.add(cardLabel);
            tempCards.add(card);
        }
        centerPanel.add(cardsPanel);

        // move into own method
        Player tempPlayer = players.get(0);
        southPanel = new JPanel();
        JLabel playerNameLabel = new JLabel(tempPlayer.getName());
        JLabel playerChipsLabel = new JLabel("" + tempPlayer.getScore() + " chips");
        southPanel.add(playerNameLabel);
        southPanel.add(playerChipsLabel);

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);

        frame.setVisible(true);
    }
}
