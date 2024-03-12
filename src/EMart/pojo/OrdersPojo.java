/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EMart.pojo;


public class OrdersPojo {

    @Override
    public String toString() {
        return "OrdersPojo{" + "orderId=" + orderId + ", pId=" + pId + ", Quantity=" + Quantity + ", userId=" + userId + '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
   private String orderId;
   private String pId;
   private int Quantity;
   private String userId;
}
