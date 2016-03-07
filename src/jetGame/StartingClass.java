/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import bean.ArrierePlan;
import bean.Attack;
import bean.Avion;
import bean.AvionEnnemi;
import bean.BossEnnemi;
import bean.Joueur;
import bean.Niveau;
import bean.Projectile;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    public static Avion avion;
    private Image image, character, background,
            avionMoved, avionActuel, vieOff, vieOn, ennemie, explode, trans;
    private Image planMovedLeft;
    private Image planMovedRight;
    private Image boss;
    private Image gameOver;
    private Thread thread;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    private Thread shootBoss;
    private BossEnnemi bossEnnemi;
    private Niveau niveau = new Niveau(1);
    //pour lancer l'Attack des Avion Ennemis
    private Attack attack;
    private int score = 0;
    private Thread shootThread;
    private Joueur joueur = new Joueur("Ahmed");

    enum EtatJeu {
        Running, Dead
    }

    EtatJeu etat = EtatJeu.Running;

    @Override
    public void destroy() {
        Platform.exit();
    }

    @Override
    public void stop() {
    }

    @Override
    public void init() {
        //initialisation se fait ici
        setSize(1340, 650);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setResizable(false);
        frame.setTitle("JetGame By AHMED WAFDI & ANAS SAOUDI");
        Font f = new Font("Arial", Font.BOLD, 18);
        setFont(f);
        //Images
        character = getImage(getCodeBase(), "res/mini-plan1.png");
        avionMoved = getImage(getCodeBase(), "res/mini-plan1-onMove.png");
        vieOff = getImage(getCodeBase(), "res/nbr-vie-off.png");
        vieOn = getImage(getCodeBase(), "res/nbr-vieON.png");
        ennemie = getImage(getCodeBase(), "res/ennemi-mini.png");
        planMovedLeft = getImage(getCodeBase(), "res/moveLeft.png");
        planMovedRight = getImage(getCodeBase(), "res/moveRight.PNG");
        boss = getImage(getCodeBase(), "res/Boss B-3.mini.png");
        avionActuel = character;
        background = getImage(getCodeBase(), "res/warshipsBackground-Récupéré.jpg");
        gameOver = getImage(getCodeBase(), "res/gameOver.png");
        //Instanceiation des Objets
        attack = new Attack();
        bossEnnemi = new BossEnnemi();

    }

    @Override
    public void start() {
        //lancer les threads ici
//Inctances des Backgrounds et Avion
        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion();

        thread = new Thread(this);
        thread.start();
        //Lancement de l'Attack des AvionEnnemis
        Thread attackThread = new Thread(attack);
        attackThread.start();
        try {
            attackThread.sleep(3000);

        } catch (InterruptedException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        }
//verifier l activation du BOSS
        if (niveau.getDifficulte() == 3) {
            bossEnnemi.update();
            shootBoss = new Thread(bossEnnemi);
            shootBoss.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void run() {
        while (joueur.tentative != 0) {

            //rafraichire l ecrant tant que l'avion et en vie
            while (avion.getVie() != 0) {
//les modifications des x,y se fait ici;
//mise ajour la position de l'avion
                avion.update();
                if (avion.isMovingUp()) {
                    avionActuel = avionMoved;
                } else if (avion.isMovingDown()) {
                    avionActuel = character;
                }
//Verifier l activation du BOSS
                if (niveau.getDifficulte() == 3) {

                    System.out.println(" >>>>>>>>>>>>>>>> " + BossEnnemi.projectiles.size());
                    for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

                        System.out.println("updating shoot Boss");
                        BossEnnemi.projectiles.get(i).updateProjectileEnnemi();

                        bossEnnemi.update();
                    }

                    if (checkBOSScolision()) {
                        removeProjectil();
                    }

                    if (checkBossProjectilcolision()) {
                        removeBossProjectiles();
                    }
                }

                for (int i = 0; i < Avion.projectiles.size(); i++) {
                    avion.getProjectiles().get(i).update();
                }
                bg1.update();
                bg2.update();

                checkAvionColision();
                if (checkcolision()) {
                    removeProjectil();
                }

                if (checkcolision()) {
                    removeEnnemisOverLimitte();
                }
                removeEnnemisOverLimitte();
                repaint();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            joueur.tentative -= 1;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) { //if (e.getKeyCode()==KeyEvent.VK_UP)
            case KeyEvent.VK_UP:

                avion.up();
                avionActuel = avionMoved;
                System.out.println("Avancer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:

                avionActuel = character;
                avion.down();
                System.out.println("Reculer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:

                avionActuel = planMovedLeft;
                avion.moveLeft();
                System.out.println("vol droite Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:

                avionActuel = planMovedRight;
                avion.moveRight();
                System.out.println("vol gauche Pressed" + e.getKeyCode());
                break;
            case KeyEvent.VK_SPACE:

                System.out.println("SPACE Fire button Pressed " + e.getKeyCode());
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) { //if (e.getKeyCode()==KeyEvent.VK_UP)
            case KeyEvent.VK_UP:
                avion.stop();
                avionActuel = character;
                System.out.println("Arrête de bouger vers le haut " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:
                avion.stop();
                avionActuel = character;
                System.out.println("Arrête de bouger vers le bas " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:
                avion.stop();
                avionActuel = character;
                System.out.println("Arrête de bouger à gauche " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:
                avion.stop();
                avionActuel = character;
                System.out.println("Arrête de bouger à droite " + e.getKeyCode());
                break;
            case KeyEvent.VK_SPACE:
                avion.shoot();
                System.out.println(" SPACE Fire button Released" + e.getKeyCode());
                break;

        }
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        g.drawImage(image, 0, 0, this);

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
        switch (avion.vie) {

            case 1:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOff, 90, 570, this);
                g.drawImage(vieOff, 130, 570, this);
                break;
            case 2:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOff, 130, 570, this);
                break;
            case 3:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                break;

        }

        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);

            System.out.println("drawing shooot Avion");

            g.drawImage(getImage(getCodeBase(), "res/messile.png"), p.getX(), p.getY(), this);
            g.drawRect(p.getX(), p.getY(), 5, 20);
        }
        if (niveau.getDifficulte() == 3) {
            if (bossEnnemi.getCurrentHealth() != 0) {
                g.drawImage(boss, bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), this);
                g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);

            } else if (bossEnnemi.getCurrentHealth() == 0) {
                g.drawImage(boss, bossEnnemi.getCenterX() - 2, bossEnnemi.getCenterY() - 2, this);
                g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);
            }

            for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

                System.out.println("drawing shoot Boss");
                g.drawImage(getImage(getCodeBase(), "res/tiremal.png"), BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), this);
                g.drawRect(BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), 10, 10);
            }
        }

        for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
            explode = getImage(getCodeBase(), "res/explod.gif");
            if (Attack.getAvionEnnemis().get(i).isDetruit() == true) {
                g.drawImage(explode, Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), this);
            }
            g.drawImage(Attack.getAvionEnnemis().get(i).getImage(), Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), this);
            g.drawRect(Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), 69, 60);

        }

        g.drawImage(avionActuel, avion.getCenterX(), avion.getCenterY(), this);
        g.drawRect(avion.getCenterX(), avion.getCenterY(), 48, 87);
        g.drawString("Player " + joueur.getNom(), 1200, 40);
        g.drawString("SCORE " + score, 1200, 80);

