package shoppingMall;

import java.util.*;
import java.io.*;

public class Main {
	 static final int PRODUCT = 1;
	 static final int CUSTOMER = 2;
	 static final int ORDER = 3;
	 static Scanner sc = new Scanner(System.in);
	  

	public static void main(String[] args) throws IOException {
		
		 ProductManager mp = new ProductManager();
		 CustomerManager cm = new CustomerManager();
		 
		/*
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String productInfo = br.readLine();
		
		String[] data = productInfo.split(" ");
		
		Product pd = new Product(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		
		ProductManager mp = new ProductManager();

		
		
		// 상품 리스트 불러오기

		mp.readList();
		
		
		// 상품 등록

		mp.addList(pd);
		
		
		ProductManager mp = new ProductManager();
		
		// 상품 리스트 출력

		mp.printList();
		*/
		/*
		boolean run = true;
				
		while(run) {
	        System.out.println("--------------------------------------------------------");
	        System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 끝내기");
	        System.out.println("--------------------------------------------------------");
	        System.out.print("선택 >> ");
	        System.out.println();
	        
	        int n = sc.nextInt();
	        
	        if(n == 1) {
	           printMenu.product();
	        }else if(n == 2) {
	           printMenu.customer();
	        }else if(n == 3) {
	           printMenu.order();
	        }else if(n == 0) {
	           run = false;
	        }else {
	           System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
	        }
	     } 
	     
	     System.out.println("종료");
	    */

		 // 고객 리스트 불러오기

		 cm.readList();
			
			
	     //고객 등록
	     
	     Scanner sc= new Scanner(System.in);
			      
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
	      
	      cm.addList(customer);
	      
	      System.out.println("결과 : " + customID + " " + customName + " 등록 완료");
	      System.out.println();
	      
	      //고객 리스트 출력
	      
	      cm.printList();
	      
	     
	      // 고객 정보 수정
	      
	      System.out.println("정보를 수정할 ID를 입력해주세요.");
	      System.out.println("ID : ");
	      int m_customID = sc.nextInt();
	      
//	      for(Integer id : customerHash.keySet()) {
//	         System.out.println(String.format("id : %s, 값 : %s", id, customerHash.get(id)));
//	      }
	      
	      cm.modifyList(m_customID);
	     
	      
	     //고객 리스트 출력
	      
	     cm.printList();
	        
	    // 고객 정보 삭제

		int delete_id;
		System.out.println("삭제할 ID를 입력하세요.");
					
		delete_id = sc.nextInt();
			
		cm.deleteList(delete_id);
		
		//고객 리스트 출력
	      
	      cm.printList();
	      
	    // 고객 리스트 업로드
		
	     cm.uploadList();
	     
		
		// 상품 리스트 업로드
		
		//mp.uploadList();
				
				
/*
		
		// 상품 정보 수정
		
		int modify_id;
		System.out.println("수정할 ID를 입력하세요.");
				
		Scanner sc = new Scanner(System.in);
		
		modify_id = sc.nextInt();
		
		String m_productInfo = br.readLine();
		
		String[] m_data = m_productInfo.split(" ");
		
		Product m_pd = new Product(Integer.parseInt(m_data[0]),m_data[1],Integer.parseInt(m_data[2]),Integer.parseInt(m_data[3]));
				
		mp.modifyList(modify_id,m_pd);
		
		mp.printList();
				
		
		// 상품 정보 삭제

		int delete_id;
		System.out.println("삭제할 ID를 입력하세요.");
				
		delete_id = sc.nextInt();
		
		mp.deleteList(delete_id);
		
		
		// 상품 리스트 업로드
		
		mp.uploadList();
		
		
		// 상품 목록 조회

		mp.printList();
*/
	}
}
