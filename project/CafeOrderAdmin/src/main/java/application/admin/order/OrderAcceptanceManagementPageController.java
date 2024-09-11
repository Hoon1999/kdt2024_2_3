package application.admin.order;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

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
				
				// 요소 가져오기.
				tpTilePane = (TilePane) orderAcceptanceManagementPage.lookup("#tpTilePane");
				
				System.out.println("init success orderAcceptanceManagementPage");
			} catch (IOException e) {
				System.out.println("!(orderAcceptance-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(orderAcceptance-IOException) : " + e.getMessage());
			}
		}
		
		
		System.out.println("return productProductManagementPage");
		return orderAcceptanceManagementPage;
		
	}
	
	public void setContents() {
		// DB 에서 주문 완료가 아닌, 주문 접수 중인 리스트 가져옴.
		LinkedList dbList = null;
		
		// 리스트에 맞게 / 주문 번호, 주문 시간, 주문 내용을 넣는 객체의 리스트로 만들고,
		// 그 객체 리스트를 orderList 에 추가해서 띄움.
		
		
		orderList = tpTilePane.getChildren();
		orderList.clear();
		orderList.add(dbList);
		
	}
	
	
}
