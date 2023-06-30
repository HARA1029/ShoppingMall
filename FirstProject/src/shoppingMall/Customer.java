package shoppingMall;

public class Customer {

	private String customID;
	private String customPW;
	private String customName;
	private String phoneNum;
	private String address;

	Customer(String customID, String customPW, String customName, String phoneNum, String address) {
		this.customID = customID;
		this.customPW = customPW;
		this.customName = customName;
		this.phoneNum = phoneNum;
		this.address = address;
	}


	public String getCustomID() {
		return customID;
	}

	public void setCustomID(String customID) {
		this.customID = customID;
	}
	public String getCustomPW() {
		return customPW;
	}

	public void setCustomPW(String customPW) {
		this.customPW = customPW;
	}
	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}