/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Joueur;
import dao.JoueurDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class JoueurService {
    
    JoueurDAO joueurDAO = new JoueurDAO();
    
     public int create(Joueur joueur) throws SQLException {
         return joueurDAO.create(joueur);
     }
     
     public List<Joueur> findAll() throws SQLException{
         return joueurDAO.findAll();
     }
    
}
