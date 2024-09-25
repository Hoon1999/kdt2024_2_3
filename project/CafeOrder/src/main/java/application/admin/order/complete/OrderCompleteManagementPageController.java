package application.admin.order.complete;

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

public class OrderCompleteManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent orderCompleteManagementPage = null;
	
	TilePane tpTilePane;
	ObservableList orderCompleteList;
	
	public Parent getOrderCompleteManagementPage() {
		if (orderCompleteManagementPage == null) {
			try {
				System.out.println("init orderCompleteManagementPage");
				// 페이지 가져오기. 
				orderCompleteManagementPage = FXMLLoader.load(getClass().getResource("OrderCompleteManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(orderCompleteManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				tpTilePane = (TilePane) orderCompleteManagementPage.lookup("#tpTilePane");
				orderCompleteList = tpTilePane.getChildren();
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success orderCompleteManagementPage");
			} catch (Exception e) {
				System.out.println("!(orderComplete-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(orderComplete-Exception) : " + e.getMessage());
			}
		}
		
		setContents();
		
		System.out.println("return orderCompleteManagementPage");
		return orderCompleteManagementPage;
		
	}
	
	public void setContents() {
		System.out.println("orderCompleteManagementPage-setContents");
		// DB 에서 주문 완료인 리스트 가져옴.
		
		// TODO test. 주문 내역. 양이 많은 경우.
		LinkedList dbList = AdminMainPageController.getMainPageController().getOrderManager().getOrdersCompleteList();
		
		orderCompleteList.clear();
		
		System.out.println("dbList : " + dbList);
		System.out.println("size : " + dbList.size());
		
		if (dbList == null || dbList.isEmpty()) {
			orderCompleteList.add(new Label("현재 완료된 주문이 없습니다."));
		}
		else {
			Iterator<Order> iter = dbList.iterator();
			while (iter.hasNext()) {
				Order itemObject = iter.next();
				OrderCompleteSimpleTile orderSimpleTile = new OrderCompleteSimpleTile();
				Parent tile = orderSimpleTile.getOrderCompleteSimpleTile(itemObject.getOrderNum());
				
				orderCompleteList.add(tile);
			}			
			
		}
		
	}
	
	
}
