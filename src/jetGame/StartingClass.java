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
import bean.GeneratePowerUp;
import bean.Partie;
import bean.Projectile;
import service.Session;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    public static Avion avion;
    public static BossEnnemi bossEnnemi;
    public static Partie partie;
    public static Attack attack;
    public static GeneratePowerUp powerUpGenerator;
    private Image image, vieOff, vieOn, socreboard;
    private static ArrierePlan bg1, bg2;
    private Graphics second;
    public static ThreadGroup g = new ThreadGroup("SFX Sound");
    private JFrame frame;

    private Thread thread;

    public static Thread attackThread;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    Font f;
    Label label2;
    Label label1;

    public StartingClass() {

    }

    public void initImages() {

        socreboard = toolkit.getImage("src/res/ScoreBoard2.png");
        vieOff = toolkit.getImage("src/res/nbr-vie-off.png");
        vieOn = toolkit.getImage("src/res/nbr-vieON.png");
    }

    public void initComponent() throws FontFormatException, IOException {
        setSize(toolkit.getScreenSize());
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("JetGame By AHMED WAFDI & ANAS SAOUDI");
        frame.setFocusable(true);
        //Font f = new Font("Brush Script MT Italic", Font.ITALIC, 24);
        f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/font/digital-7.ttf"));
        f = f.deriveFont(75.0f);
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

        try {
            initComponent();
        } catch (FontFormatException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        initImages();
        partie = (Partie) Session.getAttributes("partie");

        //InfoBoard
        Label label = new Label();
        label.setBounds(1170, 168, 80, 31);
        label1 = new Label();
        label1.setBounds(1270, 108, 10, 10);
        label2 = new Label();
        label2.setBounds(1070, 108, 10, 10);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label1.setFont(new Font("Arial", Font.BOLD, 12));
        label2.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBackground(new Color(0, 100, 186));
        label1.setBackground(Color.WHITE);
        label2.setBackground(Color.WHITE);
        label1.setForeground(new Color(131, 28, 28));
        label2.setForeground(new Color(131, 28, 28));
        label.setText(partie.getJoueur().getNom());
        label1.setText("##");
        label2.setText(partie.getNiveau().toString());

        add(label);
        add(label1);
        add(label2);

        partie = (Partie) Session.getAttributes("partie");
        bossEnnemi = new BossEnnemi();
    }

    @Override
    public void start() {

        attack = new Attack();

        //lancer les threads ici
        //Inctances des Backgrounds et Avion
        bg1 = new ArrierePlan(0, -1750);
        bg2 = new ArrierePlan(0, -4200);
        avion = new Avion(partie.getAvion());
        System.out.println("partie----->" + partie);
        thread = new Thread(this, "Principal Thread");
        thread.start();
        //Lancement de l'Attack des AvionEnnemis
        attackThread = new Thread(attack, "Attack Thread");
        attackThread.start();

        powerUpGenerator = new GeneratePowerUp();
        powerUpGenerator.startGeneration();
        if (partie.audio) {
            MediaPlayer.playBgSound("/res/sound/Music-loop-electronic.wav");
        }
        

    }

    @Override
    public void run() {

        //rafraichire l ecrant tant que l'avion et en vie
//les modifications des x,y se fait ici;

        while (avion.vie != 0) {
            if (avion.vie != 0) {

                whileAvionIsAlive();
            } else {

                gameOver();

            }

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (partie.audio) {
            MediaPlayer.playSound("/res/sound/game_over.wav");
        }

        bg1.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg1.setBgY(-1750);
        repaint();
        int choix = JOptionPane.showConfirmDialog(this, "Joueur : " + partie.getJoueur().getNom() + "\n Votre Score : " + partie.score + " \n ", "Fin de partie", JOptionPane.CLOSED_OPTION,
                JOptionPane.CLOSED_OPTION);
        if (choix == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        System.exit(0);
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
    public void keyReleased(KeyEvent e
    ) {
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
                    if (partie.audio) {
                    MediaPlayer.playSound("/res/sound/weapon_player.wav");
                    }
                }
                break;

        }
    }

    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == restart) {
//          
//        }

    }

    public synchronized void gameOver() {
        try {
            thread.wait(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartingClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        attackThread.stop();
        if (bossEnnemi.getMove().isAlive()) {
            bossEnnemi.stopMove();
        }
        for (int i = 0; i < attack.avionEnnemis.size(); i++) {
            if (attack.avionEnnemis.get(i).getMoveAvionEnnemi().isAlive()) {
                attack.avionEnnemis.get(i).getMoveAvionEnnemi().stop();
            }
        }
        bg1.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg2.setBackground(toolkit.getImage("src/res/gameoverbg.png"));
        bg1.update();
        bg2.update();
        repaint();
    }

    public void whileAvionIsAlive() {
        levelUp();
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

        if (bossEnnemi.getMove().isAlive() == true) {
            updateBossEnnemi();
        }
//        attack.removeEnnemisOverLimitte();

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
        drawInfoBoard(g);
        drawNbrVie(g, vieOn, avion.vie);

        drawProjectilles(g);

        drawAvion(g);

        drawEnnemis(g);
        drawShootEnnemie(g);
        if (bossEnnemi.getMove().isAlive() == true) {
            drawBossEnnemie(g);
        }
        drawShield(g);
        powerUpGenerator.drawPowerUp(g, this);

//        if (partie.getNiveau() == 1 || partie.getNiveau() == 2 && partie.score >= 1000 && partie.score <= 1050) {
//            g.drawImage(toolkit.getImage("src/res/levelCompleted.png"), 550, 200, this);
//
//        }
    }

    public void levelUp() {
        if (partie.score >= 1000 && partie.getNiveau() == 1) {
            partie.setNiveau(2);
        } else if (partie.score >= 2000 && partie.getNiveau() == 2) {
            partie.setNiveau(3);

        } else if (partie.score >= 3000 && partie.getNiveau() == 3) {
            partie.setNiveau(4);
        }

    }

    public void drawEnnemis(Graphics g) {
        for (int i = 0; i < attack.avionEnnemis.size(); i++) {
            g.drawImage(attack.avionEnnemis.get(i).getImage(), attack.avionEnnemis.get(i).getCenterX(), attack.avionEnnemis.get(i).getCenterY(), this);

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
            bossEnnemi.drawBossHealth(g, this);
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

    public void drawInfoBoard(Graphics g) {
        //Affichage Score
        g.drawImage(socreboard, 1000, 10, this);
        Color c = new Color(131, 28, 28);
        g.setColor(c);
        String s = Integer.toString(partie.score);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                StringBuffer bf = new StringBuffer(s);
                bf.insert(i, ' ');
                g.drawString("" + bf.toString(), 1090, 142);
                break;
            } else {
                g.drawString("" + partie.score, 1090, 142);
                break;
            }
        }
        //Affichage Niveau
        label2.setText(partie.getNiveau().toString());
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

    public boolean isLevelUp() {

        return true;
    }
}
