package edu.gonzaga.events.gui;

import edu.gonzaga.utils.SoundThread;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseWindowListener extends WindowAdapter {

    public static CloseWindowListener INSTANCE = null;

    public static CloseWindowListener getInstance() {
        return INSTANCE != null ? INSTANCE : new CloseWindowListener();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        SoundThread.getInstance().destroyPlayer();
    }
}
