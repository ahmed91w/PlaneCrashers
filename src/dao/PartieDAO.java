/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class PartieDAO {

    public int create(Partie partie) throws SQLException {
        String requette = "insert into partie(joueur,avion,niveau) values('" + partie.getJoueur().getNom() + "','" + partie.getAvion() + "'"
                + "," + partie.getNiveau() + ")";
        System.out.println(requette);
        return ConnectionBD.exec(requette);
    }

    public List<Partie> select(String requette) throws SQLException {
        int flag = 0;
        ResultSet resultSet = ConnectionBD.select(requette);
        List<Partie> parties = new ArrayList<>();
        parties.clear();
        while (resultSet.next()) {
            flag = 1;
            Partie partie = new Partie();
            partie.getJoueur().setNom(resultSet.getString("joueur"));
            partie.setAvion(resultSet.getString("avion"));
            partie.setNiveau(resultSet.getInt("niveau"));
            parties.add(partie);
        }
        if (flag == 1) {
            return parties;
        } else {
            return null;
        }
    }
    
    public List<Partie> findAll() throws SQLException{
         String requette = "select * from partie ";
         System.out.println(requette);
         return select(requette);
     }
}
