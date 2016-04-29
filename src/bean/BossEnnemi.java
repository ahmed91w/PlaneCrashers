/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;
import static jetGame.StartingClass.partie;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class BossEnnemi extends Ennemi implements Runnable {

    private int maxHealth, currentHealth, power, vitesse;

    public static List<Projectile> projectiles = new ArrayList<>();

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    private Thread move;

    private Image healtBarEmpty;
    
    public BossEnnemi() {
        move = new Thread(this, "Boss Thread");
        maxHealth = 50;
        currentHealth = maxHealth;
        vitesse = -(StartingClass.partie.getNiveau());
        setCenterX(600);
        setCenterY(-250);
        getR().setBounds(this.getCenterX(), this.getCenterY(), 150, 80);
        setImage(toolkit.getImage("src/res/Boss B-3.mini.png"));
        healtBarEmpty = toolkit.getImage("src/res/BossHealthBarEmpty.png");
    }

    @Override
    public boolean checkCollision(Rectangle rect) {
        if (rect.intersects(this.getR())) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (vitesse > 0) {
            if (this.getCenterX() < 1200) {
                this.setCenterX(this.getCenterX() + vitesse);
            }

        } else if (vitesse < 0) {
            if (this.getCenterX() > 10) {
                this.setCenterX(this.getCenterX() + vitesse);
            }
        }
        this.getR().setBounds(this.getCenterX(), this.getCenterY(), 130, 240);
//        follow();
        for (int i = 0; i < StartingClass.avion.projectiles.size(); i++) {
            if (checkCollision(StartingClass.avion.projectiles.get(i).getR())) {
                StartingClass.avion.projectiles.remove(i);
                if (currentHealth > 1) {
                    currentHealth--;

                }

            }

        }

    }

    @Override
    public void follow() {

        if (this.getCenterX() < (StartingClass.avion.getCenterX() + 30)) {
            vitesse = (StartingClass.partie.getNiveau());
        }
        if (this.getCenterX() > (StartingClass.avion.getCenterX() - 30)) {
            vitesse = -(StartingClass.partie.getNiveau());
        }
        if (this.getCenterX() == (StartingClass.avion.getCenterX() - 30)) {
            vitesse = 0;
            this.setCenterX(StartingClass.avion.getCenterX() - 30);
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
        while (this.getCenterY() <= 5) {
            System.out.println("Boss moving down at " + getCenterY());
            setCenterY(getCenterY() + 7);
            vitesse = 0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        vitesse = -(StartingClass.partie.getNiveau());
        while (currentHealth > 1) {
            System.out.println("Boss Health " + currentHealth);
            if (projectiles.size() <= 1) {
                this.shootMal();
            }

            if (this.getCenterX() >= 1200) {
                vitesse = -(StartingClass.partie.getNiveau());
            } else if (this.getCenterX() <= 10) {
                vitesse = (StartingClass.partie.getNiveau());
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        destroy();

    }

    public void shootMal() {
        removeBossProjectiles();
        Projectile p = new Projectile(this.getCenterX() + 60, this.getCenterY() + 200, true);
        projectiles.add(p);

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
                projectiles.remove(i);
            }

        }

    }

    public void destroy() {
        synchronized (this) {

            setImage(toolkit.getImage("src/res/explod.gif"));
            //MediaPlayer.playSound("/res/sound/Explosion.wav");
            if (currentHealth == 0) {
                this.setCenterY(this.getCenterY() - 2);
            }
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BossEnnemi.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentHealth--;
            move.stop();

        }

    }

    public void removeBossProjectiles() {
        for (int i = 0; i < this.getProjectiles().size(); i++) {
            if (this.getProjectiles().get(i).getY() > 700) {
                this.getProjectiles().remove(i);
            }
        }

    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public Thread getMove() {
        return move;
    }

    public void setMove(Thread move) {
        this.move = move;
    }

    public void startMove() {
        if (move.isAlive() == false) {
            move.start();
        }

    }

    public void stopMove() {
        move.stop();
    }

    public void drawBossHealth(Graphics g, ImageObserver im) {
        g.drawImage(healtBarEmpty, 1060, 600, im);
        int distance = 0;   
        for (int i = 0; i < currentHealth; i++) {
            g.drawImage(toolkit.getImage("src/res/health_" + i + ".gif"), 1065 + distance, 600, im);
            distance = distance + 5;
        }
    }
}
