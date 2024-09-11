package application.admin.order;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class OrderManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent orderManagementPage = null;

	private BorderPane bpAcceptance;
	private BorderPane bpComplete;
	
	private TabPane tpTapPane;
	private Tab tabAcceptance;
	private Tab tabComplete;
	
	// 페이지 및 컨트롤러, fxml 만들어야 함. TODO
	
	private OrderAcceptanceManagementPageController orderAcceptanceManagementPageController = null;
	public OrderAcceptanceManagementPageController getOrderAcceptanceManagementPageController() {
		if (orderAcceptanceManagementPageController == null) {
			orderAcceptanceManagementPageController = new OrderAcceptanceManagementPageController();
		}
		return orderAcceptanceManagementPageController;
	}
	
	private OrderCompleteManagementPageController orderCompleteManagementPageController = null;
	public OrderCompleteManagementPageController getOrderCompleteManagementPageController() {
		if (orderCompleteManagementPageController == null) {
			orderCompleteManagementPageController = new OrderCompleteManagementPageController();
		}
		return orderCompleteManagementPageController;
	}
	
	
	public Parent getOrderManagementPage() {
		if (orderManagementPage == null) {
			try {
				System.out.println("init orderManagementPage");
				orderManagementPage = FXMLLoader.load(getClass().getResource("OrderManagementPage.fxml"));
				
				bpAcceptance = (BorderPane) orderManagementPage.lookup("#bpAcceptance");
				bpComplete = (BorderPane) orderManagementPage.lookup("#bpComplete");
			}catch (Exception e) {
				System.out.println("!(orderManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(orderManagementPage-IOException) : " + e.getMessage());
			}
		}

		setOrderAcceptanceManagementPage();
		setOrderCompleteManagementPage();
		
		System.out.println("return orderManagementPage");
		return orderManagementPage;
	}
	
	public void setOrderAcceptanceManagementPage() {
		bpAcceptance.setCenter(AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderAcceptanceManagementPageController().getOrderAcceptanceManagementPage());
	}
	
	public void setOrderCompleteManagementPage() {
		bpComplete.setCenter(AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderCompleteManagementPageController().getOrderCompleteManagementPage());
	}
	
}
