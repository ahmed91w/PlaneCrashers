/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Joueur;
import bean.Partie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class JoueurDAO {
    
    public int create(Joueur joueur) throws SQLException {
        String requette = "insert into joueur values('" + joueur.getNom()+"')";
        System.out.println(requette);
        return ConnectionBD.exec(requette);
    }
    
    public List<Joueur> select(String requette) throws SQLException {
        int flag = 0;
        ResultSet resultSet = ConnectionBD.select(requette);
        List<Joueur> joueurs = new ArrayList<>();
        joueurs.clear();
        while (resultSet.next()) {
            flag = 1;
            Joueur joueur = new Joueur();
            joueur.setNom(resultSet.getString("nom"));
            joueurs.add(joueur);
        }
        if (flag == 1) {
            return joueurs;
        } else {
            return null;
        }
    }
    
    public List<Joueur> findAll() throws SQLException{
         String requette = "select * from joueur ";
         System.out.println(requette);
         return select(requette);
     }
    
}
