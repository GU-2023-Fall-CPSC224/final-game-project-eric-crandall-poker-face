/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 * 
 * 
 * Contributors:
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;


import edu.gonzaga.events.EventExecutor;
import edu.gonzaga.events.EventManager;
import edu.gonzaga.events.gui.CloseWindowListener;
import edu.gonzaga.events.gui.HydraListener;
import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;

/** Main program class for launching your team's program. */
public class MainGame {

    public static boolean hydra = false;

    public static final SoundThread sound = SoundThread.getInstance();

    public static EventManager manager;


    public static void main(String[] args) {

        EventExecutor executor = new EventExecutor();
        manager = new EventManager(executor);

        System.out.println("Hello Team Game");
        JFrame frame1 = new JFrame("Hydra?");
        sound.start();

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

        frame1.setVisible(true);
        // Your code here. Good luck!
    }
}
