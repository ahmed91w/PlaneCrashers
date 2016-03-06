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
import bean.Niveau;
import bean.Partie;
import bean.Projectile;
import bean.ScoreBoard;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    public static Avion avion;
//    private AvionEnnemi ennemi = new AvionEnnemi(500, 0);
    private Image image, character, background, avionMoved, avionActuel, vie1, vie2, vie3, ennemie, explode, trans;
    private Image planMovedLeft;
    private Image planMovedRight;
    private Image boss;
    private Thread thread;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    private ScoreBoard board;
    private Thread ajoutEnnemis;
    private Thread shootBoss;
    private BossEnnemi bossEnnemi;
    private Partie partie;
    private Niveau niveau = new Niveau(3);
    Projectile p;
    private Attack attack;
    private List<AvionEnnemi> avionEnnemis = new ArrayList<>();

    @Override
    public void destroy() {
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
        character = getImage(getCodeBase(), "res/mini-plan1.png");
        avionMoved = getImage(getCodeBase(), "res/mini-plan1-onMove.png");
        vie1 = getImage(getCodeBase(), "res/nbr-vie.png");
        vie2 = getImage(getCodeBase(), "res/nbr-vie.png");
        vie3 = getImage(getCodeBase(), "res/nbr-vie.png");

        ennemie = getImage(getCodeBase(), "res/ennemi-mini.png");
        planMovedLeft = getImage(getCodeBase(), "res/moveLeft.png");
        planMovedRight = getImage(getCodeBase(), "res/moveRight.PNG");

        boss = getImage(getCodeBase(), "res/Boss B-3.mini.png");
        avionActuel = character;
        background = getImage(getCodeBase(), "res/warshipsBackground-Récupéré.jpg");
        attack = new Attack();
        bossEnnemi = new BossEnnemi();
    }

    @Override
    public void start() {

        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion();

        thread = new Thread(this);
        thread.start();

        Thread attackThread = new Thread(attack);
        attackThread.start();
        try {
            attackThread.sleep(500);

        } catch (InterruptedException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        }
//lancer les threads ici
        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p1 = (Projectile) projectiles.get(i);
            p1.getMoveProj().start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (niveau.getDifficulte() == 3) {
            bossEnnemi.update();
            shootBoss = new Thread(bossEnnemi);
            shootBoss.start();
        }

    }

    @Override
    public void run() {
        while (avion.getVie() != 0) {
//les modifications des x,y se fait ici;

            avion.update();
            if (avion.isMovingUp()) {
                avionActuel = avionMoved;
            } else if (avion.isMovingDown()) {
                avionActuel = character;
            }

            Random rand = new Random();
            int random = rand.nextInt((1300 - 300) + 1) + 600;
            if (niveau.getDifficulte() == 3) {
                System.out.println(" >>>>>>>>>>>>>>>> " + BossEnnemi.projectiles.size());
                for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

                    System.out.println("updating shoot Boss");
                    BossEnnemi.projectiles.get(i).updateProjectileEnnemi();

                }
            }

            bg1.update();
            bg2.update();
            if (niveau.getDifficulte() == 3) {
                bossEnnemi.update();
            }

            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
                avion.shoot();

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

        g.drawImage(vie1, 50, 570, this);
        g.drawImage(vie2, 90, 570, this);
        g.drawImage(vie3, 130, 570, this);
        if (p != null) {
            g.drawImage(getImage(getCodeBase(), "res/tire3.png"), p.getY(), p.getX(), this);
        }

        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (avion.isHasMessile()) {
                g.drawImage(getImage(getCodeBase(), "res/messile.png"), p.getX(), p.getY(), this);

                //g.drawRect((int)p.getX(), (int)p.getY(), 20, 20);
            } else {
                g.drawImage(getImage(getCodeBase(), "res/tire3.png"), p.getX(), p.getY(), this);
            }

        }
        if (niveau.getDifficulte() == 3) {
            g.drawImage(boss, bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), this);

            for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

                System.out.println("drawing shoot Boss");
                g.drawImage(getImage(getCodeBase(), "res/tiremal.png"), BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), this);

            }
        }

        for (int i = 0; i < Attack.avionEnnemis.size(); i++) {
            explode = getImage(getCodeBase(), "res/explod.gif");
            if (Attack.avionEnnemis.get(i).isDetruit() == true) {
                g.drawImage(explode, Attack.avionEnnemis.get(i).getCenterX(), Attack.avionEnnemis.get(i).getCenterY(), this);

            }
            g.drawImage(Attack.avionEnnemis.get(i).getImage(), Attack.avionEnnemis.get(i).getCenterX(), Attack.avionEnnemis.get(i).getCenterY(), this);
        }

        g.drawImage(avionActuel, avion.getCenterX(), avion.getCenterY(), this);

    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }

}
