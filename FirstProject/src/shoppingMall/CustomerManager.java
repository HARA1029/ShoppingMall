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
	
private LinkedHashMap<Integer,Customer> customerList = new LinkedHashMap<Integer,Customer>();
	
	File csv = new File("C:\\Users\\KOSA\\eclipse-workspace\\HelloJava\\customer.csv");

	
	public void addList() {
		//고객 등록
	     
	     Scanner sc= new Scanner(System.in);
			      
	      System.out.print("고객 ID (3자리이내의 숫자로 입력해주세요): ");
	      int customID = sc.nextInt();
	      
	      sc.nextLine();
	      System.out.print("고객명 : ");
	      String customName = sc.nextLine();
	      
	      System.out.print("전화번호 : ");
	      String phoneNum = sc.nextLine();
	      
	      System.out.print("주소 : ");
	      String address = sc.nextLine();
	      
	      Customer ct = new Customer(customID, customName, phoneNum, address);
	      
	      customerList.put(ct.getCustomID(),ct);
	      
	      System.out.println("결과 : " + customID + " " + customName + " 등록 완료");
	      System.out.println();

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
			
			bw.write("CustomerID,CustomerName,phoneNum,address");
			bw.write(NEWLINE);

			Iterator<Integer> keys = customerList.keySet().iterator();
			while(keys.hasNext()) {
				Integer key = keys.next();
				String aData;
			
				aData = customerList.get(key).getCustomID() +","+customerList.get(key).getCustomName()+","+customerList.get(key).getPhoneNum()+","+customerList.get(key).getAddress();
	
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

		Iterator<Integer> keys=customerList.keySet().iterator();
		
		while(keys.hasNext()) {
			
			int key=keys.next();
			
			int id = customerList.get(key).getCustomID();
			String name = customerList.get(key).getCustomName();
			String phoneNum = customerList.get(key).getPhoneNum();
			String address = customerList.get(key).getAddress();
								
			sb.append(Integer.toString(id)).append(' ').append(name).append(' ').append(phoneNum).append(' ').append(address).append("\n");
		}
		
		System.out.println(sb);
	}
	
	public void searchInfo(int id) {
		
	}
	
	public void modifyList() {
		
		// 고객 정보 수정
		
		Scanner sc =new Scanner(System.in);
	      
	      System.out.println("고객정보를 수정할 ID를 입력해주세요.");
	      System.out.println("ID : ");
	      int modify_id = sc.nextInt();
		
		if(customerList.containsKey(modify_id)==true) {
			
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
			
			  customerList.replace(modify_id,m_customer);
			
			System.out.println("수정이 완료되었습니다.");

		}
		else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
	
	public void deleteList(){
		// 고객 정보 삭제

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Scanner sc= new Scanner(System.in);

		String ans;

		int delete_cid;
		System.out.println("삭제할 ID를 입력하세요.");
					
		delete_cid = sc.nextInt();

		if(customerList.containsKey(delete_cid)==true) {
			
			System.out.println("정말 삭제하시겠습니까?(y/n)");
			ans = sc.next();
			//ans = br.readLine();
			
			if(ans.equals("y"))
				customerList.remove(delete_cid);
			else
				return;
		}else {
			System.out.println("해당 ID를 찾을 수 없습니다.");
		}
	}
}
