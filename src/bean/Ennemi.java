/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Ennemi {

    private int vitesseY, vitesseX, centerX, centerY;

    private boolean onFire = false;

    private ArrierePlan bg = StartingClass.getBg1();

    private List<Projectile> projectiles = new ArrayList<>();

    private boolean detruit = false;

    private boolean canShoot;

    private Image image;

    private Niveau niveau;

    private Rectangle r = new Rectangle();


    public void update() {
        if (detruit == false) {
            centerY -= vitesseY;
            vitesseY = -StartingClass.partie.getNiveau();
            if (vitesseX > 0) {
                if (this.getCenterX() < 1270) {
                    this.setCenterX(this.getCenterX() + vitesseX);
                }
            } else if (vitesseX < 0) {
                if (this.getCenterX() > 20) {
                    this.setCenterX(this.getCenterX() + vitesseX);
                }
            }
            if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
                follow();
            }

            r.setBounds(centerX, centerY, 69, 60);

//           collisionWithAvion(StartingClass.avion);
        }
       
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

    public void shootMal() {
        Projectile p = new Projectile(this.getCenterX() + 25, this.getCenterY() + 25, true);
//        p.setVisible(true);
        projectiles.add(p);
       
    }

    public boolean checkCollision(Rectangle rect) {
        if (rect.intersects(r)) {
            

            return true;

        }
        return false;
    }

    public void collisionWithAvion(Avion a) {
        if (checkCollision(StartingClass.avion.getR()) == true) {
            System.out.println("VIE --");
            StartingClass.avion.vie = StartingClass.avion.vie - 1;
        }
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
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

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

}
