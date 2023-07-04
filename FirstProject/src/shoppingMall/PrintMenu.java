package shoppingMall;

import java.util.Scanner;

public class PrintMenu {
	/*
      1. 고객
         (1) 로그인
            1) 상품 조회
            2) 상품 구매
            3) 구매 내역
            4) 구매 취소
            5) 정보 수정
         (2) 회원가입
         (3) 회원탈퇴

      2. 관리자
         (1) 로그인
            1) 상품관리
               1> 상품조회
               2> 상품등록
               3> 상품수정
               4> 상품삭제
            2) 고객관리
               1> 고객 조회
             3) 주문관리
                1> 주문 조회
      0. 종료


	 */

	private static Scanner sc = new Scanner(System.in);

	String bar = "-";
	int barCount = 80;

	//메인 첫화면
	public void printMain(CustomerManager cm, ProductManager pm, OrdersManager om, PrintMenu menu) {

		/*
       KOSA 쇼핑몰에 오신 것을 환영합니다.
       서비스를 이용하시려면 로그인을 해주세요.

        1. 고객
        2. 관리자
        0. 끝내기
		 */

		System.out.println("KOSA 쇼핑몰에 오신 것을 환영합니다.");
		System.out.println("서비스를 이용하시려면 로그인을 해주세요.");

		boolean run = true;
		while(run) {

			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 고객 | 2. 관리자 | 0. 끝내기");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//고객 로그인
				loginCustomer(pm, cm, om, menu);
			}else if(n.equals("2")) {
				//관리자 로그인
				loginAdmin(pm, cm, om);
			}else if(n.equals("0")) {
				//종료
				run = false;
			}else {
				//1, 2, 0 이외의 값을 입력할 경우
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
			}
		} System.out.println("종료");
	}

	//메인화면에서 고객 선택시
	public void loginCustomer(ProductManager pm, CustomerManager cm, OrdersManager om, PrintMenu menu){

		/*
       1. 로그인
       2. 회원가입
       3. 회원탈퇴
       0. 이전단계(메인화면)
		 */
		boolean run = true;
		while(run) {

			System.out.println("1. 고객");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 로그인 | 2. 회원가입 | 3. 회원탈퇴 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//로그인
				if(!cm.checkLogin(cm, pm, om, menu))
					continue;

			} else if(n.equals("2")) {
				//회원가입
				cm.addList(cm, pm, om, menu);
			} else if(n.equals("3")) {
				//회원탈퇴
				cm.deleteList();
			} else if(n.equals("0")) {
				run = false;
			} else {
				//1, 2, 3, 0 이외의 값을 입력시 
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				continue;
			}
			run = false;
			System.out.println();
		}

	}

	//메인화면에서 관리자 선택시
	public void loginAdmin(ProductManager pm, CustomerManager cm, OrdersManager om) {

		/*
       1. 로그인
       2. 이전단계
		 */
		boolean run = true;
		while(run) {
			System.out.println("2. 관리자");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 로그인 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) { 
				//로그인
				System.out.print("ID : ");
				String id = sc.nextLine();

				System.out.print("PW : ");
				String pw = sc.nextLine();
				System.out.println();

				//관리자 : 1명
				//ID : kosa, PW: 1207
				if ((id.equals("kosa") == true) && (pw.equals("1207") == true)) {
					admin(pm, cm, om);
					System.out.println();

				} else {
					//잘못된 정보 입력시 메인화면으로 돌아가기
					System.out.println("관리자의 정보가 일치하지 않습니다.");
					System.out.println();
					continue;
				}

			}
			//메인 화면으로 돌아가기
			else if(n.equals("0")) {
				run = false;
			} else {
				System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요.");
				System.out.println();
				continue;
			}
			run = false;
		}

	}

	//고객 로그인시 
	public void customer(CustomerManager cm, ProductManager pm, OrdersManager om, String id) { //고객
		/*
            1. 상품조회
            2. 상품구매
            3. 구매내역
            4. 구매취소
            5. 정보수정
            0. 로그아웃(메인화면으로 돌아감)
		 */

		boolean run = true;
		while(run) {
			System.out.println("1. 고객");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 상품조회 | 2. 상품구매 | 3. 구매내역 | 4. 구매취소 | 5. 정보수정 | 0. 로그아웃 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");


			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//저장되어 있는 상품 리스트 출력
				pm.printList();
			} else if(n.equals("2")) {
				//상품 구매

				//상품목록 출력
				pm.printList();
				//주문목록에 추가
				om.addList(pm,id);
			} else if(n.equals("3")) {
				//구매 내역

				//주문 파일에서 고객정보와 일치하는 주문정보 가져오기
				om.readListCustomer(id);
				//가져온 목록 출력
				om.printListCustomer();
			} else if(n.equals("4")) {
				//구매 취소

				//주문 파일에서 고객정보와 일치하는 주문정보 가져오기
				om.readListCustomer(id);
				//가져온 목록 출력
				om.printListCustomer();
				//주문 파일에서 해당도는 목록 삭제
				om.deleteList(pm);

			} else if(n.equals("5")) {
				//고객(본인) 정보 수정

				//수정할 고객정보 만들기
				cm.modifyList();
			} else if(n.equals("0")) {
				//로그아웃
				run = false;
			}
		}
		System.out.println();
	}

	//관리자 로그인시
	public void admin(ProductManager pm, CustomerManager cm, OrdersManager om) {

		boolean run = true;
		while(run) {
			System.out.println("2. 관리자");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 상품관리 | 2. 고객관리 | 3. 주문관리 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			//System.out.println();

			if(n.equals("1")) {
				product(pm);
			} else if(n.equals("2")) {
				customerAdmin(cm);
			} else if(n.equals("3")) {
				orders(om);
			} else if(n.equals("0")) {
				run = false;
			}
		}
		System.out.println();
	}


	//관리자 - 상품관리 
	public void product(ProductManager pm) {
		/*
       1. 상품조회
       2. 상품등록
       3. 상품수정
       4. 상품삭제
       0. 이전단계(관리자 관리목록)
		 */
		boolean run = true;
		while(run) {
			System.out.println("1. 상품관리");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 상품조회 | 2. 상품등록 | 3. 상품수정 | 4. 상품삭제 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//상품목록조회
				pm.printList();
			}else if(n.equals("2")) {
				//상품추가
				pm.addList();

			} else if(n.equals("3")) {
				//상품수정
				
				//상품 목록 조회
				pm.printList();
				//상품수정
				pm.modifyList();

			} else if(n.equals("4")) {
				//상품삭제

				//상품 목록 조회
				pm.printList();
				//삭제할 상품 정보
				pm.deleteList();

			} else if(n.equals("0")) {
				//관리자 목록 출력
				run = false;
			}
		}
		System.out.println();

	}

	//관리자 - 고객관리
	public void customerAdmin(CustomerManager cm) { 

		/*
       1. 고객조회
       0. 이전단계(관리자 목록조회)
		 */
		boolean run = true;
		while(run) {
			System.out.println("2. 고객관리");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 고객조회 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//고객 목록 출력
				cm.printList();
			}  else if(n.equals("0")) {
				run = false;
			}
		}
		System.out.println();
	}

	//관리자 - 주문조회
	public void orders(OrdersManager om) {

		/*
       1. 주문조회
       0. 이전단계(관리자 목록조회)
		 */
		boolean run = true;
		while(run) {
			System.out.println("3. 주문관리");
			System.out.println(bar.repeat(barCount));
			System.out.println(" 1. 주문조회 | 0. 이전단계 ");
			System.out.println(bar.repeat(barCount));
			System.out.print("선택 >>> ");

			String n = sc.nextLine();
			System.out.println();

			if(n.equals("1")) {
				//주문 목록 조회
				om.printList();
			}  else if(n.equals("0")) {
				run = false;
			}
		}
		System.out.println();
	}
}