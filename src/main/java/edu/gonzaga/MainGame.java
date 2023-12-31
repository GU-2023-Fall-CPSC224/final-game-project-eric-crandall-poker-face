/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 *   This is our version of the popular game Texas Hold'em. On the start screen,
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

import edu.gonzaga.events.TurnButtonListener;
import edu.gonzaga.events.backend.EventExecutor;
import edu.gonzaga.events.backend.EventManager;
import edu.gonzaga.items.Player;
import edu.gonzaga.items.StartFrame;
import edu.gonzaga.utils.SoundThread;

import java.util.ArrayList;

/** Main program class for launching your team's program. */
public class MainGame {

    public static boolean hydra = false;

    public static SoundThread sound;

    public static EventManager getManager() {
        return manager;
    }

    private static EventManager manager;

    public static void main(String[] args) {
        EventExecutor executor = new EventExecutor();
        manager = new EventManager(executor);
        sound = SoundThread.getInstance();
        sound.startSong();

        ArrayList<Player> players = new ArrayList<>();

        new StartFrame(players);
        new TurnButtonListener();
        
        System.out.println("Hello Team Game");
    }
}
