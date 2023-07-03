package shoppingMall;

import java.util.*;
import java.io.*;


public class ProductManager {

	private LinkedHashMap<Integer,Product> productList = new LinkedHashMap<Integer,Product>();

	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");

	InputCheck ip = new InputCheck();

	public void addList() {

		// 상품 등록

		Scanner sc = new Scanner(System.in);

		// 상품ID 마지막값 + 1 연구

		int productID = productList.size()+1;

		System.out.print("상품명 : ");
		String productName = sc.nextLine();

		int price=0;
		String tmp_p="";

		do {
			System.out.print("가격 : ");

			tmp_p=sc.nextLine();			

		}while(!ip.checkInput(tmp_p));

		price=Integer.parseInt(tmp_p);

		//System.out.print("가격 : ");
		//int price = sc.nextInt();
		
		int stock=0;
		String tmp_s="";

		do {
			System.out.print("재고 : ");

			tmp_s=sc.nextLine();			

		}while(!ip.checkInput(tmp_s));

		stock=Integer.parseInt(tmp_s);

		//System.out.print("재고 : ");
		//int stock = sc.nextInt();

		Product pd = new Product(productID, productName, price, stock);

		productList.put(pd.getProductID(),pd);

		System.out.println("결과 : " + productID + " " + productName + " 등록 완료");
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

			bw.write("productID,productName,price,stock");
			bw.write(NEWLINE);

			Iterator<Integer> keys = productList.keySet().iterator();
			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;

				aData = productList.get(key).getProductID() +","+productList.get(key).getProductName()+","+productList.get(key).getPrice()+","+productList.get(key).getStock();

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

			String[] values = new String[4];

			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}

			for(int i=1; i<records.size();i++) {
				Product prod = new Product(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),Integer.parseInt(records.get(i).get(3)));
				//productList.add(prod);
				productList.put(prod.getProductID(), prod);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)

		//return productList;
	}

	public void printList() {

		System.out.println(" ID  상품명  가격  재고");

		//StringBuilder sb = new StringBuilder ();

		Iterator<Integer> keys=productList.keySet().iterator();

		while(keys.hasNext()) {

			int key=keys.next();

			int id = productList.get(key).getProductID();
			String name = productList.get(key).getProductName();
			int price = productList.get(key).getPrice();
			int stock = productList.get(key).getStock();

			System.out.printf("%3d%5s%5d%5d\n",id,name,price,stock);
			//sb.append(Integer.toString(id)).append(' ').append(name).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(stock)).append("\n");
		}

		//System.out.println(sb);
	}

	public Product searchInfo(int id) {

		// 상품 ID로 검색

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

	public boolean check_PID(int id) {

		// 상품 ID 존재 여부 검사

		boolean result=false;

		if(productList.containsKey(id)==true) {
			result=true;
		}

		return result;
	}

	public void modifyStock(Product pd,int count) {

		int id = pd.getProductID();
		String productName = pd.getProductName();
		int price = pd.getPrice();
		int stock = pd.getStock() + count;

		if(productList.containsKey(id)==true) {

			//String productName = productList.get(id).getProductName();

			Product m_product = new Product(id, productName, price, stock);

			productList.replace(id,m_product);

			//System.out.println("수정이 완료되었습니다.");
		}
		else {
			System.out.println("재고 변경 오류");
		}
	}

	public void modifyList(){
		// 상품 정보 수정

		Scanner sc = new Scanner(System.in);

		System.out.println("상품정보를 수정할 ID를 입력해주세요.");
		System.out.println("ID : ");

		String tmp = sc.nextLine();
		int modify_id = Integer.parseInt(tmp);

		if(productList.containsKey(modify_id)==true) {

			sc.nextLine();
			System.out.print("수정할 상품명 : ");
			String getProductName = sc.nextLine();

			//여기 문제
			System.out.print("수정할 가격 : ");
			String tmp_p = sc.nextLine();
			int getPrice = Integer.parseInt(tmp_p);

			//여기 문제
			System.out.print("수정할 재고 : ");
			String tmp_s = sc.nextLine();
			int getStock = Integer.parseInt(tmp_s);

			Product m_product = new Product(modify_id, getProductName, getPrice, getStock);

			productList.replace(modify_id,m_product);

			System.out.println("수정이 완료되었습니다.");

		}
		else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}

	public void deleteList(){

		// 상품 정보 삭제

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String ans;
		Scanner sc = new Scanner(System.in);

		System.out.println("삭제할 ID를 입력하세요.");

		String tmp = sc.nextLine();
		int delete_pid = Integer.parseInt(tmp);

		if(productList.containsKey(delete_pid)==true) {

			System.out.println("정말 삭제하시겠습니까?(y/n)");
			ans = sc.next();

			if(ans.equals("y"))
				productList.remove(delete_pid);
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
}
