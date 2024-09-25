package application.admin.product;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProductManagementPageController {

	// FXML 페이지 및 요소.
	private Parent productManagementPage = null;

	private BorderPane bpProduct;
	private BorderPane bpOption;
	private BorderPane bpProdOptLink;
	
	private TabPane tpTapPane;
	private Tab tabProduct;
	private Tab tabOption;
	private Tab tabProdOptLink;
	
	
	private ProductProductManagementPageController productProductManagementPageController = null;
	public ProductProductManagementPageController getProductProductManagementPageController() {
		if (productProductManagementPageController == null) {
			productProductManagementPageController = new ProductProductManagementPageController();
		}
		return productProductManagementPageController;
	}
	private ProductOptionManagementPageController productOptionManagementPageController = null;
	public ProductOptionManagementPageController getProductOptionManagementPageController() {
		if (productOptionManagementPageController == null) {
			productOptionManagementPageController = new ProductOptionManagementPageController();
		}
		return productOptionManagementPageController;
	}
	private ProductProdOptLinkManagementPageController productProdOptLinkManagementPageController = null;
	public ProductProdOptLinkManagementPageController getProductProdOptLinkManagementPageController() {
		if (productProdOptLinkManagementPageController == null) {
			productProdOptLinkManagementPageController= new ProductProdOptLinkManagementPageController();
		}
		return productProdOptLinkManagementPageController;
	}
	
	
	public Parent getProductManagementPage() {
		if(productManagementPage == null) {
			try {
				System.out.println("init productManagementPage");
				productManagementPage = FXMLLoader.load(getClass().getResource("ProductManagementPage.fxml"));
				
				// 요소 가져오기.
				bpProduct = (BorderPane) productManagementPage.lookup("#bpProduct");
				bpOption = (BorderPane) productManagementPage.lookup("#bpOption");
				bpProdOptLink = (BorderPane) productManagementPage.lookup("#bpProdOptLink");
				
			} catch (IOException e) {
				System.out.println("!(productManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(productManagementPage-IOException) : " + e.getMessage());
			}
		}
		
		setProductProductManagementPage();
		setProductOptionManagementPage();
		setProductProdOptLinkManagementPage();
		
		System.out.println("return productManagementPage");
		return productManagementPage;
	}
	
	public void setProductProductManagementPage() {
		bpProduct.setCenter(AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProductManagementPageController().getProductProductManagementPage());
	}
	
	public void setProductOptionManagementPage() {
		bpOption.setCenter(AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage());
	}
	
	public void setProductProdOptLinkManagementPage() {
		bpProdOptLink.setCenter(AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProdOptLinkManagementPageController().getProductProdOptLinkManagementPage());
	}
}
