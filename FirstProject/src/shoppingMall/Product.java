package shoppingMall;

public class Product {
	private int productID;
	private String productName;
	private int price;
	private int stock;
	
	Product(int productID,String productName,int price,int stock){
		this.productID = productID;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
	}
	
	public int getProductID(){
		return productID;
	}
	public void setProductID(int productID) {
		this.productID=productID;
	}
	
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName) {
		this.productName=productName;
	}
	
	public int getPrice(){
		return price;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	
	public int getStock(){
		return stock;
	}
	public void setStock(int stock) {
		this.stock=stock;
	}
	
}
