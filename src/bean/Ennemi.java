/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Ennemi {

    private int vitesseY, vitesseX, centerX, centerY;

    private ArrierePlan bg = StartingClass.getBg1();

    private List<Projectile> projectiles = new ArrayList<>();

    private boolean detruit = false;

    private Image image;
    private Avion avion = StartingClass.avion;

    public void update() {

        centerY -= vitesseY;
        vitesseY = - 3;
        if (vitesseX > 0) {
            if (this.getCenterX() < 1200) {
                this.setCenterX(this.getCenterX() + vitesseX);

            }

        } else if (vitesseX < 0) {
            if (this.getCenterX() > 20) {
                this.setCenterX(this.getCenterX() + vitesseX);

            }
        }

        follow();

    }

    public void follow() {
        if (centerY < StartingClass.avion.getCenterY()) {
            if (centerX < StartingClass.avion.getCenterX()) {
                vitesseX = 3;
            } else if (centerX > StartingClass.avion.getCenterX()) {
                vitesseX = -3;
            } else if (centerX == StartingClass.avion.getCenterX()) {
                vitesseX = 0;
            }
        }

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public int getVitesseX() {
        return vitesseX;
    }

    public void setVitesseX(int vitesseX) {
        this.vitesseX = vitesseX;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

}
