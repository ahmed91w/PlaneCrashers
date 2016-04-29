/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jetGame.StartingClass;
import service.MediaPlayer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class PowerUp implements Runnable {

    private int x; //coordonnées
    private int y = -60; //coordonnées
    private int hauteur = 60; //coordonnées
    private int largeur = 59; //coordonnées
    private Thread move;
    private Image image;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Boolean eated;
    private int vitesseY;
    private Rectangle r = new Rectangle(0, 0, 0, 0);
    private int type;

    public PowerUp() {
        move = new Thread(this);
        Random random = new Random();
        random.nextInt(1200);
        x = random.nextInt(1200);

        vitesseY = StartingClass.partie.getNiveau();
        type = random.nextInt((4 - 1) + 1) + 1;
        eated = false;
        if (type == 1) {
            image = toolkit.getImage("src/res/healtUp.png");
        } else if (type == 2) {
            image = toolkit.getImage("src/res/DoubleShoot.png");
        } else if (type == 3) {
            image = toolkit.getImage("src/res/tripleShoot.png");
        } else if (type == 4) {
            image = toolkit.getImage("src/res/shield.png");
        }

    }

    public void update() {
//        System.out.println("Y==" + y);
        y -= vitesseY;
        vitesseY = -5;
        r.setBounds(x, y, hauteur, largeur);
        if (r.intersects(StartingClass.avion.getR())) {
            MediaPlayer.playSound("/sound/missile_level_up.wav");
            GeneratePowerUp.powerUps.remove(this);
//            System.out.println("Greate you have eat this powerUps");
            eated = true;
            StartingClass.avion.setPowerUpON(this);
            if (type == 1) {//life up
                StartingClass.avion.vie += 1;
                System.out.println("Power Up Type 1");
            } else if (type == 2) {//double shoot 
                System.out.println("Power Up Type 2");
            } else if (type == 3) {//tripleShoot
                System.out.println("Power Up Type 3");
            } else if (type == 4) {//shield
                System.out.println("Power Up Type 4");
                StartingClass.avion.setBouclier(5);
            }
            move.stop();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public Thread getMove() {
        return move;
    }

    public void setMove(Thread move) {
        this.move = move;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Toolkit getToolkit() {
        return toolkit;
    }

    public void setToolkit(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    public Boolean getEated() {
        return eated;
    }

    public void setEated(Boolean eated) {
        this.eated = eated;
    }

    public int getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(int vitesseY) {
        this.vitesseY = vitesseY;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    @Override
    public void run() {
        while (y < 700) {
            
            update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(PowerUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        GeneratePowerUp.powerUps.remove(this);
        move.stop();

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
