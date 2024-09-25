package application.admin.etc.order;

import application.admin.etc.option.OptionManager;
import application.admin.etc.option.SubOption;
import application.admin.main.AdminMainPageController;

public class OrderOption {
	
	private int subOptionId;	// 주문 하위 옵션 id.
	private int count;			// 주문 하위 옵션 구매 개수.
	
	// getter/setter.
	public int getSubOptionId() { return subOptionId; }
	public void setSubOptionId(int subOptionId) { this.subOptionId = subOptionId; }
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }	
}
