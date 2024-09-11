package org.example.cafeorder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

	@FXML
	private BorderPane bp;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//TO DO
		loadPage("recommend");
	}

	@FXML
	private void recommendClick(MouseEvent event) {
		loadPage("recommend"); // 추천 탭 로드
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
			Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
			bp.setCenter(root);
		} catch (IOException e) {
			Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, e);

		}
	}
}

















