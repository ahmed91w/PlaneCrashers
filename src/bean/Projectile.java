/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Projectile {

    private int x, y, vitesseY;
    private boolean visible;
    private Image bullet;

    public Projectile() {
    }

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        vitesseY = 1;
        visible = true;
    }

    public void update() {
        vitesseY = 7;
        y -= vitesseY;
        if (y < 0) {
            visible = false;
        }

    }

    public void updateProjectileEnnemi() {
        vitesseY = 3;
        y += vitesseY;
        if (y > 700) {
            visible = false;
        }

    }

    public int getX() {
        return x;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Image getBullet() {
        return bullet;
    }

    public void setBullet(Image bullet) {
        this.bullet = bullet;
    }

}
