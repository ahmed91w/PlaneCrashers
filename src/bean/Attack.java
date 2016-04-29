/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class Attack implements Runnable {

    public static List<AvionEnnemi> avionEnnemis = new ArrayList<>();

    public Attack() {

    }

    public static List<AvionEnnemi> getAvionEnnemis() {
        return avionEnnemis;
    }

    public static void setAvionEnnemis(List<AvionEnnemi> avionEnnemis) {
        Attack.avionEnnemis = avionEnnemis;
    }

    public void removeEnnemisOverLimitte() {

        for (int i = 0; i < avionEnnemis.size(); i++) {

            if (avionEnnemis.get(i).getCenterY() >= 700) {
                avionEnnemis.get(i).getMoveAvionEnnemi().stop();
                avionEnnemis.remove(i);
            }

        }

    }

    public static void stopAllEnnemi() {
        for (int i = 0; i < avionEnnemis.size(); i++) {
            if (avionEnnemis.get(i).getMoveAvionEnnemi().isAlive()) {
                avionEnnemis.get(i).getMoveAvionEnnemi().stop();
            }

        }

    }

    public void startShooting(AvionEnnemi a) {

        a.getShoot().shoot.start();
        MediaPlayer.playSound("/sound/weapon_enemy.wav");
        try {
            a.getShoot().shoot.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AvionEnnemi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void StopShoting(AvionEnnemi a) {
        if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
            a.getShoot().shoot.stop();
        }
    }

    @Override
    public void run() {
        while (StartingClass.avion.vie != 0) {
//            if (avionEnnemis.size() < StartingClass.partie.getNiveau()) {
//            System.out.println("Still Runnung Attack !!!!");
            AvionEnnemi a = AvionEnnemi.newEnnemi();
            a.getMoveAvionEnnemi().start();
            avionEnnemis.add(a);
            if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
                startShooting(a);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            } else if (StartingClass.partie.getNiveau() == 2) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

//            }
        }
    }

}
