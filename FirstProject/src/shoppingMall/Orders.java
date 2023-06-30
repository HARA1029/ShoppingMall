package shoppingMall;

public class Orders {
	
	private int ordersID;
	private String customID;
	private int productID;
	private int price;
	private int count;
	private int total;
	//private String payment;
	
	Orders(int ordersID,String customID,int productID,int price, int count, int total){
		this.ordersID = ordersID;
		this.customID = customID;
		this.productID = productID;
		this.price = price;
		this.count = count;
		this.total = total;
		//this.payment = payment;
	}
	
	public int getOrdersID(){
		return ordersID;
	}
	public void setOrdersID(int ordersID) {
		this.ordersID=ordersID;
	}
	
	public String getCustomID(){
		return customID;
	}
	public void setCustomID(String customID) {
		this.customID=customID;
	}
	
	public int getProductID(){
		return productID;
	}
	public void setProductID(int productID) {
		this.productID=productID;
	}
	
	public int getPrice(){
		return price;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	
	public int getCount(){
		return count;
	}
	public void setCount(int count) {
		this.count=count;
	}
	
	public int getTotal(){
		return total;
	}
	public void setTotal(int total) {
		this.total=total;
	}
	
//	public String getPayment(){
//		return payment;
//	}
//	public void setPayment(String payment) {
//		this.payment=payment;
//	}
}
