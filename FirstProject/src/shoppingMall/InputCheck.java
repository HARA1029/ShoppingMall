package shoppingMall;

public class InputCheck {
	//입력값이 정수값인지 검사
		public boolean checkInput(String str) {

			for(int i=0;i<str.length();i++) {
				char chr = str.charAt(i);

				if(chr<0x30 || chr>0x39) {
					return false;
				}
			}
			return true;
		}
}
