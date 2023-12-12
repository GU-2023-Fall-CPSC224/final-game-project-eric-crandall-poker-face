/** Class Name: PlayerPanel
 *  Desc: This panel creates and manages Panels which hold the current player's information.
 *        The panel includes the player's name and icon, current chips, and their cards. The 
 *        cards are hidden by default, but are "flipped" when a button is held down.
 *  Notes: Documented by Gabe Hoing
 */

package edu.gonzaga.items;

import edu.gonzaga.utils.CardImages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerPanel {
    private final Player player; // Player PlayePanel is representing.

    private JPanel panel = new JPanel(); // JPanel representation of PlayerPanel.


    private JLabel scoreLabel; // Label which displays the player's current chips.
    private JButton showCardButton; // Button which reveals the player's cards.
    private ArrayList<JLabel> cardLabels; // List of JLabels containing the images representing the player's cards.

    private final CardImages cardImages; // Object containing image representations of cards.
    private final Dimension smallCardDimension; // Dimension value for card images and labels. 

    /* Constructor For PlayerPanel
     * Sets this.player and this.cardImages equal to the accepted values. 
     * Sets this.smallCardDimension equal to cardImages value for this dimension and generates the panel. 
     */
    public PlayerPanel(Player player, CardImages cardImages) {
        this.player = player;
        this.cardImages = cardImages;
        this.smallCardDimension = cardImages.getSmallImageDimension();
        genPanel();
    }

    /* Method Name: getPanel()
     * Returns: A JPanel
     * Desc: Returns this.panel, the JPanel representation of PlayerPanel.
     * Events: N/A
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /* Method Name: updateScoreLabel()
     * Returns: N/A (void)
     * Desc: Updates scoreLabel's text string to this.player's current Chips amount.
     * Events: N/A
     */
    public void updateScoreLabel() {
        scoreLabel.setText("Score: " + player.getChips() + " Chips");
    }

    /* Method Name: initCardLabels()
     * Returns: N/A (void)
     * Desc: initializes cardLabels to cardImages' faceDown image, setting their size equal to this.smallCardDimension
     * Events: N/A
     */
    private void initCardLabels() {
        cardLabels = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            JLabel cardLabel = new JLabel(cardImages.getSmallFacedownImage());
            cardLabel.setPreferredSize(smallCardDimension);
            cardLabels.add(cardLabel);
        }
    }

    /* Method Name: addShowCardButtonHandler()
     * Returns: N/A (void)
     * Desc: Adds a change listener to showCardButton. Sets each cardLabel's image to the associated card image when pressed.
     *       Otherwise sets the image to cardImages' facedown image.
     * Events: N/A
     */
    private void addShowCardButtonHandler() {
        showCardButton.addChangeListener(e -> {
            JButton button = (JButton) e.getSource();
            ButtonModel model = button.getModel();
            if (model.isPressed()) {
                cardLabels.get(0).setIcon(cardImages.getSmallCardImage(player.getCardOne()));
                cardLabels.get(1).setIcon(cardImages.getSmallCardImage(player.getCardTwo()));
            } else {
                cardLabels.get(0).setIcon(cardImages.getSmallFacedownImage());
                cardLabels.get(1).setIcon(cardImages.getSmallFacedownImage());
            }
        });
    }

    /* Method Name: genPanel()
     * Returns: N/A (void)
     * Desc: Generates panel by initializing its widgets and stylizing them, then adding handlers. 
     * Events: N/A
     */
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
