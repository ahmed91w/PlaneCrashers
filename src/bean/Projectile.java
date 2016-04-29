/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class Projectile {

    private int x, y, vitesseY;
    private Image bullet;
    private Rectangle r = new Rectangle();
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public Projectile(int x, int y, boolean isEnnemie) {
        this.x = x;
        this.y = y;

        if (isEnnemie) {
            vitesseY = (StartingClass.partie.getNiveau() + 7);
            bullet = toolkit.getImage("src/res/tiremal.png");
        } else {
            vitesseY = 9;
            bullet = toolkit.getImage("src/res/tire1.png");
        }

    }

    public void update() {
        
        if (y > 0) {
//            vitesseY = 9;
            y -= vitesseY;
            r.setBounds(this.x, this.y, 10, 20);
//            System.out.println("Proje Avion "+y);
        } else {
            StartingClass.avion.projectiles.remove(this);
        }

    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    public void updateProjectileEnnemi() {
        if (y < 700) {
            if (checkCollision(StartingClass.avion.getR())) {
                if (StartingClass.avion.getBouclier() == 0) {
                    this.setY(700);
                    StartingClass.avion.destroy();
                } else {
                    this.setY(700);
                    StartingClass.avion.setBouclier(StartingClass.avion.getBouclier() - 1);
                }

            }
            y += vitesseY;
            r.setBounds(this.x, this.y, 10, 20);
//            System.out.println("Proje Ennemie "+y);
        }
    }

    public void updateProjectileBoss() {
        if (y < 700) {
            if (checkCollision(StartingClass.avion.getR())) {
                if (StartingClass.avion.getBouclier() == 0) {
                    this.setY(700);
                    StartingClass.avion.destroy();
                } else {
                    this.setY(700);
                    StartingClass.avion.setBouclier(StartingClass.avion.getBouclier() - 1);

                }

            }
            y += vitesseY;
            r.setBounds(this.x, this.y, 10, 20);
        } else {
            BossEnnemi.projectiles.remove(this);
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
            return true;
        }
        return false;
    }

}
