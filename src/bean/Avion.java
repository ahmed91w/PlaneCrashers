/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Rectangle;
import java.util.ArrayList;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Avion {

    final int MOVESPEED = 1;

    private int centerX = 600; //coordonnées
    private int centerY = 540; //coordonnées
    private boolean hasMessile = true;
    private static int vie = 3;

    private int vitesseX = 0;
    private int vitesseY = 0;

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private boolean movingUp = false;
    private boolean movingDown = false;

    private static ArrierePlan bg1 = StartingClass.getBg1();
    private static ArrierePlan bg2 = StartingClass.getBg2();

    private static Rectangle collusion=new Rectangle(0, 0, 0, 0);

    public void update() {

        // deplacement de l'Avion
        if (vitesseX < 0) {// si vitesseX est négative ==> deplacement a gauche
            if (centerX > 20) {//pour ne pas depasser les bornes
                centerX += vitesseX;
            }
        } else if (vitesseX > 0) {// si vitesseX est posetive ==> deplacement a droite
            if (centerX < 1270) {//pour ne pas depasser les bornes
                centerX += vitesseX;
            }
        } else if (vitesseY < 0) { //si vitesseY est négative ==> deplacement en haut
            if (centerY > 20) {//pour ne pas depasser les bornes
                centerY += vitesseY;
                bg1.setVitesseY(-MOVESPEED);
                bg2.setVitesseY(-MOVESPEED);
            }

        } else if (vitesseY > 0) { //si vitesseY est posetive ==> deplacement en bas
            if (centerY < 500) {//pour ne pas depasser les bornes
                centerY += vitesseY;
            }

        }

    }

    public void shoot() {
        Projectile p = new Projectile(centerX + 24, centerY - 10);
        projectiles.add(p);

    }

    public void removeShoot() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).getY() <= 0) {
                System.out.println("Suppression du projectile a y=" + projectiles.get(i).getY());
                projectiles.remove(i);

            }

        }

    }

    public void up() {
        vitesseY = -6;
    }

    public void down() {
        vitesseY = 6;
    }

    public void moveRight() {
        vitesseX = 6;

    }

    public void moveLeft() {
        vitesseX = -6;
    }

    public void stopUP() {
        setMovingUp(false);
        stop();
    }

    public void stopDown() {
        setMovingDown(false);
        stop();
    }

    public void stop() {
        if (isMovingDown() == false && isMovingUp() == false) {
            vitesseX = 0;
            vitesseY = 0;
        }
        if (isMovingDown() == false && isMovingUp() == true) {
            up();
        }

        if (isMovingDown() == true && isMovingUp() == false) {
            down();
        }

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

    public static int getVie() {
        return vie;
    }

    public static void setVie(int vie) {
        Avion.vie = vie;
    }

    public int getVitesseX() {
        return vitesseX;
    }

    public void setVitesseX(int vitesseX) {
        this.vitesseX = vitesseX;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(int vitesseY) {
        this.vitesseY = vitesseY;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean isHasMessile() {
        return hasMessile;
    }

    public void setHasMessile(boolean hasMessile) {
        this.hasMessile = hasMessile;
    }

}
