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
import static jetGame.StartingClass.g;

public class MediaPlayer {

    public MediaPlayer() {
    }

//    public static synchronized void playBgSound(final String url) {
//
//        new Thread(new Runnable() {
//            // The wrapper thread is unnecessary, unless it blocks on the
//            // Clip finishing; see comments.
//            public void run() {
//                try {
//                    Clip clip = AudioSystem.getClip();
//                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//                            StartingClass.class.getResourceAsStream(url));
//                    clip.open(inputStream);
//
//                    clip.start();
//                    clip.loop(Clip.LOOP_CONTINUOUSLY);
//                } catch (Exception e) {
//                    System.out.println("sound playing " + url);
//                    System.err.println(e.getMessage());
//                }
//            }
//        }, "SFX Thread").start();
//
//    }
//
//    public static void playSound(final String url) {
//
////        new Thread(StartingClass.g, new Runnable() {
////            // The wrapper thread is unnecessary, unless it blocks on the
////            // Clip finishing; see comments.
////            public void run() {
//        try {
//            Clip clip = AudioSystem.getClip();
//            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//                    StartingClass.class.getResourceAsStream(url));
//            clip.open(inputStream);
//            clip.start();
//            Thread.sleep(500);
//            clip.stop();
//         
//
//        } catch (Exception e) {
//            System.out.println("sound playing " + url);
//            System.err.println(e.getMessage());
//        }
//    }
//        }, "SFX Thread").start();

}
