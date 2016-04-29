/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import view.Home;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class AvionEnnemi extends Ennemi implements Runnable {

    private List<Projectile> projectiles;
    private Thread moveAvionEnnemi;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Shoot shoot;
    Random random = new Random();
    private int lv;//1 vert | 2 noir | 3 jaune

    public AvionEnnemi() {
        Random ranX = new Random();
        lv = random.nextInt((3 - 1) + 1) + 1;
        setCenterX(ranX.nextInt(1200));
        setCenterY(-80);
        moveAvionEnnemi = new Thread(this, "AvionEnnemie");
        if (StartingClass.partie.getNiveau() == 1) {
            setVitesseY(-StartingClass.partie.getNiveau() - 2);
        } else if (lv == 2) {
            setVitesseY(-StartingClass.partie.getNiveau() - 3);
        } else {
            setVitesseY(-StartingClass.partie.getNiveau() - 4);
        }
        setImage(toolkit.getImage("src/res/ennemiLvl" + lv + ".png"));
//        setImage(toolkit.getImage("src/res/ennemiLvl3.png"));

        projectiles = new ArrayList<>();
        if ( StartingClass.partie.getNiveau() == 2 || StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
            shoot = new Shoot(this);
        }
        
        

    }

    public void update() {

        if (isDetruit() == false) {

//            getCenterY() -= getVitesseY();
            setCenterY(getCenterY() - getVitesseY());
//            setVitesseY(-lvl);
            if (getVitesseX() > 0) {
                if (this.getCenterX() < 1270) {
                    this.setCenterX(this.getCenterX() + getVitesseX());
                }
            } else if (getVitesseX() < 0) {
                if (this.getCenterX() > 20) {
                    this.setCenterX(this.getCenterX() + getVitesseX());
                }
            }
            if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
                follow();
            }
            if (lv == 1) {
                getR().setBounds(getCenterX(), getCenterY(), 69, 60);
            } else if (lv == 2) {
                getR().setBounds(getCenterX(), getCenterY(), 69, 60);
            } else {
                getR().setBounds(getCenterX(), getCenterY(), 90, 69);
            }

//           collisionWithAvion(StartingClass.avion);
        }

    }

    public void destroy() {
        synchronized (moveAvionEnnemi) {

            this.setDetruit(true);
            //MediaPlayer.playSound("/res/sound/Explosion.wav");
            Image im = toolkit.getImage("src/res/explode.gif");

            this.setImage(im);
            try {
                moveAvionEnnemi.wait(150);
            } catch (InterruptedException ex) {
                Logger.getLogger(AvionEnnemi.class.getName()).log(Level.SEVERE, null, ex);
            }
            Attack.avionEnnemis.remove(this);
            if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
                System.out.println("lvl "+StartingClass.partie.getNiveau()+" isAlive "+shoot.shoot.isAlive());
                    shoot.shoot.stop();
                
            }
            moveAvionEnnemi.stop();

            System.out.println("enemie killed");
        }
    }

    public void startAvion() {
        this.update();
        moveAvionEnnemi.start();
    }

    public void updateAll() {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).updateProjectileEnnemi();
        }
    }

    public void drawAll(Graphics g, ImageObserver imageObserver) {
        for (int i = 0; i < projectiles.size(); i++) {
            g.drawImage(projectiles.get(i).getBullet(), projectiles.get(i).getX(), projectiles.get(i).getY(), imageObserver);
        }
    }

    public void shoot() {

        Projectile p = new Projectile(getCenterX() + 30, getCenterY() + 60, true);
        projectiles.add(p);

    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public void setShoot(Shoot shoot) {
        this.shoot = shoot;
    }

    @Override
    public void run() {
        while (this.getMoveAvionEnnemi().isAlive()) {
            if (this.getCenterY() < 700) {
                update();
                for (int i = 0; i < StartingClass.avion.getProjectiles().size(); i++) {
                    if (StartingClass.avion.getProjectiles().get(i).checkCollision(this.getR()) == true) {
                        if (lv == 1) {//verte
                            StartingClass.partie.score += 100;
                        } else if (lv == 2) {//noir
                            StartingClass.partie.score += 50;
                        } else {//jaune
                            StartingClass.partie.score += 150;
                        }

                        StartingClass.avion.getProjectiles().remove(i);
                        destroy();
                    }
                }
                if (this.getR().intersects(StartingClass.avion.getR()) == true) {
                    if (StartingClass.avion.getBouclier() == 0) {
                        StartingClass.avion.destroy();
                        destroy();
                    } else {
                        StartingClass.avion.setBouclier(StartingClass.avion.getBouclier() - 1);
                        destroy();
                    }

                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                Attack.avionEnnemis.remove(this);
                this.setDetruit(true);
                moveAvionEnnemi.stop();
                if (StartingClass.partie.getNiveau() == 3 || StartingClass.partie.getNiveau() == 4) {
                    
                        shoot.shoot.stop();
                    
                }
            }
        }
    }

    public Thread getMoveAvionEnnemi() {
        return moveAvionEnnemi;
    }

    public void setMoveAvionEnnemi(Thread moveAvionEnnemi) {
        this.moveAvionEnnemi = moveAvionEnnemi;
    }

}
