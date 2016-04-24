/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import bean.ArrierePlan;
import bean.Attack;

import bean.Avion;
import bean.BossEnnemi;
import bean.Partie;
import bean.Projectile;
import bean.Session;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import javafx.application.Platform;
import javax.swing.JFrame;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener, ActionListener {

    public static Avion avion;
    private BossEnnemi bossEnnemi;
    public static Partie partie;
    public static Attack attack;

    private Image image, vieOff, vieOn, vieOn1on3, vieOn2On3, socreboard;
    private static ArrierePlan bg1, bg2;
    private Graphics second;

    private Thread thread;
    private Thread shootBoss;
    public static Thread attackThread;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public StartingClass() {

    }

    public void initImages() {

        socreboard = toolkit.getImage("src/res/score.png");
        vieOff = toolkit.getImage("src/res/nbr-vie-off.png");
        vieOn = toolkit.getImage("src/res/nbr-vieON.png");
        vieOn1on3 = toolkit.getImage("src/res/nbr-vieOn1_3");
        vieOn2On3 = toolkit.getImage("src/res/nbr-vieOn2_3");

//        boss = toolkit.getImage("src/res/Boss B-3.mini.png");
    }

    public void initComponent() {
        setSize(toolkit.getScreenSize());
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("JetGame By AHMED WAFDI & ANAS SAOUDI");
        frame.setFocusable(true);
        Font f = new Font("Arial", Font.BOLD, 18);
        setFont(f);
    }

    @Override
    public void destroy() {
        Platform.exit();
    }

    @Override
    public void stop() {
    }

    @Override
    public void init() {

        initComponent();
        initImages();
//        pause = new Button("PAUSE");
//        pause.addActionListener(this);
//        pause.setBounds(10, 10, 100, 30);
        partie = (Partie) Session.getAttributes("partie");

//        add(pause);
    }

    @Override
    public void start() {

        attack = new Attack();
//      bossEnnemi = new BossEnnemi();
        //lancer les threads ici
//Inctances des Backgrounds et Avion
        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion(partie.getAvion());
        System.out.println("partie----->" + partie);
        thread = new Thread(this);
        thread.start();
        //Lancement de l'Attack des AvionEnnemis
        attackThread = new Thread(attack);
        attackThread.start();
//verifier l activation du BOSS
//        if (niveau.getDifficulte() == 3) {
//            bossEnnemi.update();
//            shootBoss = new Thread(bossEnnemi);
//            shootBoss.start();
//        niveauHaut();
    }

    @Override
    public void run() {

        //rafraichire l ecrant tant que l'avion et en vie
//les modifications des x,y se fait ici;
//mise ajour la position de l'avion
        while (isActive()) {
            if (avion.vie != 0) {
                whileAvionIsAlive();
            } else {
                gameOver();
            }

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
                avion.setDrawingimage(avion.getImageMoveUp());
                break;
            case KeyEvent.VK_DOWN:

                avion.setDrawingimage(avion.getImageMoveDown());
                avion.down();

                break;
            case KeyEvent.VK_LEFT:

                avion.setDrawingimage(avion.getImageMoveLeft());
                avion.moveLeft();

                break;
            case KeyEvent.VK_RIGHT:

                avion.setDrawingimage(avion.getImageMoveRight());
                avion.moveRight();

                break;
            case KeyEvent.VK_SPACE:
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) { //if (e.getKeyCode()==KeyEvent.VK_UP)
            case KeyEvent.VK_UP:
                avion.stop();
                avion.setDrawingimage(avion.getImage());
                break;
            case KeyEvent.VK_DOWN:
                avion.stop();
                avion.setDrawingimage(avion.getImage());

                break;
            case KeyEvent.VK_LEFT:
                avion.stop();
                avion.setDrawingimage(avion.getImage());

                break;
            case KeyEvent.VK_RIGHT:
                avion.stop();
                avion.setDrawingimage(avion.getImage());
                break;
            case KeyEvent.VK_SPACE:
                if (avion.vie != 0) {
                    avion.shoot();
                    MediaPlayer.playSound("/sound/weapon_player.wav");
                }
                break;

        }
    }

    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == restart) {
//          
//        }

    }

    public void gameOver() {
        attackThread.stop();

        bg1.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg2.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg1.update();
        bg2.update();
        repaint();
    }

    public void whileAvionIsAlive() {
        niveauHaut();
        avion.update();

        if (Avion.projectiles.size() > 0) {
            for (int i = 0; i < Avion.projectiles.size(); i++) {
                avion.getProjectiles().get(i).update();
            }
        }

        bg1.update();
        bg2.update();

        updateShootEnnemie();
        repaint();
        attack.removeEnnemisOverLimitte();

    }

    public void updateBossEnnemi() {
        bossEnnemi.update();
        System.out.println(" >>>>>>>shoot BOSS>>>>>>>>> " + BossEnnemi.projectiles.size());
        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

            System.out.println("updating shoot Boss");
            BossEnnemi.projectiles.get(i).updateProjectileEnnemi();

        }
    }

    public void updateShootEnnemie() {
        if (partie.getNiveau() == 3 || partie.getNiveau() == 4) {
            for (int i = 0; i < Attack.avionEnnemis.size(); i++) {
                Attack.avionEnnemis.get(i).updateAll();
            }
        }

    }

    public void drawShootEnnemie(Graphics g) {
        for (int i = 0; i < Attack.avionEnnemis.size(); i++) {
            Attack.avionEnnemis.get(i).drawAll(g, this);
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
        drawBackground(g);
//        drawVie(g);
        drawNbrVie(g, vieOn, this, avion.vie);

        drawProjectilles(g);
        drawEnnemis(g);
        drawAvion(g);

        g.drawString("Player " + partie.getJoueur(), 1200, 40);
        g.drawString("SCORE " + partie.score, 1200, 80);

        drawShootEnnemie(g);
    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }

    public void niveauHaut() {
        if (partie.score == 1000) {
            partie.setNiveau(2);
        } else if (partie.score == 2000) {
            partie.setNiveau(3);
        } else if (partie.score >= 3000) {
            partie.setNiveau(4);
        }
    }

    public void drawEnnemis(Graphics g) {
        for (int i = 0; i < attack.getAvionEnnemis().size(); i++) {
            g.drawImage(attack.getAvionEnnemis().get(i).getImage(), attack.getAvionEnnemis().get(i).getCenterX(), attack.getAvionEnnemis().get(i).getCenterY(), this);
            g.drawRect(attack.getAvionEnnemis().get(i).getR().x, attack.getAvionEnnemis().get(i).getR().y, attack.getAvionEnnemis().get(i).getR().width, attack.getAvionEnnemis().get(i).getR().height);

        }
    }

    public void drawProjectilles(Graphics g) {
        if (avion.vie != 0) {
            for (int i = 0; i < avion.getProjectiles().size(); i++) {
                Projectile p = (Projectile) avion.getProjectiles().get(i);
                g.drawImage(p.getBullet(), p.getX(), p.getY(), this);
                g.drawRect(p.getX(), p.getY(), 5, 20);
            }
        }
    }

    public void drawBossEnnemie(Graphics g) {
        if (bossEnnemi.getCurrentHealth() != 0) {
            g.drawImage(bossEnnemi.getImage(), bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), this);
            g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);

        } else if (bossEnnemi.getCurrentHealth() == 0) {
            g.drawImage(bossEnnemi.getImage(), bossEnnemi.getCenterX() - 2, bossEnnemi.getCenterY() - 2, this);
            g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);
        }
        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

            System.out.println("drawing shoot Boss");
            g.drawImage(BossEnnemi.projectiles.get(i).getBullet(), BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), this);
            g.drawRect(BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), 10, 10);
        }
    }

    public void drawVie(Graphics g) {

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
            case 4:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                break;
            case 5:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                g.drawImage(vieOn, 210, 570, this);

                break;
            case 6:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                g.drawImage(vieOn, 210, 570, this);
                g.drawImage(vieOn, 250, 570, this);

                break;
            case 7:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                g.drawImage(vieOn, 210, 570, this);
                g.drawImage(vieOn, 250, 570, this);
                g.drawImage(vieOn, 290, 570, this);

                break;
            case 8:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                g.drawImage(vieOn, 210, 570, this);
                g.drawImage(vieOn, 250, 570, this);
                g.drawImage(vieOn, 290, 570, this);

                break;
            case 9:
                g.drawImage(vieOn, 50, 570, this);
                g.drawImage(vieOn, 90, 570, this);
                g.drawImage(vieOn, 130, 570, this);
                g.drawImage(vieOn, 170, 570, this);
                g.drawImage(vieOn, 210, 570, this);
                g.drawImage(vieOn, 250, 570, this);
                g.drawImage(vieOn, 290, 570, this);
                g.drawImage(vieOn, 330, 570, this);
                break;

        }
    }

    public void drawBackground(Graphics g) {
        g.drawImage(bg1.getBackground(), bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(bg2.getBackground(), bg2.getBgX(), bg2.getBgY(), this);
    }

    public void drawAvion(Graphics g) {
        if (avion.vie != 0) {
            g.drawImage(avion.getDrawingimage(), avion.getCenterX(), avion.getCenterY(), this);
            g.drawRect(avion.getCenterX(), avion.getCenterY(), avion.getHeight(), avion.getWidth());
        }
    }

    public void drawNbrVie(Graphics g, Image img, ImageObserver io, int nbr) {
        int distance = 0;
        for (int i = 0; i < nbr; i++) {
            g.drawImage(img, 50 + distance, 570, io);
            distance += 40;
        }
    }

    public void drawVieOff(Graphics g, ImageObserver io) {
        int distance = 0;
       
    }
}
