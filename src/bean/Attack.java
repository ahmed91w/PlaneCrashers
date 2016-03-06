/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Attack implements Runnable {

    private static List<AvionEnnemi> avionEnnemis = new ArrayList<>();

    public Attack() {

    }

    public static List<AvionEnnemi> getAvionEnnemis() {
        return avionEnnemis;
    }

    public static void setAvionEnnemis(List<AvionEnnemi> avionEnnemis) {
        Attack.avionEnnemis = avionEnnemis;
    }

    public void revertAttack() {

    }

    @Override
    public void run() {
//        if (avionEnnemis.size() < 5) {
//        Random random = new Random();
        while (StartingClass.avion.vie != 0) {
            
            AvionEnnemi a = AvionEnnemi.newEnnemi();
            a.getMoveAvionEnnemi().start();
            avionEnnemis.add(a);
            
            System.out.println(">>>>>>>>>>>> nbr ennemis >>>>>>>>>" + avionEnnemis.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
//        }

    }

}
