package application.customer.order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderPageController implements Initializable {

	@FXML
	private BorderPane bp;
	@FXML
	private VBox cart;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//TO DO
		loadPage("productList");
		if(bp != null) {
			bp.rightProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> observableValue, Node oldValue, Node newValue) {
					cartReDraw();
				}
			});
		}
	}

	@FXML
	private void recommendClick(MouseEvent event) {
		loadPage("productList"); // 추천 탭 로드
	}

	@FXML
	private void previousOrderClick(MouseEvent event) {
		loadPage("previousOrder"); // 이전에 주문한 메뉴 탭 로드
	}

	@FXML
	private void coffeeClick(MouseEvent event) {
		loadPage("coffee"); // 커피 탭 로드
	}

	@FXML
	private void nonCoffeeClick(MouseEvent event) {
		loadPage("nonCoffee"); // 논커피 탭 로드
	}

	@FXML
	private void bakeryClick(MouseEvent event) {
		loadPage("bakery"); // 베이커리 탭 로드
	}

	@FXML
	private void merchandiseClick(MouseEvent event) {
		loadPage("merchandise"); // MD 탭 로드
	}

	private void loadPage(String page) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource( "/application/customer/order/" + page + ".fxml"));
			bp.setCenter(root);
			bp.setRight(root = FXMLLoader.load(getClass().getResource("/application/customer/order/cart.fxml")));

		} catch (IOException e) {
			Logger.getLogger(OrderPageController.class.getName()).log(Level.SEVERE, null, e);

		}
	}
	public void cartReDraw() {
//		bp.setRight()
		bp.requestLayout();
		System.out.println("cart re draw 호출됨");
	}
}

















