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


import edu.gonzaga.events.HydraListener;
import edu.gonzaga.utils.SoundThread;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/** Main program class for launching your team's program. */
public class MainGame {

    public static boolean hydra = true;

    public static final SoundThread sound = SoundThread.getInstance();


    public static void main(String[] args) throws IOException, JavaLayerException {
        System.out.println("Hello Team Game");
        JFrame frame1 = new JFrame("Hydra?");
        sound.start();

        frame1.setSize(640, 360);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setLocation(dim.width/2-frame1.getSize().width/2, dim.height/2-frame1.getSize().height/2);
        if (hydra) {
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.addWindowListener(HydraListener.getInstance());
        } else {
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        frame1.setVisible(true);
        sound.destroyPlayer();
        // Your code here. Good luck!
    }
}
