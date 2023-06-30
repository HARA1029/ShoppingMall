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

	private static Scanner sc = new Scanner(System.in);
	
	public void printMain(CustomerManager cm,ProductManager pm,OrdersManager om) {

		System.out.println("KOSA 쇼핑몰에 오신 것을 환영합니다.");
		System.out.println("서비스를 이용하시려면 로그인을 해주세요");

		boolean run = true;
		while(run) {
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 고객 | 2. 관리자 | 0. 끝내기");
			System.out.println("--------------------------------------------------------");
			System.out.print("선택 >> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) {
				login_c(pm, cm, om);
			}else if(n == 2) {
				login_a(pm, cm, om);
			}else if(n == 0) {
				run = false;
			}else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				return;
			}
		} System.out.println("종료");
	}

	public void login_c(ProductManager pm, CustomerManager cm, OrdersManager om){
		boolean run = true;
		while(run) {

			System.out.println("1. 고객");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 로그인 | 2. 회원가입 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) { //로그인
				cm.checkLogin(pm, om);
			} else if(n == 2) { //회원가입
				cm.addList();
				cm.uploadList();
			} else if(n == 0) {
				run = false;
			} else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				return;
			}
			System.out.println();
		}

	}


	public void login_a(ProductManager pm, CustomerManager cm, OrdersManager om) {
		boolean run = true;
		while(run) {
			System.out.println("2. 기업");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 로그인 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			sc.nextLine();

			if(n == 1) { //로그인
				System.out.print("ID : ");
				String id = sc.nextLine();

				System.out.print("PW : ");
				String pw = sc.nextLine();

				if ((id.equals("kosa") == true) && (pw.equals("1207") == true)) {
					admin(pm, cm, om);

				} else {
					System.out.println("관리자의 정보가 일치하지 않습니다. 다시 입력해주세요.");
					System.out.print("관리자가 아니시면 '0' 를 입력해주세요.");
					int num = sc.nextInt();
					if(num == 0)
						run = false;
				}

			} else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				return;
			}
			System.out.println();
		}

	}


	public void product(ProductManager pm) {
		boolean run = true;
		while(run) {
			System.out.println("1. 상품관리");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 조회 | 2. 등록 | 3. 수정 | 4. 삭제 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n==1) {
				pm.printList();
			}else if(n == 2) {
				pm.addList();
				pm.uploadList();
			} else if(n == 3) {
				pm.modifyList();
				pm.uploadList();
			} else if(n == 4) {
				pm.deleteList();
				pm.uploadList();
			} else if(n == 0) {
				run = false;
			}

			//상품 리스트 출력 
			//pm.printList();
		}
	}

	public void customer(ProductManager pm, OrdersManager om, String id) { //고객
		/*
	      1. 상품조회
	      2. 상품구매
	      3. 구매취소
		 */

		boolean run = true;
		while(run) {
			System.out.println("1. 고객");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 상품조회 | 2. 상품구매 | 3. 구매목록조회 | 4. 구매취소 | 0. 로그아웃 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) {
				pm.printList();
			} else if(n == 2) {
				pm.printList();
				om.addList(pm,id);
				om.uploadList();
			} else if(n == 3) {
				om.readList_c(id);
				om.printList_c();
			} else if(n == 4) {
				om.deleteList(pm);
				om.uploadList();
			} else if(n == 0) {
				run = false;
			}
		}
	}

	public void admin(ProductManager pm, CustomerManager cm, OrdersManager om) {

		boolean run = true;
		while(run) {
			System.out.println("2. 관리자");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) {
				product(pm);
			} else if(n == 2) {
				customer_a(cm);
			} else if(n == 3) {
				orders(om);
			} else if(n == 0) {
				run = false;
			}

		}
	}

	public void customer_a(CustomerManager cm){

		boolean run = true;
		while(run) {
			System.out.println("2. 고객관리");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 조회 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) {
				cm.printList();
			} else if(n == 0) {
				run = false;
			}
		}
	}
	
	// 환불 승인 구현해야함
	
	public void orders(OrdersManager om) {
		boolean run = true;
		while(run) {
			System.out.println("3. 주문관리");
			System.out.println("--------------------------------------------------------");
			System.out.println(" 1. 조회 | 0. 이전단계 ");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택 >>> ");
			System.out.println();

			int n = sc.nextInt();

			if(n == 1) {
				om.printList();
			} else if(n == 0) {
				run = false;
			}
		}
	}
}

