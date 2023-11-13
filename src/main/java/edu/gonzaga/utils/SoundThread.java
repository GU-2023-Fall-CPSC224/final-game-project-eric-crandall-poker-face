package edu.gonzaga.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.IOException;

@Deprecated(forRemoval = true)
public class SoundThread extends Thread {

    public static SoundThread INSTANCE = null;

    public static SoundThread getInstance() {
        return INSTANCE != null ? INSTANCE : new SoundThread();
    }

    private final File file = new File("src/main/resources/audio/PokerFace.mp3");
    private final Player playMP3;

    private SoundThread() {
        try {
            playMP3 = new Player(file.toURI().toURL().openStream());
        } catch (JavaLayerException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.start();
        }
    }

    @Override
    public void run() {
        super.run();
        addSong();
    }


    public void addSong() {
        try {
            playMP3.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void destroyPlayer() {
        playMP3.close();
    }

}
