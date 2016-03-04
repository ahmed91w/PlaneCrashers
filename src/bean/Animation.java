/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 */
public class Animation {

    private ArrayList frames;//contenires les frames d'animations
    private int frameActuel;//l'image afficher actuellement
    private long tempsAnimation; //
    private long totalDure;//temps total de l'animation

    public Animation() {
        frames = new ArrayList();
        totalDure = 0;
        synchronized (this) {
            tempsAnimation = 0;
            frameActuel = 0;
        }
    }
}

