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
import jetGame.StartingClass;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class PowerUp implements Runnable {

    private int x; //coordonnées
    private int y = 0; //coordonnées
    private Thread move;
    private Image image;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Boolean eat = false;
    private int vitesseY;
    private Rectangle r = new Rectangle(0, 0, 0, 0);

    public PowerUp() {
        move = new Thread(this);
        Random random = new Random();
        random.nextInt(1200);
        x = random.nextInt(1200);
        y = -5;
        image = toolkit.getImage("src/res/nbr-vieOn.png");
        vitesseY = StartingClass.partie.getNiveau();
    }

    public void update() {

        y -= vitesseY;
        vitesseY = -StartingClass.partie.getNiveau();
        r.setBounds(x, y, 30, 30);

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

    public Boolean getEat() {
        return eat;
    }

    public void setEat(Boolean eat) {
        this.eat = eat;
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

        while (StartingClass.avion.vie != 0) {
            if (x < 700) {
                update();
            } else {
                move.stop();
            }

        }

    }

}
