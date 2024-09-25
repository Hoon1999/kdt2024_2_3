package application.admin.menu.product;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.product.Product;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class MenuProductPageController {
	
	// FXML 페이지 및 요소.
	private Parent menuProductManagementPage = null;

	ListView<String> lvCategoryList;
	VBox vbProductList;
	
	ObservableList<String> categoryList;
	ObservableList productList;
	
	public Parent getMenuProductManagementPage() {
		if (menuProductManagementPage == null) {
			try {
				System.out.println("init menuProductManagementPage");
				// 페이지 가져오기. 
				menuProductManagementPage = FXMLLoader.load(getClass().getResource("MenuProductManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(menuProductManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				lvCategoryList = (ListView) menuProductManagementPage.lookup("#lvCategoryList");
				vbProductList = (VBox) menuProductManagementPage.lookup("#vbProductList");

				// categoryList 와 lvCategoryList 연결.
				categoryList = lvCategoryList.getItems();
								
				// productList 와 vbProductList 연결.
				productList = vbProductList.getChildren();
				

				// 리스트뷰 changeListener 추가.
				lvCategoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
						System.out.println("menu product list changed :: " + oldValue + " -> " + newValue);
						
						productList.clear();
						
						// 카테고리 선택(변경) 시 해당 카테고리에 맞게 vbox 설정.
						if (newValue != null) {
							LinkedList selectedProductList = AdminMainPageController.getMainPageController().getProductManager().findProductsByCategory(newValue);
							
							if (selectedProductList == null || selectedProductList.isEmpty()) {
								productList.add(new Label("현재 선택된 카테고리의 상품이 없습니다."));
							}
							else {
								Iterator<Product> iter = selectedProductList.iterator();
								while (iter.hasNext()) {
									Product product = iter.next();
									MenuProductTile menuProductTile = new MenuProductTile();
									Parent tile = menuProductTile.getMenuProductTile(product.getId());
									
									productList.add(tile);
								}
							}
						}
					}
				});
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success menuProductManagementPage");
			} catch (IOException e) {
				System.out.println("!(MenuProductPageController-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(MenuProductPageController-IOException) : " + e.getMessage());
			}
		} // ~ if (menuProductManagementPage == null).
		
		/////////////////////////////////////////
		
		// 카테고리 리스트 초기화.
		categoryList = lvCategoryList.getItems();
		categoryList.setAll(AdminMainPageController.getMainPageController().getProductManager().getCategoryNameList());
		lvCategoryList.setItems(categoryList);
		
		// 리스트에서 제일 앞의 요소 선택.
		lvCategoryList.getSelectionModel().clearSelection();
		lvCategoryList.getSelectionModel().selectFirst();
		
		
		System.out.println("return menuProductManagementPage");
		return menuProductManagementPage;
	}
	
}
