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
import bean.Niveau;
import bean.Partie;
import bean.Projectile;
import bean.Session;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.application.Platform;
import javax.swing.JFrame;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener ,ActionListener  {

    public static Avion avion;
    private Image image, character, background,
            avionMoved, avionActuel, vieOff, vieOn, ennemie, explode, trans, bossShot;
    private Image planMovedLeft;
    private Image planMovedRight;
    private Image boss;
    private Thread thread;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    private Thread shootBoss;
    private BossEnnemi bossEnnemi;
    private Niveau niveau = new Niveau(3);
    //pour lancer l'Attack des Avion Ennemis
    private Attack attack;
    private int score = 0;
    private Thread shootThread;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Partie partie;
    Button pause;

    public StartingClass() throws HeadlessException {
        super();
//        url = url;
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

        pause = new Button("PAUSE");
        pause.addActionListener(this);
        pause.setBounds(10, 10, 100, 30);

        partie = (Partie) Session.getAttributes("partie");
        setSize(1340, 650);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);
        JFrame frame = new JFrame();
//                (Frame) this.getParent().getParent();
        frame.setResizable(false);
        frame.setTitle("JetGame By AHMED WAFDI & ANAS SAOUDI");
        frame.setFocusable(true);
        Font f = new Font("Arial", Font.BOLD, 18);
        setFont(f);
        //Images
        add(pause);
        bossShot = toolkit.getImage("src/res/tiremal.png");
        character = toolkit.getImage("src/res/mini-plan1.png");
        avionMoved = toolkit.getImage("src/res/mini-plan1-onMove.png");
        vieOff = toolkit.getImage("src/res/nbr-vie-off.png");
        vieOn = toolkit.getImage("src/res/nbr-vieON.png");
        planMovedLeft = toolkit.getImage("src/res/moveLeft.png");
        planMovedRight = toolkit.getImage("src/res/moveRight.PNG");
        boss = toolkit.getImage("src/res/Boss B-3.mini.png");
        background = toolkit.getImage("src/res/warshipsBackground-Récupéré.jpg");

    }

    @Override
    public void start() {

        attack = new Attack();
        bossEnnemi = new BossEnnemi();
        //lancer les threads ici
//Inctances des Backgrounds et Avion
        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion(partie.getAvion(), 6);
        System.out.println("partie----->" + partie);
        avionActuel = avion.getImage();
        thread = new Thread(this);
        thread.start();
        //Lancement de l'Attack des AvionEnnemis
        Thread attackThread = new Thread(attack);
        attackThread.start();

//verifier l activation du BOSS
//        if (niveau.getDifficulte() == 3) {
        bossEnnemi.update();
        shootBoss = new Thread(bossEnnemi);
        shootBoss.start();

//        }
    }

    @Override
    public void run() {

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
//                if (niveau.getDifficulte() == 3) {
            bossEnnemi.update();
            System.out.println(" >>>>>>>shoot BOSS>>>>>>>>> " + BossEnnemi.projectiles.size());
            for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

                System.out.println("updating shoot Boss");
                BossEnnemi.projectiles.get(i).updateProjectileEnnemi();

//                    
            }

//                if (checkBOSScolision()) {
//                    removeProjectil();
//                }
//
//                if (checkBossProjectilcolision()) {
//                    removeBossProjectiles();
//                }
//                }
            for (int i = 0; i < Avion.projectiles.size(); i++) {
                avion.getProjectiles().get(i).update();
            }
            bg1.update();
            bg2.update();

//                checkAvionColision();
//                if (checkcolision()) {
//                    removeProjectil();
//                }
//
//                if (checkcolision()) {
//                    removeEnnemisOverLimitte();
//                }
            removeEnnemisOverLimitte();
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e
    ) {

    }

    @Override
    public void keyPressed(KeyEvent e
    ) {
        switch (e.getKeyCode()) { //if (e.getKeyCode()==KeyEvent.VK_UP)
            case KeyEvent.VK_UP:

                avion.up();
                avionActuel = avionMoved;

                break;
            case KeyEvent.VK_DOWN:

                avionActuel = character;
                avion.down();

                break;
            case KeyEvent.VK_LEFT:

                avionActuel = planMovedLeft;
                avion.moveLeft();

                break;
            case KeyEvent.VK_RIGHT:

                avionActuel = planMovedRight;
                avion.moveRight();

                break;
            case KeyEvent.VK_SPACE:
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e
    ) {
        switch (e.getKeyCode()) { //if (e.getKeyCode()==KeyEvent.VK_UP)
            case KeyEvent.VK_UP:
                avion.stop();
                avionActuel = character;
                break;
            case KeyEvent.VK_DOWN:
                avion.stop();
                avionActuel = character;

                break;
            case KeyEvent.VK_LEFT:
                avion.stop();
                avionActuel = character;

                break;
            case KeyEvent.VK_RIGHT:
                avion.stop();
                avionActuel = character;

                break;
            case KeyEvent.VK_SPACE:
                avion.shoot();

                break;

        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pause) {
            System.out.println("Button 1 was pressed");
        } else {
            System.out.println("Button 2 was pressed");
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
    public void paint(Graphics g
    ) {
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

//        if (niveau.getDifficulte() == 3) {
        if (bossEnnemi.getCurrentHealth() != 0) {
            g.drawImage(boss, bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), this);
            g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);

        } else if (bossEnnemi.getCurrentHealth() == 0) {
            g.drawImage(boss, bossEnnemi.getCenterX() - 2, bossEnnemi.getCenterY() - 2, this);
            g.drawRect(bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), 130, 240);
        }

        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {

            System.out.println("drawing shoot Boss");
            g.drawImage(bossShot, BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), this);
            g.drawRect(BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), 10, 10);
        }
