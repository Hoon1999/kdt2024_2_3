package application.admin.etc.order;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import application.admin.etc.option.Option;
import application.admin.etc.option.OptionManager;
import application.admin.etc.option.SubOption;
import application.admin.etc.product.Product;
import application.admin.etc.product.ProductManager;
import application.admin.main.AdminMainPageController;

public class Order {
	private int orderId;						// 주문 아이디.
	private String MemberId;					// 회원 아이디.
	private int orderNum;						// 주문번호.
	private ZonedDateTime paymentTime;			// 결제시간.
	private boolean isTakeout;					// 포장(t)/매장(f).
	private LinkedList<OrderItem> orderList;	// 주문한 품목 리스트.
	
	private int totalPaymentAmount;				// 총 결제 가격. (클라에서 계산....)
	
	// getter/setter.
	public int getOrderId() { return orderId; }
	public void setOrderId(int orderId) { this.orderId = orderId; }
	public String getMemberId() { return MemberId; }
	public void setMemberId(String memberId) { MemberId = memberId; }
	public int getOrderNum() { return orderNum; }
	public void setOrderNum(int orderNum) { this.orderNum = orderNum; }
	public ZonedDateTime getPaymentTime() { return paymentTime; }
	public void setPaymentTime(ZonedDateTime paymentTime) { this.paymentTime = paymentTime; }
	public boolean getIsTakeout() { return isTakeout; }
	public void setIsTakeout(boolean takeout) { this.isTakeout = takeout; }
	public LinkedList<OrderItem> getOrderList() { return orderList; }
	public void setOrderList(LinkedList<OrderItem> orderList) { this.orderList = orderList; }
	
	
	// 총 결제 가격 비용 계산.
	public void calculateTotalPaymentAmount() {
		ProductManager pm = AdminMainPageController.getMainPageController().getProductManager();
		OptionManager om = AdminMainPageController.getMainPageController().getOptionManager();
		
		int total = 0;
		
		for (OrderItem orderItem : orderList) {
			Product product = pm.findProductById(orderItem.productId);
			if (product != null) {
				total += orderItem.count * product.getPrice();
				
				for (OrderOption orderOption : orderItem.getOptions()) {
					SubOption subOption = om.findSubOptionById(orderOption.getSubOptionId());
					if (orderOption != null) {
						total += orderOption.getCount() * subOption.getPrice() * orderItem.count;
					}
				}
			}
		}
		
		totalPaymentAmount = total;
	}
	
	public int getTotalPaymentAmount() { return totalPaymentAmount; }
	public void setTotalPaymentAmount(int totalPaymentAmount) { this.totalPaymentAmount = totalPaymentAmount; }
	
	// HH:mm 형식 시간 문자열 반환 메소드.
	public String getFormattedPaymentTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String formattedString = paymentTime.format(formatter);
		return formattedString;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[orderId : ").append(orderId);
		sb.append(", MemberId : ").append(MemberId);
		sb.append(", orderNum : ").append(orderNum);
		sb.append(", paymentTime : ").append(paymentTime);
		sb.append(", takeout : ").append(isTakeout);
		sb.append(", orderList : ").append(orderList);
		sb.append(", totalPaymentAmount : ").append(totalPaymentAmount).append("]");
		
		return sb.toString();
	}
}
