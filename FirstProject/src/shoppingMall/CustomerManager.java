package shoppingMall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {

	private LinkedHashMap<String,Customer> customerList = new LinkedHashMap<String,Customer>();

	//파일 저장 경로
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\customer.csv");

	static Scanner sc= new Scanner(System.in); // 입력시 전역변수 설정

	//고객 정보 업로드
	public void uploadList() {

		BufferedWriter bw = null;

		String newLine = System.lineSeparator(); //다음 줄로 넘기기

		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));

			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));

			bw.write("CustomerID,CustomerPW,CustomerName,phoneNum,address"); 
			bw.write(newLine);

			//데이터 입력하는 반복문
			Iterator<String> keys = customerList.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				String aData;

				aData = customerList.get(key).getCustomID() + "," + customerList.get(key).getCustomPW() + "," +
						customerList.get(key).getCustomName()+ "," + customerList.get(key).getPhoneNum()+ "," +
						customerList.get(key).getAddress();

				bw.write(aData);
				bw.write(newLine);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if(bw!=null) {
					bw.flush();
					bw.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//업로드한 정보 읽어오기
	public void readList() {

		List<List<String>> records = new ArrayList<>();

		String line = "";

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[5];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i = 1; i<records.size();i++) {
				Customer cust = new Customer(records.get(i).get(0),records.get(i).get(1),records.get(i).get(2),
						records.get(i).get(3),records.get(i).get(4));
				customerList.put(cust.getCustomID(), cust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//고객 정보 출력
	public void printList() {

		StringBuilder sb = new StringBuilder ();

		System.out.println("  ID      PW       고객명         고객번호          주소 ");
		System.out.println("------------------------------------------------------");

		Iterator<String> keys = customerList.keySet().iterator();

		while(keys.hasNext()) {

			String key = keys.next();

			String id = customerList.get(key).getCustomID();
			String pw = customerList.get(key).getCustomPW();
			String name = customerList.get(key).getCustomName();
			String phoneNum = customerList.get(key).getPhoneNum();
			String address = customerList.get(key).getAddress();

			System.out.printf("%4s %6s %6s %6s %7s\n",id,pw,name,phoneNum,address);
			//         sb.append(id).append(' ').append(pw).append(' ').append(name).append(' ').append(phoneNum).append(' ').
			//         append(address).append("\n");
		}

		System.out.println();
	}

	//고객 로그인시 정보 확인
	public boolean checkLogin(CustomerManager cm, ProductManager pm, OrdersManager om, PrintMenu menu) {

		boolean run = true; //반복문 지속 여부

		boolean check = false; //로그인 성공 여부

		String id = null;

		while(run) {
			System.out.print("ID : ");
			id = sc.nextLine();

			//System.out.println(); // 없으면 ID, PW 붙어서 나옴
			System.out.print("PW : ");
			String pw = sc.nextLine();

			if(customerList.containsKey(id) == true) { // 입력한 ID 와 고객리스트에 있는 ID 비교
				if (customerList.get(id).getCustomPW().equals(pw)) { // ID 가 정보가 일치하는 경우 PW 일치 여부 확인
					System.out.println("로그인에 성공하였습니다.");
					System.out.println();
					run = false; //반복문 종료
					check = true; // 로그인 성공

				} else {
					System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
					System.out.print("가입하지 않으신 분들은 '0' 을 다시 로그인하고 싶으신 분들은 다른 키를 입력해주세요.");
					String num = sc.nextLine();
					if(num.equals("0")) {
						run = false;  
						check = false; //로그인 실패
					}
				}

			} else {
				System.out.println("아이디가 틀렸습니다. 다시 입력해주세요.");
				System.out.print("가입하지 않으신 분들은 '0' 을 다시 로그인하고 싶으신 분들은 다른 키를 입력해주세요.");
				String num = sc.nextLine();
				if(num.equals("0")) {
					run = false;  
					check = false;
				}
			}  
		}

		if(check == true) // 로그인 성공할 경우 
			menu.customer(cm, pm, om, id);

		return check;
	}

	//고객 등록
	public void addList(CustomerManager cm, ProductManager pm, OrdersManager om, PrintMenu menu) {
		//모든 항목에 공백입력 불가
		//각 항목에 입력할 자리수 지정


		String customID = null;
		String customPW = null;
		String customName = null;
		String phoneNum = null;
		String address = null;

		//ID

		boolean run = true;

		while(run) {

			System.out.print("고객 ID (4~6자리로 입력해주세요.): ");
			customID = sc.nextLine();

			//공백입력 불가
			if(customID.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customID = customID.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customID + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			//ID 길이제한 4 ~ 6
			if(customID.length() < 4 || customID.length() > 6 ) { 
				System.out.println("ID는 4~6자리로 입력해주세요.");
				System.out.println("입력하신 글자 수 : " + customID.length());
				System.out.println();
				continue;
			}

			// ID 중복 검사
			if(customerList.containsKey(customID) == true) {
				System.out.println("중복된 아이디입니다. 다시 입력해주세요");
				System.out.println();
				continue;

			} 
			run = false;
		}

		//PW

		run = true;

		while(run){

			System.out.print("고객 PW (4~10자리로 입력해주세요.): ");
			customPW = sc.nextLine();

			//PW 공백 입력 불가
			if(customPW.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customPW = customPW.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customPW + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			// PW 길이제한 4 ~ 10
			if(customPW.length() > 11 || customPW.length() < 4) { 
				System.out.println("PW는 4~10자리로 입력해주세요.");
				System.out.println("입력하신 글자 수 : " + customPW.length());
				System.out.println();
				continue;
			}
			run = false;
		}

		//고객명
		run = true;

		while(run) {
			System.out.print("고객명 : ");
			customName = sc.nextLine();

			//고객명 공백 입력 불가
			if(customName.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customName = customName.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customName + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			//고객명 길이제한 2 ~ 7
			if(customName.length() < 2 || customName.length() > 7) { 
				System.out.println("고객명은 2자 ~ 7자로 입력해주세요.");
				System.out.println();
				continue;
			}
			run = false;
		}

		//고객 전화번호
		// 전체 번호 입력받은 후에 각 자리별로 잘라서 입력받기

		run = true;

		while(run) {


			System.out.print("전화번호 (10~11자리 입력해주세요.): ");
			phoneNum = sc.nextLine();

			//전화번호 공백입력불가
			if(phoneNum.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				phoneNum = phoneNum.replaceAll(" ", "");
				System.out.println("공백제거 후 " + phoneNum + "로 변경하여 입력됩니다.");
				System.out.println();   
				System.out.println();
				continue;
			}

			//전화번호 길이제한 10~11자
			if(phoneNum.length() < 10 || phoneNum.length() > 11 ) {
				System.out.println("전화번호는 10자~11자로 입력해주세요");
				System.out.println("입력하신 글자 수 : " + phoneNum.length());
				System.out.println();
				continue;
			}

			// 앞에 3자리
			String num1 = phoneNum.substring(0,3); 
			String check = num1.substring(0,2);

			//대부분 앞자리 01x로 시작하는 3자리 체크( ex. 010.011.017.018...)
			if(!check.equals("01") ) {
				System.out.println("전화번호는 01x으로 시작해야 합니다.");
				System.out.println();
				continue;
			} else {
				// 중간자리 3~4자리(ex.010이 아닌 번호는 중간에 3자리인 경우 있음.) : 앞 3자리와 뒷 4자리 자르고 나머지 부분 입력
				String num2 = phoneNum.substring(3, phoneNum.length()-4);
				//맨뒤에 4자리 잘라서 입력
				String num3 = phoneNum.substring(phoneNum.length()-4, phoneNum.length());

				//나눈 번호 합쳐서 xxx-xxxx-xxxx 식으로 입력
				System.out.println("입력된 전화번호 : " + num1 + "-" + num2 + "-" +  num3);


			}
			run = false;
		}

		//주소
		run = true;

		while(run) {

			//매우 아쉬운점 : 콘솔로 입력해서 주소 검사하기 어려움
			// => 전화번호 처럼 나누기 어려움(주소는 나뉘는 가짓수가 많이 때문에.
			// ex. 시, 도, 군, 구, 동, 읍, 면, 리

			System.out.print("주소(공백없이 10자리 이상 입력해주세요.) : ");
			address = sc.nextLine();

			if(address.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				address = address.replaceAll(" ", "");
				System.out.println("공백제거 후 " + address + "로 변경하여 입력됩니다.");
				System.out.println();
			}

			//주소 길이 제한 10자 이상
			if(address.length() < 10 || address.length() < 1) { 
				System.out.println("주소는 공백없이 10자 이상으로 입력해주세요");
				System.out.println("입력하신 글자 수 : " + address.length());
				System.out.println();
				continue;
			} 
			run = false;
		}


		Customer ct = new Customer(customID, customPW, customName, phoneNum, address);

		//고객리스트에 고객정보 추가
		customerList.put(ct.getCustomID(),ct);

		System.out.println("결과 : " + customID + " " + customName + " 가입이 완료되었습니다.");
		System.out.println();

		System.out.println("로그인 하시려면 '1' 를 눌러주세요. 초기화면으로 가려면 '0'을 눌러주세요");

		String num = sc.nextLine();

		if(num.equals("1")) {
			checkLogin(cm, pm, om, menu);
			sc.nextLine();

		} else if(num.equals("0")) {
			menu.loginCustomer(pm, cm, om, menu);
		}
	}


	// 고객 정보 수정
	public void modifyList() {

		String modifyID = null;
		String customPW = null;
		String customName = null;
		String phoneNum = null;
		String address = null;

		boolean run = true;

		String num = null;


		//고객정보 확인 구문
		while(run) {

			System.out.println("고객정보를 수정할 ID와 PW 입력해주세요.");
			System.out.print("ID : ");
			modifyID = sc.nextLine();
			System.out.print("PW : ");
			customPW = sc.nextLine();

			//수정할 고객정보 확인
			if(customerList.containsKey(modifyID) == true) {
				if (customerList.get(modifyID).getCustomPW().equals(customPW)) {
					System.out.println("고객확인이 완료되었습니다.");

				} else {
					System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
					continue;
				}

			} else {
				System.out.println("해당 ID를 찾을 수 없습니다. 다시 입력해주세요.");
				continue;

			}
			run = false; 
		}


		//PW
		run = true;      

		while(run){


			//PW 수정 여부 선택
			System.out.println("PW를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			//PW 수정
			if(num.equals("1")) {
				System.out.print("수정할 PW (4~10자리로 입력해주세요.): ");
				customPW = sc.nextLine();

				if(customerList.get(modifyID).getCustomPW().equals(customPW)) {
					System.out.println("기존 PW와 같습니다. 기존 PW와 다른 PW를 입력해주세요.");
				}
	
				//공백 입력 불가
				else if(customPW.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다.");
					customPW = customPW.replaceAll(" ", "");
					System.out.println("공백제거 후 " + customPW + "로 변경하여 입력됩니다.");
					System.out.println();   
				}

				// PW 길이제한 4 ~ 10
				else if(customPW.length() > 10 || customPW.length() < 4) { 
					System.out.println("PW는 10자 이내로 입력해주세요.");
					System.out.println("입력하신 글자 수 : " + customPW.length());
					System.out.println();
					continue;

				}
				
				else {
					run=false;
				}
			} 

			//수정을 원하지 않을 경우 다음로 넘어감
			else if(num.equals("0")) {
				run = false;
			}

		}

		//고객명 수정
		run = true;

		while(run) {
			//고객명 수정 여부 선택
			System.out.println("고객명을 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 고객명 (2자~7자로 입력해주세요.): ");
				customName = sc.nextLine();

				//기존 고객명과 같을 경우 변경x
				if(customerList.get(modifyID).getCustomName().equals(customName)) {
					System.out.println("기존 고객명과 같습니다. 기존 고객명과 다른 고객명을 입력해주세요.");
				}

				//공백 입력 불가 => 공백입력시 모두 제거
				else if(customName.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다.");
					customName = customName.replaceAll(" ", "");
					System.out.println("공백제거 후 " + customName + "로 변경하여 입력됩니다.");
					System.out.println();   
				}

				//고객명 길이 제한 2~7자
				else if(customName.length() < 2 || customName.length() > 7) { 
					System.out.println("고객명은 2자~7자로 입력해주세요");
					System.out.println();
				}
				
				else {
					run = false;
				}
			}
			//고객명 수정하지 않을 경우 다음으로 가기
			else if(num.equals("0")) {
				run = false;
			}
		}

		//전화번호 수정

		run = true;

		// 앞에 3자리
		
		while(run) {
			//전화번호 수정여부 선택
			System.out.println("전화번호를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 전화번호 (10자~11자로 입력해주세요.): ");
				phoneNum= sc.nextLine();
				
				String num1 = phoneNum.substring(0,3); 
				String check = num1.substring(0,2);

				//기존 전화번호와 같으면 수정 불가
				if(customerList.get(modifyID).getPhoneNum().equals(phoneNum)) {
					System.out.println("기존 전화번호와 같습니다. 기존 전화번호와 다른 전화번호를 입력해주세요.");
				}

				//공백입력 불가
				else if(phoneNum.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다. 다시 입력해주세요.");
					System.out.println();
					continue;
				}

				//전화번호 길이 10~11자
				else if(phoneNum.length() < 10 || phoneNum.length() > 11 || phoneNum.length() < 1) { 
					System.out.println("전화번호는는 10자~11자로 입력해주세요.");
					System.out.println("입력하신 글자 수 : " + phoneNum.length());
					System.out.println();
				}

				//대부분 앞자리 01x로 시작하는 3자리 체크( ex. 010.011.017.018...)
				else if(!check.equals("01") ) {
					System.out.println("전화번호는 01x으로 시작해야 합니다.");
					System.out.println();
					continue;

				} else {
					// 중간자리 3~4자리(ex.010이 아닌 번호는 중간에 3자리인 경우 있음.) : 앞 3자리와 뒷 4자리 자르고 나머지 부분 입력
					String num2 = phoneNum.substring(3, phoneNum.length()-4);
					//맨뒤에 4자리 잘라서 입력
					String num3 = phoneNum.substring(phoneNum.length()-4, phoneNum.length());
					//나눈 번호 합쳐서 xxx-xxxx-xxxx 식으로 입력
					System.out.println("입력된 전화번호 : " + num1 + "-" + num2 + "-" +  num3);
					
					run = false;
				}
			} 
			// 전화번호 수정 원하지 않는 경우 다음으로 넘어감
			else if(num.equals("0")) {
				run = false;
			}
		}

		//주소 수정
		run = true;

		while(run) {

			//주소 수정 여부 선택
			System.out.println("주소를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 주소 : ");
				address = sc.nextLine();

				//기존 주소와 같은 경우 수정 불가
				if(customerList.get(modifyID).getAddress().equals(address)) {
					System.out.println("기존 주소와 같습니다. 기존 주소와 다른 주소를 입력해주세요.");
				}
				
				else if(address.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다. 다시 입력해주세요.");
					System.out.println();
					continue;
				}

				//주소길이 제한 10자 이상
				else if(address.length() < 10 || address.length() < 1) { 
					System.out.println("주소는 10자 이상으로 입력해주세요");
					System.out.println("입력하신 글자 수 : " + address.length());
					System.out.println();
				} 
				
				else {
					run = false;
				}
				
			} else if(num.equals("0")) {
				run= false;
			}
		}

		Customer ct = new Customer(modifyID, customPW, customName, phoneNum, address);

		//고객리스트에서 일치하는 고객의 정보를 입력한 값으로 변경
		customerList.replace(modifyID ,ct);

		System.out.println("수정이 완료되었습니다.");
	}


	// 고객 정보 삭제
	public void deleteList(){

		boolean run = true;

		//탈퇴여부 입력값
		String ans;

		String deleteID = null;
		String getCustomPW = null;

		while(run) {
			System.out.println("고객정보를 탈퇴할 ID와 PW를 입력해주세요.");
			System.out.print("ID : ");
			deleteID = sc.nextLine();
			System.out.print("PW : ");
			getCustomPW = sc.nextLine();

			//입력한 고객 정보와 고객리스트에 있는 정보가 같은 경우 삭제 가능
			if(customerList.containsKey(deleteID) == true && customerList.get(deleteID).getCustomPW().equals(getCustomPW)) {

				System.out.println("고객정보가 확인되었습니다.");
				System.out.println("정말 탈퇴하시겠습니까?(y/n)");
				ans = sc.nextLine();

				//삭제를 원하는 경우 'y'
				if(ans.equals("y")) {
					customerList.remove(deleteID);
					System.out.println("탈퇴가 완료되었습니다.");

					if(customerList == null) {
						System.out.println("입력된 고객이 없습니다.");
					}
				}

				//삭제를 원하지 않는 경우 'n'
				else
					return;
			}
			//입력한 고객 정보와 고객리스트에 있는 정보가 다른 경우 삭제 불가능
			else {
				System.out.println("고객 정보가 일치하지 않습니다. 다시 입력해주세요.");
				System.out.println("탈퇴를 안하시려면 '0'을 눌러서 뒤로 나가주세요. 계속 진행하시려면 '0'을 제외한 키를 눌러주세요.");
				ans = sc.nextLine();
				if(ans.equals("0")) {
					return;
				} else {
					continue;
				}
			}
			run = false;
		}
	}
}