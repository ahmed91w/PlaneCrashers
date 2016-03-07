/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class BossEnnemi extends Ennemi implements Runnable {

    private int maxHealth, currentHealth, power, vitesse;

    public static List<Projectile> projectiles = new ArrayList<>();

    public BossEnnemi() {
        maxHealth = 20;
        currentHealth = maxHealth;
        vitesse = 2;
        this.setCenterX(600);
        this.setCenterY(0);
        this.getR().setBounds(this.getCenterX(), this.getCenterY(), 150, 80);

    }

    @Override
    public boolean checkCollision(Rectangle rect) {
        if (rect.intersects(this.getR())) {
            System.out.println("BOSS Collision detected!");
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (currentHealth == 0) {
            destroy();
        } else {
            follow();
            if (vitesse > 0) {
                if (this.getCenterX() < 1300) {
                    this.setCenterX(this.getCenterX() + vitesse);
                    System.out.println("Boss x=" + this.getCenterX());
                }

            } else if (vitesse < 0) {
                if (this.getCenterX() > 10) {
                    this.setCenterX(this.getCenterX() + vitesse);
                    System.out.println("Boss x=" + this.getCenterX());
                }
            }
            this.getR().setBounds(this.getCenterX(), this.getCenterY(), 130, 240);
        }

    }

    @Override
    public void follow() {

        if (this.getCenterX() < StartingClass.avion.getCenterX()) {
            vitesse = 2;
        } else if (this.getCenterX() > StartingClass.avion.getCenterX()) {
            vitesse = -2;
        } else if (this.getCenterX() == StartingClass.avion.getCenterX()) {
            vitesse = 0;
            this.setCenterX(StartingClass.avion.getCenterX());
        }
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

    @Override
    public void run() {
        while (currentHealth != 0) {

            this.shootMal();
            System.out.println("thread shoot BOSSSSS");

            if (this.getCenterX() >= 1300) {
                vitesse = -2;
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Vitesse " + vitesse);
            } else if (this.getCenterX() <= 10) {
                vitesse = 2;
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>Vitesse " + vitesse);
            }

//            this.update();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void shootMal() {
        removeBossProjectiles();
        Projectile p = new Projectile(this.getCenterX() + 60, this.getCenterY() + 200);
//        p.setVisible(true);
        projectiles.add(p);
        System.out.println("nombre de shoot Boss " + projectiles.size());

    }

    public void moveRight() {
        vitesse = 2;

    }

    public void moveLeft() {
        vitesse = -2;
    }

    public void removeShoot() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).getY() <= 700) {
                System.out.println("Suppression du projectile a y=" + projectiles.get(i).getY());
                projectiles.remove(i);

            }

        }

    }

    public void destroy() {
        this.setCenterY(this.getCenterY() - 3);
    }

    public void removeBossProjectiles() {
//        synchronized (bossEnnemi.getProjectiles()) {
        for (int i = 0; i < this.getProjectiles().size(); i++) {
            if (this.getProjectiles().get(i).getY() > 700) {
                this.getProjectiles().remove(i);
            }
        }

//        }
    }
}
