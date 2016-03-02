/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import bean.ArrierePlan;
import bean.Avion;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    private Avion avion;
    private Image image, character, background, planMoved, avionActuel;
    private static ArrierePlan bg1, bg2;
//    private URL base;
    private Graphics second;

    @Override
    public void destroy() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void start() {
        bg1 = new ArrierePlan(0, 2000);
        bg2 = new ArrierePlan(0, -2000);
        avion = new Avion();
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

        character = getImage(getCodeBase(), "res/mini-plan1.png");
        planMoved = getImage(getCodeBase(), "res/mini-plan1-onMove.png");
        avionActuel = character;
        background = getImage(getCodeBase(), "res/warshipsBackground.jpg");

    }

    @Override
    public void run() {
        while (true) {
            avion.update();
            if (avion.isMovingUp()) {
                avionActuel = planMoved;
            } else if (avion.isMovingDown()) {
                avionActuel = character;
            }
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
                avionActuel = planMoved;
                avion.up();
                repaint();
                System.out.println("Avancer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:
                avion.down();
                avionActuel = planMoved;
                repaint();
                System.out.println("Reculer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:
                avion.moveLeft();
                avionActuel = planMoved;
                repaint();
                System.out.println("vol droite Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:
                avion.moveRight();
                avionActuel = planMoved;
                repaint();
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

                System.out.println("Arrête de bouger vers le haut " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:
                avion.stop();
                System.out.println("Arrête de bouger vers le bas " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:
                avion.stop();
                System.out.println("Arrête de bouger à gauche " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:
                avion.stop();
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

    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jetGame;

import bean.ArrierePlan;
import bean.Avion;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    private Avion avion;
    private Image image, character, background, planMoved, avionActuel;
    private static ArrierePlan bg1, bg2;
//    private URL base;
    private Graphics second;

    @Override
    public void destroy() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {
        bg1 = new ArrierePlan(0, 0);
        bg2 = new ArrierePlan(1366, 0);
        avion = new Avion();
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

        character = getImage(getCodeBase(), "mini-plan1.png");
        planMoved = getImage(getCodeBase(), "mini-plan1-onMove.png");
        avionActuel = character;
        background = getImage(getCodeBase(), "warshipsBackground.jpg");

    }

    @Override
    public void run() {
        while (true) {
            avion.update();
            if (avion.isMovingUp()) {
                avionActuel = planMoved;
            } else if (avion.isMovingDown()) {
                avionActuel = character;
            }
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
                avionActuel = planMoved;
                avion.up();
                System.out.println("Avancer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:
                avion.down();
                avionActuel = planMoved;
                System.out.println("Reculer Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:
                avion.moveLeft();
                avionActuel = planMoved;
                System.out.println("vol droite Pressed " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:
                avion.moveRight();
                avionActuel = planMoved;
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

                System.out.println("Arrête de bouger vers le haut " + e.getKeyCode());
                break;
            case KeyEvent.VK_DOWN:
                avion.stop();
                System.out.println("Arrête de bouger vers le bas " + e.getKeyCode());
                break;
            case KeyEvent.VK_LEFT:
                avion.stop();
                System.out.println("Arrête de bouger à gauche " + e.getKeyCode());
                break;
            case KeyEvent.VK_RIGHT:
                avion.stop();
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
        g.drawImage(avionActuel, avion.getCenterX() , avion.getCenterY(), this);
        

    }

    public static ArrierePlan getBg1() {
        return bg1;
    }

    public static ArrierePlan getBg2() {
        return bg2;
    }
}
