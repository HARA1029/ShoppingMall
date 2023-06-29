package shoppingMall;


public class Main {

	public static void main(String[] args) {
		
		 // 입력값 검열해주는 코드 추가해야함
		
		 
		 CustomerManager cm = new CustomerManager();
		 ProductManager pm = new ProductManager();
		 OrdersManager om = new OrdersManager();
		 
		 PrintMenu menu = new PrintMenu();
		 
		 // 고객/상품/구매 리스트 불러오기
		 
		 cm.readList();
		 pm.readList();
		 om.readList();
 
		 //초기 메뉴 - 로그인 메뉴
		 
		 
		 
		 // 메인 메뉴
		 menu.printMain(cm,pm,om);
		 
		 // 고객/상품/구매 리스트 업로드
			
	     cm.uploadList();	  			
	  	 pm.uploadList();
	  	 om.uploadList();

	}
}
