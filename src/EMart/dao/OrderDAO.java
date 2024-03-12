/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMart.dao;

import EMart.dbutil.DBconnection;
import EMart.pojo.ProductsPojo;
import EMart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class OrderDAO {
      public static String getNextOrderId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(order_id) from orders");
        rs.next();
        String orderid = rs.getString(1);
       
        if(orderid == null)
            return "O-101";
       
        int orderno = Integer.parseInt(orderid.substring(2));
        orderno = orderno+1;
        return "O-"+orderno ;
    
    } 
      public static boolean addOrder(ArrayList<ProductsPojo> al, String ordid)throws SQLException{
          Connection conn = DBconnection.getConnection();
          PreparedStatement ps = conn.prepareStatement("insert into orders values(?,?,?,?)");
         int count=0;
          for(ProductsPojo p:al){
            ps.setString(1, ordid);
            ps.setString(2,p.getProductId());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, UserProfile.getUserid());
            count=count+ ps.executeUpdate();
          }
          return count==al.size();
      }
      
      public static List<String> loadOrderId()throws SQLException{
           Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select order_id from orders");
        
              ArrayList<String> orderid = new ArrayList();
             while(rs.next()){
                String id = rs.getString(1);
                orderid.add(id);
          }
             return orderid;
      } 
      
      public static Map<String,Integer> getIdandQuant(String orderid)throws SQLException{
          Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT p_id, quantity FROM orders WHERE order_id = ?");

          ps.setString(1,orderid);
          ResultSet rs = ps.executeQuery();
         
          Map<String,Integer> stMap = new HashMap<>();
          
         
          while(rs.next()){
            String id  = rs.getString(1);
            int quant = rs.getInt(2);
            stMap.put(id, quant);
          }
          return stMap;
      }
    

      
      public static List<ProductsPojo> getProducts(Map<String,Integer> map)throws SQLException{
           Connection conn = DBconnection.getConnection();
          PreparedStatement ps = conn.prepareStatement("select*from products where p_id=? and status='Y'");
          ArrayList<ProductsPojo> al = new  ArrayList();
         Set<String> stringKeys = map.keySet();
         for(String key : stringKeys){
             System.out.println(key);
         }
         ProductsPojo p = new ProductsPojo();
        
         for (String key : stringKeys) {
             ps.setString(1, key);
             ResultSet rs = ps.executeQuery();
             if(rs.next())
             { 
             int quant = map.get(key);  
             p.setProductId(rs.getString(1));
             p.setProductName(rs.getString(2));
             p.setProductCompany(rs.getString(3));
             p.setProductPrice(rs.getDouble(4));
             p.setOurPrice(rs.getDouble(5));
             p.setTax(rs.getInt(6));
             p.setQuantity(quant);
              al.add(p);
             }
       }
             return al;
     }
}
