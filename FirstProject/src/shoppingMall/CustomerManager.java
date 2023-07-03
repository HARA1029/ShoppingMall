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

	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\customer.csv");

	static Scanner sc= new Scanner(System.in);

	public void uploadList() {

		//File csv = new File("C:\\Users\\gyeong\\KOSA\\git\\kosa_java\\customer.csv");
		BufferedWriter bw = null;

		String NEWLINE = System.lineSeparator();

		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));

			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));

			bw.write("CustomerID,CustomerPW,CustomerName,phoneNum,address");
			bw.write(NEWLINE);

			Iterator<String> keys = customerList.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				String aData;

				aData = customerList.get(key).getCustomID() +","+customerList.get(key).getCustomPW() +","+
						customerList.get(key).getCustomName()+","+customerList.get(key).getPhoneNum()+","+customerList.get(key).getAddress();

				bw.write(aData);
				bw.write(NEWLINE);

				//bw.flush();
				//bw.close();
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

	public void readList() {

		//File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\Customer.csv");

		List<List<String>> records = new ArrayList<>();

		String line="";

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[5];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {
				Customer cust = new Customer(records.get(i).get(0),records.get(i).get(1),records.get(i).get(2),
						records.get(i).get(3),records.get(i).get(4));
				customerList.put(cust.getCustomID(), cust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)

		//return customerList;
	}

	public void printList() {

		StringBuilder sb = new StringBuilder ();

		System.out.println("     ID      PW      고객명      전화번호        주소");
		System.out.println("------------------------------------------------------------");

		Iterator<String> keys = customerList.keySet().iterator();

		while(keys.hasNext()) {

			String key=keys.next();

			String id = customerList.get(key).getCustomID();
			String pw = customerList.get(key).getCustomPW();
			String name = customerList.get(key).getCustomName();
			String phoneNum = customerList.get(key).getPhoneNum();
			String address = customerList.get(key).getAddress();

			System.out.printf("%6s %10s %7s %12s %15s\n",id,pw,name,phoneNum,address);

			//sb.append(id).append(' ').append(pw).append(' ').append(name).append(' ').append(phoneNum).append(' ').
			//append(address).append("\n");
		}

		//System.out.println(sb);
	}

	public void searchInfo(int id) {

	}

	public void checkLogin(ProductManager pm, OrdersManager om) {

		boolean run = true;

		boolean check = false;

		Scanner sc = new Scanner(System.in);

		PrintMenu menu = new PrintMenu();

		String id = null;

		while(run) {
			System.out.print("ID : ");
			id = sc.nextLine();

			System.out.print("PW : ");
			String pw = sc.nextLine();

			if(customerList.containsKey(id) == true) {
				if (customerList.get(id).getCustomPW().equals(pw)) {
					System.out.println("로그인에 성공하였습니다.");
					check = true;
					run = false;

				} else {
					System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
					System.out.print("가입하지 않으신 분들은 '0' 을 다시 로그인하고 싶으신 분들은 다른 숫자를 입력해주세요.");
					String num = sc.nextLine();
					if(num.equals("0")) {
						run = false;  
					}
				}

			} else {
				System.out.println("아이디가 틀렸습니다. 다시 입력해주세요.");
				System.out.print("가입하지 않으신 분들은 '0' 을 다시 로그인하고 싶으신 분들은 다른 숫자를 입력해주세요.");
				String num = sc.nextLine();
				if(num.equals("0")) {
					run = false; 
				}
			}  
		}

		if(check == true)
			menu.customer(pm, om, id);
		//return check;
	}

	public void addList() {
		//고객 등록

		CustomerManager cm = new CustomerManager();
		ProductManager pm = new ProductManager();
		OrdersManager om = new OrdersManager();
		PrintMenu menu = new PrintMenu();

		String customID = null;
		String customPW = null;
		String customName = null;
		String phoneNum = null;
		String address = null;

		//ID

		boolean run = true;
		while(run) {

			System.out.print("고객 ID (6자리 이내로 입력해주세요): ");
			customID = sc.nextLine();
			if(customID.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customID = customID.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customID + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			if(customID.length() < 1 || customID.length() > 6 ) { // 마무리할 때 ID 길이제한 4 ~ 6으로 변경
				System.out.println("ID는 6자 이내로 입력해주세요");
				System.out.println("입력하신 글자 수 : " + customID.length());
				System.out.println();
				continue;
			}

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

			System.out.print("고객 PW (10자리 이내로 입력해주세요): ");
			customPW = sc.nextLine();
			if(customPW.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customPW = customPW.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customPW + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			if(customPW.length() > 11 || customPW.length() < 1) { // 마무리할 때 PW 길이제한 4 ~ 10 으로 변경
				System.out.println("PW는 10자 이내로 입력해주세요");
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

			if(customName.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다.");
				customName = customName.replaceAll(" ", "");
				System.out.println("공백제거 후 " + customName + "로 변경하여 입력됩니다.");
				System.out.println();   
			}

			if(customName.length() < 2 ) { // 마무리할 때 길이제한 2 ~ 7 으로 변경
				System.out.println("고객명은 2자 이상으로 입력해주세요");
				System.out.println();
				continue;
			}
			run = false;
		}

		//고객명
		run = true;

		while(run) {


			System.out.print("전화번호 : ");
			phoneNum= sc.nextLine();

			if(phoneNum.contains(" ") == true) {
				System.out.println("공백은 입력할 수 없습니다. 다시 입력해주세요.");
				System.out.println();
				continue;
			}

			if(phoneNum.length() < 10 || phoneNum.length() > 11 || phoneNum.length() < 1) { //길이제한
				System.out.println("전화번호는는 10자~11자로 입력해주세요");
				System.out.println("입력하신 글자 수 : " + phoneNum.length());
				System.out.println();
				continue;
			}

			String num1 = phoneNum.substring(0,3); // 앞에 3자리
			String check = num1.substring(0,2);

			if(!check.equals("01") ) {
				System.out.println("전화번호는 01x으로 시작해야 합니다.");
				System.out.println();
				continue;
			} else {

				String num2 = phoneNum.substring(3, phoneNum.length()-4); // 중간 3~4
				String num3 = phoneNum.substring(phoneNum.length()-4, phoneNum.length()); // 뒤에 4자리

				System.out.println("입력된 전화번호 : " + num1 + "-" + num2 + "-" +  num3);
			}
			run = false;
		}

		//주소
		run = true;

		while(run) {

			//매우 아쉬운점
			System.out.print("주소 : ");
			address = sc.nextLine();
			if(address.length() < 10 || address.length() < 1) { // 길이제한 10자 이상
				System.out.println("주소는 10자 이상으로 입력해주세요");
				System.out.println("입력하신 글자 수 : " + address.length());
				System.out.println();
				continue;
			} 
			run= false;
		}

		//첫 자리 부터 ~시 나올 때까지 끊기 -> 도시
		// 그 뒤 자리부터 ~구

		Customer ct = new Customer(customID, customPW, customName, phoneNum, address);

		customerList.put(ct.getCustomID(),ct);

		System.out.println("결과 : " + customID + " " + customName + " 가입이 완료되었습니다.");
		System.out.println();

		System.out.println("로그인 하시려면 '1' 를 눌러주세요. 초기화면으로 가려면 '0'을 눌러주세요");

		int num = sc.nextInt();

		if(num == 1) {
			checkLogin(pm, om);
			sc.nextLine();
		} else if(num == 0) {
			menu.login_c(pm, cm, om);
		}
	}

	public void modifyList() {

		// 고객 정보 수정

		String modify_id = null;
		String getCustomPW = null;
		String getCustomName = null;
		String getPhoneNum = null;
		String getAddress = null;

		boolean run = true;
		String num = null;


		while(run) {
			System.out.println("고객정보를 수정할 ID를 입력해주세요.");
			System.out.print("ID : ");
			modify_id = sc.nextLine();
			System.out.print("PW : ");
			getCustomPW = sc.nextLine();

			if(customerList.containsKey(modify_id) == true) {
				if (customerList.get(modify_id).getCustomPW().equals(getCustomPW)) {
					System.out.println("고객확인이 완료되었습니다.");

				} else {
					System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
					continue;
				}


			} else {
				System.out.println("해당 ID를 찾을 수 없습니다.");
				System.out.println("다시 입력해주세요.");
				continue;

			}
			run = false; 
		}

		run = true;
		//PW

		while(run){

			System.out.println("PW를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 PW (10자리 이내로 입력해주세요.): ");
				getCustomPW = sc.nextLine();
				if(customerList.containsKey(getCustomPW) == true) {
					System.out.println("기존 PW와 같습니다. 기존 PW와 다른 PW를 입력해주세요.");
				}
				if(getCustomPW.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다.");
					getCustomPW = getCustomPW.replaceAll(" ", "");
					System.out.println("공백제거 후 " + getCustomPW + "로 변경하여 입력됩니다.");
					System.out.println();   
				}

				if(getCustomPW.length() > 11 || getCustomPW.length() < 1) { // 마무리할 때 PW 길이제한 4 ~ 10 으로 변경
					System.out.println("PW는 10자 이내로 입력해주세요.");
					System.out.println("입력하신 글자 수 : " + getCustomPW.length());
					System.out.println();
					continue;
				} 
			} else if(num.equals("0")) {
				run = false;
			}
		}

		run = true;

		//고객명 수정
		while(run) {
			System.out.println("고객명을 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 고객명 (2자 이상으로 입력해주세요.): ");
				getCustomName = sc.nextLine();

				if(customerList.containsKey(getCustomName) == true) {
					System.out.println("기존 고객명과 같습니다. 기존 고객명과 다른 고객명을 입력해주세요.");
				}

				if(getCustomName.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다.");
					getCustomName = getCustomName.replaceAll(" ", "");
					System.out.println("공백제거 후 " + getCustomName + "로 변경하여 입력됩니다.");
					System.out.println();   
				}

				if(getCustomName.length() < 2 ) { // 마무리할 때 길이제한 2 ~ 7 으로 변경
					System.out.println("고객명은 2자 이상으로 입력해주세요");
					System.out.println();
					continue;
				}
			} else if(num.equals("0")) {
				run = false;
			}
		}

		//전화번호 수정

		run = true;

		while(run) {
			System.out.println("전화번호를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 전화번호 (10자~11자로 입력해주세요.): ");
				getPhoneNum= sc.nextLine();

				if(customerList.containsKey(getPhoneNum) == true) {
					System.out.println("기존 전화번호와 같습니다. 기존 전화번호와 다른 전화번호를 입력해주세요.");
				}

				if(getPhoneNum.contains(" ") == true) {
					System.out.println("공백은 입력할 수 없습니다. 다시 입력해주세요.");
					System.out.println();
					continue;
				}

				if(getPhoneNum.length() < 10 || getPhoneNum.length() > 11 || getPhoneNum.length() < 1) { //길이제한
					System.out.println("전화번호는는 10자~11자로 입력해주세요.");
					System.out.println("입력하신 글자 수 : " + getPhoneNum.length());
					System.out.println();
					continue;
				}

				String num1 = getPhoneNum.substring(0,3); // 앞에 3자리
				String check = num1.substring(0,2);

				if(!check.equals("01") ) {
					System.out.println("전화번호는 01x으로 시작해야 합니다.");
					System.out.println();
					continue;

				} else {

					String num2 = getPhoneNum.substring(3, getPhoneNum.length()-4); // 중간 3~4
					String num3 = getPhoneNum.substring(getPhoneNum.length()-4, getPhoneNum.length()); // 뒤에 4자리

					System.out.println("입력된 전화번호 : " + num1 + "-" + num2 + "-" +  num3);
				}
			} else if(num.equals("0")) {
				run = false;
			}
		}

		//주소 수정
		run = true;

		while(run) {

			//매우 아쉬운점
			System.out.println("주소를 수정하려면 '1', 다음으로 넘어가려면 '0'을 눌러주세요.");
			num = sc.nextLine();

			if(num.equals("1")) {
				System.out.print("수정할 주소 : ");
				getAddress = sc.nextLine();

				if(customerList.containsKey(getAddress) == true) {
					System.out.println("기존 주소와 같습니다. 기존 주소와 다른 주소를 입력해주세요.");
				}

				if(getAddress.length() < 10 || getAddress.length() < 1) { // 길이제한 10자 이상
					System.out.println("주소는 10자 이상으로 입력해주세요");
					System.out.println("입력하신 글자 수 : " + getAddress.length());
					System.out.println();
					continue;
				} 
			} else if(num.equals("0")) {
				run= false;
			}
		}

		Customer m_customer = new Customer(modify_id, getCustomPW, getCustomName, getPhoneNum, getAddress);

		customerList.replace(modify_id ,m_customer);

		System.out.println("수정이 완료되었습니다.");
	}



	public void deleteList(){
		// 고객 정보 삭제

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		boolean run = true;
		String ans;

		String delete_cid = null;
		String getCustomPW = null;

		while(run) {
			System.out.println("고객정보를 탈퇴할 ID와 PW를 입력해주세요.");
			System.out.print("ID : ");
			delete_cid = sc.nextLine();
			System.out.print("PW : ");
			getCustomPW = sc.nextLine();

			if(customerList.containsKey(delete_cid) == true && customerList.get(delete_cid).getCustomPW().equals(getCustomPW)) {

				System.out.println("고객정보가 확인되었습니다.");
				System.out.println("정말 탈퇴하시겠습니까?(y/n)");
				ans = sc.next();
				//ans = br.readLine();

				if(ans.equals("y")) {
					customerList.remove(delete_cid);
					System.out.println("탈퇴가 완료되었습니다.");

					if(customerList == null) {
						System.out.println("입력된 고객이 없습니다.");
					}
				}

				else
					return;
			}else {
				System.out.println("해당 ID를 찾을 수 없습니다.");
				System.out.println("다시 입력해주세요.");
				continue;
			}
			run = false;
		}
	}
}