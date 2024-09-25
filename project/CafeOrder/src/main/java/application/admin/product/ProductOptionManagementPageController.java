package application.admin.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.etc.option.SubOption;
import application.admin.etc.product.Product;
import application.admin.etc.table.OptionDisplayTableData;
import application.admin.etc.table.OptionTableData;
import application.admin.etc.table.ProductDisplayTableData;
import application.admin.etc.table.ProductTableData;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import application.admin.product.option.OptionAddDialogController;
import application.admin.product.option.OptionDeleteDialogController;
import application.admin.product.option.OptionUpdateDialogController;
import application.admin.product.option.SubOptionAddDialogController;
import application.admin.product.option.SubOptionDeleteDialogController;
import application.admin.product.option.SubOptionUpdateDialogController;
import application.admin.product.option.SubOptionViewDetailDialogController;
import application.admin.product.product.ProductUpdateDialogController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProductOptionManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent productOptionManagementPage = null;

	ListView<String> lvOptionList;
	Button btnInsertOption;
	Button btnUpdateOption;
	Button btnDeleteOption;
	
	TableView<?> tvSubOptionList;
	ImageView ivSubOptionImage;
	
	Button btnInsertSubOption;
	Button btnDetailSubOption;
	Button btnUpdateSubOption;
	Button btnDeleteSubOption;
	
	ObservableList<String> optionList;
	List tableDataList;
	
	
	public Parent getProductOptionManagementPage() {
		if (productOptionManagementPage == null) {
			try {
				System.out.println("init productOptionManagementPage");
				// 페이지 가져오기. 
				productOptionManagementPage = FXMLLoader.load(getClass().getResource("ProductOptionManagementPage.fxml"));

				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(productOptionManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				lvOptionList = (ListView<String>) productOptionManagementPage.lookup("#lvOptionList");
				btnInsertOption = (Button) productOptionManagementPage.lookup("#btnInsertOption");
				btnUpdateOption = (Button) productOptionManagementPage.lookup("#btnUpdateOption");
				btnDeleteOption = (Button) productOptionManagementPage.lookup("#btnDeleteOption");
				tvSubOptionList = (TableView<?>) productOptionManagementPage.lookup("#tvSubOptionList");
				ivSubOptionImage = (ImageView) productOptionManagementPage.lookup("#ivSubOptionImage");
				btnInsertSubOption = (Button) productOptionManagementPage.lookup("#btnInsertSubOption");
				btnDetailSubOption = (Button) productOptionManagementPage.lookup("#btnDetailSubOption");
				btnUpdateSubOption = (Button) productOptionManagementPage.lookup("#btnUpdateSubOption");
				btnDeleteSubOption = (Button) productOptionManagementPage.lookup("#btnDeleteSubOption");
				
				// 테이블 뷰 컬럼 초기화.
				tvSubOptionList.getColumns().setAll(new OptionDisplayTableData().getColumns());
				
				// optionList 와 lvOptionList 연결.
				optionList = lvOptionList.getItems();
				
				// 리스트뷰 changeListener 추가.
				lvOptionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
						System.out.println("option list changed :: " + oldValue + " -> " + newValue);
						
						tableDataList = new LinkedList<OptionTableData>();
						// 옵션 선택(변경) 시 해당 옵션에 맞게 테이블 뷰 설정.
						if (newValue != null) {
							if (newValue.equals("All")) {
								for (SubOption subOption : AdminMainPageController.getMainPageController().getOptionManager().getSubOptionList()) {
									tableDataList.add(new OptionTableData(subOption.getId(), subOption.getName(), subOption.getPrice() , subOption.getStockCount()));
								}
							} else {
								for (SubOption subOption : AdminMainPageController.getMainPageController().getOptionManager().findSubOptionsByOption(newValue)) {
									tableDataList.add(new OptionTableData(subOption.getId(), subOption.getName(), subOption.getPrice(), subOption.getStockCount()));
								}
							}
						}
						tvSubOptionList.getItems().setAll(tableDataList);
						
						tvSubOptionList.getSelectionModel().clearSelection();
						tvSubOptionList.getSelectionModel().selectFirst();
					}
				});
				
				// 테이블뷰 changeLister 추가.
				tvSubOptionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object> () {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object oldValue, Object newValue) {
						System.out.println("subOption table changed ::: " + oldValue + " -> " + newValue);

						// 테이블 선택(변경) 시 해당 상품에 맞게 정보 변경.
						if (newValue != null) {
							ivSubOptionImage.setImage(AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(((OptionTableData)newValue).idProperty().intValue()).getImage());
						}
						else {
							ivSubOptionImage.setImage(new Image("https://placehold.co/100x100.png"));
						}
					}
				});
				
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success productOptionManagementPage");
			} catch (Exception e) {
				System.out.println("!(ProductOptionManagementPageController-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(ProductOptionManagementPageController-Exception) : " + e.getMessage());
			}
		} // ~ if (productoptionManagementPage == null).
		
		////////////////////////////

		// 옵션 리스트 초기화.
		optionList = lvOptionList.getItems();
		optionList.setAll(AdminMainPageController.getMainPageController().getOptionManager().getOptionNameList());
		lvOptionList.setItems(optionList);
		lvOptionList.getItems().addFirst("All");	// 리스트 맨 앞에 모든 옵션의 하위 옵션을 보여주기 위한 "All" 추가.
		
		// 리스트에서 제일 앞의 요소("All") 선택.
		lvOptionList.getSelectionModel().clearSelection();
		lvOptionList.getSelectionModel().selectFirst();
		
		// 테이블에서 제일 앞의 요소 선택.
		tvSubOptionList.getSelectionModel().clearSelection();
		tvSubOptionList.getSelectionModel().selectFirst();
		
		
		// 버튼 액션 설정.
		btnInsertOption.setOnAction(event -> addOption());
		btnUpdateOption.setOnAction(event -> updateOption(lvOptionList.getSelectionModel().getSelectedItem().toString()));
		btnDeleteOption.setOnAction(event -> deleteOption(lvOptionList.getSelectionModel().getSelectedItem().toString()));
		
		btnInsertSubOption.setOnAction(event -> addSubOption(lvOptionList.getSelectionModel().getSelectedItem().toString()));
		btnUpdateSubOption.setOnAction(event -> updateSubOption(tvSubOptionList.getSelectionModel().getSelectedItem()));
		btnDeleteSubOption.setOnAction(event -> deleteSubOption(tvSubOptionList.getSelectionModel().getSelectedItem()));
		btnDetailSubOption.setOnAction(event -> viewDetailSubOption(tvSubOptionList.getSelectionModel().getSelectedItem()));
		
		System.out.println("return productOptionManagementPage");
		return productOptionManagementPage;
		
	}
	
	
	public void addOption() {
		// 옵션 추가.
		OptionAddDialogController addDialogController = new OptionAddDialogController();
		addDialogController.makeOptionAddDialog();
	}
	public void updateOption(String selectedOptionName) {
		// 옵션 수정/변경.
		if (selectedOptionName != null) {
			OptionUpdateDialogController updateDialogController = new OptionUpdateDialogController();
			updateDialogController.makeOptionUpdateDialog(selectedOptionName);
		}
	}
	public void deleteOption(String selectedOptionName) {
		// 옵션 삭제.
		if (selectedOptionName != null) {
			OptionDeleteDialogController deleteDialogController = new OptionDeleteDialogController();
			deleteDialogController.makeProductDeleteDialog(selectedOptionName);
		}
	}
	
	public void addSubOption(String selectedOptionName) {
		// 하위옵션 추가.
		SubOptionAddDialogController addDialogController = new SubOptionAddDialogController();
		addDialogController.makeSubOptionAddDialog(selectedOptionName);
	}
	public void updateSubOption(Object selectedItem) {
		// 하위옵션 수정/변경.
		if (selectedItem instanceof OptionTableData) {
			int subOptionId = ((OptionTableData) selectedItem).idProperty().intValue();
			
			SubOptionUpdateDialogController updateDialogController = new SubOptionUpdateDialogController();
			updateDialogController.makeSubOptionUpdateDialog(subOptionId);
		}
	}
	public void deleteSubOption(Object selectedItem) {
		// 하위옵션 삭제.
		if (selectedItem instanceof OptionTableData) {
			int subOptionId = ((OptionTableData) selectedItem).idProperty().intValue();
			
			SubOptionDeleteDialogController deleteDialogController = new SubOptionDeleteDialogController();
			deleteDialogController.makeSubOptionDeleteDialog(subOptionId);
		}
	}
	public void viewDetailSubOption(Object selectedItem) {
		// 하위옵션 상세 보기.
		if (selectedItem instanceof OptionTableData) {
			int subOptionId = ((OptionTableData) selectedItem).idProperty().intValue();
			
			SubOptionViewDetailDialogController viewDetailDialogController = new SubOptionViewDetailDialogController();
			viewDetailDialogController.makeSubOptionViewDetailDialog(subOptionId);
		}		
	}
	
	
}
