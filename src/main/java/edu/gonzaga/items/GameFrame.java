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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//TODO: add table of all player names and chips
public class GameFrame {

    private ArrayList<Player> players;
    private int currRound = 1;
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
    private int currentPot = 0;

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
    private int numChecks = 0;
    private int foldedPlayers = 0;
    private static int allInPlayers = 0;

    public static void incrementAllIn() {
        allInPlayers++;
    }

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

    // TODO: cannot raise if raise field < 1
    private void addTurnButtonEvents() {
        callButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this,
                    TurnButtonEvent.ButtonType.CALL_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                if (!players.get(currentPlayerWatched).isAllIn()) {
                    numChecks++;
                }
                endPlayerTurn();
            } else
                System.out.println("Check cancelled");
        });
        foldButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this,
                    TurnButtonEvent.ButtonType.FOLD_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                foldedPlayers++;
                endPlayerTurn();
            } else {
                service.schedule(this::doShowEarly, 0L, TimeUnit.MILLISECONDS);
            }
        });
        raiseButton.addActionListener(ae -> {
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this,
                    TurnButtonEvent.ButtonType.RAISE_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                if (players.get(currentPlayerWatched).getChips() <= currentBet) {
                    numChecks = 0;
                } else {
                    numChecks = 1;
                }
                endPlayerTurn();
            }
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

        // also temporary
        PlayerPanel p = playerPanels.get(0);

        return p.getPanel();
    }

    // TODO: delete
    ArrayList<JLabel> tempLabels = new ArrayList<>();

    private JPanel genCenterPanel() {
        // TODO: revert to commented out code
        // JPanel newPanel = new JPanel();
        JPanel newPanel = new JPanel(new GridLayout(2, 1));

        cardsPanel = new JPanel(new GridLayout(1, 5, 2, 1));

        for (Integer index = 0; index < 5; index++) {
            JLabel cardLabel = new JLabel(cardImages.getFacedownImage());
            cardLabel.setPreferredSize(cardDimension);
            cardsPanel.add(cardLabel);
            cardsList.add(cardLabel);
        }

        newPanel.add(cardsPanel);

        // TODO: delete
        //
        JPanel tempPlayerInfo = new JPanel(new GridLayout(1, 7));
        for (int i = 0; i < players.size(); i++) {
            JLabel playerInfo = new JLabel("" + (i + 1) + ": " + players.get(i).getChips());
            tempLabels.add(playerInfo);
            tempPlayerInfo.add(playerInfo);
        }
        newPanel.add(tempPlayerInfo);
        //

        newPanel.setBackground(new Color(0x35654d));

        return newPanel;
    }

    // temporary, replace with betting/turn options
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

        // Player player = players.get(currentPlayerWatched);

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

        callButton = new JButton("Check");
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

    // TODO: delete
    private void addTempEndButtonListener() {
        this.tempEndButton.addActionListener(e -> {
            frame.setVisible(false);
            new EndFrame(this);
        });
    }

    private void addVolumeSliderHandler() {
        volumeSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            if (slider.getValue() == slider.getMinimum()) {
                SoundThread.getInstance().setVolume(0);
                return;
            }
            SoundThread.getInstance().setVolume(slider.getValue());
        });
    }

    private void initFrame(JFrame frame) {
        setupFrame();

        // delete eventually
        addTurnButtonEvents();

        addVolumeSliderHandler();
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

    public Integer getRaiseAmount() throws NumberFormatException {
        return Integer.parseInt(raiseField.getText());
    }

    public int getCurrentBet() {
        return this.currentBet;
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

    public void setButtonsEnabled(Boolean isEnabled) {
        callButton.setEnabled(isEnabled);
        raiseButton.setEnabled(isEnabled);
        foldButton.setEnabled(isEnabled);
    }

    int gameStage = 0;
    private ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    private void advanceStage() {
        currentBet = 0;
        currentPlayerWatched = 0;
        while (currentPlayerWatched < players.size() && (players.get(currentPlayerWatched).isFolded() || players.get(currentPlayerWatched).isAllIn())) {
            currentPlayerWatched++;
        }
        numChecks = 0;
        betLabel.setText("Current Bet: " + currentBet);
        calculatePot();
        setButtonsEnabled(false);
    }

    private void advanceGame() {
        if (gameStage == 0) {
            service.schedule(this::doFlop, 0L, TimeUnit.MILLISECONDS);
        }
        if (gameStage == 1) {
            service.schedule(this::doTurn, 0L, TimeUnit.MILLISECONDS);
        }
        if (gameStage == 2) {
            service.schedule(this::doRiver, 0L, TimeUnit.MILLISECONDS);
        }
        if (gameStage == 3) {
            service.schedule(this::doEndRound, 0L, TimeUnit.MILLISECONDS);
        }
    }

    private void doFlop() {
        long cooldown = 500;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; ) {
            if (System.currentTimeMillis() - start < cooldown)
                continue;
            start = System.currentTimeMillis();
            JLabel l = cardsList.get(i);
            Card card = deck.drawCard();
            tableCards.add(card);
            l.setIcon(cardImages.getCardImage(card));
            i++;
        }
        gameStage++;
        setButtonsEnabled(true);
    }

    private void doTurn() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JLabel l = cardsList.get(3);
        Card card = deck.drawCard();
        tableCards.add(card);
        l.setIcon(cardImages.getCardImage(card));
        gameStage++;
        setButtonsEnabled(true);
    }

    private void doRiver() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JLabel l = cardsList.get(4);
        Card card = deck.drawCard();
        tableCards.add(card);
        l.setIcon(cardImages.getCardImage(card));
        gameStage++;
        setButtonsEnabled(true);
    }

    private void doShowEarly() {
        long cooldown = 500;
        long start = System.currentTimeMillis();
        for (int i = gameStage; i < 5; ) {
            if (System.currentTimeMillis() - start < cooldown)
                continue;
            start = System.currentTimeMillis();
            JLabel l = cardsList.get(i);
            Card card = deck.drawCard();
            tableCards.add(card);
            l.setIcon(cardImages.getCardImage(card));
            i++;
        }
        doEndRound();
    }

    // TODO: move to advance stage??
    private void calculatePot() {
        for (Player p : players) {
            currentPot += p.getEscrowChips();
            p.resetEscrowChips();
        }
    }

    // TODO: handle round stuff here
    // TODO: calculate winning player, adding pot to them
    // DONE TODO: auto fold players with no moneys
    // DONE TODO: in round mode if currentRound == numRounds, endGame
    // DONE TODO: in bustMode if all players but one are out of money, endGame
    private void doEndRound() {
        advanceStage();
        foldedPlayers = 0;
        distributeChips();
        for (Player p : players) {
            if (p.getChips() <= 0) {
                p.setFolded(true);
                foldedPlayers++;
            } else {
                p.setFolded(false);
            }
        }
        if (isRoundMode && numRounds <= currRound) {
            frame.setVisible(false);
            new EndFrame(this);
        } else currRound++;
        if (!isRoundMode) {
            int more_than_zero = 0;
            for (Player p : players) {
               if (p.getChips() >= 0) more_than_zero++;
            }
            if (more_than_zero == 1)  {
                frame.setVisible(false);
                new EndFrame(this);
            }
        }
        System.out.println("" + currentPot + " chips will be added to winning player when we implement");
    }

    private void distributeChips() {
        for (Player p : players) {
            if (p.isFolded()) continue;
        }
    }

    private void endPlayerTurn() {
        PlayerPanel p = playerPanels.get(currentPlayerWatched);
        p.updateScoreLabel();
        frame.getContentPane().remove(p.getPanel());

        // TODO: delete
        tempLabels.get(currentPlayerWatched).setText("" + (currentPlayerWatched + 1) + ": " + players.get(currentPlayerWatched).getChips());

        Player player = players.get(currentPlayerWatched);

        do {
            if (numChecks + foldedPlayers + allInPlayers >= players.size()) {
                if (player.isAllIn() || player.isFolded()) {
                    service.schedule(this::doShowEarly, 0L, TimeUnit.MILLISECONDS);
                } else {
                    advanceStage();
                    advanceGame();
                }
                break;
            }


            currentPlayerWatched = (currentPlayerWatched + 1) % players.size();
            player = players.get(currentPlayerWatched);
            if (player.getChips() <= currentBet && !player.isAllIn() && !player.isFolded()) {
                break;
            }

        } while (player.isFolded() || player.isAllIn());
        p = playerPanels.get(currentPlayerWatched);


        raiseField.setText("0");
        callButton.setText(player.getEscrowChips() >= currentBet ? "Check" : ((player.getEscrowChips() + player.getChips()) <= currentBet ? "All In!" : "Call: " + (currentBet - player.getEscrowChips())));

        northPanel = p.getPanel();
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);

        frame.validate();
        frame.repaint();
    }
}
