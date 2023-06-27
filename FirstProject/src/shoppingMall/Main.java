package shoppingMall;

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
	
		// 상품 등록
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		/*
		String productInfo = br.readLine();
		
		String[] data = productInfo.split(" ");
		
		Product pd = new Product(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		
		ManageProduct mp = new ManageProduct();
		
		mp.addList(pd);
		*/
		
		// 상품 정보 수정
		
		
		
		// 상품 정보 삭제
		ManageProduct mp2 = new ManageProduct();

		int delete_id;
		System.out.println("삭제할 ID를 입력하세요.");
		
		//delete_id = br.read();
		Scanner sc = new Scanner(System.in);
		
		delete_id = sc.nextInt();
		
		mp2.deleteList(delete_id);
		
		// 상품 리스트 업로드
		
		mp2.uploadList();
		
		// 상품 목록 출력

		ManageProduct mp1 = new ManageProduct();

		mp1.printList();

	}
}
