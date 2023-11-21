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
            this.ais = AudioSystem.getAudioInputStream(file);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            this.clip = (Clip) AudioSystem.getLine(info);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } finally {
            this.start();
            INSTANCE = this;
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Thread started.");
        if (this.clip != null && ais != null) {
            try {
                clip.open(ais);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        } else throw new RuntimeException("InputStream or Clip is null!");
    }


    public void startSong() {
        while (true) {
            if (this.clip.isOpen() && !this.clip.isRunning()) {
                this.clip.start();
            } else if (this.clip.isOpen()) break;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                this.interrupt();
            }
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
