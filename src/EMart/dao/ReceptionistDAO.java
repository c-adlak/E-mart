/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMart.dao;

import EMart.dbutil.DBconnection;
import EMart.pojo.ReceptionistPojo;
import EMart.pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.net.aso.r;

public class ReceptionistDAO {
   public static Map<String,String> getNotRegisteredReceptionist()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select empid,empname from employees where JOB='Receptionist' and empid not in (select empid from users where usertype='Receptionist') ");
         HashMap<String,String> receptionistList = new HashMap();
        while(rs.next()){
        String id = rs.getString(1);
        String name = rs.getString(2);
        receptionistList.put(id,name);
       }
        return receptionistList;
   } 
   
   public static boolean addReceptionist(UserPojo user)throws SQLException{
       Connection conn = DBconnection.getConnection();
       PreparedStatement ps = conn.prepareStatement("insert into users values(?,?,?,?,?)");
       ps.setString(1,user.getUserid());
       ps.setString(2,user.getEmpid());
       ps.setString(3,user.getPassword());
       ps.setString(4, user.getUsertype());
       ps.setString(5, user.getUsername());
      int result = ps.executeUpdate();
      return result==1;
   
   }
   
    public static List<ReceptionistPojo>getAllReceptionistDetails()throws SQLException
   {
     Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(" select users.empid,empname,userid,job,salary from users,employees where usertype='Receptionist' and users.empid=employees.empid");
         
         ArrayList<ReceptionistPojo> empList = new ArrayList<>();
         
         while(rs.next()){
             ReceptionistPojo r = new  ReceptionistPojo();
             r.setEmpid(rs.getString(1));
             r.setEmpname(rs.getString(2));
             r.setUserid(rs.getString(3));
             r.setJob(rs.getString(4));
             r.setSalary(rs.getDouble(5));
             empList.add(r);
         }
         return empList;
        
   }
      
    public static Map<String,String> getAllReceptionistId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select userid,username from users where usertype='Receptionist'order by userid");
         HashMap<String,String> receptionistList = new HashMap();
        while(rs.next()){
        String id = rs.getString(1);
        String name = rs.getString(2);
        receptionistList.put(id,name);
       }
        return receptionistList;
    }
    
    public static boolean updatePassword(String userid,String pwd)throws SQLException{
        Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("update users set password=? where userid=?");
        ps.setString(1,pwd);
        ps.setString(2,userid);
        return ps.executeUpdate() == 1;
    }
    public static List<String> getAllReceptionistuserId()throws SQLException{
         Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select userid from users where usertype='Receptionist'order by userid");
        List<String> receptionistList = new ArrayList();
        while(rs.next()){
        String id = rs.getString(1);
        receptionistList.add(id);
       }
        return receptionistList;
    }

     public static boolean deleteEmloyee(String userid)throws SQLException{
       Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("delete from users where userid=?");
       ps.setString(1,userid); 
       int x = ps.executeUpdate();
       return x==1;
     }
}
