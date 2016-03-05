/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Rectangle;
import java.util.Random;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Ennemi extends Thread {

    private int maxHealth, currentHealth, power, vitesseY, centerX, centerY;
    private boolean onFire=false;
    private ArrierePlan bg = StartingClass.getBg1();

    private boolean detruit = false;
    private Rectangle r = new Rectangle();

    public void update() {

        centerY -= vitesseY;
        vitesseY = - 3;
        r.setBounds(centerX, centerY, 40, 40);
        //checkCollision(Avion.collision);

    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public ArrierePlan getBg() {
        return bg;
    }

    public void setBg(ArrierePlan bg) {
        this.bg = bg;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(int vitesseY) {
        this.vitesseY = vitesseY;
    }

    public boolean destroyTis() {
        return true;
    }

    public boolean isDetruit() {
        return detruit;
    }

    public void setDetruit(boolean detruit) {
        this.detruit = detruit;
    }
    
    public boolean checkCollision(Rectangle rect){
        if(rect.intersects(r)){
            System.out.println("Collision detected!");
            onFire=true;
            return true;
            
        }
        return false;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

}
