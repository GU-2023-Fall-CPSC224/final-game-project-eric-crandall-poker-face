/** Class Name: GameFrame
 *  Desc: This Class creates a JFrame which displays pre-game information and settings. Contains two "sections" of information: 
 *        Start and Name sections. Start section is where game version and player/round numbers are determined. Name section is 
 *        Where player names are determined. 
 *  Notes: Documented by Gabe Hoing
 */

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

public class GameFrame {
    // List of player objects and their associated PlayerPanels.
    private ArrayList<Player> players;
    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    // Deck of cards and associated images.
    private Deck deck = new Deck();
    private CardImages cardImages;

    // List of Cards used in the center table and their associated JLabel
    // representations.
    private ArrayList<JLabel> cardsList = new ArrayList<>();
    private ArrayList<Card> tableCards = new ArrayList<>();

    // Dimensions for buttons and labels within the GUI window.
    private Dimension cardDimension;
    private Dimension turnButtonDimension = new Dimension(80, 25);

    // Variables representing various data values of the round.
    private int currRound = 1;
    private int numRounds;
    private boolean isRoundMode;

    private int currentPlayerWatched = 0;
    private int currentBet = 0;
    private int currentPot = 0;

    private int numChecks = 0;
    private int foldedPlayers = 0;
    private static int allInPlayers = 0;

    private int gameStage = 0;

    // GUI window for the game and panels displaying its information
    private final JFrame frame;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;

    // Widgets further separating game information.
    private JPanel cardsPanel;
    private JPanel actionPanel;
    private JLabel infoLabel;
    private JLabel betLabel;

    // Widgets controlling the game.
    private JTextField raiseField;
    private JButton callButton;
    private JButton foldButton;
    private JButton raiseButton;
    private JButton exitButton;

