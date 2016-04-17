/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class AvionEnnemi extends Ennemi implements Runnable {

//    private List<AvionEnnemi> avionEnnemis = new ArrayList<>();
    private Thread moveAvionEnnemi;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
//    private boolean destroy = false;
    public AvionEnnemi(int x, int y) {
        
        setCenterX(x);
        setCenterY(y);
        moveAvionEnnemi = new Thread(this);
        this.setImage(toolkit.getImage("src/res/ennemi-mini.png"));
    }

//    @Override
//    public void update() {
//        if (this.isDetruit() == false) {
//            this.setCenterX(this.getCenterX() - this.getVitesseX());
//            this.setVitesseY(this.getCenterY() - 4);
//            if (this.getVitesseX() > 0) {
//                if (this.getCenterX() < 1200) {
//                    this.setCenterX(this.getCenterX() + this.getVitesseX());
//                }
//            } else if (this.getVitesseX() < 0) {
//                if (this.getCenterX() > 20) {
//                    this.setCenterX(this.getCenterX() + this.getVitesseX());
//                }
//            }
//
//            follow();
//            this.getR().setBounds(this.getCenterX(), this.getCenterY(), 40, 40);
//        } else {
//            this.setCenterX(this.getCenterX() + 5);
//            this.setCenterY(this.getCenterY() + 1);
//        }
//
//        //checkCollision(Avion.collision);
//    }
    public void destroy() {
        this.setDetruit(true);
        this.setImage(toolkit.getImage("src/res/explode.gif"));
        try {
            Thread.sleep(1050);
        } catch (InterruptedException ex) {
            Logger.getLogger(AvionEnnemi.class.getName()).log(Level.SEVERE, null, ex);
        }
        moveAvionEnnemi.stop();

        System.out.println("enemie Stooped");

    }

    public void startAvion() {
        this.update();
        moveAvionEnnemi.start();
    }

    public static AvionEnnemi newEnnemi() {

        Random random = new Random();
        random.nextInt(1200);
        AvionEnnemi ae = new AvionEnnemi(random.nextInt(1200), 0);
        return ae;
    }

//    public List<AvionEnnemi> getAvionEnnemis() {
//        return avionEnnemis;
//    }
//    public void setAvionEnnemis(List<AvionEnnemi> avionEnnemis) {
//        this.avionEnnemis = avionEnnemis;
//    }
    @Override
    public void run() {
        while (this.getMoveAvionEnnemi().isAlive()) {
//            if (this.getCenterY() < 700) {
            update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
//            }
//            else {
//                this.destroy();
//                System.out.println("---------------> Ennemi auto-destroyed");
//            }
        }
    }

    public Thread getMoveAvionEnnemi() {
        return moveAvionEnnemi;
    }

    public void setMoveAvionEnnemi(Thread moveAvionEnnemi) {
        this.moveAvionEnnemi = moveAvionEnnemi;
    }

}
