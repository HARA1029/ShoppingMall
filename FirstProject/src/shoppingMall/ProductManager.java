package shoppingMall;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;


public class ProductManager {

	// 상품 리스트
	private LinkedHashMap<Integer,Product> productList = new LinkedHashMap<Integer,Product>();

	// product.csv 파일 경로
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");

	// 입력값 체크(정수) 객체 생성
	InputCheck ip = new InputCheck();

	// 새로운 상품 등록
	public void addList() {

		Scanner sc = new Scanner(System.in);

		// 상품ID 설정 방법 조금 아쉬움
		// 새로 들어갈 상품ID : 상품리스트에 들어있는 마지막 상품ID +1 로 설정

		int lastID =0;

		if(!productList.isEmpty()) {

			Integer[] keys = productList.keySet().toArray(new Integer[0]);
			lastID = keys[keys.length - 1];
		}

		int productID = lastID+1;

		// 상품명 입력

		System.out.print("상품명 : ");
		String productName = sc.nextLine();

		// 상품명 공백 처리

		if(productName.contains(" ") == true) {
			System.out.println("공백은 입력할 수 없습니다.");
			productName = productName.replaceAll(" ", "");
			System.out.println("공백제거 후 " + productName + "로 변경하여 입력됩니다.");
			System.out.println();   
		}

		int price=0;	// 실질 가격(정수)
		String tmpPrice="";	// 정수값 판별을 위한 입력 변수

		// 가격을 checkInput메소드로 정수값만 받기

		do {
			System.out.print("가격 : ");

			tmpPrice=sc.nextLine();			

		}while(!ip.checkInput(tmpPrice));

		price=Integer.parseInt(tmpPrice);


		int stock=0;	// 실질 재고(정수)
		String tmpStock="";	// 정수값 판별을 위한 입력 변수

		// 재고를 checkInput메소드로 정수값만 받기

		do {
			System.out.print("재고 : ");

			tmpStock=sc.nextLine();			

		}while(!ip.checkInput(tmpStock));

		stock=Integer.parseInt(tmpStock);

		// 설정된 값을 바탕으로 product 객체 셍성 -> 객체를 productList에 추가

		Product pd = new Product(productID, productName, price, stock);

		productList.put(pd.getProductID(),pd);

		System.out.println("결과 : " + productID + " " + productName + " 등록 완료");
		System.out.println();

	}


