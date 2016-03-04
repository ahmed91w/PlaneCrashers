/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import bean.ArrierePlan;
import bean.Avion;
import bean.AvionEnnemi;
import bean.Projectile;
import bean.ScoreBoard;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    private Avion avion;
    private AvionEnnemi ennemi;
    private Image image, character, background, avionMoved, avionActuel, vie1, vie2, vie3, ennemie, explode;
    private Image planMovedLeft;
    private Image planMovedRight;
    private Image vide;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    private ScoreBoard board;
    private Thread thread1;

    @Override
    public void destroy() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void start() {
        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion();

        board = new ScoreBoard();

        Thread thread = new Thread(this);

        thread1.start();
        thread.start();
//lancer les threads ici
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
        explode = getImage(getCodeBase(), "res/explod.gif");
        avionActuel = character;
        background = getImage(getCodeBase(), "res/warshipsBackground-Récupéré.jpg");
        ennemi = new AvionEnnemi(0, 0);
        thread1 = new Thread(ennemi);
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

//            Random rand = new Random();
//            int random = rand.nextInt((1300 - 300) + 1) + 600;
            ArrayList projectiles = avion.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                if (p.isVisible() == true) {
                    System.out.println("updating shoot");
                    p.update();
                } else {
                    projectiles.remove(i);
                }
            }

            for (int i = 0; i < ennemi.getAvionEnnemis().size(); i++) {
                ennemi.getAvionEnnemis().get(i).update();
            }
            bg1.update();
            bg2.update();

            repaint();
            try {
                Thread.sleep(15);
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
                avion.removeShoot();
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

                System.out.println("avion Projectil supprimer");
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
        g.drawImage(avionActuel, avion.getCenterX(), avion.getCenterY(), this);
        g.drawImage(vie1, 50, 570, this);
        g.drawImage(vie2, 90, 570, this);
        g.drawImage(vie3, 130, 570, this);

//affichage des projectiles
        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (avion.isHasMessile()) {
                g.drawImage(getImage(getCodeBase(), "res/messile.png"), p.getX(), p.getY(), this);
            } else {
                g.drawImage(getImage(getCodeBase(), "res/tire3.png"), p.getX(), p.getY(), this);
            }

        }
//affichage des ennemis
        for (int i = 0; i < ennemi.getAvionEnnemis().size(); i++) {
            g.drawImage(ennemie, ennemi.getAvionEnnemis().get(i).getCenterX(), ennemi.getAvionEnnemis().get(i).getCenterY(), this);
        }
    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }

}
