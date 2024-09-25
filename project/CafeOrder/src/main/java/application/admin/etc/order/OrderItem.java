package application.admin.etc.order;

import java.util.LinkedList;

public class OrderItem {
	int productId;						// 주문 품목 id.
	int count;							// 주문 개수.
	LinkedList<OrderOption> options;	// 주문 품목의 (하위)옵션 리스트.
	
	// getter/setter.
	public int getProductId() { return productId; }
	public void setProductId(int productId) { this.productId = productId; }
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }
	public LinkedList<OrderOption> getOptions() { return options; }
	public void setOptions(LinkedList<OrderOption> options) { this.options = options; }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[productId : ").append(productId);
		sb.append(", count : ").append(count);
		sb.append(", options : ").append(options).append("]");
		
		return sb.toString();
	}
}