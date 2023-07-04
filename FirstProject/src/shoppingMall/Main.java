package shoppingMall;

import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		//LinkedHashMap : 자료가 입력된 순서를 기억함

		// String 입력값에 공백 허용X
		// 단, 검색에는 앞뒤공백 빼고 처리해주기
		// 입력 예외처리 ( ex. 숫자입력에 문자입력)

		// 의문 ? 여러 곳에서 객체 생성..

		CustomerManager cm = new CustomerManager();
		ProductManager pm = new ProductManager();
		OrdersManager om = new OrdersManager();
		PrintMenu menu = new PrintMenu();

		// 고객/상품/구매 리스트 불러오기

		cm.readList();
		pm.readList();
		om.readList();

		// 메인 메뉴
		menu.printMain(cm,pm,om,menu);

		
 		//고객/상품/구매 리스트 업로드
		
		cm.uploadList();	  			
		pm.uploadList();
		om.uploadList();

		// 변수 깔끔하게 정리 , 주석 달기
	}
}
