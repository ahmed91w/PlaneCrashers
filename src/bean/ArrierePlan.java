/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class ArrierePlan {

    private int bgX, bgY;
    private int vitesseY;//vitesse de defilement

    public ArrierePlan(int x, int y) {
        this.bgX = x;
        this.bgY = y;
        this.vitesseY = -1;
    }

    public void update() {
        bgY -= vitesseY;
        if (bgY >= 1750) {
            bgY = -(2 * 1450);
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
