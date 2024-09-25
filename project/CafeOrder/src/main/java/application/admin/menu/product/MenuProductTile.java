package application.admin.menu.product;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import application.admin.etc.product.Product;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class MenuProductTile {

	Parent menuProductTile;
	
	Label lbProductId;
	Label lbProductName;
	Button btnToSale;
	Button btnToStockOut;
	
	public Parent getMenuProductTile(int productId) {
		try {
			menuProductTile = FXMLLoader.load(getClass().getResource("MenuProductTile.fxml"));
			
			// 요소 가져오기.
			lbProductId = (Label) menuProductTile.lookup("#lbProductId");
			lbProductName = (Label) menuProductTile.lookup("#lbProductName");
			btnToSale = (Button) menuProductTile.lookup("#btnToSale");
			btnToStockOut = (Button) menuProductTile .lookup("#btnToStockOut");
			
		} catch (Exception e) {
			System.out.println("!(menuProductTile-Exception) :" + e.getMessage());
			Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("~!(menuProductTile-Exception) : " + e.getMessage());
		}
		
		////////////////
		
		Product product = AdminMainPageController.getMainPageController().getProductManager().findProductById(productId);
		if (product != null) {
			lbProductId.setText(String.valueOf(product.getId()));
			lbProductName.setText(product.getName());
			
			if (product.getIsStockOut()) {
				// 품절이면, 품절로 변경 버튼은 비활성화. / 판매로 변경 버튼은 활성화.
				btnToStockOut.setDisable(true);
				btnToSale.setDisable(false);
			}
			else
			{
				// 아니면, 품절로 변경 버튼은 활성화. / 판매로 변경 버튼은 비활성화.
				btnToStockOut.setDisable(false);
				btnToSale.setDisable(true);
			}
			
			btnToSale.setOnAction(event -> toSale(productId));
			btnToStockOut.setOnAction(event -> toStockOut(productId));
		}
		
		return menuProductTile;
	}
	
	public void toStockOut(int selectedProductId) {
		Product selectedProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(selectedProductId);
		selectedProduct.toStockOut();
		
		AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuProductPageController().getMenuProductManagementPage();
	}
	
	public void toSale(int selectedProductId) {
		Product selectedProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(selectedProductId);
		selectedProduct.toInStock();
		
		AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuProductPageController().getMenuProductManagementPage();
	}
}
