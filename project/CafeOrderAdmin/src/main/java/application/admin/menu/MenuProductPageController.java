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

public class MenuProductPageController {
	
	// FXML 페이지 및 요소.
	private Parent menuProductManagementPage = null;

	ListView lvCategoryList;
	TableView tvProductList;
	ImageView ivProductImage;
	Button btnInsertProduct;
	Button btnDetailProduct;
	Button btnUpdateProduct;
	Button btnDeleteProduct;
	
	public Parent getMenuProductManagementPage() {
		if (menuProductManagementPage == null) {
			try {
				System.out.println("init menuProductManagementPage");
				// 페이지 가져오기. 
				menuProductManagementPage = FXMLLoader.load(getClass().getResource("MenuProductManagementPage.fxml"));
				
				// 요소 가져오기.
				lvCategoryList = (ListView) menuProductManagementPage.lookup("#lvCategoryList");
				
				System.out.println("init success menuProductManagementPage");
			} catch (IOException e) {
				System.out.println("!(MenuProductPageController-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(MenuProductPageController-IOException) : " + e.getMessage());
			}
		}
		
		System.out.println("return menuProductManagementPage");
		return menuProductManagementPage;
	}
	
}
