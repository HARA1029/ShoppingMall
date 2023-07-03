package shoppingMall;

import java.util.*;
import java.io.*;


public class OrdersManager {

	private LinkedHashMap<Integer,Orders> ordersList = new LinkedHashMap<Integer,Orders>();
	private ArrayList<Orders> custom_ordersList = new ArrayList<Orders>();

	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\orders.csv");
	
	
	InputCheck ip = new InputCheck();

	//예외처리 후 다시 목록조회 해주기?

	public void addList(ProductManager pm, String id){

		// 구매 리스트 추가

		// 마지막 주문ID + 1

		int lastID =0;
		if(!ordersList.isEmpty()) {
			System.out.println(ordersList.size());
			
			Integer[] keys = ordersList.keySet().toArray(new Integer[0]);
			lastID = keys[keys.length - 1];
		}
		
		int ordersID = lastID+1;

		Scanner sc= new Scanner(System.in);

		String customID = id;

		System.out.print("구매할 상품 ID : ");
		String tmp = sc.nextLine();
		int productID = Integer.parseInt(tmp);

		if(pm.check_PID(productID)) {

			// 구매할 상품
			Product pd = pm.searchInfo(productID); 

			// 재고 0일 때
			if(pd.getStock()==0) {
				System.out.println("해당 ID의 상품은 품절입니다.");
			}
			else {
				
				int count=0;
				String tmp_cnt="";
				
				do {
					System.out.print("구매할 상품 수량 : ");
					
					tmp_cnt=sc.nextLine();			
					
				}while(!ip.checkInput(tmp_cnt));
								
				count=Integer.parseInt(tmp_cnt);
				

				if(pd.getStock()>=count) {
					//sc.nextLine();
					//System.out.print("결제 수단 번호를 선택하세요");
					//String payment = sc.next();
					
					//상품ID로 상품명 가져오기
					String productName = pd.getProductName();

					//상품ID로 가격 가져오기
					int price = pd.getPrice();

					int total = price*count;

					Orders od = new Orders(ordersID, customID, productID, productName, price, count, total);

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

			bw.write("ordersID,customoerID,productID,productName,price,count,total");
			bw.write(NEWLINE);

			Iterator<Integer> keys = ordersList.keySet().iterator();
			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;

				aData = ordersList.get(key).getOrdersID() +","+ordersList.get(key).getCustomID()+","+ordersList.get(key).getProductID()+","+ordersList.get(key).getProductName()+","+ordersList.get(key).getPrice() + "," + ordersList.get(key).getCount() + "," + ordersList.get(key).getTotal();

				bw.write(aData);
				bw.write(NEWLINE);

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

			String[] values = new String[7];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {
				Orders od = new Orders(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),records.get(i).get(3),Integer.parseInt(records.get(i).get(4)), Integer.parseInt(records.get(i).get(5)), Integer.parseInt(records.get(i).get(6)));

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

			String[] values = new String[7];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {

				String customID = records.get(i).get(1);

				if( customID.equals(cid) ) {

					Orders od = new Orders(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),records.get(i).get(3),Integer.parseInt(records.get(i).get(4)), Integer.parseInt(records.get(i).get(5)), Integer.parseInt(records.get(i).get(6)));

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
			
			System.out.println(" 주문ID   상품ID   상품명   상품가격    수량    총액");
			System.out.println("------------------------------------------");

			int id = e.getOrdersID();
			int productID = e.getProductID();
			String productName = e.getProductName();
			int price =	e.getPrice();
			int count = e.getCount();
			int total = e.getTotal();

			System.out.printf("%4d %6d %6s %7d %6d %7d\n",id,productID,productName,price,count,total);

			//sb.append(Integer.toString(id)).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");
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

		//System.out.println(sb);
	}

	public void printList() {

		StringBuilder sb = new StringBuilder ();
		
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
			int count = ordersList.get(key).getCount();
			int total = ordersList.get(key).getTotal();

			System.out.printf("%4d %6s %6d %6s %7d %6d %8d\n",id,customID,productID,productName,price,count,total);
			
			//sb.append(Integer.toString(id)).append(' ').append(customID).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");
		}

		//System.out.println(sb);
	}

	public void searchInfo(int id) {

	}

	public void deleteList(ProductManager pm){

		// 주문 정보 삭제 (환불)

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String ans;
		Scanner sc = new Scanner(System.in);


		String tmp="";
		
		do {
			System.out.println("주문번호 입력 : ");
			
			tmp=sc.nextLine();			
			
		}while(!ip.checkInput(tmp));
		
		int delete_id = Integer.parseInt(tmp);

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
