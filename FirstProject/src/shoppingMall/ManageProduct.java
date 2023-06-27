package shoppingMall;

import java.io.*;
import java.util.*;

public class ManageProduct {
	
	private ArrayList<Product> productList = new ArrayList<>();
	//private HashMap<Integer,Product> productHash;
	
	public void addList(Product pd) {
		
		ManageProduct mp = new ManageProduct();
		
		productList = mp.readList();
		
		productList.add(pd);

	}
	
	public void uploadList() {
		
		File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\dataa.csv");
		BufferedWriter bw = null;
		
		String NEWLINE = System.lineSeparator();
		
		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));
			
			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));
			
			bw.write("productID,productName,price,stock");
			bw.write(NEWLINE);

			for(int i=0;i<productList.size();i++) {
				String aData;
				
				aData = productList.get(i).getProductID() +","+productList.get(i).getProductName()+","+productList.get(i).getPrice()+","+productList.get(i).getStock();
	
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
	
	public ArrayList<Product> readList() {
		
		File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\dataa.csv");
		
		List<List<String>> records = new ArrayList<>();
		
		//ArrayList<Product> pd = new ArrayList<>();
	
		String line="";
		
		try(BufferedReader br = new BufferedReader(new FileReader(csv))){

			String[] values = new String[4];
			
			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}
					
			for(int i=1; i<records.size();i++) {
				Product prod = new Product(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),Integer.parseInt(records.get(i).get(2)),Integer.parseInt(records.get(i).get(3)));
				productList.add(prod);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)
		
		return productList;
	}
	
	public void printList() {
		
		//ArrayList<Product> pd= new ArrayList<>();

		ManageProduct mp = new ManageProduct();
						
		productList = mp.readList();
		
		StringBuilder sb = new StringBuilder ();

		for(int i=0;i< productList.size();i++) {
			int id = productList.get(i).getProductID();
			String name = productList.get(i).getProductName();
			int price = productList.get(i).getPrice();
			int stock = productList.get(i).getStock();
								
			sb.append(Integer.toString(id)).append(' ').append(name).append(' ').append(Integer.toString(price)).append(' ').append(Integer.toString(stock)).append("\n");
		}
		
		System.out.println(sb);
	}
	
	public void searchInfo(int id) {
		
	}
	
	public void modifyList(Product pd) {
		
		File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\dataa.csv");
		
	}
	
	public void deleteList(int delete_id) throws IOException{
		//productHash = new HashMap<Integer,Product>();
		
		ManageProduct mp = new ManageProduct();
		productList = mp.readList();

		ArrayList<Integer> idList = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String ans;

		for(int i=0;i<productList.size();i++)
			idList.add(productList.get(i).getProductID());
		
		for(int i=0;i<productList.size();i++)
			System.out.print(idList.get(i)+" ");
		System.out.println();
		
		if(idList.contains(delete_id)==true) {
			
			System.out.println("정말 삭제하시겠습니까?");
			ans = br.readLine();
			int idx=idList.indexOf(delete_id);
			
			if(ans.equals("y"))
				productList.remove(idx);
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
}
