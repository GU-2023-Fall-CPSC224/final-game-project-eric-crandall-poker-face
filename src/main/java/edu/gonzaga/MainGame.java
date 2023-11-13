/*
 * Final project main driver class
 * 
 * 
 * Project Description: 
 *   For our version of the game, the tentative plan is to make a two player form of texas holdem. On the start screen,
 *   players will be able to enter their names, get assigned an icon, select the number of starting chips/cash
 *   they wish to have, or select the number of rounds the number of rounds they wish to play (by default this
 *   will be as many rounds as it takes for one player to gain all the chips). A standard deck of 52 cards is used
 *   and will be shuffled after each round of play.
 * 
 * 
 * Contributors: McEwan Bain, Jake VanZyverden, Gabriel Hoing
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;


import edu.gonzaga.events.backend.EventExecutor;
import edu.gonzaga.events.backend.EventManager;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;

/** Main program class for launching your team's program. */
public class MainGame {

    public static boolean hydra = false;

    public static SoundThread sound;

    public static EventManager manager;


    public static void main(String[] args) {

        EventExecutor executor = new EventExecutor();
        manager = new EventManager(executor);

        sound = SoundThread.getInstance();

        System.out.println("Hello Team Game");
        JFrame frame1 = new JFrame("Hydra?");
        frame1.setSize(640, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
        frame1.addWindowListener(CloseWindowListener.getInstance());
        if (hydra) {
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.addWindowListener(HydraListener.getInstance());
        } else {
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        frame1.setLayout(null);
        frame1.setVisible(true);
        // Your code here. Good luck!
    }
}
