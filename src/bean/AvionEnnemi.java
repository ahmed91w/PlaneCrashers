/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Random;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class AvionEnnemi extends Ennemi {

    Thread t;

    public AvionEnnemi(int x, int y) {
        this.setCenterX(x);
        this.setCenterY(y);

    }

    public AvionEnnemi newEnnemi() {
        Random random = new Random();
        random.nextInt(1200);
        AvionEnnemi ae = new AvionEnnemi(random.nextInt(1200), 0);
        return ae;
    }

}
