/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class Niveau {

    private int vitt_ennemie;
    private int nbr_ennemmie;
    private boolean bosse;
    private Image Background;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public Niveau(int vitt_ennemie, int nbr_ennemmie, boolean bosse, String Background) {
        this.vitt_ennemie = vitt_ennemie;
        this.nbr_ennemmie = nbr_ennemmie;
        this.bosse = bosse;
        this.Background = toolkit.getImage("src/res/" + Background);
    }

    public Niveau(int vitt_ennemie) {
        this.vitt_ennemie = vitt_ennemie;
    }

    public Niveau() {
    }

    public int getVitt_ennemie() {
        return vitt_ennemie;
    }

    public void setVitt_ennemie(int vitt_ennemie) {
        this.vitt_ennemie = vitt_ennemie;
    }

    public int getNbr_ennemmie() {
        return nbr_ennemmie;
    }

    public void setNbr_ennemmie(int nbr_ennemmie) {
        this.nbr_ennemmie = nbr_ennemmie;
    }

    public boolean isBosse() {
        return bosse;
    }

    public void setBosse(boolean bosse) {
        this.bosse = bosse;
    }

    public Image getBackground() {
        return Background;
    }

    public void setBackground(Image Background) {
        this.Background = Background;
    }

}
