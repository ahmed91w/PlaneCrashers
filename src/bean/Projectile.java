/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledEditorKit;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Projectile {

    private int x, y, vitesseY;
    private Image bullet;
    private Rectangle r = new Rectangle();
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public Projectile(int x, int y, boolean isEnnemie) {
        this.x = x;
        this.y = y;
        vitesseY = 1;
        if (isEnnemie) {
            bullet = toolkit.getImage("src/res/tire1.png");
        } else {
            bullet = toolkit.getImage("src/res/tire1.png");
        }

    }

    public void update() {
        vitesseY = 2;
        y -= vitesseY;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>projectil se deplace a " + y);

        System.out.println(">>>>>>>>>>>>>>>>>>>>> Y " + y);
        r.setBounds(this.x, this.y, 10, 20);
        System.out.println("BOUNDS OF RECT PROJECTILES X");

        for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
            if (Attack.getAvionEnnemis().get(i).checkCollision(this.getR())) {
                Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().stop();
                Attack.getAvionEnnemis().remove(i);
                removerThis();
            }
        }
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    public void updateProjectileEnnemi() {
        vitesseY = 3;
        y += vitesseY;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>Y:" + y);
//        if (this.getR().intersects(StartingClass.avion.getR())==true) {
//            StartingClass.avion.vie--;
//        }
        if (y >= 700) {
//           
            removerThisBoss();
            System.out.println("shoot removed");
        }

    }

    public int getX() {
        return x;
    }

    public void removerThis() {
        for (int i = 0; i < Avion.projectiles.size(); i++) {
            if (Avion.projectiles.get(i) == this) {
                Avion.projectiles.remove(i);
            }
        }
    }

    public void removerThisBoss() {
        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {
            if (BossEnnemi.projectiles.get(i) == this) {
                BossEnnemi.projectiles.remove(i);
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(int vitesseY) {
        this.vitesseY = vitesseY;
    }

    public Image getBullet() {
        return bullet;
    }

    public void setBullet(Image bullet) {
        this.bullet = bullet;
    }

    public boolean checkCollision(Rectangle rect) {
        if (rect.intersects(r)) {
            System.out.println("Collision detected![Projectile]");
            return true;
        }
        return false;
    }

//    @Override
//    public void run() {
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>thread Projectil");
//        update();
//
//        try {
//            Thread.sleep(500);
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Projectile.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//    public Thread getMoveProj() {
//        return moveProj;
//    }
//
//    public void setMoveProj(Thread moveProj) {
//        this.moveProj = moveProj;
//    }
}
