package edu.gonzaga.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundThread extends Thread {

    public static SoundThread INSTANCE = null;

    public static float DEFAULT_VOLUME = (100.0F + 35.0F) / 2;

    public static SoundThread getInstance() {
        return INSTANCE != null ? INSTANCE : new SoundThread();
    }

    private final File file = new File("src/main/resources/audio/PokerFace.wav");
    private Clip clip;
    private final AudioInputStream ais;

    private SoundThread() {
        try {
            this.clip = AudioSystem.getClip();
            this.ais = AudioSystem.getAudioInputStream(file);
            INSTANCE = this;
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
                setVolume(DEFAULT_VOLUME);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void startSong() {
        Future<Clip> song = startSongAsync(this.clip);
        try {
            this.clip = song.get();
            if (!this.clip.isRunning()) {
                this.clip.start();
            }
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, "There was an error while starting song!");
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<Clip> startSongAsync(Clip clip) {
        CompletableFuture<Clip> cf = new CompletableFuture<>();
        try {
            while (!(this.clip.isOpen() && !this.clip.isRunning())) {
                Thread.sleep(1000L);
            }
        } catch (InterruptedException ignored) {
        }
        cf.complete(clip);
        return cf;
    }

    public void stopSong() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }


    public boolean isPlaying() {
        return this.clip.isRunning();
    }

    public void setVolume(float v) {
        FloatControl gc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gc.getMaximum() - gc.getMinimum();
        float gain = (range * v / 100.0F) + gc.getMinimum();
        gc.setValue(gain);
    }

    public float getVolume() {
        FloatControl gc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gc.getMaximum() - gc.getMinimum();
        float volume = (gc.getValue() - gc.getMinimum()) / range;
        return volume;
    }

    public void restartAudio() {
        this.clip.setFramePosition(0);
    }

}
