/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class AvionEnnemi extends Ennemi implements Runnable {

    private List<AvionEnnemi> avionEnnemis = new ArrayList<>();

    public AvionEnnemi(int x, int y) {
        this.setCenterX(x);
        this.setCenterY(y);

    }

    public static AvionEnnemi newEnnemi() {
        Random random = new Random();
        random.nextInt(1200);
        AvionEnnemi ae = new AvionEnnemi(random.nextInt(1200), 0);
        return ae;
    }

    public List<AvionEnnemi> getAvionEnnemis() {
        return avionEnnemis;
    }

    public void setAvionEnnemis(List<AvionEnnemi> avionEnnemis) {
        this.avionEnnemis = avionEnnemis;
    }

    @Override
    public void run() {
        while (true) {
            avionEnnemis.add(AvionEnnemi.newEnnemi());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
