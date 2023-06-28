package shoppingMall;

import java.util.Scanner;

public class printMenu {
	/*
    (1) 상품 관리
     - 상품 등록
     - 상품 수정
     - 상품 삭제

   (2) 고객 관리
     - 고객 등록
     - 고객 수정
     - 고객 삭제

   (3) 주문 관리
     - 조회
     - 구매
     - 환불
   */

  static final int PRODUCT = 1;
  static final int CUSTOMER = 2;
  static final int ORDER = 3;
  static Scanner sc = new Scanner(System.in);

  ProductManager mp = new ProductManager();
  
  public void printMain() {	
    
     boolean run = true;
     while(run) {
        System.out.println("--------------------------------------------------------");
        System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 끝내기");
        System.out.println("--------------------------------------------------------");
        System.out.print("선택 >> ");
        System.out.println();
        
        int n = sc.nextInt();
        
        if(n == 1) {
           product();
        }else if(n == 2) {
           //customer();
        }else if(n == 3) {
           order();
        }else if(n == 0) {
           run = false;
        }else {
           System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
        }
     } 
     
     System.out.println("종료");
  }
  
  public static void product() {
      boolean run = true;
      while(run) {
         System.out.println("1. 상품관리");
         System.out.println("-----------------------------------------");
         System.out.println(" 1. 등록 | 2. 수정 | 3. 삭제 | 0. 이전단계 ");
         System.out.println("-----------------------------------------");
         System.out.println("선택 >>> ");
         System.out.println();
         
         int n = sc.nextInt();
         
         if(n == 1) {
            
         } else if(n == 2) {
            
         } else if(n == 3) {
            
         } else if(n == 0) {
            run = false;
         }
      }
   }
  
  public static void customer() {
      boolean run = true;
      while(run) {
         System.out.println("2. 고객관리");
         System.out.println("-----------------------------------------");
         System.out.println(" 1. 등록 | 2. 수정 | 3. 삭제 | 0. 이전단계 ");
         System.out.println("-----------------------------------------");
         System.out.println("선택 >>> ");
         System.out.println();
         
         int n = sc.nextInt();
         
         if(n == 1) {
            Customer.createCustomer();
         } else if(n == 2) {
            Customer.modifyCustomer();
         } else if(n == 3) {
            Customer.deleteCustomer();
         } else if(n == 0) {
            run = false;
         }
      }
      
   }
  
  public static void order() {}
 }

