/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Timer;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class ScoreBoard {

    
    private Joueur joueur;
    private Timer timer;

    public ScoreBoard(Timer timer) {
        this.timer = timer;
    }

    public ScoreBoard() {
        joueur=new Joueur("Ahmed");
    }

    
    

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    
    

}
