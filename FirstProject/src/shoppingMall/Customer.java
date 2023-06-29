package shoppingMall;

public class Customer {
   
   private int customID;
   private String customName;
   private String phoneNum;
   private String address;

   Customer(int customID, String customName, String phoneNum, String address) {
      this.customID = customID;
      this.customName = customName;
      this.phoneNum = phoneNum;
      this.address = address;
   }

   public int getCustomID() {
      return customID;
   }

   public void setCustomID(int customID) {
      this.customID = customID;
   }

   public String getCustomName() {
      return customName;
   }

   public void setCustomName(String customName) {
      this.customName = customName;
   }

   public String getPhoneNum() {
      return phoneNum;
   }

   public void setPhoneNum(String phoneNum) {
      this.phoneNum = phoneNum;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }
  
}