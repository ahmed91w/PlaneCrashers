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
public class ArrierePlan {
    private int bgX, bgY;
    private int vitesseY=1;//vitesse de defilement

    public ArrierePlan(int x, int y) {
        this.bgX = x;
        this.bgY = y;
        this.vitesseY = 0;
    }

    public void update() {
        bgY -= vitesseY;
        if (bgY <= -2489) {
            bgY += (2489*2);
        }
    }

    public int getBgX() {
        return bgX;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(int vitesseY) {
        this.vitesseY = vitesseY;
    }

}
