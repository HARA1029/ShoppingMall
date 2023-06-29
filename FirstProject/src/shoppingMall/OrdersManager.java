package shoppingMall;

import java.util.*;
import java.io.*;


public class OrdersManager {

	private LinkedHashMap<Integer,Orders> ordersList = new LinkedHashMap<Integer,Orders>();
	
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\orders.csv");

	
	public void addList(ProductManager pm){
		// 구매 리스트 추가
		
		int ordersID=0;
		
		Scanner sc= new Scanner(System.in);
		
		// 로그인 하면 고쳐야함.. 고객ID 있는지없는지 확인 없음
		System.out.println("고객 ID : ");
		int customID = sc.nextInt();
	      
	    System.out.print("구매할 상품 ID : ");
	    int productID = sc.nextInt();
	    	    
	    System.out.print("구매할 상품 수량 : ");
	    int count = sc.nextInt();
	    
	    //sc.nextLine();
	    //System.out.print("결제 수단 번호를 선택하세요");
	    //String payment = sc.next();

	    //상품ID로 가격 가져오기
	    int price = pm.searchInfo(productID).getPrice();
	    
	    int total = price*count;
		
	    Orders od = new Orders(ordersID, customID, productID, price, count, total);
	      
		ordersList.put(od.getProductID(),od);
		
		//재고 수량 감소
		
		pm.modifyStock(pm.searchInfo(productID),-count);
	      
	    System.out.println("주문번호: " + ordersID + "/ " + customID + "님 " +" 구매가 완료되었습니다.");
	    System.out.println();
	      
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
		            Orders od = new Orders(Integer.parseInt(records.get(i).get(0)),Integer.parseInt(records.get(i).get(1)),Integer.parseInt(records.get(i).get(2)),Integer.parseInt(records.get(i).get(3)), Integer.parseInt(records.get(i).get(4)), Integer.parseInt(records.get(i).get(5)));

				ordersList.put(od.getOrdersID(), od);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)
		
	}
	
	public void printList() {
		
		StringBuilder sb = new StringBuilder ();

		Iterator<Integer> keys=ordersList.keySet().iterator();
		
		while(keys.hasNext()) {
			
			int key=keys.next();
			
			int id = ordersList.get(key).getOrdersID();
			int customID = ordersList.get(key).getCustomID();
			int productID = ordersList.get(key).getProductID();
			int price = ordersList.get(key).getPrice();
			int count = ordersList.get(key).getCount();
			int total = ordersList.get(key).getTotal();
			
			sb.append(Integer.toString(id)).append(' ').append(Integer.toString(customID)).append(' ').append(Integer.toString(productID)).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(count)).append(' ').append(Integer.toString(total)).append("\n");
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
		System.out.println("고객 ID : ");
		
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
