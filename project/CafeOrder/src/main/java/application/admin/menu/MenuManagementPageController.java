package application.admin.menu;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import application.admin.menu.option.MenuOptionPageController;
import application.admin.menu.product.MenuProductPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuManagementPageController {

	// FXML 페이지 및 요소.
	private Parent menuManagementPage = null;

	private BorderPane bpProduct;
	private BorderPane bpOption;
	
	private TabPane tpTapPane;
	private Tab tabProduct;
	private Tab tabOption;
	
	/// TODO 각 탭을 각각의 fxml 로 나누고, 세팅처럼 해보자.
	private MenuProductPageController menuProductPageController = null;
	public MenuProductPageController getMenuProductPageController() {
		if (menuProductPageController == null) {
			menuProductPageController = new MenuProductPageController();
		}
		return menuProductPageController;
	}
	private MenuOptionPageController menuOptionPageController = null;
	public MenuOptionPageController getMenuOptionPageController () {
		if (menuOptionPageController == null) {
			menuOptionPageController = new MenuOptionPageController();
		}
		return menuOptionPageController;
	}
	
	
	public Parent getMenuManagementPage() {
		if(menuManagementPage == null) {
			try {
				System.out.println("init menuManagementPage");
				menuManagementPage = FXMLLoader.load(getClass().getResource("MenuManagementPage.fxml"));
				
				// 요소 가져오기. // TODO
//				vbDefault = (VBox) productManagementPage.lookup("#vbDefault");
				bpProduct = (BorderPane) menuManagementPage.lookup("#bpProduct");
				bpOption = (BorderPane) menuManagementPage.lookup("#bpOption");
				
			} catch (IOException e) {
				System.out.println("!(menuManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(menuManagementPage-IOException) : " + e.getMessage());
			}
		}
		
		setMenuProductManagementPage();
		setMenuOptionManagementPage();
		
		System.out.println("return menuManagementPage");
		return menuManagementPage;
	}
	
	
	public void setMenuProductManagementPage() {
		bpProduct.setCenter(AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuProductPageController().getMenuProductManagementPage());
	}
	public void setMenuOptionManagementPage() {
		bpOption.setCenter(AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuOptionPageController().getMenuOptionManagementPage());
	}
}
