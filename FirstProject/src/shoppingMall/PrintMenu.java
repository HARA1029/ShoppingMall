package shoppingMall;

import java.util.Scanner;

public class PrintMenu {
	/*
      1. 고객
         (1) 로그인
            1) 상품 조회
            2) 상품 구매
            3) 구매 취소
         (2) 회원가입

      2. 관리자
      (1) 로그인
         1) 상품관리
            1> 등록
            2> 수정
            3> 삭제
         2) 고객관리
            1> 고객 조회

	 */

	private static Scanner sc = new Scanner(System.in);

	String bar="-";
	int bar_count=80;

	public void printMain(CustomerManager cm, ProductManager pm, OrdersManager om) {

		System.out.println("KOSA 쇼핑몰에 오신 것을 환영합니다.");
		System.out.println("서비스를 이용하시려면 로그인을 해주세요");

		boolean run = true;
		while(run) {

			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 고객 | 2. 관리자 | 0. 끝내기");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >> ");


			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				login_c(pm, cm, om);
			}else if(n.equals("2")) {
				login_a(pm, cm, om);
			}else if(n.equals("0")) {
				run = false;
			}else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
			}
		} System.out.println("종료");
	}

	public void login_c(ProductManager pm, CustomerManager cm, OrdersManager om){

		boolean run = true;
		while(run) {

			System.out.println("1. 고객");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 로그인 | 2. 회원가입 | 3. 회원탈퇴 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				cm.checkLogin(pm, om);
			} else if(n.equals("2")) {
				cm.addList();
				cm.uploadList();
			} else if(n.equals("3")) {
				cm.deleteList();
				cm.uploadList();
			} else if(n.equals("0")) {
				run = false;
				//printMain(cm, pm, om);
			} else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				return;
			}
			run = false;
			System.out.println();
		}

	}

	public void login_a(ProductManager pm, CustomerManager cm, OrdersManager om) {

		boolean run = true;
		while(run) {
			System.out.println("2. 관리자");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 로그인 | 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			//sc.nextLine();

			if(n.equals("1")) { //로그인
				System.out.print("ID : ");
				String id = sc.nextLine();

				System.out.print("PW : ");
				String pw = sc.nextLine();

				if ((id.equals("kosa") == true) && (pw.equals("1207") == true)) {
					admin(pm, cm, om);

				} else {
					System.out.println("관리자의 정보가 일치하지 않습니다.");
					continue;
				}
				System.out.println();

			} else if(n.equals("0")) {
				run = false;
			} else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				continue;
			}
			run = false;
			System.out.println();
		}
	}



	public void customer(ProductManager pm, OrdersManager om, String id) { //고객
		/*
            1. 상품조회
            2. 상품구매
            3. 구매취소
		 */

		CustomerManager cm = new CustomerManager();

		boolean run = true;
		while(run) {
			System.out.println("1. 고객");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 상품조회 | 2. 상품구매 | 3. 구매내역 | 4. 구매취소 | 5. 정보수정 | 0. 로그아웃 ");
			System.out.println(bar.repeat(bar_count));
			System.out.println("선택 >>> ");
			System.out.println();

			String n = sc.nextLine();

			if(n.equals("1")) {
				pm.printList();
			} else if(n.equals("2")) {
				pm.printList();
				om.addList(pm,id);
				pm.uploadList();
				om.uploadList();
			} else if(n.equals("3")) {
				om.readList_c(id);
				om.printList_c();
			} else if(n.equals("4")) {
				om.readList_c(id);
				om.printList_c();
				om.deleteList(pm);
				om.uploadList();
			} else if(n.equals("5")) {
				cm.readList();
				cm.modifyList();
				cm.uploadList();
			} else if(n.equals("0")) {
				run = false;
			}
		}
	}


	public void admin(ProductManager pm, CustomerManager cm, OrdersManager om) {

		boolean run = true;
		while(run) {
			System.out.println("2. 관리자");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");
			System.out.println();

			String n = sc.nextLine();
			//System.out.println();

			if(n.equals("1")) {
				product(pm);
			} else if(n.equals("2")) {
				customer_a(cm);
			} else if(n.equals("3")) {
				orders(om);
			} else if(n.equals("0")) {
				run = false;
			}
		}
	}


	public void product(ProductManager pm) {
		boolean run = true;
		while(run) {
			System.out.println("1. 상품관리");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 상품조회 | 2. 상품등록 | 3. 상품수정 | 4. 상품삭제 | 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				pm.printList();
			}else if(n.equals("2")) {
				pm.addList();
				pm.uploadList();
			} else if(n.equals("3")) {
				pm.modifyList();
				pm.uploadList();
			} else if(n.equals("4")) {
				pm.printList();
				pm.deleteList();
				pm.uploadList();
			} else if(n.equals("0")) {
				run = false;
			}
		}

	}


	public void customer_a(CustomerManager cm) { //고객관리

		boolean run = true;
		while(run) {
			System.out.println("2. 고객관리");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 고객조회 | 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				cm.printList();
			}  else if(n.equals("0")) {
				run = false;
			}
		}
		System.out.println();
	}

	public void orders(OrdersManager om) {//관리자 주문 조회

		boolean run = true;
		while(run) {
			System.out.println("3. 주문관리");
			System.out.println(bar.repeat(bar_count));
			System.out.println(" 1. 주문조회 | 0. 이전단계 ");
			System.out.println(bar.repeat(bar_count));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				om.printList();
			}  else if(n.equals("0")) {
				run = false;
			}
		}
		System.out.println();
	}
}