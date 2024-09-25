package application.admin.product;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.option.Option;
import application.admin.etc.product.Product;
import application.admin.etc.table.LinkProductDisplayTableData;
import application.admin.etc.table.LinkProductTableData;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import application.admin.product.link.LinkOptionDialogController;
import application.admin.product.link.UnlinkOptionDialogController;

public class ProductProdOptLinkManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent productProdOptLinkManagementPage = null;
	
	ListView<String> lvCategoryList;
	TableView<?> tvProductList;
	ListView<String> lvLinkedOptionList;
	Label lbProductName;
	Button btnLinkOption;
	Button btnUnlinkOption;
	Button btnSave;

	ObservableList<String> categoryList;
	List productTableDataList;
	ObservableList<String> linkedOptionList;
	
	public Parent getProductProdOptLinkManagementPage() {
		if (productProdOptLinkManagementPage == null) {
			try {
				System.out.println("init productProdOptLinkManagementPage");
				// 페이지 가져오기. 
				productProdOptLinkManagementPage = FXMLLoader.load(getClass().getResource("ProductProdOptLinkManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(productProdOptLinkManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				lvCategoryList = (ListView<String>) productProdOptLinkManagementPage.lookup("#lvCategoryList");
				tvProductList = (TableView<?>) productProdOptLinkManagementPage.lookup("#tvProductList");
				lvLinkedOptionList = (ListView<String>) productProdOptLinkManagementPage.lookup("#lvLinkedOptionList");
				lbProductName = (Label) productProdOptLinkManagementPage.lookup("#lbProductName");
				btnLinkOption = (Button) productProdOptLinkManagementPage.lookup("#btnLinkOption");
				btnUnlinkOption= (Button) productProdOptLinkManagementPage.lookup("#btnUnlinkOption");
				btnSave = (Button) productProdOptLinkManagementPage.lookup("#btnSave");
				
				
				// 상품 테이블 뷰 컬럼 초기화.
				tvProductList.getColumns().setAll(new LinkProductDisplayTableData().getColumns());
				
				// categoryList 와 lvCategoryList 연결.
				categoryList = lvCategoryList.getItems();
				
				// linkedOptionList 와 lvLinkedOptionList 연결.
				linkedOptionList = lvLinkedOptionList.getItems();
				
				
				// 상품 카테고리 리스트뷰 changeListener 추가.
				lvCategoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
						System.out.println("optlink-category list changed :: " + oldValue + " -> " + newValue);
						
						productTableDataList = new LinkedList<LinkProductTableData>();
						// 카테고리 선택(변경) 시 해당 카테고리에 맞게 상품 리스트뷰 설정.
						if (newValue != null) {
							if (newValue.equals("All")) {
								for (Product product : AdminMainPageController.getMainPageController().getProductManager().getProductList()) {
									productTableDataList.add(new LinkProductTableData(product.getId(), product.getName()));
								}
							} else {
								for (Product product : AdminMainPageController.getMainPageController().getProductManager().findProductsByCategory(newValue)) {
									productTableDataList.add(new LinkProductTableData(product.getId(), product.getName()));
								}
							}
						}
						tvProductList.getItems().setAll(productTableDataList);
						
						tvProductList.getSelectionModel().clearSelection();
						tvProductList.getSelectionModel().selectFirst();
					}
				});

				// 상품 테이블뷰 changeListener 추가.
				tvProductList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object oldValue, Object newValue) {
						System.out.println("optlink-product list changed :: " + (LinkProductTableData)oldValue + " -> " + (LinkProductTableData)newValue);
						
						// 상품 선택(변경) 시 해당 상품에 맞게 연결된 옵션 리스트뷰 설정.
						if (newValue != null) {
							// 리스트뷰 위의 상품명 레이블에 선택한 상품의 이름으로 텍스트 설정.
							lbProductName.setText(((LinkProductTableData)newValue).nameProperty().get());
							
							// 리스트뷰 설정.
							// 선택한 상품 id 가져오기. = 테이블의 id 값.
							int selectedProductId = ((LinkProductTableData)newValue).idProperty().intValue();
							// id 로 상품 가져옴.
							Product selectedProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(selectedProductId);
							// 선택한 상품의 옵션 리스트(id 리스트임.) 가져옴.
							
							// 리스트에 추가할 새로운 연결된 옵션 리스트.
							LinkedList<String> newLinkedOptionNameList = new LinkedList<String>();
							System.out.println(selectedProduct + " : 연결된 옵션들 -> " + selectedProduct.getLinkedOptionList());
							for (Integer optionId : selectedProduct.getLinkedOptionList()) {
								// 연결된 옵션 id 로 옵션 정보 가져오고, 해당 옵션의 이름을 리스트에 추가.
								Option curOption = AdminMainPageController.getMainPageController().getOptionManager().findOptionById(optionId);
								if (curOption != null) {
									newLinkedOptionNameList.add(curOption.getName());
								}
							}
							
							linkedOptionList.setAll(newLinkedOptionNameList);
							lvLinkedOptionList.setItems(linkedOptionList);
							
							lvLinkedOptionList.getSelectionModel().clearSelection();
							lvLinkedOptionList.getSelectionModel().selectFirst();
						}
					}
				});
				
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success productProdOptLinkManagementPage");
			} catch (IOException e) {
				System.out.println("!(productProdOptLinkManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(productProdOptLinkManagementPage-IOException) : " + e.getMessage());
			}
		} // ~ if null;
		
		////////////////////////////////////
		// 레이블 텍스트 초기화.
		lbProductName.setText("");
		
		// 상품 카테고리. 리스트 초기화.
		categoryList = lvCategoryList.getItems();
		categoryList.setAll(AdminMainPageController.getMainPageController().getProductManager().getCategoryNameList());
		lvCategoryList.setItems(categoryList);
		lvCategoryList.getItems().addFirst("All");	// 리스트 맨 앞에 모든 카테고리의 상품을 보여주기 위한 "All" 추가.

		// 상품 카테고리 리스트에서 제일 앞의 요소("All") 선택.
		lvCategoryList.getSelectionModel().clearSelection();
		lvCategoryList.getSelectionModel().selectFirst();
		
		// 상품 테이블에서 제일 앞의 요소 선택.
		tvProductList.getSelectionModel().clearSelection();
		tvProductList.getSelectionModel().selectFirst();
		
		//연결된 옵션 리스트에서 제일 앞의 요소 선택.
		lvLinkedOptionList.getSelectionModel().clearSelection();
		lvLinkedOptionList.getSelectionModel().selectFirst();
		
		
		
		// 버튼 액션 설정.
		btnLinkOption.setOnAction(event -> linkOption(tvProductList.getSelectionModel().getSelectedItem()));
		btnUnlinkOption.setOnAction(event -> unLinkOption(tvProductList.getSelectionModel().getSelectedItem()));
		
		System.out.println("return productProdOptLinkManagementPage");
		return productProdOptLinkManagementPage;
		
	}
	
	
	public void linkOption(Object selectedItem) {
		if (selectedItem instanceof LinkProductTableData) {
			int productId = ((LinkProductTableData) selectedItem).idProperty().intValue();
			
			LinkOptionDialogController linkOptionDialogController = new LinkOptionDialogController();
			linkOptionDialogController.makeLinkOptionDialog(productId);
		}
	}
	public void unLinkOption(Object selectedItem) {
		if (selectedItem instanceof LinkProductTableData) {
			int productId = ((LinkProductTableData) selectedItem).idProperty().intValue();
			
			UnlinkOptionDialogController unlinkOptionDialogController = new UnlinkOptionDialogController();
			unlinkOptionDialogController.makeUnlinkOptionDialog(productId);
		}
	}
}
