package application.admin.order.acceptance;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.admin.etc.order.Order;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class OrderAcceptanceManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent orderAcceptanceManagementPage = null;
	
	TilePane tpTilePane;
	ObservableList orderList;
	
	public Parent getOrderAcceptanceManagementPage() {
		if (orderAcceptanceManagementPage == null) {
			try {
				System.out.println("init orderAcceptanceManagementPage");
				// 페이지 가져오기. 
				orderAcceptanceManagementPage = FXMLLoader.load(getClass().getResource("OrderAcceptanceManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(orderAcceptanceManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();

				// 요소 가져오기.
				tpTilePane = (TilePane) orderAcceptanceManagementPage.lookup("#tpTilePane");
				orderList = tpTilePane.getChildren();
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success orderAcceptanceManagementPage");
			} catch (Exception e) {
				System.out.println("!(orderAcceptance-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(orderAcceptance-Exception) : " + e.getMessage());
			}
			
		}
		
		setContents();

		System.out.println("return orderAcceptanceManagementPage");
		return orderAcceptanceManagementPage;
		
	}
	

	
	public void setContents() {
		System.out.println("orderAcceptanceManagementPage-setContents");
		// DB 에서 주문 완료가 아닌, 주문 접수 중인 리스트 가져옴.

		LinkedList dbList = AdminMainPageController.getMainPageController().getOrderManager().getOrderList();
		
		orderList.clear(); // 현재 tpTilePane 의 요소 모두 초기화.
		
		System.out.println("dbList : " + dbList);
		System.out.println("size : " + dbList.size());

		if (dbList == null || dbList.isEmpty()) {
			orderList.add(new Label("현재 접수된 주문이 없습니다."));
		}
		else {
			Iterator<Order> iter = dbList.iterator();
			while (iter.hasNext()) {
				Order itemObject = iter.next();
				OrderSimpleTile orderSimpleTile = new OrderSimpleTile();
				Parent tile = orderSimpleTile.getOrderSimpleTile(itemObject.getOrderNum());
				
				orderList.add(tile);
			}			
			
		}
		
	}
	
	
}
