package shoppingMall;

import java.util.*;
import java.io.*;


public class ProductManager {

	private LinkedHashMap<Integer,Product> productList = new LinkedHashMap<Integer,Product>();
	
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\product.csv");

	
	public void addList(Product pd) {
		
		productList.put(pd.getProductID(),pd);

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
		
		StringBuilder sb = new StringBuilder ();

		Iterator<Integer> keys=productList.keySet().iterator();
		
		while(keys.hasNext()) {
			
			int key=keys.next();
			
			int id = productList.get(key).getProductID();
			String name = productList.get(key).getProductName();
			int price = productList.get(key).getPrice();
			int stock = productList.get(key).getStock();
								
			sb.append(Integer.toString(id)).append(' ').append(name).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(stock)).append("\n");
		}
		
		System.out.println(sb);
	}
	
	public void searchInfo(int id) {
		
	}
	
	public void modifyList(int modify_id, Product pd) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String ans;

		if(productList.containsKey(modify_id)==true) {
			
			productList.replace(modify_id,pd);
			
			System.out.println("정보가 변경되었습니다.");

		}
		else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
	
	public void deleteList(int delete_id) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String ans;

		if(productList.containsKey(delete_id)==true) {
			
			System.out.println("정말 삭제하시겠습니까?(y/n)");
			ans = br.readLine();
			
			if(ans.equals("y"))
				productList.remove(delete_id);
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
}
