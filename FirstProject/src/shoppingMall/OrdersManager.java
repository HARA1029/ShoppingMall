package shoppingMall;

import java.util.*;
import java.io.*;


public class OrdersManager {

	private LinkedHashMap<Integer,Orders> ordersList = new LinkedHashMap<Integer,Orders>();
	//private LinkedHashMap<Integer,Orders> custom_ordersList = new LinkedHashMap<Integer,Orders>();
	private ArrayList<Orders> custom_ordersList = new ArrayList<Orders>();

	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\orders.csv");


	// ! 상품 재고 0이면 구매 못하게 함

	public void addList(ProductManager pm, String id){
		
		// 구매 리스트 추가

		// 주문ID 마지막값 + 1 연구

		int ordersID = ordersList.size()+1;

		Scanner sc= new Scanner(System.in);

		//System.out.println("고객 ID : ");
		String customID = id;


		System.out.print("구매할 상품 ID : ");
		int productID = sc.nextInt();

		if(pm.check_PID(productID)) {

			// 구매할 상품
			Product pd = pm.searchInfo(productID); 

			System.out.print("구매할 상품 수량 : ");
			int count = sc.nextInt();

			if(pd.getStock()>=count) {
				//sc.nextLine();
				//System.out.print("결제 수단 번호를 선택하세요");
				//String payment = sc.next();

				//상품ID로 가격 가져오기
				int price = pd.getPrice();

				int total = price*count;

				Orders od = new Orders(ordersID, customID, productID, price, count, total);

				ordersList.put(od.getOrdersID(),od);


				//재고 수량 감소

				pm.modifyStock(pm.searchInfo(productID),-count);

				System.out.println("(주문번호 " + ordersID + ") " + customID + "님 " +" 구매가 완료되었습니다. ");
				System.out.println();
			}
			else {
				System.out.println("상품의 수량을 "+pd.getStock()+" 이하로 입력하세요.");
			}
		}
		else {
			System.out.println("해당 ID의 상품이 존재하지 않습니다.");
		}

	}

	public void uploadList() {

		//File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");
		BufferedWriter bw = null;

		String NEWLINE = System.lineSeparator();

		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));

			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));

			bw.write("ordersID,customoerID,productID,price,count,total");
			bw.write(NEWLINE);

			Iterator<Integer> keys = ordersList.keySet().iterator();
			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;

				aData = ordersList.get(key).getOrdersID() +","+ordersList.get(key).getCustomID()+","+ordersList.get(key).getProductID()+","+ordersList.get(key).getPrice() + "," + ordersList.get(key).getCount() + "," + ordersList.get(key).getTotal();

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

		//File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");

		List<List<String>> records = new ArrayList<>();

		String line="";

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[6];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {
				Orders od = new Orders(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),Integer.parseInt(records.get(i).get(3)), Integer.parseInt(records.get(i).get(4)), Integer.parseInt(records.get(i).get(5)));

				ordersList.put(od.getOrdersID(), od);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)

	}

	// ! get으로 가져오는 값들 다 변수 선언으로 빼주기

	public void readList_c(String cid) {

		//File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");

		List<List<String>> records = new ArrayList<>();

		String line="";

		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			// custom_ordersList 초기화
			custom_ordersList.clear();

			String[] values = new String[6];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {

				String customID = records.get(i).get(1);

				if( customID.equals(cid) ) {

					Orders od = new Orders(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),Integer.parseInt(records.get(i).get(3)), Integer.parseInt(records.get(i).get(4)), Integer.parseInt(records.get(i).get(5)));

					//중복키라 중복넣기가 불가;;; 리스트로 구현하자
					custom_ordersList.add(od);	
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)

	}

	public void printList_c() {

		StringBuilder sb = new StringBuilder ();

		for(Orders e : custom_ordersList) {

			int id = e.getOrdersID();
			String customID = e.getCustomID();
			int productID = e.getProductID();
			int price =	e.getPrice();
			int count = e.getCount();
			int total = e.getTotal();

			sb.append(Integer.toString(id)).append(' ').append(customID).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");

		}

		//		Iterator<Orders> iter = custom_ordersList.iterator();
		//
		//		while(iter.hasNext()) {
		//
		//			int ordersid = iter.next().getOrdersID();
		//			int customID = iter.next().getCustomID();
		//			int productID = iter.next().getProductID();
		//			int price = iter.next().getPrice();
		//			int count = iter.next().getCount();
		//			int total = iter.next().getTotal();
		//
		//			sb.append(Integer.toString(ordersid)).append(' ').append(Integer.toString(customID)).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");
		//		}

		System.out.println(sb);
	}

	public void printList() {

		StringBuilder sb = new StringBuilder ();

		Iterator<Integer> keys = ordersList.keySet().iterator();

		while(keys.hasNext()) {

			int key=keys.next();

			int id = ordersList.get(key).getOrdersID();
			String customID = ordersList.get(key).getCustomID();
			int productID = ordersList.get(key).getProductID();
			int price = ordersList.get(key).getPrice();
			int count = ordersList.get(key).getCount();
			int total = ordersList.get(key).getTotal();

			sb.append(Integer.toString(id)).append(' ').append(customID).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");
		}

		System.out.println(sb);
	}

	public void searchInfo(int id) {

	}

	public void deleteList(ProductManager pm){

		// 주문 정보 삭제 (환불)

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String ans;
		Scanner sc = new Scanner(System.in);

		int delete_id;
		System.out.println("주문번호 입력 : ");

		delete_id = sc.nextInt();

		if(ordersList.containsKey(delete_id)==true) {

			System.out.println("정말 주문 취소하시겠습니까?(y/n)");
			ans = sc.next();

			if(ans.equals("y")) {

				int productID = ordersList.get(delete_id).getProductID();
				int count = ordersList.get(delete_id).getCount();

				//재고 수량 증가
				pm.modifyStock(pm.searchInfo(productID),count);

				//주문 정보 삭제
				ordersList.remove(delete_id);

			}
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}

}
