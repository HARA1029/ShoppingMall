package shoppingMall;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;


public class OrdersManager {

	// 구매 리스트
	private LinkedHashMap<Integer,Orders> ordersList = new LinkedHashMap<Integer,Orders>();
	// 고객별 구매 리스트
	private ArrayList<Orders> customOrdersList = new ArrayList<Orders>();

	// orders.csv 파일 경로
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\orders.csv");

	// 입력값 체크(정수) 객체 생성
	InputCheck ip = new InputCheck();

	String bar = "-";
	int barCount = 55;

	// 구매 리스트 추가
	public void addList(ProductManager pm, String id){

		Scanner sc = new Scanner(System.in);

		boolean run = true;
		while(run) {

			// 구매 리스트의 마지막 주문번호 획득
			int lastID = 0;

			if(!ordersList.isEmpty()) {
				Integer[] keys = ordersList.keySet().toArray(new Integer[0]);
				lastID = keys[keys.length - 1];
			}

			int ordersID = lastID + 1; // 마지막 주문ID + 1


			String customID = id;

			String tmp = ""; // 정수값 판별을 위한 입력 변수

			// 상품ID를 checkInput메소드로 정수값만 받기

			do {
				System.out.print("구매할 상품 ID : ");

				tmp=sc.nextLine();			

			}while(!ip.checkInput(tmp));

			int productID = Integer.parseInt(tmp);

			// 구매할 상품의 ID 존재 여부 확인
			if(pm.checkPID(productID)) {

				// 구매할 상품
				Product pd = pm.searchInfo(productID); 

				// 재고 0일 때
				if(pd.getStock() == 0) {
					System.out.println("해당 ID의 상품은 품절입니다.");
				}
				else {

					int count = 0;
					String tmpCount = "";	// 정수값 판별을 위한 입력 변수

					// 상품 수량을 checkInput메소드로 정수값만 받기
					do {
						System.out.print("구매할 상품 수량 : ");

						tmpCount = sc.nextLine();         

					}while(!ip.checkInput(tmpCount));

					count=Integer.parseInt(tmpCount);

					// 구매할 상품 수량이 재고보다 작거나 같을 때만 구매 가능
					if(pd.getStock() >= count) {
						//sc.nextLine();
						//System.out.print("결제 수단 번호를 선택하세요");
						//String payment = sc.next();

						//상품ID로 상품명 가져오기
						String productName = pd.getProductName();

						//상품ID로 가격 가져오기
						int price = pd.getPrice();
						String deci = setDecimalFormat(price); 
						int total = price*count;

						// 구매정보를 토대로 객체 생성

						Orders od = new Orders(ordersID, customID, productID, productName, price, count, total);

						// 객체를 구매리스트에 추가

						ordersList.put(od.getOrdersID(),od);

						//재고 수량 감소

						pm.modifyStock(pm.searchInfo(productID),-count);

						System.out.println("(주문번호 " + ordersID + ") " + customID + "님 " +" 구매가 완료되었습니다. ");
						System.out.println();
						System.out.println("   주문ID     상품ID     상품명    상품가격     수량     총액");
						System.out.println(bar.repeat(barCount));
						System.out.printf("   %4s %6d %6s %7s %6d %7d\n",ordersID, productID, productName, deci, count, total);

						//추가 구매시 제품 구매 수량이 기존 수량에 추가해서 출력되지 않음
						System.out.println("계속 구매하시려면 '1', 구매를 끝내시려면 '0'을 눌러주세요.");
						System.out.print("선택 >>> ");
						String num = sc.nextLine();

						if(num.equals("1")) {
							continue;

						} else if(num.equals("0")) {
							run = false;
						}

					}
					// 구매할 상품 수량이 재고보다 많을 때 구매 불가
					else {
						System.out.println("상품의 수량을 "+ pd.getStock() +" 이하로 입력하세요.");
					}
				}
			}
			// 입력받은 상품ID가 존재하지 않을 때
			else {
				System.out.println("해당 ID의 상품이 존재하지 않습니다.");
				continue;
			}
			run = false;
		}System.out.println();

	}

