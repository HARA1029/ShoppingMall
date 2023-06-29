package shoppingMall;

import java.util.Scanner;

public class PrintMenu {
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
   
   private static Scanner sc = new Scanner(System.in);
   
   public void printMain(CustomerManager cm,ProductManager pm) {
      boolean run = true;
      while(run) {
         System.out.println("--------------------------------------------");
         System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 끝내기");
         System.out.println("--------------------------------------------");
         System.out.print("선택 >> ");
         System.out.println();
         
         int n = sc.nextInt();
         
         if(n == 1) {
            product(pm);
         }else if(n == 2) {
            customer(cm);
         }else if(n == 3) {
            order();
         }else if(n == 0) {
            run = false;
         }else {
            System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
         }
      } System.out.println("종료");

   }
   
   private static void product(ProductManager pm) {
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
            pm.addList();
         } else if(n == 2) {
            pm.modifyList();
         } else if(n == 3) {
            pm.deleteList();
         } else if(n == 0) {
            run = false;
         }
         
         //상품 리스트 출력 
         pm.printList();
      }
   }

   static void customer(CustomerManager cm){
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
            cm.addList();
         } else if(n == 2) {
            cm.modifyList();
         } else if(n == 3) {
            cm.deleteList();
         } else if(n == 0) {
            run = false;
         }
         
         //고객 리스트 출력 
	      cm.printList();
      }
      
   }

   private static void order() {
	   boolean run = true;
	      while(run) {
	         System.out.println("3. 주문관리");
	         System.out.println("-----------------------------------------");
	         System.out.println(" 1. 조회 | 2. 구매 | 3. 환불 | 0. 이전단계 ");
	         System.out.println("-----------------------------------------");
	         System.out.println("선택 >>> ");
	         System.out.println();
	         
	         int n = sc.nextInt();
	         
	         if(n == 1) {
	            //cm.addList();
	         } else if(n == 2) {
	            //cm.modifyList();
	         } else if(n == 3) {
	            //cm.deleteList();
	         } else if(n == 0) {
	            run = false;
	         }
      
   }

   }
}

