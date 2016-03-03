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

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    private Avion avion;
    private AvionEnnemi ennemi1, ennemi2;
    private Image image, character, background, avionMoved, avionActuel, vie1, vie2, vie3, ennemie;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    private ScoreBoard board;
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
        
        board=new ScoreBoard();
        ennemi1 = new AvionEnnemi(540, 80);
        ennemi2 = new AvionEnnemi(640, 120);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void init() {
        setSize(1340, 650);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("JetPlan By Ahmed WAFDI and Anas SAOUDI");
        frame.setResizable(false);
        Label player=new Label("Joueur : Ahmed");
        Label score=new Label("Score : "+1000);
        
        player.setFocusable(true);
        player.setLocation(20,80);
        player.setBackground(Color.gray);
        player.setForeground(Color.red);
        score.setFocusable(true);
        score.setLocation(20,80);
        score.setBackground(Color.gray);
        score.setForeground(Color.red);
        add(player);
        add(score);

        character = getImage(getCodeBase(), "res/mini-plan1.png");
        avionMoved = getImage(getCodeBase(), "res/mini-plan1-onMove.png");
        vie1 = getImage(getCodeBase(), "res/nbr-vie.png");
        vie2 = getImage(getCodeBase(), "res/nbr-vie.png");
        vie3 = getImage(getCodeBase(), "res/nbr-vie.png");
        ennemie = getImage(getCodeBase(), "res/ennemi-mini.png");
        avionActuel = character;
        background = getImage(getCodeBase(), "res/warshipsBackground-Récupéré.jpg");
    }

    @Override
    public void run() {
        while (avion.getVie() != 0) {
            avion.update();
            if (avion.isMovingUp()) {
                avionActuel = avionMoved;
            } else if (avion.isMovingDown()) {
                avionActuel = character;
            }
            ArrayList projectiles = avion.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                if (p.isVisible() == true) {
                    p.update();
                } else {
                    projectiles.remove(i);
                }
            }

            ennemi1.update();
            ennemi2.update();

            bg1.update();
            bg2.update();

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

                avionActuel = avionMoved;
                avion.moveLeft();
                System.out.println("vol droite Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:

                avionActuel = avionMoved;
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
        g.drawImage(character, avion.getCenterX(), avion.getCenterY(), this);
        g.drawImage(avionActuel, avion.getCenterX(), avion.getCenterY(), this);
        g.drawImage(vie1, 50, 570, this);
        g.drawImage(vie2, 90, 570, this);
        g.drawImage(vie3, 130, 570, this);
        g.drawImage(ennemie, ennemi1.getCenterX(), ennemi1.getCenterY(), this);
        g.drawImage(ennemie, ennemi2.getCenterX(), ennemi2.getCenterY(), this);

        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (avion.isHasMessile()) {
                g.drawImage(getImage(getCodeBase(), "res/messile.png"), p.getX(), p.getY(), this);
            } else {
                g.drawImage(getImage(getCodeBase(), "res/tire3.png"), p.getX(), p.getY(), this);
            }

        }

    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }
}