    // JSlider to control volume.
    private JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 35, 100, (int) SoundThread.DEFAULT_VOLUME);

    // A scheduled ExecutorService object for animations and delay.
    private ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    /*
     * Constructor For GameFrame
     * Sets initial game data and settings equal to values contained in accepted
     * startFrame, then initializes the GUI window.
     */
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

    /*
     * Method Name: getFrame()
     * Returns: A JFrame
     * Desc: Returns this.frame, the pre-game GUI window.
     * Events: N/A
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /*
     * Method Name: getPlayers()
     * Returns: An ArrayList of Player objects.
     * Desc: Returns the list of players who will be playing the game.
     * Events: N/A
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /*
     * Method Name: setupFrame()
     * Returns: N/A (void)
     * Desc: Sets up the GUI window's settings such as size and location, then
     * generates the panels which display its information.
     * Events: N/A
     */
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

        // Generate panels
        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();
        this.southPanel = genSouthPanel();

        // Add panels to window.
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
    }

    /*
     * Method Name: genNorthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the top of
     * the screen.
     * Events: N/A
     */
    private JPanel genNorthPanel() {
        for (int i = 0; i < players.size(); i++) {
            PlayerPanel panel = new PlayerPanel(players.get(i), cardImages);
            playerPanels.add(panel);
        }

        PlayerPanel p = playerPanels.get(currentPlayerWatched);

        return p.getPanel();
    }

    /*
     * Method Name: genCenterPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the center
     * of the screen.
     * Events: N/A
     */
    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;

        cardsPanel = new JPanel(new GridLayout(1, 5, 3, 1));
        for (Integer index = 0; index < 5; index++) {
            JLabel cardLabel = new JLabel(cardImages.getFacedownImage());
            cardLabel.setPreferredSize(cardDimension);
            cardsPanel.add(cardLabel);
            cardsList.add(cardLabel);
        }
        cardsPanel.setBackground(new Color(0x35654d));

        newPanel.add(cardsPanel, c);
        c.gridy = 1;

        JPanel infoPanel = new JPanel();
        infoLabel = new JLabel("Pot: " + currentPot + " Chips");
        infoLabel.setFont(infoLabel.getFont().deriveFont(18.0f));
        infoPanel.setPreferredSize(new Dimension(300, 150));
        infoPanel.setBackground(new Color(0x35654d));
        infoPanel.add(infoLabel);
        newPanel.add(infoPanel, c);
        newPanel.setBackground(new Color(0x35654d));

        return newPanel;
    }

    /*
     * Method Name: genSouthPanel()
     * Returns: A JPanel
     * Desc: Creates and returns a JPanel representing the information at the bottom
     * of the screen.
     * Events: N/A
     */
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

    /*
     * Method Name: addTurnButtonEvents()
     * Returns: N/A (void)
     * Desc: Adds Action Listeners to player turn actions.
     * Events: N/A
     */
    private void addTurnButtonEvents() {
        // Adds an Action Listener to callButton handling a player calling / checking.
        callButton.addActionListener(ae -> {
            int amt = 0;
            int chips = players.get(currentPlayerWatched).getChips();
            if (currentBet >= chips) {
                amt = chips;
            } else {
                amt = currentBet - players.get(currentPlayerWatched).getEscrowChips();
            }
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this,
                    TurnButtonEvent.ButtonType.CALL_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                if (!players.get(currentPlayerWatched).isAllIn()) {
                    numChecks++;
                }
                currentPot += amt;
                endPlayerTurn();
            } else
                System.out.println("Check cancelled");
        });
        // Adds an Action Listener to foldButton handling a player folding.
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
        // Adds an Action Listener to rasieButton handling a player raising.
        raiseButton.addActionListener(ae -> {
            int amount = getRaiseAmount();
            int chips = players.get(currentPlayerWatched).getChips();
            if (currentBet + amount >= chips) {
                amount = chips;
            } else {
                amount += currentBet - players.get(currentPlayerWatched).getEscrowChips();
            }
            TurnButtonEvent event = new TurnButtonEvent((JButton) ae.getSource(), this,
                    TurnButtonEvent.ButtonType.RAISE_BUTTON);
            EventManager.callEvent(event);
            if (!event.isCancelled()) {
                if (players.get(currentPlayerWatched).getChips() <= currentBet) {
                    if (players.get(currentPlayerWatched).isAllIn()) {
                        numChecks = 0;
                    } else {
                        numChecks = 1;
                    }
                } else {
                    numChecks = 1;
                }
                currentPot += amount;
                raiseBetAmount(amount - currentBet);
                System.out.println(
                        players.get(currentPlayerWatched).getName() + " Raised " + (amount - currentBet) + " Chips");
                endPlayerTurn();
            }
        });
    }

    /*
     * Method Name: addExisButtonListener()
     * Returns: N/A (void)
     * Desc: Adds an Action Listener to exitButton.
     * Events: N/A
     */
    private void addExitButtonListener() {
        // Disposes of the frame and exits the program.
        this.exitButton.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });
    }

    /*
     * Method Name: addVolumeSliderHandler()
     * Returns: N/A (void)
     * Desc: Adds a change listener to volumeSlider which sets the SoundThread's
     * volume equal to slider's current value.
     * Events: N/A
     */
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

    /*
     * Method Name: initFrame()
     * Returns: A JPanel
     * Desc: Sets up this.frame and adds handlers before setting the accepted
     * JFrame's visibility to true and maximizing.
     * Events: N/A
     */
    private void initFrame(JFrame frame) {
        setupFrame();

        addTurnButtonEvents();
        addVolumeSliderHandler();
        addExitButtonListener();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);
    }

    /*
     * Method Name: incrementAllIn()
     * Returns: N/A (void)
     * Desc: Increments the number of players which are all-in by one.
     * Events: N/A
     */
    public static void incrementAllIn() {
        allInPlayers++;
    }

    /*
     * Method Name: initPlayerCards()
     * Returns: N/A (void)
     * Desc: Shuffles the deck and passes two cards to each player.
     * Events: N/A
     */
    private void initPlayerCards() {
        deck.shuffle();

        for (Player player : players) {
            Card c1 = deck.drawCard();
            Card c2 = deck.drawCard();
            player.setCards(c1, c2);
        }
    }

    /*
     * Method Name: genCenterPanel()
     * Returns: If roundMode is true or false
     * Desc: Returns a boolean representing whether the game is in round or bust
     * mode.
     * Events: N/A
     */
    public Boolean isRoundMode() {
        return this.isRoundMode;
    }

    /*
     * Method Name: getNumRounds()
     * Returns: Number of rounds to play to
     * Desc: Returns a integer representing the number of rounds specified to be
     * played to
     * Events: N/A
     */
    public Integer getNumRounds() {
        return numRounds;
    }

    /*
     * Method Name: getRaiseAmount()
     * Returns: The number of chips the bet is to be raised by.
     * Desc: Returns the String value of raiseField to integer.
     * Events: N/A
     */
    public Integer getRaiseAmount() throws NumberFormatException {
        return Integer.parseInt(raiseField.getText());
    }

    /*
     * Method Name: getCurrentBet()
     * Returns: The current bet
     * Desc: Returns this.currentBet, an integer representing the current bet.
     * Events: N/A
     */
    public Integer getCurrentBet() {
        return this.currentBet;
    }

    /*
     * Method Name: getDeck()
     * Returns: A Deck object
     * Desc: The object representation of the game's deck of cards.
     * Events: N/A
     */
    public Deck getDeck() {
        return deck;
    }

    /*
     * Method Name: getCardImages
     * Returns: An object of card images.
     * Desc: returns the object representation of the images associated with each
     * card.
     * Events: N/A
     */
    public CardImages getCardImages() {
        return cardImages;
    }

    /*
     * Method Name: getPlayerPanels()
     * Returns: An ArrayList of PlayerPanels
     * Desc: Returns a list of PlayerPanels which display player information.
     * Events: N/A
     */
    public ArrayList<PlayerPanel> getPlayerPanels() {
        return playerPanels;
    }

    /*
     * Method Name: getCardDimension()
     * Returns: Dimension cardDimension
     * Desc: Returns the dimension of card labels and buttons.
     * Events: N/A
     */
    public Dimension getCardDimension() {
        return cardDimension;
    }

    /*
     * Method Name: genCenterPanel()
     * Returns: Dimension turnButtonDimension
     * Desc: Returns the dimension of the turn buttons.
     * Events: N/A
     */
    public Dimension getTurnButtonDimension() {
        return turnButtonDimension;
    }

    /*
     * Method Name: getCurrentPlayerWatched()
     * Returns: Integer currentPlayerWatched
     * Desc: Returns the integer index of the player whose turn it is.
     * Events: N/A
     */
    public int getCurrentPlayerWatched() {
        return currentPlayerWatched;
    }

    /*
     * Method Name: getCallButton()
     * Returns: JButton callButton
     * Desc: Returns the button a player presses to call / check.
     * Events: N/A
     */
    public JButton getCallButton() {
        return callButton;
    }

    /*
     * Method Name: getFoldButton()
     * Returns: JButton foldButton
     * Desc: Returns the button a player presses to fold.
     * Events: N/A
     */
    public JButton getFoldButton() {
        return foldButton;
    }

    /*
     * Method Name: getRaiseButton()
     * Returns: JButton raiseButton
     * Desc: Returns the button a player presses to raise.
     * Events: N/A
     */
    public JButton getRaiseButton() {
        return raiseButton;
    }

    /*
     * Method Name: getNorthPanel()
     * Returns: JPanel northPanel
     * Desc: Returns the panel displaying infomation at the top of the screen.
     * Events: N/A
     */
    public JPanel getNorthPanel() {
        return northPanel;
    }

    /*
     * Method Name: getCenterPanel()
     * Returns: JPanel centerPanel
     * Desc: Returns the panel displaying infomation at the center of the screen.
     * Events: N/A
     */
    public JPanel getCenterPanel() {
        return centerPanel;
    }

    /*
     * Method Name: getSouthPanel()
     * Returns: JPanel southPanel
     * Desc: Returns the panel displaying infomation at the bottom of the screen.
     * Events: N/A
     */
    public JPanel getSouthPanel() {
        return southPanel;
    }

    /*
     * Method Name: getCardsPanel()
     * Returns: JPanel cardsPanel
     * Desc: Returns the panel which displays the community cards.
     * Events: N/A
     */
    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    /*
     * Method Name: getExitButton()
     * Returns: JButton exitButton
     * Desc: Returns the button used to exit the game early.
     * Events: N/A
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /*
     * Method Name: getRaiseField()
     * Returns: JTextField raiseField
     * Desc: Returns the text field which determines the raise amount.
     * Events: N/A
     */
    public JTextField getRaiseField() {
        return raiseField;
    }

    /*
     * Method Name: setCurrentPlayerWatched()
     * Returns: N/A (void)
     * Desc: Sets the integer index representing which player's turn it currently is
     * equal to the accepted integer value.
     * Events: N/A
     */
    public void setCurrentPlayerWatched(int currentPlayerWatched) {
        this.currentPlayerWatched = currentPlayerWatched;
    }

    /*
     * Method Name: raiseBetAmount()
     * Returns: N/A (void)
     * Desc: Adds the accepted integer value to the current bet and sets betLabel's
     * text field to display this new value.
     * Events: N/A
     */
    public void raiseBetAmount(int betAmount) {
        currentBet += betAmount;
        betLabel.setText("Current Bet: " + currentBet);
    }

    /*
     * Method Name: setButtonsEnabled()
     * Returns: N/A (void)
     * Desc: Enables or disables the turn control buttons depending on the value of
     * the accepted boolean.
     * Events: N/A
     */
    public void setButtonsEnabled(Boolean isEnabled) {
        callButton.setEnabled(isEnabled);
        raiseButton.setEnabled(isEnabled);
        foldButton.setEnabled(isEnabled);
    }

    /*
     * Method Name: advanceStage()
     * Returns: N/A (void)
     * Desc: Advances the stage of the round by resetting turn values, then
     * iterating through the list of players to find the
     * index of the first player elegible to play before finally disabling turn
     * buttons.
     * Events: N/A
     */
    private void advanceStage() {
        currentBet = 0;
        currentPlayerWatched = 0;
        numChecks = 0;
        betLabel.setText("Current Bet: " + currentBet);
        while (currentPlayerWatched < players.size()
                && (players.get(currentPlayerWatched).isFolded() || players.get(currentPlayerWatched).isAllIn())) {
            currentPlayerWatched++;
        }
        for (Player p : players) {
            p.resetEscrowChips();
        }
        setButtonsEnabled(false);
    }

    /*
     * Method Name: advanceGame()
     * Returns: N/A (void)
     * Desc: Schedules turn actions depending on which stage the round currently is
     * in.
     * Events: N/A
     */
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

    /*
     * Method Name: doFlop()
     * Returns: N/A (void)
     * Desc: Does the "flop" of Texas Hold'em by discarding a card then flipping the
     * next three over before enabling turn buttons.
     * Events: N/A
     */
    private void doFlop() {
        deck.drawCard();
        for (int i = 0; i < 3;) {
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

    /*
     * Method Name: doTurn()
     * Returns: N/A (void)
     * Desc: Does the "turn" of Texas Hold'em by discarding a card then flipping a
     * card over then enabling turn buttons.
     * Events: N/A
     */
    private void doTurn() {
        deck.drawCard();
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

    /*
     * Method Name: doRiver()
     * Returns: N/A (void)
     * Desc: Does the "river" of Texas Hold'em by discarding a card then flipping a
     * card over before enabling turn buttons.
     * Events: N/A
     */
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

    /*
     * Method Name: doShowEarly()
     * Returns: N/A (void)
     * Desc: "Shows" cards early by progressing the events of flop, turn, and river in the case of no players being able to go.
     * Events: N/A
     */
    private void doShowEarly() {
        setButtonsEnabled(false);

        int cardIndex = 0;
        if (gameStage == 0) {
            deck.drawCard();
            for (int i = 0; i < 3;) {
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
            cardIndex = 3;
        } else {
            cardIndex = gameStage + 2;
        }

        for (int i = cardIndex; i < 5;) {
            deck.drawCard();
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

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        service.schedule(this::doEndRound, 0L, TimeUnit.MILLISECONDS);
    }

    /*
     * Method Name: doEndRound()
     * Returns: N/A (void)
     * Desc: Resets round values and calculate scores. Then calculates if any of the criteria for the game ending have been reached. 
     * Events: N/A
     */
    private void doEndRound() {
        setButtonsEnabled(false);
        foldedPlayers = 0;
        allInPlayers = 0;

        distributeChips();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        players.forEach(Player::resetRound);
        advanceStage();

        if (isRoundMode && numRounds <= currRound) {
            frame.setVisible(false);
            new EndFrame(this);
            return;
        } else
            currRound++;

        int more_than_zero = 0;
        for (Player p : players) {
            if (p.getChips() > 0) {
                more_than_zero++;
            } else {
                foldedPlayers++;
            }
        }

        if (more_than_zero == 1) {
            frame.setVisible(false);
            new EndFrame(this);
            return;
        }

        gameStage = 0;

        deck = new Deck();
        deck.shuffle();
        tableCards = new ArrayList<>();

        players.forEach(p -> p.setCards(deck.drawCard(), deck.drawCard()));
        playerPanels.forEach(panel -> panel.updateScoreLabel());
        cardsList = new ArrayList<>();

        setButtonsEnabled(true);

        frame.getContentPane().remove(this.northPanel);
        frame.getContentPane().remove(this.centerPanel);

        this.northPanel = genNorthPanel();
        this.centerPanel = genCenterPanel();

        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);

        frame.validate();
        frame.repaint();
    }

    /*
     * Method Name: distributeChips()
     * Returns: N/A (void)
     * Desc: "Distributes" the chips to the calculated winning player(s). 
     * Events: N/A
     */
    private void distributeChips() {
        int winningIndex = 0, compareOutput;
        boolean isTie = false, startLoop = false;
        ArrayList<Integer> tieIndexs = new ArrayList<>();

        for (Player p : players) {
            p.resetEscrowChips();
            if (p.isFolded())
                continue;
            p.addHandToScorer();
            p.addCardListToScorer(tableCards);
            p.runScorerChecks();
        }

        // Finding the index of winning players
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isFolded())
                continue;
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
                // Else statement for readability
            }
        }

        // Calculates if a tie is reached and splits chips evenly between all winning players
        if (isTie) {
            Integer dividedPot = currentPot / (tieIndexs.size());
            for (int tieIndex : tieIndexs) {
                players.get(tieIndex).addToChips(dividedPot);
            }
            this.currentPot = 0;
            infoLabel.setText("Tie!");
        } else {
            players.get(winningIndex).addToChips(currentPot);
            infoLabel.setText(players.get(winningIndex).getName() + " Wins!");
            this.currentPot = 0;
        }
    }

    /*
     * Method Name: endPlayerTurn
     * Returns: N/A (void)
     * Desc: Ends a player's turn by updating the text within various information labels before finding the next elegible player.
     *       If criteria is met, instead advances to the next stage or ends round early. 
     * Events: N/A
     */
    private void endPlayerTurn() {
        PlayerPanel p = playerPanels.get(currentPlayerWatched);
        p.updateScoreLabel();
        frame.getContentPane().remove(p.getPanel());

        Player player = players.get(currentPlayerWatched);
        infoLabel.setText("Pot: " + currentPot + " Chips");

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
        callButton.setText(player.getEscrowChips() >= currentBet ? "Check"
                : ((player.getEscrowChips() + player.getChips()) <= currentBet ? "All In!"
                        : "Call: " + (currentBet - player.getEscrowChips())));

        northPanel = p.getPanel();
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);

        frame.validate();
        frame.repaint();
    }
}
