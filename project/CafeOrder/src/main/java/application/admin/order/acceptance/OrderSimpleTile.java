package application.admin.order.acceptance;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import application.admin.etc.order.Order;
import application.admin.etc.order.OrderItem;
import application.admin.etc.order.OrderManager;
import application.admin.etc.product.Product;
import application.admin.etc.product.ProductManager;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class OrderSimpleTile {

	Parent orderSimpleTile;
	
	Label lbOrderTime;
	Label lbOrderNum;
	Label lbIsTakeout;
	Button btnOrderDetail;
	Label lbOrderCount;
	
	OrderManager orderManager = AdminMainPageController.getMainPageController().getOrderManager();
	ProductManager productManager = AdminMainPageController.getMainPageController().getProductManager();
	
	public Parent getOrderSimpleTile(int orderNum) {
		try {
			orderSimpleTile = FXMLLoader.load(getClass().getResource("OrderSimpleTile.fxml"));
			
			// 버튼 가져오기.
			lbOrderTime = (Label) orderSimpleTile.lookup("#lbOrderTime");
			lbOrderNum = (Label) orderSimpleTile.lookup("#lbOrderNum");
			lbIsTakeout = (Label) orderSimpleTile.lookup("#lbIsTakeout");
			lbOrderCount = (Label) orderSimpleTile.lookup("#lbOrderCount");
			btnOrderDetail = (Button) orderSimpleTile.lookup("#btnOrderDetail");
			
		} catch (Exception e) {
			System.out.println("!(orderSimpleTile-Exception) :" + e.getMessage());
			Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("~!(orderSimpleTile-Exception) : " + e.getMessage());
		}
		
		Order order = orderManager.findOrderByOrderNum(orderNum);
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
				Product product = productManager.findProductById(o.getProductId());
				sbDetail.append(product.getName()).append(" ");
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
		
		
		return orderSimpleTile;
	}

	private void callDetail(int orderNum) {
//		System.out.println("=======================");
//		System.out.println("lbOrderNum : " + lbOrderNum.getText());
//		System.out.println("lbOrderTime : " + lbOrderTime.getText());
//		System.out.println("lbOrderCount : " + lbOrderCount.getText());
//		System.out.println("=======================");
		
		OrderDetailDialogController dialog = new OrderDetailDialogController();
		dialog.makeOrderDetailDialog(orderNum);
	}
}
