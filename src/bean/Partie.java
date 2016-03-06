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

    private Niveau niveau;
    private BossEnnemi boss;

    public void startPartie(int choix) {
        if (choix == 1) {

        } else if (choix == 2) {

        } else if (choix == 3) {

            boss = new BossEnnemi();
            Thread bossThread = new Thread(boss);
            bossThread.start();

        }
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public BossEnnemi getBoss() {
        return boss;
    }

    public void setBoss(BossEnnemi boss) {
        this.boss = boss;
    }

}
