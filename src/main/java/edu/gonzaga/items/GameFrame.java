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
    //we need to move images and stuff like players, cards, etc.. to a main class where we impliment everything
    CardImages cardImages = new CardImages("media/");

    private ArrayList<Player> players;
    private Deck deck = new Deck();

    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    //make its own object "displayCards, flop, river..."
    private ArrayList<Card> tempCards = new ArrayList<Card>();

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

        // move into own method
        JPanel northPanel = new JPanel();
        PlayerPanel p = new PlayerPanel(players.get(0), cardImages);
        northPanel.add(p.getPanel());

        // move into own method
        JPanel centerPanel = new JPanel();
        JPanel cardsPanel = new JPanel(new GridLayout(1, 7, 2, 1));
        JButton potButton = new JButton("Pot");
        JButton deckButton = new JButton("Deck");
        cardsPanel.add(potButton);
        cardsPanel.add(deckButton);
        for (Integer index = 0; index < 5; index++) {
            Card card = deck.drawCard();
            JButton cardButton = new JButton(cardImages.getCardImage(card));
            cardButton.setPreferredSize(new Dimension(60, 80));
            cardsPanel.add(cardButton);
            tempCards.add(card);
        }
        centerPanel.add(cardsPanel);

        // move into own method
        Player tempPlayer = players.get(0);
        JPanel southPanel = new JPanel();
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

