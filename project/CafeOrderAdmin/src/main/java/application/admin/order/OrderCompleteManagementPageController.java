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
				
				// 요소 가져오기.
				tpTilePane = (TilePane) orderCompleteManagementPage.lookup("#tpTilePane");
				
				System.out.println("init success orderCompleteManagementPage");
			} catch (IOException e) {
				System.out.println("!(orderComplete-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(orderComplete-IOException) : " + e.getMessage());
			}
		}
		
		
		System.out.println("return orderCompleteManagementPage");
		return orderCompleteManagementPage;
		
	}
	
	public void setContents() {
		// DB 에서 주문 완료인 리스트 가져옴.
		LinkedList dbList = null;
		
		// 리스트에 맞게 / 주문 번호, 주문 시간, 주문 내용을 넣는 객체의 리스트로 만들고,
		// 그 객체 리스트를 orderCompleteList 에 추가해서 띄움.
		
		
		orderCompleteList = tpTilePane.getChildren();
		orderCompleteList.clear();
		orderCompleteList.add(dbList);
	}
	
	
}
