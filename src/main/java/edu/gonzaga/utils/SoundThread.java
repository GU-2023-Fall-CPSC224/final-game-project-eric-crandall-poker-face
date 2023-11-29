package edu.gonzaga.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundThread extends Thread {

    public static SoundThread INSTANCE = null;

    public static SoundThread getInstance() {
        return INSTANCE != null ? INSTANCE : new SoundThread();
    }

    private final File file = new File("src/main/resources/audio/PokerFace.wav");
    private final Clip clip;
    private final AudioInputStream ais;

    private SoundThread() {
        try {
            this.clip = AudioSystem.getClip();
            this.ais = AudioSystem.getAudioInputStream(file);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } finally {
            this.start();
        }
    }

    @Override
    public void run() {
        super.run();
        if (this.clip != null && ais != null) {
            try {
                clip.open(ais);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void startSong() {
        if (this.clip.isOpen() && !this.clip.isRunning()) {
            this.clip.start();
        }
    }

    public void stopSong() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }


    public boolean isPlaying() {
        return this.clip.isRunning();
    }

}
