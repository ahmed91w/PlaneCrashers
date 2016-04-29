/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class PlaySound implements Runnable {

    private Clip clip;
    private Thread start = new Thread(this, "PlaySound");

    public void initClip(String url) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                StartingClass.class.getResourceAsStream(url));
        clip.open(inputStream);
    }

    @Override
    public synchronized void run() {
        clip.start();
        try {
            start.wait(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void StopPlay() {
        start.stop();
        clip.stop();
    }
    
    
}
