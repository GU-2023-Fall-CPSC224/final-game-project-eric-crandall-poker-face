package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;

//TODO: add mute button 
import edu.gonzaga.utils.SoundThread;
import edu.gonzaga.utils.CardImages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TODO: load images faster
public class GameFrame {
    
    private ArrayList<Player> players;
    private Boolean isRoundMode;
    private Integer numRounds;

    private Deck deck = new Deck();

    CardImages cardImages = new CardImages("media/");
    ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    ArrayList<Card> tempCards = new ArrayList<Card>();

    Dimension cardDimension = new Dimension(60, 80);

    //temp for testing purposes
    private int currentPlayerWatched = 0;

    JButton cycleButton;
    JButton potButton;
    JButton deckButton;

    JLabel playerNameLabel;
    JLabel playerChipsLabel;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;

    JPanel cardsPanel;

    JButton exitButton;
    JButton tempEndButton;

    private final JFrame frame;

    public GameFrame(StartFrame startFrame) {
        this.players = startFrame.getPlayers();
        this.isRoundMode = startFrame.getIsRoundMode();
        this.numRounds = startFrame.getNumRounds();

        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    //testing switching player panels, delete eventually
    private void addTempCallbackHandler() {
        cycleButton.addActionListener(ae -> {
            //remove previous panel
            PlayerPanel p = playerPanels.get(currentPlayerWatched);
            northPanel.remove(p.getPanel());

            //change player being "watched"
            currentPlayerWatched = (currentPlayerWatched + 1) % players.size();
            
            //add new panel 
            p = playerPanels.get(currentPlayerWatched);
            northPanel.add(p.getPanel());

            //set player information on bottom
            Player player = players.get(currentPlayerWatched);
            playerNameLabel.setText(player.getName());
            playerChipsLabel.setText("" + player.getScore() + " chips");

            frame.validate();
            frame.repaint();
        });
    }

    private void setupFrame() {
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

        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();
        this.southPanel = genSouthPanel();

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
    }

    private JPanel genNorthPanel() {
        JPanel newPanel = new JPanel();

        // temporary, eventually move or delete
        for (int i = 0; i < players.size(); i++) {
            PlayerPanel panel = new PlayerPanel(players.get(i), cardImages);
            playerPanels.add(panel);
        }
        
        //also temporary
        PlayerPanel p = playerPanels.get(0);

        newPanel.add(p.getPanel());

        return newPanel;
    }

    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel();

        cardsPanel = new JPanel(new GridLayout(1, 8, 2, 1));

        //TODO: add location (top left of center area?)
        cycleButton = new JButton("Next");
        cycleButton.setPreferredSize(cardDimension);

        potButton = new JButton("Pot");
        potButton.setPreferredSize(cardDimension);

        deckButton = new JButton(cardImages.getFacedownImage());
        deckButton.setPreferredSize(cardDimension);

        cardsPanel.add(cycleButton);
        cardsPanel.add(potButton);
        cardsPanel.add(deckButton);

        // redo when integrating with hand class
        for (Integer index = 0; index < 5; index++) {
            Card card = deck.drawCard();
            JLabel cardLabel = new JLabel(cardImages.getCardImage(card));
            cardLabel.setPreferredSize(cardDimension);
            cardsPanel.add(cardLabel);
            tempCards.add(card);
        }

        newPanel.add(cardsPanel);

        return newPanel;
    }

    // temporary, replace with betting/turn options
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel();

        Player player = players.get(currentPlayerWatched);

        playerNameLabel = new JLabel(player.getName());
        playerChipsLabel = new JLabel("" + player.getScore() + " chips");
        exitButton = new JButton("Exit Game");
        tempEndButton = new JButton("Test End Game");
        addExitButtonListener();
        addTempEndButtonListener();

        newPanel.add(playerNameLabel);
        newPanel.add(playerChipsLabel);
        newPanel.add(tempEndButton);
        newPanel.add(exitButton);

        return newPanel;
    }

    private void addExitButtonListener() {
        this.exitButton.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });
    }

    private void addTempEndButtonListener() {
        this.tempEndButton.addActionListener(e -> {
            frame.setVisible(false);
            new EndFrame(this);
        });
    }

    private void initFrame(JFrame frame) {
        // move somewhere down the road
        deck.shuffle();

        // temporary adding cards to players, delete eventualy
        for (int i = 0; i < players.size(); i++) {
            Card c1 = deck.drawCard();
            Card c2 = deck.drawCard();
            Player p = players.get(i);
            p.setCards(c1, c2);
        }

        setupFrame();

        //delete eventually
        addTempCallbackHandler();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
}
