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

    private final ArrayList<Player> players;
    private int currRound = 1;
    private final boolean isRoundMode;
    private final int numRounds;

    private Deck deck = new Deck();
    CardImages cardImages;
    ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    ArrayList<JLabel> cardsList = new ArrayList<>();

    ArrayList<Card> tableCards = new ArrayList<>();

    Dimension cardDimension;

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
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this, TurnButtonEvent.ButtonType.RAISE_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                if (players.get(currentPlayerWatched).getChips() <= currentBet) {
                    if (players.get(currentPlayerWatched).isAllIn()) {
                        numChecks = 0;
                        System.out.println("all in raise");
                    } else {
                        numChecks = 1;
                    }
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
        frame.setBackground(new Color(0x35654d));

        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();
        this.southPanel = genSouthPanel();

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
    }

    private JPanel genNorthPanel() {
        // temporary, eventually move or delete
        for (Player player : players) {
            PlayerPanel panel = new PlayerPanel(player, cardImages);
            playerPanels.add(panel);
        }

        PlayerPanel p = playerPanels.get(0);

        return p.getPanel();
    }

    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;

        cardsPanel = new JPanel(new GridLayout(1, 5, 3, 1));

        for (int index = 0; index < 5; index++) {
            JLabel cardLabel = new JLabel(cardImages.getFacedownImage());
            cardLabel.setPreferredSize(cardDimension);
            cardsPanel.add(cardLabel);
            cardsList.add(cardLabel);
        }
        cardsPanel.setBackground(new Color(0x35654d));

        newPanel.add(cardsPanel, c);
        c.gridy = 1;

        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(0, 150));
        newPanel.add(spacerPanel, c);
        newPanel.setBackground(new Color(0x35654d));

        return newPanel;
    }

    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());

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

        exitButton = new JButton("Exit Game");
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setFont(exitButton.getFont().deriveFont(16.0f));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        newPanel.add(BorderLayout.WEST, volumeSlider);
        newPanel.add(BorderLayout.CENTER, actionPanel);
        newPanel.add(BorderLayout.EAST, exitButton);

        Dimension d = newPanel.getPreferredSize();
        newPanel.setPreferredSize(new Dimension(d.width, 50));
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return newPanel;
    }

    private void addExitButtonListener() {
        this.exitButton.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
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

    public Integer getRaiseAmount() throws NumberFormatException {
        return Integer.parseInt(raiseField.getText());
    }

    public int getCurrentBet() {
        return this.currentBet;
    }


    public int getCurrentPlayerWatched() {
        return currentPlayerWatched;
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
    private final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    private void advanceStage() {
        currentBet = 0;
        currentPlayerWatched = 0;
        while (currentPlayerWatched <= players.size() && (players.get(currentPlayerWatched).isFolded() || players.get(currentPlayerWatched).isAllIn())) {
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
        for (int i = 0; i < 3; ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        setButtonsEnabled(false);
        for (int i = gameStage; i < 5; ) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            JLabel l = cardsList.get(i);
            Card card = deck.drawCard();
            tableCards.add(card);
            l.setIcon(cardImages.getCardImage(card));
            i++;
        }
        doEndRound();
    }

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
        foldedPlayers = 0;
        allInPlayers = 0;
        advanceStage();
        distributeChips();
        players.forEach(Player::resetRound);
        if (isRoundMode && numRounds <= currRound) {
            frame.setVisible(false);
            new EndFrame(this);
            return;
        } else currRound++;
        int more_than_zero = 0;
        for (Player p : players) {
            if (p.getChips() > 0) more_than_zero++;
        }
        if (more_than_zero == 1) {
            frame.setVisible(false);
            new EndFrame(this);
            return;
        }
        System.out.println(currentPot + " chips will be added to winning player when we implement");
        gameStage = 0;
        setButtonsEnabled(true);
        deck = new Deck();
        deck.shuffle();
        tableCards = new ArrayList<>();

        players.forEach(p -> p.setCards(deck.drawCard(), deck.drawCard()));
        playerPanels.forEach(panel -> panel.updateScoreLabel());
        cardsList = new ArrayList<>();

        frame.getContentPane().remove(this.northPanel);
        frame.getContentPane().remove(this.centerPanel);

        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);

        frame.validate();
        frame.repaint();
    }

    private void distributeChips() {
        int winningIndex = 0, compareOutput;
        boolean isTie = false, startLoop = false;
        ArrayList<Integer> tieIndexs = new ArrayList<>();

        for (Player p : players) {
            if (p.isFolded()) continue;
            p.addHandToScorer();
            p.addCardListToScorer(tableCards);
            p.runScorerChecks();
        }

        //Finding the index of winning players
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isFolded()) continue;
            if (!startLoop) {
                winningIndex = i;
                startLoop = true;
                continue;
            }

            compareOutput = players.get(i).callCompareScores(players.get(winningIndex).getScorer());

            if (compareOutput == 0) {
                winningIndex = i;
                isTie = false;
                tieIndexs.clear();
            } else if (compareOutput == -1) {
                isTie = true;
                if (tieIndexs.isEmpty()) {
                    tieIndexs.add(i);
                    tieIndexs.add(winningIndex);
                } else {
                    tieIndexs.add(i);
                }
            } else if (compareOutput == 1) {
                //Aint gotta do jack
                //This if statment is unessisary but it makes me feel better
            }
        }

        if (isTie) {
            Integer dividedPot = currentPot / (tieIndexs.size());
            for (int tieIndex : tieIndexs) {
                players.get(tieIndex).addToChips(dividedPot);
            }
            this.currentPot = 0;
        } else {
            players.get(winningIndex).addToChips(currentPot);
            this.currentPot = 0;
        }
    }

    private void endPlayerTurn() {
        PlayerPanel p = playerPanels.get(currentPlayerWatched);
        p.updateScoreLabel();
        frame.getContentPane().remove(p.getPanel());

        Player player = players.get(currentPlayerWatched);
        // doEndRound if all but one out regardless of mode, end early
        // under first if below, add check for if all but player all in/folded
        do {
            if (numChecks + foldedPlayers + allInPlayers >= players.size()) {
                if (player.isAllIn() || player.isFolded()) {
                    service.schedule(this::doShowEarly, 0L, TimeUnit.MILLISECONDS);
                    break;
                } else {
                    advanceStage();
                    advanceGame();
                }
            } else {
                currentPlayerWatched = (currentPlayerWatched + 1) % players.size();
                player = players.get(currentPlayerWatched);
                if (player.getChips() <= currentBet && !player.isAllIn() && !player.isFolded()) {
                    break;
                }
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
