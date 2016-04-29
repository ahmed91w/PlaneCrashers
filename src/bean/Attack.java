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
import static jetGame.StartingClass.attack;


/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Attack implements Runnable {

    public static List<AvionEnnemi> avionEnnemis = new ArrayList<>();
    private static int nbr = 0;

    public Attack() {

    }

    public void removeEnnemisOverLimitte() {
        System.out.println("Removing Ennemies");
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
        //MediaPlayer.playSound("/res/sound/weapon_enemy.wav");
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

//            if (avionEnnemis.size() < StartingClass.partie.getNiveau()) {
//            System.out.println("Still Runnung Attack !!!!");
        while (StartingClass.avion.vie != 0) {
            if (avionEnnemis.size() < StartingClass.partie.getNiveau()) {

                System.out.println("Ennemie " + attack.avionEnnemis.size());

                System.out.println("Ennemie " + attack.avionEnnemis.size());
                AvionEnnemi a = new AvionEnnemi();
                a.getMoveAvionEnnemi().start();
                avionEnnemis.add(a);
                if (StartingClass.partie.getNiveau() == 4) {
                    startShooting(a);
                    if (StartingClass.bossEnnemi.getMove().isAlive() == false) {
                        if (nbr == 0) {
                            nbr++;
                            StartingClass.bossEnnemi.startMove();
                        }

                    }
                } else if (StartingClass.partie.getNiveau() == 3) {
                    startShooting(a);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
