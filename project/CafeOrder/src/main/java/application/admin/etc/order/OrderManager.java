package application.admin.etc.order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrderManager {
	
	private LinkedList<Order> orderList;			// 주문 리스트.
	private LinkedList<Order> orderCompleteList;	// 주문 완료 리스트.
	
	// 생성자.
	public OrderManager() {
		initOrderList();
		initOrderCompleteList();
	}
	
	// getter/setter.
	public LinkedList<Order> getOrderList() { return orderList; }
	public void setOrderList(LinkedList<Order> orderList) { this.orderList = orderList; }
	public LinkedList<Order> getOrdersCompleteList() { return orderCompleteList; }
	public void setOrderCompleteList(LinkedList<Order> orderCompleteList) { this.orderCompleteList = orderCompleteList; }
	
	// 주문 관련.
	public void addOrder(Order order) {
		// 주문 추가.
		orderList.add(order);
	}
	public void deleteOrder(Order order) {
		// 주문 삭제.
		orderList.remove(order);
	}
	public void completeOrder(Order order) {
		// 주문 완료.
		deleteOrder(order);
		orderCompleteList.add(order);
	}
	public void incompleteOrder(Order order) {
		// 주문 완료 취소(접수로 변경).
		addOrder(order);
		orderCompleteList.remove(order);
	}
	
	public void cancelOrder(Order order) {
		// 주문 취소.
		
		// 주문 
		if (orderList.remove(order)) {
			// 환불.
		} else if ( orderCompleteList.remove(order) ) {
			// 환불.
		}
	}
	
	// find(주문번호).
	public Order findOrderByOrderNum(int orderNum) {
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getOrderNum() == orderNum)
			{
				return orderList.get(i);
			}
		}
		return null;
	}
	public Order findCompletedOrderByOrderNum(int orderNum) {
		for (int i = 0; i < orderCompleteList.size(); i++) {
			if (orderCompleteList.get(i).getOrderNum() == orderNum)
			{
				return orderCompleteList.get(i);
			}
		}
		return null;
	}
	// find(주문 id).
	public Order findOrderByOrderId(int orderId) {
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getOrderId() == orderId) {
				return orderList.get(i);
			}
		}
		return null;
	}
	public Order findCompletedOrderByOrderId(int orderId) {
		for (int i = 0; i < orderCompleteList.size(); i++) {
			if (orderCompleteList.get(i).getOrderId() == orderId) {
				return orderCompleteList.get(i);
			}
		}
		return null;
	}
	
	
	// 주문 리스트 초기화.
	public void initOrderList() {
		
		// TODO 통신 필요. 임시.
		////////////////////////////////////////////////////////////////////
//		String fname = "json\\tmpOrder.json";
		String fname = "json/tmpOrder.json";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fname, Charset.forName("UTF-8")));
			String inJson = br.readLine();
			br.close();
			// System.out.println("inJson" + inJson);
			JSONArray root = new JSONArray(inJson);
			
			
			orderList = new LinkedList<Order>();
		
			for (int i = 0; i < root.length(); i++) {
				JSONObject orderJsonObj = (JSONObject)root.get(i);
				Order order = new Order();

				// order_id.
				order.setOrderId(orderJsonObj.getInt("id"));
				// member_id.
				order.setMemberId(orderJsonObj.getString("member_id"));
				// order_num.
				order.setOrderNum(orderJsonObj.getInt("order_num"));
				// payment_time.
				order.setPaymentTime(ZonedDateTime.parse(orderJsonObj.getString("payment_time")));
				// takeout.
				order.setIsTakeout(orderJsonObj.getBoolean("takeout"));
				
				// order_list.
				LinkedList<OrderItem> orderItemList = new LinkedList<OrderItem>();
				JSONArray orderItemListJsonArray = orderJsonObj.getJSONArray("order_list");
				for (int j = 0; j < orderItemListJsonArray.length(); j++) {
					// order_list[j]. (order)
					OrderItem orderItem = new OrderItem();
					JSONObject orderItemJsonObj = (JSONObject) orderItemListJsonArray.get(j);
					
					// product id.
					int productId = orderItemJsonObj.getInt("product_id");
					orderItem.setProductId(productId);
					// count.
					int count = orderItemJsonObj.getInt("count");
					orderItem.setCount(count);
					// options.
					LinkedList<OrderOption> optionList = new LinkedList<OrderOption>();
					JSONArray optionListJsonArray = orderItemJsonObj.getJSONArray("option");
					for (int k = 0; k < optionListJsonArray.length(); k++) {
						// optionList[k]. (option)
						OrderOption subOption = new OrderOption();
						JSONObject optionJsonObj = (JSONObject) optionListJsonArray.get(k);
						
						// id.
						subOption.setSubOptionId(optionJsonObj.getInt("option_id"));
						// count.
						subOption.setCount(optionJsonObj.getInt("count"));
						
						optionList.add(subOption);
						
					}
					orderItem.setOptions(optionList);
					
					orderItemList.add(orderItem);
					
				}
				
				order.setOrderList(orderItemList); // 주문 목록을 설정해주고,

				// + totalPaymentAmount.
				order.calculateTotalPaymentAmount(); // 주문 목록을 토대로 비용을 계산.
				
				orderList.add(order);
			}
			
		} catch (IOException e) {
			System.out.println("!OrderManager-init : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!OrderManager-init : " + e.getMessage());
		}
		
		System.out.println("return ordermanager - init ");
	}
	
	// 주문 완료 리스트 초기화.
	public void initOrderCompleteList() {
		orderCompleteList = new LinkedList<Order>();
	}
}