//        }

        ArrayList projectiles = avion.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);

            System.out.println("drawing shooot Avion");

            g.drawImage(p.getBullet(), p.getX(), p.getY(), this);
            g.drawRect(p.getX(), p.getY(), 5, 20);
        }

        for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
//            explode = getImage(url, "res/explod.gif");
//            if (Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().isAlive() == false) {
////                try {
////                    Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().sleep(100);
////                } catch (InterruptedException ex) {
////                    Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
////                }
//                g.drawImage(explode, Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), this);
//            }
            g.drawImage(Attack.getAvionEnnemis().get(i).getImage(), Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), this);
            g.drawRect(Attack.getAvionEnnemis().get(i).getCenterX(), Attack.getAvionEnnemis().get(i).getCenterY(), 69, 60);

        }
        g.drawImage(avionActuel, avion.getCenterX(), avion.getCenterY(), this);
        g.drawRect(avion.getCenterX(), avion.getCenterY(), 48, 87);
        g.drawString("Player " + partie.getJoueur(), 1200, 40);
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

//    public boolean checkcolision() {
//
////        synchronized(list){
////            //traitement
////           
////            
////        }
//        System.out.println("CHECK COLLISION");
//        System.out.println(">>>>>>>>>>>>>> SIZE AVION ENNEMIS " + Attack.getAvionEnnemis().size());
//        System.out.println(">>>>>>>>>>>>>> SIZE PROJECTILES " + avion.getProjectiles().size());
////        if (Attack.getAvionEnnemis().size() > 0) {
//            for (int i = 0; i < Attack.getAvionEnnemis().size(); i++) {
//                if (i > 0) {
//                    for (int j = 0; j < avion.getProjectiles().size(); j++) {
//                        if (j > 0) {
//
//                            try {
//                                if (Attack.getAvionEnnemis().get(i).checkCollision(avion.getProjectiles().get(j).getR())) {
//
////                                    Thread.sleep(100);
//                                    Attack.getAvionEnnemis().get(i).getMoveAvionEnnemi().stop();
//                                    Attack.getAvionEnnemis().remove(i);
//                                    avion.getProjectiles().remove(j);
//
//                                    score += 10;
//                                    System.out.println("COLLISION DETECTED WHIT SCORE :" + score);
//                                    return true;
//                                }
//                            } catch (Exception e) {
//                                System.out.println("<<<<<<<<<<< ENEMIS>>>>>>>>>>" + i);
//                                System.out.println("<<<<<<<<<<<SIZE PROJECTIL>>>>>>>>>>" + Attack.getAvionEnnemis().size());
//                                System.out.println("<<<<<<<<<<< PROJECTIL>>>>>>>>>>" + j);
//                                System.out.println("<<<<<<<<<<<SIZE PROJECTIL>>>>>>>>>>" + avion.getProjectiles().size());
//                                System.exit(1);
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//
//        return false;
//    }
//    public boolean checkBOSScolision() {
////        synchronized (avion.getProjectiles()) {
//        for (int j = 0; j < avion.getProjectiles().size(); j++) {
//            if (avion.getProjectiles().get(j).checkCollision(bossEnnemi.getR())) {
//                avion.getProjectiles().remove(j);
//                if (bossEnnemi.getCurrentHealth() > 0) {
//                    bossEnnemi.setCurrentHealth(bossEnnemi.getCurrentHealth() - 1);
//                }
//
//                return true;
//            }
//        }
//
////        }
//        return false;
//    }
//    public boolean checkBossProjectilcolision() {
////        synchronized (bossEnnemi.getProjectiles()) {
//        for (int j = 0; j < bossEnnemi.getProjectiles().size(); j++) {
//            if (bossEnnemi.getProjectiles().get(j).checkCollision(avion.getR())) {
//                bossEnnemi.getProjectiles().remove(j);
//                if (avion.vie > 0) {
//                    avion.vie -= 1;
//                }
//
//                return true;
//            }
////            }
//
//        }
//
//        return false;
//    }
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
