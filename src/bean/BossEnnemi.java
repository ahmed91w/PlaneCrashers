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
import java.util.List;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class BossEnnemi extends Ennemi implements Runnable {
    
    private int maxHealth, currentHealth, power, vitesse;
    
    public static List<Projectile> projectiles = new ArrayList<>();
    
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    public BossEnnemi() {
        maxHealth = 20;
        currentHealth = maxHealth;
        vitesse = StartingClass.partie.getNiveau();
        setCenterX(600);
        setCenterY(0);
        getR().setBounds(this.getCenterX(), this.getCenterY(), 150, 80);
        setImage(toolkit.getImage("src/res/Boss B-3.mini.png"));
        
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
        if (currentHealth == 0) {
            destroy();
        } else {
            if (vitesse > 0) {
                if (this.getCenterX() < 1300) {
                    this.setCenterX(this.getCenterX() + vitesse);
                }
                
            } else if (vitesse < 0) {
                if (this.getCenterX() > 10) {
                    this.setCenterX(this.getCenterX() + vitesse);
                }
            }
            
            follow();
            
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
            this.setCenterX(StartingClass.avion.getCenterX() - 10);
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
            if (this.getCenterX() >= 1300) {
                vitesse = -StartingClass.partie.getNiveau();
            } else if (this.getCenterX() <= 10) {
                vitesse = StartingClass.partie.getNiveau();
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
        this.setCenterY(this.getCenterY() - 2);
        
    }
    
    public void removeBossProjectiles() {
        for (int i = 0; i < this.getProjectiles().size(); i++) {
            if (this.getProjectiles().get(i).getY() > 700) {
                this.getProjectiles().remove(i);
            }
        }

    }
}
