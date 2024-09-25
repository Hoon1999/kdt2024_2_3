package application.admin.order.complete;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import application.admin.etc.order.Order;
import application.admin.etc.order.OrderItem;
import application.admin.etc.order.OrderManager;
import application.admin.etc.product.ProductManager;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class OrderCompleteSimpleTile {

	Parent orderCompleteSimpleTile;
	
	Label lbOrderTime;
	Label lbOrderNum;
	Label lbIsTakeout;
	Button btnOrderDetail;
	Label lbOrderCount;

	ProductManager pm = AdminMainPageController.getMainPageController().getProductManager();
	OrderManager om = AdminMainPageController.getMainPageController().getOrderManager();
	
	public Parent getOrderCompleteSimpleTile(int orderNum) {
		try {
			orderCompleteSimpleTile = FXMLLoader.load(getClass().getResource("OrderCompleteSimpleTile.fxml"));
			
			// 버튼 가져오기.
			lbOrderTime = (Label) orderCompleteSimpleTile.lookup("#lbOrderTime");
			lbOrderNum = (Label) orderCompleteSimpleTile.lookup("#lbOrderNum");
			lbIsTakeout = (Label) orderCompleteSimpleTile.lookup("#lbIsTakeout");
			lbOrderCount = (Label) orderCompleteSimpleTile.lookup("#lbOrderCount");
			btnOrderDetail = (Button) orderCompleteSimpleTile.lookup("#btnOrderDetail");
			
		} catch (Exception e) {
			System.out.println("!(orderCompleteSimpleTile-Exception) :" + e.getMessage());
			Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("~!(orderCompleteSimpleTile-Exception) : " + e.getMessage());
		}
		
		Order order = om.findCompletedOrderByOrderNum(orderNum);
		if (order != null) {
			lbOrderNum.setText(String.valueOf(order.getOrderNum()));
			lbOrderTime.setText(order.getFormattedPaymentTime());
			lbIsTakeout.setText(String.valueOf(order.getIsTakeout() ? " (포장)" : " (매장)") );
			int orderCount = 0;
			
			StringBuilder sbDetail = new StringBuilder();
			for (OrderItem o : order.getOrderList()) {
				orderCount += o.getCount();
			}
			lbOrderCount.setText(String.valueOf(orderCount));
			
			for (int i = 0; i < order.getOrderList().size(); i++) {
				OrderItem o = order.getOrderList().get(i);
				sbDetail.append(pm.findProductById(o.getProductId()).getName()).append(" ");
				sbDetail.append(o.getCount());
				if (i == 0) {
					sbDetail.append("\n");
				}
				
				if (i >= 1) {
					sbDetail.append(" ...");
					break;
				}
			}
			
			btnOrderDetail.setText(sbDetail.toString());
			btnOrderDetail.setOnAction(event -> callDetail(orderNum));
		}
		
		
		return orderCompleteSimpleTile;
	}

	private void callDetail(int orderNum) {
//		System.out.println("=======================");
//		System.out.println("lbOrderNum : " + lbOrderNum.getText());
//		System.out.println("lbOrderTime : " + lbOrderTime.getText());
//		System.out.println("lbOrderCount : " + lbOrderCount.getText());
//		System.out.println("=======================");
		
		OrderCompleteDetailDialogController dialog = new OrderCompleteDetailDialogController();
		dialog.makeOrderDetailDialog(orderNum);
	}
}
