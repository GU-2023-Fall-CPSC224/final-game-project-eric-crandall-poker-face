package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.TurnButtonEvent;
import edu.gonzaga.events.backend.EventManager;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.CardImages;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TODO: add table of all player names and chips
public class GameFrame {

    private ArrayList<Player> players;
    private Boolean isRoundMode;
    private Integer numRounds;

    private Deck deck = new Deck();

    CardImages cardImages;
    ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    ArrayList<JLabel> cardsList = new ArrayList<>();

    ArrayList<Card> tableCards = new ArrayList<>();

    Dimension cardDimension;
    Dimension turnButtonDimension = new Dimension(80, 25);

    private int currentPlayerWatched = 0;
    private int currentBet = 0;

    JLabel betLabel;
    JTextField raiseField;
    JButton callButton;
    JButton foldButton;
    JButton raiseButton;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;

    JPanel cardsPanel;
    JPanel actionPanel;

    JButton exitButton;
    JButton tempEndButton;

    JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 35, 100, (int) SoundThread.DEFAULT_VOLUME);

    private final JFrame frame;

    public GameFrame(StartFrame startFrame) {
        this.players = startFrame.getPlayers();
        this.isRoundMode = startFrame.getIsRoundMode();
        this.numRounds = startFrame.getNumRounds();

        frame = new JFrame("Eric Crandall Poker");
        startFrame.getFrame().setVisible(false);
        initPlayerCards();
        ArrayList<Card> cards = new ArrayList<>();
        for (Player p : players) {
            cards.add(p.getCardOne());
            cards.add(p.getCardTwo());
        }

        this.cardImages = new CardImages("media/", cards);
        this.cardDimension = cardImages.getImageDimension();
        initFrame(frame);
        SoundThread.getInstance().restartAudio();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    //testing switching player panels, delete eventually
    private void addTurnButtonEvents() {
        callButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this, TurnButtonEvent.ButtonType.CALL_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                endPlayerTurn();
            }
        });
        foldButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this, TurnButtonEvent.ButtonType.FOLD_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                endPlayerTurn();
            }
        });
        raiseButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this, TurnButtonEvent.ButtonType.RAISE_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                endPlayerTurn();
            } else System.out.println("No end turn.");
        });
    }

    private void setupFrame() {
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
        // temporary, eventually move or delete
        for (int i = 0; i < players.size(); i++) {
            PlayerPanel panel = new PlayerPanel(players.get(i), cardImages);
            playerPanels.add(panel);
        }

        //also temporary
        PlayerPanel p = playerPanels.get(0);

        return p.getPanel();
    }

    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel();

        cardsPanel = new JPanel(new GridLayout(1, 5, 2, 1));

        for (Integer index = 0; index < 5; index++) {
            JLabel cardLabel = new JLabel(cardImages.getFacedownImage());
            cardLabel.setPreferredSize(cardDimension);
            cardsPanel.add(cardLabel);
            cardsList.add(cardLabel);
        }

        newPanel.add(cardsPanel);
        newPanel.setBackground(new Color(0x35654d));

        return newPanel;
    }

    // temporary, replace with betting/turn options
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

        Player player = players.get(currentPlayerWatched);

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 35, 100, (int) SoundThread.DEFAULT_VOLUME);
        volumeSlider.setBackground(new Color(0x643e36));
        volumeSlider.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(10);
        actionPanel = new JPanel(flowLayout);
        betLabel = new JLabel("Current Bet: " + currentBet);
        betLabel.setFont(betLabel.getFont().deriveFont(18.0f));

        raiseField = new JTextField(5);
        raiseField.setHorizontalAlignment(SwingConstants.CENTER);
        raiseField.setFont(raiseField.getFont().deriveFont(16.0f));
        raiseField.setPreferredSize(new Dimension(60, 32));
        raiseField.setText("0");
        
        callButton = new JButton("Call");
        callButton.setPreferredSize(new Dimension(100, 32));
        callButton.setFont(betLabel.getFont().deriveFont(16.0f));

        foldButton = new JButton("Fold");
        foldButton.setPreferredSize(new Dimension(100, 32));
        foldButton.setFont(betLabel.getFont().deriveFont(16.0f));

        raiseButton = new JButton("Raise");
        raiseButton.setPreferredSize(new Dimension(100, 32));
        raiseButton.setFont(betLabel.getFont().deriveFont(16.0f));

        actionPanel.add(betLabel);
        actionPanel.add(callButton);
        actionPanel.add(foldButton);
        actionPanel.add(raiseButton);
        actionPanel.add(raiseField);

        actionPanel.setBackground(new Color(0x643e36));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JPanel tempButtonPanel = new JPanel();

        exitButton = new JButton("Exit Game");
        tempEndButton = new JButton("Test End Game");

        tempButtonPanel.add(tempEndButton);
        tempButtonPanel.add(exitButton);

        newPanel.add(BorderLayout.WEST, volumeSlider);
        tempButtonPanel.setBackground(new Color(0x643e36));
        tempButtonPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        newPanel.add(BorderLayout.WEST, volumeSlider);
        newPanel.add(BorderLayout.CENTER, actionPanel);
        newPanel.add(BorderLayout.EAST, tempButtonPanel);

        Dimension d = newPanel.getPreferredSize();
        newPanel.setPreferredSize(new Dimension(d.width, 60));
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

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
        setupFrame();

        //delete eventually
        addTurnButtonEvents();

        addExitButtonListener();
        addTempEndButtonListener();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);

    }

    private void initPlayerCards() {
        // move somewhere down the road
        deck.shuffle();

        // temporary adding cards to players, delete eventualy
        for (Player player : players) {
            Card c1 = deck.drawCard();
            Card c2 = deck.drawCard();
            player.setCards(c1, c2);
        }
    }

    public Boolean isRoundMode() {
        return this.isRoundMode;
    }

    public Integer getNumRounds() {
        return numRounds;
    }

    public Integer getRaiseAmount() {
        return Integer.parseInt(raiseField.getText());
    }

    public Deck getDeck() {
        return deck;
    }

    public CardImages getCardImages() {
        return cardImages;
    }

    public ArrayList<PlayerPanel> getPlayerPanels() {
        return playerPanels;
    }

    public Dimension getCardDimension() {
        return cardDimension;
    }

    public Dimension getTurnButtonDimension() {
        return turnButtonDimension;
    }

    public int getCurrentPlayerWatched() {
        return currentPlayerWatched;
    }

    public JButton getCallButton() {
        return callButton;
    }

    public JButton getFoldButton() {
        return foldButton;
    }

    public JButton getRaiseButton() {
        return raiseButton;
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getTempEndButton() {
        return tempEndButton;
    }

    public JTextField getRaiseField() {
        return raiseField;
    }

    public void setCurrentPlayerWatched(int currentPlayerWatched) {
        this.currentPlayerWatched = currentPlayerWatched;
    }

    public void raiseBetAmount(int betAmount) {
        currentBet += betAmount;
        betLabel.setText("Current Bet: " + currentBet);
    }

    private void doFlop() {
        new Thread() {
            @Override
            public void run() {
                long cooldown = 500;
                long start = System.currentTimeMillis();
                for (int i = 0; i < 3;) {
                    if (System.currentTimeMillis() - start < cooldown) continue;
                    start = System.currentTimeMillis();
                    JLabel l = cardsList.get(i);
                    Card card = deck.drawCard();
                    tableCards.add(card);
                    l.setIcon(cardImages.getCardImage(card));
                    i++;
                }
                interrupt();
            }
        }.start();
    }

    private void doTurn() {

    }

    private void doRiver() {

    }


    private void endPlayerTurn() {
        PlayerPanel p = playerPanels.get(currentPlayerWatched);
        frame.getContentPane().remove(p.getPanel());
        p.updateScoreLabel();

        //change player being "watched"
        do {
            currentPlayerWatched = (currentPlayerWatched + 1) % players.size();

            //add new panel
            p = playerPanels.get(currentPlayerWatched);
        } while (players.get(currentPlayerWatched).isFolded() || players.get(currentPlayerWatched).isAllIn());

        raiseField.setText("0");

        northPanel = p.getPanel();
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);

        frame.validate();
        frame.repaint();
    }
}
