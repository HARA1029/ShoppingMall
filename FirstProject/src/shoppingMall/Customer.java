package shoppingMall;

import java.io.*;
import java.util.*;

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
   
   public Customer() {
      
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


   static HashMap<Integer, Customer> customerHash = new HashMap<>();
   
   static Scanner sc = new Scanner(System.in);
   
   
   static void createCustomer() {
   
      CustomerManager cm = new CustomerManager();
      
      System.out.print("고객 ID (3자리이내의 숫자로 입력해주세요): ");
      int customID = sc.nextInt();
      
      sc.nextLine();
      System.out.print("고객명 : ");
      String customName = sc.nextLine();
      
      System.out.print("전화번호 : ");
      String phoneNum = sc.nextLine();
      
      System.out.print("주소 : ");
      String address = sc.nextLine();
      
      Customer customer = new Customer(customID, customName, phoneNum, address);
      //customerHash.put(customID, customer);
      
      cm.addList(customer);
      
      System.out.println("결과 : " + customID + " " + customName + " 등록 완료");
      System.out.println();
      AllCustomer();
      
   }
   
   static void modifyCustomer() {
   
      System.out.println("정보를 수정할 ID를 입력해주세요.");
      System.out.println("ID : ");
      int customID = sc.nextInt();
      
      for(Integer id : customerHash.keySet()) {
         System.out.println(String.format("id : %s, 값 : %s", id, customerHash.get(id)));
      }
      
      System.out.print("수정할 ID : ");
      int getCustomID = sc.nextInt();
      
      sc.nextLine();
      System.out.print("수정할 고객명 : ");
      String getCustomName = sc.nextLine();
      
      System.out.print("수정할 전화번호 : ");
      String getPhoneNum = sc.nextLine();
      
      System.out.print("주소 : ");
      String getAddress = sc.nextLine();
      
      Customer customer = new Customer(getCustomID, getCustomName, getPhoneNum, getAddress);
      customerHash.replace(customID, customer);
      
      System.out.println("결과 : 수정 완료");
      
      AllCustomer();
      
   }
   
   
   public static void deleteCustomer() {
      
      System.out.println("정보를 수정할 ID를 입력해주세요.");
      System.out.println("ID : ");
      int customID = sc.nextInt();
       System.out.println(customerHash.keySet());
      
      for(Integer id : customerHash.keySet()) {
         System.out.println(String.format("id : %s, 값 : %s", id, customerHash.get(id)));
      }
      
      customerHash.remove(customID);
      
      System.out.println("결과 : 삭제 완료");
      
      AllCustomer();
      
      System.out.println();
      
   }
   
   static void AllCustomer() {
      //System.out.println("고유번호     ID    고객명    전화번호    주소");
      Iterator<Integer> keys = customerHash.keySet().iterator();
      while(keys.hasNext()) {
         Integer key = keys.next();
         System.out.println(String.format("id:%s, customer : %s", key, customerHash.get(key)));
      }
   }
   
   public static void main(String[] args) throws IOException {
      
      /*
        String filePath = "C:\\Users\\gyeong\\KOSA\\git\\kosa_java\\data1.txt";
        File file = null; BufferedWriter bw = null; String NEWLINE =
        System.lineSeparator();
        try { file = new File(filePath); bw = new BufferedWriter(new
        FileWriter(file));
        bw.write("번호, 이름, 지역"); bw.write(NEWLINE);
        bw.write("1,김철수,서울"); bw.write("\n");
        bw.write("2,김영희,경기"); bw.write("\n");
        bw.write("3,이철희,경북");
        bw.flush(); bw.close(); } catch(Exception e) { e.printStackTrace(); }
       */
      
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(
                 new FileReader("C:\\Users\\gyeong\\KOSA\\git\\kosa_java\\data1.txt")
             );
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      
      String str;
      try {
         while((str = reader.readLine()) != null) {
            System.out.println(str);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      reader.close();
   }
}