/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
class ClipControl {

    public static void main(String[] args) throws Exception {
        String url = "/res/sound/2.wav";
        AudioInputStream ais = AudioSystem.getAudioInputStream(StartingClass.class.getResourceAsStream(url));
        final Clip clip = AudioSystem.getClip();
        clip.open(ais);
        Runnable r = new Runnable() {
            public void run() {
                final JToggleButton startStop = new JToggleButton("Stop");
                startStop.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if (startStop.isSelected()) {
                            clip.stop();
                            startStop.setText("Start");
                        } else {
                            clip.loop(-1);
                            clip.start();
                            startStop.setText("Stop");
                        }
                    }
                });
                clip.loop(-1);
                JOptionPane.showMessageDialog(null, startStop);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
