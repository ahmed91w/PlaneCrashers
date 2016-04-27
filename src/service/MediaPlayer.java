/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import jetGame.StartingClass;

public class MediaPlayer implements Runnable {

    public static Clip clip;

    public MediaPlayer() {
    }

    public static Clip getClip() {
        return clip;
    }

    public static void setClip(Clip clip) {
        MediaPlayer.clip = clip;
    }

    public static synchronized void playBgSound(final String url) {

        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            StartingClass.class.getResourceAsStream(url));
                    clip.open(inputStream);
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (Exception e) {
                    System.out.println("sound playing " + url);
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            StartingClass.class.getResourceAsStream(url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("sound playing " + url);
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void playMP3(final String file) {
        javafx.scene.media.Media hit = new javafx.scene.media.Media(file);
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(hit);
        mediaPlayer.play();
    }

    public static synchronized void Stop() {
        clip.stop();
    }

    @Override
    public void run() {

    }

}
