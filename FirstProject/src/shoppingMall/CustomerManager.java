package shoppingMall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {
	
private LinkedHashMap<Integer,Customer> CustomerList = new LinkedHashMap<Integer,Customer>();
	
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\customer.csv");

	
	public void addList(Customer ct) {
		
		CustomerList.put(ct.getCustomID(),ct);

	}
	
	public void uploadList() {
		
		//File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\Customer.csv");
		BufferedWriter bw = null;
		
		String NEWLINE = System.lineSeparator();
		
		try {
			//덮어쓰기
			bw = new BufferedWriter(new FileWriter(csv));
			
			//이어쓰기
			//bw = new BufferedWriter(new FileWriter(csv,true));
			
			bw.write("CustomerID,CustomerName,price,stock");
			bw.write(NEWLINE);

			Iterator<Integer> keys = CustomerList.keySet().iterator();
			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;
			
				aData = CustomerList.get(key).getCustomID() +","+CustomerList.get(key).getCustomName()+","+CustomerList.get(key).getPhoneNum()+","+CustomerList.get(key).getAddress();
	
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

			String[] values = new String[4];
			
			while((line = br.readLine())!= null) {
				values = line.split(",");
				records.add(Arrays.asList(values));
			}
					
			for(int i=1; i<records.size();i++) {
				Customer cust = new Customer(Integer.parseInt(records.get(i).get(0)),records.get(i).get(1),records.get(i).get(2),records.get(i).get(3));
				CustomerList.put(cust.getCustomID(), cust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//records = ((1,a,b,c),(2,d,e,f),(3,g,h,i))
		//values = (1,a,b,c)
		
		//return CustomerList;
	}
	
	public void printList() {
		
		StringBuilder sb = new StringBuilder ();

		Iterator<Integer> keys=CustomerList.keySet().iterator();
		
		while(keys.hasNext()) {
			
			int key=keys.next();
			
			int id = CustomerList.get(key).getCustomID();
			String name = CustomerList.get(key).getCustomName();
			String phoneNum = CustomerList.get(key).getPhoneNum();
			String address = CustomerList.get(key).getAddress();
								
			sb.append(Integer.toString(id)).append(' ').append(name).append(' ').append(phoneNum).append(' ').append(address).append("\n");
		}
		
		System.out.println(sb);
	}
	
	public void searchInfo(int id) {
		
	}
	
	public void modifyList(int modify_id) {
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//String ans;
		Scanner sc=new Scanner(System.in);
		
		System.out.print("수정할 ID : ");
	      int getCustomID = sc.nextInt();
	      
	      sc.nextLine();
	      System.out.print("수정할 고객명 : ");
	      String getCustomName = sc.nextLine();
	      
	      System.out.print("수정할 전화번호 : ");
	      String getPhoneNum = sc.nextLine();
	      
	      System.out.print("주소 : ");
	      String getAddress = sc.nextLine();
	      
	      Customer m_customer = new Customer(getCustomID, getCustomName, getPhoneNum, getAddress);

		if(CustomerList.containsKey(modify_id)==true) {
			
			CustomerList.replace(modify_id,m_customer);
			
			System.out.println("수정이 완료되었습니다.");

		}
		else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
	
	public void deleteList(int delete_id) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String ans;

		if(CustomerList.containsKey(delete_id)==true) {
			
			System.out.println("정말 삭제하시겠습니까?(y/n)");
			ans = br.readLine();
			
			if(ans.equals("y"))
				CustomerList.remove(delete_id);
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
}
