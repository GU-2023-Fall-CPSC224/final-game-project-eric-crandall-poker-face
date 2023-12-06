package edu.gonzaga.items;

import edu.gonzaga.MainGame;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TODO: load images faster
public class EndFrame {
    
    private final ArrayList<Player> players;

    private final JFrame frame;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;

    JLabel northLabel;
    JButton playButton = new JButton("Play Again");
    JButton exitButton = new JButton("Exit Game");

    // TODO: handle number rounds/bustmode
    public EndFrame(GameFrame gameFrame) {
        this.players = gameFrame.getPlayers();
        frame = new JFrame("Eric Crandall Poker");
        initFrame(frame);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private void setupFrame() {
        // don't allow resize
        frame.setSize(320, 360);
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

    private void addButtonHandlers() {
        playButton.addActionListener(ae -> {
            new StartFrame(players);
            frame.dispose();
            SoundThread.getInstance().restartAudio();
        });

        exitButton.addActionListener(ae -> {
            frame.dispose();
            System.exit(0);
        });
    }

    // TODO: implement ties
    private JPanel genNorthPanel() {
        JPanel newPanel = new JPanel();
        northLabel = new JLabel("Tie!");
        northLabel.setFont(northLabel.getFont().deriveFont(24.0f));

        Player winningPlayer = players.get(0);
        boolean isTie = false;
        for (int i = 1; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getChips() == winningPlayer.getChips()) {
                isTie = true;
            } else if (p.getChips() > winningPlayer.getChips()) {
                winningPlayer = p;
                isTie = false;
            }
        }

        if (!isTie) {
            northLabel.setText(winningPlayer.getName() + " Won!");
        }

        newPanel.add(northLabel);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return newPanel;
    }

    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel(new GridLayout(7, 1));

        for (Player p : players) {
            JPanel panel = new JPanel();
            JLabel playerLabel = new JLabel(p.getName() + ":  " + p.getChips() + " Chips");
            playerLabel.setFont(playerLabel.getFont().deriveFont(18.0f));

            panel.add(playerLabel);
            newPanel.add(panel);
        }

        return newPanel;
    }

    // temporary, replace with betting/turn options
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel();

        playButton.setFont(playButton.getFont().deriveFont(16.0f));
        exitButton.setFont(exitButton.getFont().deriveFont(16.0f));

        newPanel.add(playButton);
        newPanel.add(exitButton);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return newPanel;
    }

    private void initFrame(JFrame frame) {
        setupFrame();
        addButtonHandlers();

        frame.setVisible(true);
    }
}
