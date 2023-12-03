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
public class EndFrame {
    
    private ArrayList<Player> players;

    private final JFrame frame;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;

    JLabel northLabel = new JLabel("Player x won!");
    JButton playButton = new JButton("Play Again");
    JButton exitButton = new JButton("Exit");

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

        newPanel.add(northLabel);
        newPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        return newPanel;
    }

    private JPanel genCenterPanel() {
        JPanel newPanel = new JPanel(new GridLayout(7, 1));

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            JPanel panel = new JPanel();
            JLabel playerLabel = new JLabel(p.getName() + ": " + p.getScore());

            panel.add(playerLabel);
            newPanel.add(panel);
        }

        return newPanel;
    }

    // temporary, replace with betting/turn options
    private JPanel genSouthPanel() {
        JPanel newPanel = new JPanel();

        newPanel.add(playButton);
        newPanel.add(exitButton);

        return newPanel;
    }

    private void initFrame(JFrame frame) {
        setupFrame();

        frame.setVisible(true);

    }
}
