package application.admin.product;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.product.Product;
import application.admin.etc.table.ProductDisplayTableData;
import application.admin.etc.table.ProductTableData;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import application.admin.product.product.ProductAddDialogController;
import application.admin.product.product.ProductDeleteDialogController;
import application.admin.product.product.ProductUpdateDialogController;

public class ProductProductManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent productProductManagementPage = null;
	
	ListView<String> lvCategoryList;
	TableView<?> tvProductList;
	ImageView ivProductImage;
	Button btnInsertProduct;
	Button btnDetailProduct;
	Button btnUpdateProduct;
	Button btnDeleteProduct;
	
	ObservableList<String> categoryList;
	List tableDataList;
	
	public Parent getProductProductManagementPage() {
		if (productProductManagementPage == null) {
			try {
				System.out.println("init productProductManagementPage");
				// 페이지 가져오기. 
				productProductManagementPage = FXMLLoader.load(getClass().getResource("ProductProductManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(productProductManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				lvCategoryList = (ListView<String>) productProductManagementPage.lookup("#lvCategoryList");
				tvProductList = (TableView<?>) productProductManagementPage.lookup("#tvProductList");
				ivProductImage = (ImageView) productProductManagementPage.lookup("#ivProductImage");
				btnInsertProduct = (Button) productProductManagementPage.lookup("#btnInsertProduct");
				btnDetailProduct = (Button) productProductManagementPage.lookup("#btnDetailProduct");
				btnUpdateProduct = (Button) productProductManagementPage.lookup("#btnUpdateProduct");
				btnDeleteProduct = (Button) productProductManagementPage.lookup("#btnDeleteProduct");
				

				// 카테고리. 리스트 초기화.
				categoryList = lvCategoryList.getItems(); // 연결.
				categoryList.setAll(AdminMainPageController.getMainPageController().getProductManager().getCategoryNameList());
				lvCategoryList.setItems(categoryList);
				lvCategoryList.getItems().addFirst("All");	// 리스트 맨 앞에 모든 카테고리의 상품을 보여주기 위한 "All" 추가.
				
				// 테이블 뷰 컬럼 초기화.
				tvProductList.getColumns().setAll(new ProductDisplayTableData().getColumns());
				
				// 리스트뷰 changeListener 추가.
				lvCategoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
						System.out.println("category list changed :: " + oldValue + " -> " + newValue);
						
						tableDataList = new LinkedList<ProductTableData>();
						// 카테고리 선택(변경) 시 해당 카테고리에 맞게 테이블 뷰 설정.
						if (newValue != null) {
							if (newValue.equals("All")) {
								for (Product product : AdminMainPageController.getMainPageController().getProductManager().getProductList()) {
									tableDataList.add(new ProductTableData(product.getId(), product.getName(), product.getPrice() , product.getStockCount()));
								}
							} else {
								for (Product product : AdminMainPageController.getMainPageController().getProductManager().findProductsByCategory(newValue)) {
									tableDataList.add(new ProductTableData(product.getId(), product.getName(), product.getPrice(), product.getStockCount()));
								}
							}
						}
						tvProductList.getItems().setAll(tableDataList);
						
						tvProductList.getSelectionModel().clearSelection();
						tvProductList.getSelectionModel().selectFirst();
					}
				});
				
				// 테이블뷰 changeListener 추가.
				tvProductList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object oldValue, Object newValue) {
						System.out.println("product table changed ::: " + oldValue + " -> " + newValue);
						
						// 테이블 선택(변경) 시 해당 상품에 맞게 정보 변경.
						if (newValue != null) {
							ivProductImage.setImage(AdminMainPageController.getMainPageController().getProductManager().findProductById(((ProductTableData)newValue).idProperty().intValue()).getImage());
						}
						else {
							ivProductImage.setImage(new Image("https://placehold.co/100x100.png"));
						}
					}
				});
				
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success productProductManagementPage");
			} catch (Exception e) {
				System.out.println("!(ProductProductManagementPageController-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(ProductProductManagementPageController-Exception) : " + e.getMessage());
			}
		} // ~ if (productProductManagementPage == null).
		
		
		///////////////////////
		
		// 리스트에서 제일 앞의 요소("All") 선택.
		lvCategoryList.getSelectionModel().clearSelection();
		lvCategoryList.getSelectionModel().selectFirst();
		
		// 테이블에서 제일 앞의 요소 선택.
		tvProductList.getSelectionModel().clearSelection();
		tvProductList.getSelectionModel().selectFirst();
		
		// 버튼 액션 설정.
		btnInsertProduct.setOnAction(event -> addProduct(lvCategoryList.getSelectionModel().getSelectedItem().toString()));
		btnDetailProduct.setOnAction(event -> System.out.println("상품 상세 조회 버튼 클릭!"));
		btnUpdateProduct.setOnAction(event -> updateProduct(tvProductList.getSelectionModel().getSelectedItem()));
		btnDeleteProduct.setOnAction(event -> deleteProduct(tvProductList.getSelectionModel().getSelectedItem()));
		
		System.out.println("return productProductManagementPage");
		return productProductManagementPage;
		
	}
	
	public void addProduct(String selectedItemName) {
		if (selectedItemName != null) {
			ProductAddDialogController addDialogController = new ProductAddDialogController();
			addDialogController.makeProductAddDialog(selectedItemName);
		}		
	}
	public void updateProduct(Object selectedItem) {
		if (selectedItem instanceof ProductTableData) {
			int productId = ((ProductTableData) selectedItem).idProperty().intValue();
			
			ProductUpdateDialogController updateDialogController = new ProductUpdateDialogController();
			updateDialogController.makeProductUpdateDialog(productId);
		}
	}
	public void deleteProduct(Object selectedItem) {
		if (selectedItem instanceof ProductTableData) {
			int productId = ((ProductTableData) selectedItem).idProperty().intValue();
			
			ProductDeleteDialogController deleteDialogController = new ProductDeleteDialogController();
			deleteDialogController.makeProductDeleteDialog(productId);
		}
		
//		tvProductList.getSelectionModel().clearSelection();
//		AdminMainPageController.getMainPageController().getProductManager().deleteProdut(
//				AdminMainPageController.getMainPageController().getProductManager().findProductByJd(productId)
//				);
//		
//		AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProductManagementPageController().getProductProductManagementPage();
	}
	
}