	// 구매 리스트를 orders파일에 저장
	public void uploadList() {

		BufferedWriter bw = null;

		String newLine = System.lineSeparator();

		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));

			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));

			bw.write("ordersID,customoerID,productID,productName,price,count,total");
			bw.write(newLine);

			Iterator<Integer> keys = ordersList.keySet().iterator();

			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;
				
				int ordersID = ordersList.get(key).getOrdersID();
				String customID = ordersList.get(key).getCustomID();
				int productID = ordersList.get(key).getProductID();
				String productName = ordersList.get(key).getProductName();
				int price = ordersList.get(key).getPrice();
				int count = ordersList.get(key).getCount();
				int total = ordersList.get(key).getTotal();

				aData = ordersID + "," + customID + "," + productID + "," + productName + "," + price + "," + count + "," + total;

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

	// orders파일에서 구매리스트 불러오기
	public void readList() {

		List<List<String>> records = new ArrayList<>();

		String line="";

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[7];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}


			for(int i=1; i<records.size();i++) {
				
				int ordersID = Integer.parseInt(records.get(i).get(0));
				String customID = records.get(i).get(1);
				int productID = Integer.parseInt(records.get(i).get(2));
				String productName = records.get(i).get(3);
				int price = Integer.parseInt(records.get(i).get(4));
				int count = Integer.parseInt(records.get(i).get(5));
				int total = Integer.parseInt(records.get(i).get(6));

				Orders od = new Orders(ordersID, customID, productID, productName, price, count, total);

				ordersList.put(od.getOrdersID(), od);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 고객별 구매목록 불러오기
	public void readListCustomer(String cid) {

		// customOrdersList 초기화
		customOrdersList.clear();

		// 고객ID로 구매정보를 찾아서 customOrdersList에 추가

		Iterator<Integer> keys = ordersList.keySet().iterator();

		while(keys.hasNext()) {

			int key=keys.next();

			String customID = ordersList.get(key).getCustomID();

			if( customID.equals(cid) ) {

				int ordersID = ordersList.get(key).getOrdersID();
				int productID = ordersList.get(key).getProductID();
				String productName = ordersList.get(key).getProductName();
				int price = ordersList.get(key).getPrice();
				int count = ordersList.get(key).getCount();
				int total = ordersList.get(key).getTotal();

				Orders od = new Orders(ordersID,cid,productID,productName,price,count,total);

				customOrdersList.add(od);
			}
		}
	}

	// 고객별 구매목록 출력
	public void printListCustomer() {

		System.out.println(" 주문ID     상품ID     상품명     상품가격      수량      총액");
		System.out.println(bar.repeat(barCount));

		Iterator<Orders> iter = customOrdersList.iterator();

		while(iter.hasNext()) {

			Orders od =iter.next();

			int ordersid = od.getOrdersID();
			int productID = od.getProductID();
			String productName = od.getProductName();
			int price = od.getPrice();
			String deci = setDecimalFormat(price); 
			int count = od.getCount();
			int total = od.getTotal();

			System.out.printf("%4d %8d %9s %9s %8d %8d\n",ordersid,productID,productName,deci,count,total);
		}
		System.out.println();
	}


	public void printList() {

		System.out.println(" 주문ID  고객ID  상품ID  상품명  상품가격   수량   총액");
		System.out.println("--------------------------------------------");

		Iterator<Integer> keys = ordersList.keySet().iterator();

		while(keys.hasNext()) {

			int key=keys.next();

			int id = ordersList.get(key).getOrdersID();
			String customID = ordersList.get(key).getCustomID();
			int productID = ordersList.get(key).getProductID();
			String productName = ordersList.get(key).getProductName();
			int price = ordersList.get(key).getPrice();
			String deci = setDecimalFormat(price); 
			int count = ordersList.get(key).getCount();
			int total = ordersList.get(key).getTotal();

			System.out.printf("%4d %6s %6d %6s %7s %6d %8d\n",id,customID,productID,productName,deci,count,total);
		}
	}

	// 삭제할 주문ID가 customerOrdersList에 있는지 확인
	// 존재하면 인덱스 반환 / 존재하지 않으면 -1 반환
	public int searchOrders(int id) {

		Iterator<Orders> iter = customOrdersList.iterator();

		int idx=0;
		while(iter.hasNext()) {

			Orders od =iter.next();

			int ordersid = od.getOrdersID();

			if(ordersid==id) {
				return idx;
			}
			idx++;
		}

		return -1;
	}

	// 주문 정보 삭제 (환불)
	public void deleteList(ProductManager pm){

		String ans;
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		String tmp=""; // 정수값 판별을 위한 입력 변수

		// 해당 고객의 주문정보가 존재하면 주문번호 입력 받기
		if(customOrdersList.size()>0) {
			while(run) {

				// 입력받은 주문번호가 정수값인지 확인
				do {
					System.out.println("주문번호 입력 : ");

					tmp=sc.nextLine();			

				}while(!ip.checkInput(tmp));

				int deleteID = Integer.parseInt(tmp);


				// 삭제할 주문ID 존재 여부 확인
				int idx=searchOrders(deleteID);

				// 삭제할 주문ID가 존재하면 취소 여부 질문
				if(idx>=0) {

					System.out.println("정말 주문 취소하시겠습니까?(y/n)");
					ans = sc.nextLine();

					if(ans.equals("y")) {
						customOrdersList.remove(idx);
						ordersList.remove(deleteID);

						System.out.println("주문취소가 완료되었습니다.");
					}
					else {
						return;
					}
				}		
				// 삭제할 주문ID가 존재하지 않으면 다시 입력 유도
				else {
					System.out.println("해당 ID를 찾을 수 없습니다. 다시 입력해주세요.");
					System.out.println("주문취소를 안하시려면 '0'을 눌러서 뒤로 나가주세요.");

					ans = sc.nextLine();
					if(ans.equals("0")) {
						return;
					} else {
						continue;
					}
				}
				run =false;
			}
		}
		// 해당 고객의 주문정보가 존재하지 않을 때
		else {
			System.out.println("주문정보가 없습니다.");
			System.out.println();
		}
	}

	// 통화 단위 ',' 추가해서 문자열로 만들기
	public String setDecimalFormat(int price) {// 가격 , 구분
		return new DecimalFormat().format(price);
	}

}