	// 상품리스트를 product파일에 저장
	public void uploadList() {

		BufferedWriter bw = null;

		String newLine = System.lineSeparator();	// 개행 문자열

		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));

			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));

			// 파일 상단 메뉴
			bw.write("productID,productName,price,stock");
			bw.write(newLine);

			// 상품 리스트를 파일에 입력
			Iterator<Integer> keys = productList.keySet().iterator();

			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;

				int productID = productList.get(key).getProductID();
				String productName = productList.get(key).getProductName();
				int price = productList.get(key).getPrice();
				int stock = productList.get(key).getStock();

				aData = productID + "," + productName+"," + price + "," + stock;

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

	// product파일에서 상품리스트 불러오기
	public void readList() {

		List<List<String>> records = new ArrayList<>();

		String line="";

		// 파일에서 받아온 product값을 상품 리스트에 저장

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[4];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {

				int productID = Integer.parseInt(records.get(i).get(0));
				String productName = records.get(i).get(1);
				int price = Integer.parseInt(records.get(i).get(2));
				int stock = Integer.parseInt(records.get(i).get(3));

				// 파일에서 받아온 정보로 product 객체 생성
				Product pd = new Product(productID, productName, price, stock);

				// 생성한 객체를 productList에 추가
				productList.put(pd.getProductID(), pd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품리스트 출력
	public void printList() {

		System.out.println("   ID       상품명       가격       재고");
		System.out.println("--------------------------------------");

		Iterator<Integer> keys=productList.keySet().iterator();

		while(keys.hasNext()) {

			int key=keys.next();

			int id = productList.get(key).getProductID();
			String name = productList.get(key).getProductName();
			int price = productList.get(key).getPrice();
			String deci = setDecimalFormat(price); 
			int stock = productList.get(key).getStock();

			System.out.printf("%5d %8s %10s %9d\n",id,name,deci,stock);

		} System.out.println();
	}

	// 상품ID 검색 및 해당 상품 정보 반환
	public Product searchInfo(int id) {

		String productName="";
		int price=0;
		int stock=0;

		if(productList.containsKey(id)==true) {
			productName = productList.get(id).getProductName();
			price = productList.get(id).getPrice();
			stock = productList.get(id).getStock();
		}
		else {
			System.out.println("해당 ID의 상품이 존재하지 않습니다.");
		}

		Product pd = new Product(id,productName,price,stock);

		return pd;
	}

	// 상품 ID 존재 여부 검사
	public boolean checkPID(int id) {

		boolean result=false;

		if(productList.containsKey(id)==true) {
			result=true;
		}

		return result;
	}

	// 재고 변동시(구매/환불) 재고 수량 변경
	public void modifyStock(Product pd,int count) {

		int id = pd.getProductID();
		String productName = pd.getProductName();
		int price = pd.getPrice();
		int stock = pd.getStock() + count;

		// 해당 상품의 재고 변경

		pd = new Product(id, productName, price, stock);

		productList.replace(id,pd);

	}

	// 상품 정보 수정
	public void modifyList(){

		Scanner sc = new Scanner(System.in);


		String tmp=""; // 정수값 판별을 위한 입력 변수

		// 가격을 checkInput메소드로 정수값만 받기
		do {
			System.out.println("상품정보를 수정할 ID를 입력해주세요.");

			tmp=sc.nextLine();			

		}while(!ip.checkInput(tmp));

		int modifyID = Integer.parseInt(tmp);

		// 수정할 상품ID 존재 여부 검사 후, 다음 단계 진행
		if(productList.containsKey(modifyID)==true) {

			sc.nextLine();

			System.out.print("수정할 상품명 : ");
			String productName = sc.nextLine();


			String tmpPrice="";	// 정수값 판별을 위한 입력 변수

			// 수정할 가격을 checkInput메소드로 정수값만 받기
			do {
				System.out.print("수정할 가격 : ");

				tmpPrice=sc.nextLine();			

			}while(!ip.checkInput(tmpPrice));

			int price = Integer.parseInt(tmpPrice);

			String tmpStock="";	// 정수값 판별을 위한 입력 변수

			// 수정할 재고를 checkInput메소드로 정수값만 받기
			do {
				System.out.print("수정할 재고 : ");

				tmpStock=sc.nextLine();			

			}while(!ip.checkInput(tmpStock));

			int stock = Integer.parseInt(tmpStock);

			// 수정한 정보로 product 객체 생성
			Product pd = new Product(modifyID, productName, price, stock);

			// 해당ID의 기존 상품정보를 수정한 정보의 product로 변경
			productList.replace(modifyID,pd);

			System.out.println("수정이 완료되었습니다.");
		}
		else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}

	// 상품 정보 삭제
	public void deleteList(){

		Scanner sc = new Scanner(System.in);

		String ans;   // 최종삭제 의사 변수 (y/n)
		boolean run = true;
		String tmp = "";   // 정수값 판별을 위한 입력 변수

		while(run) {
			// 삭제할 ID를 checkInput메소드로 정수값만 받기

			do {
				System.out.println("삭제할 ID를 입력하세요.");

				tmp = sc.nextLine();         

			} while(!ip.checkInput(tmp));


			int deleteID = Integer.parseInt(tmp);

			// 삭제할 상품ID 존재 여부 검사 후, 다음 단계 진행
			if(productList.containsKey(deleteID) == true) {

				System.out.println("정말 삭제하시겠습니까?(y/n)");
				ans = sc.nextLine();

				if(ans.equals("y"))
					productList.remove(deleteID);
				else
					return;
			} else {
				System.out.println("해당 ID를 찾을 수 없습니다. 다시 입력해주세요.");
				System.out.println("상품삭제를 안하시려면 '0'을 눌러서 뒤로 나가주세요.");
				ans = sc.nextLine();
				if(ans.equals("0")) {
					return;
				} else {
					continue;
				}
			}
			run = false;
		}
		System.out.println();
	}

	// 통화 단위 ',' 추가해서 문자열로 만들기
	public String setDecimalFormat(int price) {
		return new DecimalFormat().format(price);
	}
}
