package application.admin.menu;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class MenuOptionPageController {
	
	// FXML 페이지 및 요소.
	private Parent menuOptionManagementPage = null;

	ListView lvCategoryList;
	TableView tvProductList;
	ImageView ivProductImage;
	Button btnInsertProduct;
	Button btnDetailProduct;
	Button btnUpdateProduct;
	Button btnDeleteProduct;
	
	public Parent getMenuOptionManagementPage() {
		if (menuOptionManagementPage == null) {
			try {
				System.out.println("init menuOptionManagementPage");
				// 페이지 가져오기. 
				menuOptionManagementPage = FXMLLoader.load(getClass().getResource("MenuOptionManagementPage.fxml"));
				
				// 요소 가져오기.
				lvCategoryList = (ListView) menuOptionManagementPage.lookup("#lvCategoryList");
				
				System.out.println("init success menuOptionManagementPage");
			} catch (IOException e) {
				System.out.println("!(MenuOptionPageController-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(MenuOptionPageController-IOException) : " + e.getMessage());
			}
		}
		
		System.out.println("return menuOptionManagementPage");
		return menuOptionManagementPage;
	}
	
}
