/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Avion {

    private int centerX = 600; //coordonnées
    private int centerY = 540; //coordonnées

    public static int vie = 3;

    private int vitesseX = 0;
    private int vitesseY = 0;

    private int height;
    private int width;

    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    private boolean movingUp = false;
    private boolean movingDown = false;

    private Image image;
    private Image drawingimage;
    private Image imageMoveUp;
    private Image imageMoveDown;
    private Image imageMoveLeft;
    private Image imageMoveRight;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private Rectangle r;

    private static int globalSpeed;

    public Avion(String name) {

        r = new Rectangle(0, 0, 0, 0);
        image = toolkit.getImage("src/res/" + name + ".png");
        drawingimage = toolkit.getImage("src/res/" + name + ".png");
        if (name.equals("MiG-51S")) {
            height = 48;
            width = 87;
            globalSpeed = 4;
            imageMoveDown = toolkit.getImage("src/res/mini-plan1.png");
            imageMoveUp = toolkit.getImage("src/res/mini-plan1-onMove.png");
            imageMoveLeft = toolkit.getImage("src/res/moveLeft.png");
            imageMoveRight = toolkit.getImage("src/res/moveRight.png");
        } else if (name.equals("F_A-28A-mini")) {
            height = 60;
            width = 89;
            globalSpeed = 6;
            imageMoveDown = image;
            imageMoveUp = image;
            imageMoveLeft = image;
            imageMoveRight = image;
        } else if (name.equals("Su-51K-mini")) {
            height = 80;
            width = 96;
            globalSpeed = 9;
            imageMoveDown = image;
            imageMoveUp = image;
            imageMoveLeft = image;
            imageMoveRight = image;
        } else {
            height = 48;
            width = 87;
            globalSpeed = 12;
            imageMoveDown = image;
            imageMoveUp = image;
            imageMoveLeft = image;
            imageMoveRight = image;
        }

    }

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

            }

        } else if (vitesseY > 0) { //si vitesseY est posetive ==> deplacement en bas
            if (centerY < 500) {//pour ne pas depasser les bornes
                centerY += vitesseY;
            }

        }
        r.setRect(centerX, centerY, height, width);

        if (StartingClass.partie.getNiveau() == 4) {
            checkCollisionShootEnemie();
        }

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void shoot() {
        Projectile p = new Projectile(centerX + 24, centerY - 10, false);
        projectiles.add(p);

    }

    public void destroy() {
        vie = vie - 1;
        MediaPlayer.playSound("/sound/Explosion.wav");
        drawingimage = toolkit.getImage("src/res/explode.gif");
    }

    public void up() {
        vitesseY = -globalSpeed;
    }

    public void down() {
        vitesseY = globalSpeed;
    }

    public void moveRight() {
        vitesseX = globalSpeed;

    }

    public void moveLeft() {
        vitesseX = -globalSpeed;
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

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getImageMoveUp() {
        return imageMoveUp;
    }

    public void setImageMoveUp(Image imageMoveUp) {
        this.imageMoveUp = imageMoveUp;
    }

    public Image getImageMoveDown() {
        return imageMoveDown;
    }

    public void setImageMoveDown(Image imageMoveDown) {
        this.imageMoveDown = imageMoveDown;
    }

    public Image getImageMoveLeft() {
        return imageMoveLeft;
    }

    public void setImageMoveLeft(Image imageMoveLeft) {
        this.imageMoveLeft = imageMoveLeft;
    }

    public Image getImageMoveRight() {
        return imageMoveRight;
    }

    public void setImageMoveRight(Image imageMoveRight) {
        this.imageMoveRight = imageMoveRight;
    }

    public static int getGlobalSpeed() {
        return globalSpeed;
    }

    public Image getDrawingimage() {
        return drawingimage;
    }

    public void setDrawingimage(Image drawingimage) {
        this.drawingimage = drawingimage;
    }

    public static void setGlobalSpeed(int globalSpeed) {
        Avion.globalSpeed = globalSpeed;
    }

    public void checkCollisionShootEnemie() {
        for (int i = 0; i < Attack.avionEnnemis.size(); i++) {
            for (int j = 0; j < Attack.avionEnnemis.get(i).getProjectiles().size(); j++) {
                if (this.getR().intersects(Attack.avionEnnemis.get(i).getProjectiles().get(j).getR()) == true) {
                    Attack.avionEnnemis.get(i).getProjectiles().remove(j);
                    StartingClass.avion.destroy();

                }

            }
        }
    }
}
