package edu.gonzaga.events.gui;

import edu.gonzaga.utils.SoundThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class HydraListener extends WindowAdapter {

    private int hydraCounter = 0;

    public static HydraListener INSTANCE = null;

    public static HydraListener getInstance() {
        return INSTANCE != null ? INSTANCE : new HydraListener();
    }
    public static final SoundThread sound = SoundThread.getInstance();


    private HydraListener() {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        JFrame hydra1 = new JFrame("Hydra " + ++hydraCounter);
        JFrame hydra2 = new JFrame("Hydra " + ++hydraCounter);
        hydra1.setSize(640, 360);
        hydra2.setSize(640, 360);
        Random rand = new Random();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int xLoc = dim.width/2-hydra1.getSize().width/2;
        int yLoc = dim.height/2-hydra1.getSize().height/2;
        int rand1 = rand.nextInt(600) - 300;
        int rand2 = rand.nextInt(600) - 300;
        int rand3 = rand.nextInt(600) - 300;
        int rand4 = rand.nextInt(600) - 300;
        hydra1.setLocation(rand1+ xLoc, rand2 + yLoc);
        hydra2.setLocation(rand3 + xLoc, rand4 + yLoc);
        hydra1.setVisible(true);
        hydra2.setVisible(true);
        hydra1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hydra2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hydra1.addWindowListener(this);
        hydra2.addWindowListener(this);
    }
}
