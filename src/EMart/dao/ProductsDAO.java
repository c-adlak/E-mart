/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMart.dao;

import EMart.dbutil.DBconnection;
import EMart.pojo.ProductsPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProductsDAO {
    public static String getNextProductId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(p_id) from products");
        rs.next();
        String productid = rs.getString(1);
       
        if(productid == null)
            return "P101";
       
        int productno = Integer.parseInt(productid.substring(1));
        productno = productno+1;
        return "P"+productno ;
    
    } 
    public static boolean addProduct(ProductsPojo p)throws SQLException{
         Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("insert into products values(?,?,?,?,?,?,?,'Y')");
       ps.setString(1,p.getProductId());
       ps.setString(2,p.getProductName());
       ps.setString(3,p.getProductCompany());
       ps.setDouble(4,p.getProductPrice());
       ps.setDouble(5,p.getOurPrice());
       ps.setInt(6,p.getTax());
       ps.setInt(7,p.getQuantity());
       return ps.executeUpdate()==1;
    }
    public static List<ProductsPojo> getProductDetails()throws SQLException{

     Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery("select* from products where ststus='Y' order by p_id");
         ArrayList<ProductsPojo> productList = new ArrayList<>();
         
         while(rs.next()){
             ProductsPojo product = new ProductsPojo();
             product.setProductId(rs.getString(1));
             product.setProductName(rs.getString(2));
             product.setProductCompany(rs.getString(3));
             product.setProductPrice(rs.getDouble(4));
             product.setOurPrice(rs.getDouble(5));
             product.setTax(rs.getInt(6));
             product.setQuantity(rs.getInt(7));
         }
         return productList;
    
    }
    public static boolean deleteProduct(String productId)throws SQLException{
       Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("update products set status='N' where p_id=?");
     ps.setString(1,productId);
     return ps.executeUpdate()==1;
    }
    public static boolean updateProduct(ProductsPojo p)throws SQLException{
          Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("update products set p_name=?,p_companyname=?,p_price=?,our_price=?,p_tax=?,quantity=? where p_id=?");
       
       ps.setString(1,p.getProductName());
       ps.setString(2,p.getProductCompany());
       ps.setDouble(3,p.getProductPrice());
       ps.setDouble(4,p.getOurPrice());
       ps.setInt(5,p.getTax());
       ps.setInt(6,p.getQuantity());
       ps.setString(7,p.getProductId());
        return ps.executeUpdate()==1;
    }
     public static List<ProductsPojo>getAllProducts()throws SQLException
   {
     Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery("select * from products where status='Y' order by p_id");
         ArrayList<ProductsPojo> productList = new ArrayList<>();
        
         while(rs.next()){
            ProductsPojo p = new ProductsPojo();
             p.setProductId(rs.getString(1));
             p.setProductName(rs.getString(2));
             p.setProductCompany(rs.getString(3));
             p.setProductPrice(rs.getDouble(4));
             p.setOurPrice(rs.getDouble(5));
             p.setQuantity(rs.getInt(7));
             p.setTax(rs.getInt(6));
             productList.add(p);
         }
         return productList;
        
   }
     
     public static ProductsPojo getProductDetails(String id)throws SQLException{
          Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("Select * from products where p_id=? and status='Y'");
     ps.setString(1,id);
     ResultSet rs = ps.executeQuery();
      ProductsPojo p = new ProductsPojo();
     if(rs.next()){
         p.setProductId(rs.getString(1));
             p.setProductName(rs.getString(2));
             p.setProductCompany(rs.getString(3));
             p.setProductPrice(rs.getDouble(4));
             p.setOurPrice(rs.getDouble(5));
             p.setTax(rs.getInt(6)); 
              p.setQuantity(rs.getInt(7));
     }
     return p;
     }
     
     public static boolean updateStock(List<ProductsPojo> productList)throws SQLException{
         Connection conn = DBconnection.getConnection();
       PreparedStatement ps  = conn.prepareStatement("update products set quantity=quantity-? where p_id=?");
       int x=0;
       for(ProductsPojo p: productList){
           ps.setInt(1, p.getQuantity());
           ps.setString(2, p.getProductId());
          int rows= ps.executeUpdate();
          if(rows!=0){
              x++;
          }
       }
     return x==productList.size();
     }
     
    
    
             
}
