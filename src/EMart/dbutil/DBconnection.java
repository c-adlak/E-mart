/*!
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBconnection {
  
    private static Connection conn;
    static{
        try{
          System.out.println("loading driver");
           Class.forName("oracle.jdbc.OracleDriver");
          
//           String url = "jdbc:oracle:thin:2//DESKTOP-VPR5Q24:1521/xe";
//           String user = "myProject";
//           String password = "java";
           
           conn = DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-VPR5Q24:1521/xe","grocery", "grocery");
           JOptionPane.showMessageDialog(null, "Connection Open Sucessfully","Sucess" ,JOptionPane.INFORMATION_MESSAGE);
           
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in lodding the driver","Driver Error!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1); 
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in opening connection","DB Error!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);  
        }
    }
    
    public static Connection getConnection(){
        return conn;
    }
    
    public static void closeConnection(){
        try{
            conn.close();
             JOptionPane.showMessageDialog(null, "Connection closed Sucessfully","Sucess" ,JOptionPane.INFORMATION_MESSAGE);
            
        }
         catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in closing connection","DB Error!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            
        }
    }
}
