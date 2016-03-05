/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Projectile {

    private int x, y, vitesseY;
    private boolean visible;
    private Image bullet;
    private Rectangle r = new Rectangle();

    public Projectile() {
    }

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        vitesseY = 7;
        visible = true;
       
        
    }

    public void update() {
        y -= vitesseY;
        if (y < 0) {
            visible = false;
        }
        r.setBounds(this.x, this.y, 40, 40);
        //checkCollision(AvionEnnemi.r);

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
    
    public void checkCollision(Rectangle rect){
        if(rect.intersects(this.r)){
            System.out.println("Collision detected![Projectile]");
            visible=false;
        }
    }

}
