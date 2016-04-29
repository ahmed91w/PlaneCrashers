/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class Partie {

    private Joueur joueur = new Joueur();
    private String avion;
    private Integer niveau;
    private BossEnnemi boss;
    public static int score=0;

    public Partie() {
    }

    
    public Partie(String joueur, String avion, Integer niveau) {
        this.joueur .setNom(joueur);
        this.avion = avion;
        this.niveau = niveau;
        //this.score = 0;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
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
