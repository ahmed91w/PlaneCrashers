/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Image;
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
    
    public synchronized void addFrame(Image image , Long duration){
    totalDure+=duration;
    frames.add(new AnimFrame(image, duration));
    }
    
    private AnimFrame getFrame(int i ){
        return (AnimFrame)frames.get(i);
    }
    
    public synchronized void update(long elapsedTime){
        if(frames.size()>1){
            tempsAnimation +=elapsedTime;
            if(tempsAnimation>=totalDure){
                tempsAnimation = tempsAnimation % totalDure;
                frameActuel=0;
            }
            while(tempsAnimation > getFrame(frameActuel).endTime ){
                frameActuel++;
            }
        }
    }
    
    public synchronized Image getImage(){
        if(frames.size() == 0){
            return null;
        }else {
            return getFrame(frameActuel).image;
        }
    }
    
}


