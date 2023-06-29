package shoppingMall;


public class Main {

	public static void main(String[] args) {
		 
		 ProductManager pm = new ProductManager();
		 CustomerManager cm = new CustomerManager();
		 
		 PrintMenu menu = new PrintMenu();
		 
		 // 고객/상품 리스트 불러오기
		 
		 cm.readList();
		 pm.readList();
			  
		 // 메인 메뉴
		 menu.printMain(cm,pm);
		 
		 // 고객/상품 리스트 업로드
			
	     cm.uploadList();	  			
	  	 pm.uploadList();

	}
}
