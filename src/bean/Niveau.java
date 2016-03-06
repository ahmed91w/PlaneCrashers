/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Niveau {

    private Image background;

    private int difficulte;

    public Niveau(Image background, int difficulte) {
        this.background = background;
        this.difficulte = difficulte;
    }

    public Niveau(int difficulte) {
        this.difficulte = difficulte;
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

}
