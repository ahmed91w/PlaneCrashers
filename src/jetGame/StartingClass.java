/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import Interface.GameBoard;
import bean.ArrierePlan;
import bean.Attack;

import bean.Avion;
import bean.BossEnnemi;
import bean.GeneratePowerUp;
import bean.Partie;
import bean.PowerUp;
import bean.Projectile;
import bean.Session;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javafx.application.Platform;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {
    
    public static Avion avion;
    private BossEnnemi bossEnnemi;
    public static Partie partie;
    public static Attack attack;
    public static GeneratePowerUp powerUpGenerator;
    private Image image, vieOff, vieOn, vieOn1on3, vieOn2On3, socreboard;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    
    private JFrame frame;
    
    private Thread thread;
    private Thread shootBoss;
    private Thread pwUp;
    
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
        frame = new JFrame();
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
        attackThread.stop();
        for (int i = 0; i < attack.avionEnnemis.size(); i++) {
            attack.avionEnnemis.get(i).getMoveAvionEnnemi().stop();
        }
        
        for (int i = 0; i < powerUpGenerator.powerUps.size(); i++) {
            powerUpGenerator.powerUps.get(i).getMove().stop();
        }
        
        System.out.println("All Threads Stoped");
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
        bossEnnemi = new BossEnnemi();
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
        
        powerUpGenerator = new GeneratePowerUp();
        pwUp = new Thread(powerUpGenerator);
        pwUp.start();
//verifier l activation du BOSS

    }
    
    @Override
    public void run() {

        //rafraichire l ecrant tant que l'avion et en vie
//les modifications des x,y se fait ici;
//mise ajour la position de l'avion
//        while (isActive()) {
        while (avion.vie != 0) {
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
        bg1.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg1.setBgY(-1750);
        repaint();
        int output = JOptionPane.showConfirmDialog(this, "Joueur : " + partie.getJoueur() + "\n Votre Score : " + partie.score + " \n ", "Fin de partie", JOptionPane.CLOSED_OPTION,
                JOptionPane.CLOSED_OPTION);
        
        if (output == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        System.exit(0);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    public void activateBoss() {
        if (partie.score == 1900) {//boss lvl 2

        } else if (partie.score == 2900) {//boss lvl 3

        }
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
        if (shootBoss.isAlive()) {
            shootBoss.stop();
        }
        
        bg1.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg2.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg1.update();
        bg2.update();
        repaint();
    }
    
    public void whileAvionIsAlive() {
        lvlUp();
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
//        if (bossEnnemi.getCurrentHealth() == 0) {
//            shootBoss.stop();
//        } else {
//            updateBossEnnemi();
//        }

    }
    
    public void updateBossEnnemi() {
        bossEnnemi.update();
        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {
            BossEnnemi.projectiles.get(i).updateProjectileBoss();
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
        
        drawNbrVie(g, vieOn, avion.vie);
        
        drawProjectilles(g);
        
        drawEnnemis(g);
        
        drawAvion(g);
        
        g.drawString("Player " + partie.getJoueur(), 1200, 40);
        g.drawString("SCORE " + partie.score, 1200, 80);
//        if (bossEnnemi.getCurrentHealth() != 0) {
//            drawBossEnnemie(g);
//        }
        drawShootEnnemie(g);
        drawShield(g);
        powerUpGenerator.drawPowerUp(g, this);
    }
    
    public void lvlUp() {
        if (partie.score == 1000 && partie.getNiveau() == 1) {
            partie.setNiveau(2);
            MediaPlayer.playBgSound("/sound/level_up.wav");
        } else if (partie.score == 2000 && partie.getNiveau() == 2) {
            partie.setNiveau(3);
            MediaPlayer.playBgSound("/sound/level_up.wav");
        } else if (partie.score >= 3000 && partie.getNiveau() == 3) {
            partie.setNiveau(4);
            MediaPlayer.playBgSound("/sound/level_up.wav");
        }
    }
    
    public void drawEnnemis(Graphics g) {
        for (int i = 0; i < attack.getAvionEnnemis().size(); i++) {
            g.drawImage(attack.getAvionEnnemis().get(i).getImage(), attack.getAvionEnnemis().get(i).getCenterX(), attack.getAvionEnnemis().get(i).getCenterY(), this);
            
        }
    }
    
    public void drawProjectilles(Graphics g) {
        if (avion.vie != 0) {
            for (int i = 0; i < avion.getProjectiles().size(); i++) {
                Projectile p = (Projectile) avion.getProjectiles().get(i);
                g.drawImage(p.getBullet(), p.getX(), p.getY(), this);
            }
        }
    }
    
    public void drawShield(Graphics g) {
        if (avion.getBouclier() != 0) {
            g.drawImage(toolkit.getImage("src/res/ShieldBar.png"), 50, 630, this);
            int distance = 0;
            for (int i = 0; i < avion.getBouclier(); i++) {
                g.drawImage(toolkit.getImage("src/res/shieldPoint.png"), 55 + distance, 635, this);
                distance += 30;
            }
        }
        
    }
    
    public void drawBossEnnemie(Graphics g) {
        if (bossEnnemi.getCurrentHealth() != 0) {
            g.drawImage(bossEnnemi.getImage(), bossEnnemi.getCenterX(), bossEnnemi.getCenterY(), this);
            
        }
        for (int i = 0; i < BossEnnemi.projectiles.size(); i++) {
            
            g.drawImage(BossEnnemi.projectiles.get(i).getBullet(), BossEnnemi.projectiles.get(i).getX(), BossEnnemi.projectiles.get(i).getY(), this);
        }
    }
    
    public void drawBackground(Graphics g) {
        g.drawImage(bg1.getBackground(), bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(bg2.getBackground(), bg2.getBgX(), bg2.getBgY(), this);
    }
    
    public void drawAvion(Graphics g) {
        if (avion.vie != 0) {
            g.drawImage(avion.getDrawingimage(), avion.getCenterX(), avion.getCenterY(), this);
            if (avion.getBouclier() != 0) {
                g.drawImage(avion.getBouclierImg(), avion.getCenterX() - 9, avion.getCenterY() - 25, this);
            }
        }
    }
    
    public void drawNbrVie(Graphics g, Image img, int nbr) {
        int distance = 0;
        for (int i = 0; i < nbr; i++) {
            g.drawImage(img, 50 + distance, 570, this);
            distance += 40;
        }
    }
    
    public static ArrierePlan getBg1() {
        return bg1;
    }
    
    public static void setBg1(ArrierePlan bg1) {
        StartingClass.bg1 = bg1;
    }
    
    public static ArrierePlan getBg2() {
        return bg2;
    }
    
    public static void setBg2(ArrierePlan bg2) {
        StartingClass.bg2 = bg2;
    }
    
}
