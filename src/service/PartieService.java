/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Partie;
import dao.PartieDAO;
import java.sql.SQLException;

/**
 *
 * @author Ahmed WAFDI <ahmed.wafdi22@gmail.com>
 * @author Anas SAOUDI <anassaoudii@gmail.com>
 */
public class PartieService {

    PartieDAO partieDAO = new PartieDAO();

    public int create(Partie partie) throws SQLException {
        return this.partieDAO.create(partie);
    }

}
