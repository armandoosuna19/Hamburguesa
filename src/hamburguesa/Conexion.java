/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hamburguesa;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author fimaz2014
 */
public class Conexion {
    
    Connection conectar = null; 
    
    public Connection conectar(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost/carls-jr","root","");
        }catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return conectar;
       
    }
}
