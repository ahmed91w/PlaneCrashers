/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class Shoot implements Runnable {

    private AvionEnnemi ae;
    public Thread shoot;

    public Shoot(AvionEnnemi ae) {
        this.ae = ae;
        this.shoot = new Thread(this);
    }

    @Override
    public void run() {
        while (ae.getProjectiles().size() != 4) {
            ae.shoot();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Shoot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
