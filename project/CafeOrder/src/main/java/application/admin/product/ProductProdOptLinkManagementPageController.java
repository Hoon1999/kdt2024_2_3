package application.admin.product;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ProductProdOptLinkManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent productProductManagementPage = null;

	ListView lvCategoryList;
	TableView tvProductList;
	ImageView ivProductImage;
	Button btnInsertProduct;
	Button btnDetailProduct;
	Button btnUpdateProduct;
	Button btnDeleteProduct;
	
	public Parent getProductProductManagementPage() {
		if (productProductManagementPage == null) {
			try {
				System.out.println("init productProductManagementPage");
				// 페이지 가져오기. 
				productProductManagementPage = FXMLLoader.load(getClass().getResource("ProductProductManagementPage.fxml"));
				
				// 요소 가져오기.
				lvCategoryList = (ListView) productProductManagementPage.lookup("#lvCategoryList");
				tvProductList = (TableView) productProductManagementPage.lookup("#tvProductList");
				ivProductImage = (ImageView) productProductManagementPage.lookup("#ivProductImage");
				btnInsertProduct = (Button) productProductManagementPage.lookup("#btnInsertProduct");
				btnDetailProduct = (Button) productProductManagementPage.lookup("#btnDetailProduct");
				btnUpdateProduct = (Button) productProductManagementPage.lookup("#btnUpdateProduct");
				btnDeleteProduct = (Button) productProductManagementPage.lookup("#btnDeleteProduct");
				
				System.out.println("init success productProductManagementPage");
			} catch (IOException e) {
				System.out.println("!(KeypadCont-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(KeypadCont-IOException) : " + e.getMessage());
			} catch (Exception e) {
				System.out.println("!(KeypadCont-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(KeypadCont-Exception) : " + e.getMessage());
			}
		}
		
		
		btnInsertProduct.setOnAction(event -> System.out.println("상품 추가 버튼 클릭!"));
		
		System.out.println("return productProductManagementPage");
		return productProductManagementPage;
		
	}
	
}
