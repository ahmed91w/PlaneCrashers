/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Partie {

    private String joueur;
    private String avion;
    private Integer niveau;
    private BossEnnemi boss;
    private int score;

    public Partie(String joueur, String avion, Integer niveau) {
        this.joueur = joueur;
        this.avion = avion;
        this.niveau = niveau;
        this.score = 0;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

}