//        g.draw3DRect(1000, 80, 150, 20, true);
    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }

    public void removeProjectil() {
//        synchronized (avion.getProjectiles()) {
        System.out.println("REMOVE  PROJECTILS");
        System.out.println(">>>>>>>>>>>>>> SIZE PROJECTILES " + avion.getProjectiles().size());
        for (int i = 0; i < avion.getProjectiles().size(); i++) {
            if (avion.getProjectiles().get(i).getY() < 0) {
                avion.getProjectiles().remove(i);
            }
        }
//        }

    }

    public void checkAvionColision() {
//        synchronized (Attack.getAvionEnnemis()) {

        System.out.println("CHECK COLLISION");
        System.out.println(">>>>>>>>>>>>>> SIZE AVION ENNEMIS " + Attack.getAvionEnnemis().size());
        for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
            if (Attack.getAvionEnnemis().get(i).checkCollision(avion.getR())) {
                Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().stop();
                Attack.getAvionEnnemis().remove(i);
                Avion.vie -= 1;
            }
        }
//        }

    }

    public boolean checkcolision() {

//        synchronized(list){
//            //traitement
//           
//            
//        }
        System.out.println("CHECK COLLISION");
        System.out.println(">>>>>>>>>>>>>> SIZE AVION ENNEMIS " + Attack.getAvionEnnemis().size());
        System.out.println(">>>>>>>>>>>>>> SIZE PROJECTILES " + avion.getProjectiles().size());
        if (Attack.getAvionEnnemis().size() > 0) {
            for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
                if (i > 0) {
                    for (int j = 0; j < avion.getProjectiles().size(); j++) {
                        if (j > 0) {

                            try {
                                if (Attack.getAvionEnnemis().get(i).checkCollision(avion.getProjectiles().get(j).getR())) {

                                    Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().stop();
                                    Attack.getAvionEnnemis().remove(i);
                                    avion.getProjectiles().remove(j);

                                    score += 10;
                                    System.out.println("COLLISION DETECTED WHIT SCORE :" + score);
                                    return true;
                                }
                            } catch (Exception e) {
                                System.out.println("<<<<<<<<<<< ENEMIS>>>>>>>>>>" + i);
                                System.out.println("<<<<<<<<<<<SIZE PROJECTIL>>>>>>>>>>" + Attack.getAvionEnnemis().size());
                                System.out.println("<<<<<<<<<<< PROJECTIL>>>>>>>>>>" + j);
                                System.out.println("<<<<<<<<<<<SIZE PROJECTIL>>>>>>>>>>" + avion.getProjectiles().size());
                                System.exit(1);
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    public boolean checkBOSScolision() {
//        synchronized (avion.getProjectiles()) {
        for (int j = 0; j < avion.getProjectiles().size(); j++) {
            if (avion.getProjectiles().get(j).checkCollision(bossEnnemi.getR())) {
                avion.getProjectiles().remove(j);
                if (bossEnnemi.getCurrentHealth() > 0) {
                    bossEnnemi.setCurrentHealth(bossEnnemi.getCurrentHealth() - 1);
                }

                return true;
            }
        }

//        }
        return false;
    }

    public boolean checkBossProjectilcolision() {
//        synchronized (bossEnnemi.getProjectiles()) {
        for (int j = 0; j < bossEnnemi.getProjectiles().size(); j++) {
            if (bossEnnemi.getProjectiles().get(j).checkCollision(avion.getR())) {
                bossEnnemi.getProjectiles().remove(j);
                if (avion.vie > 0) {
                    avion.vie -= 1;
                }

                return true;
            }
//            }

        }

        return false;
    }

    public void removeBossProjectiles() {
//        synchronized (bossEnnemi.getProjectiles()) {
        for (int i = 0; i < bossEnnemi.getProjectiles().size(); i++) {
            if (bossEnnemi.getProjectiles().get(i).getY() > 700) {
                bossEnnemi.getProjectiles().remove(i);
            }
        }

//        }
    }

    public static void removeEnnemisOverLimitte() {

//        synchronized (Attack.getAvionEnnemis()) {
        System.out.println("NOMBRE ENNEMIS --->" + Attack.getAvionEnnemis().size());
        for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
            if (Attack.getAvionEnnemis().get(i).getCenterY() > 700) {
                Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().stop();
                Attack.getAvionEnnemis().remove(i);
            }

        }

//        }
    }

}
