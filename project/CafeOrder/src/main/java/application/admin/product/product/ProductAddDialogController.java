package application.admin.product.product;

import java.util.LinkedList;
import java.awt.Dialog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.AlarmDialog;
import application.admin.etc.product.Product;
import application.admin.etc.product.ProductCategory;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class ProductAddDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeProductAddDialog(String selectedItemName) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("ProductAddDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			TextField tfProductId = (TextField) parent.lookup("#tfProductId");
			TextField tfProductName = (TextField) parent.lookup("#tfProductName");
			Spinner<Integer> spnProductPrice = (Spinner<Integer>) parent.lookup("#spnProductPrice");
			Spinner<Integer> spnProductStock = (Spinner<Integer>) parent.lookup("#spnProductStock");
			ComboBox<String> cbProductCategory = (ComboBox<String>) parent.lookup("#cbProductCategory");
			ImageView ivProductImage = (ImageView) parent.lookup("#ivProductImage");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnUploadImage = (Button) parent.lookup("#btnUploadImage");
			Button btnAddProduct = (Button) parent.lookup("#btnAddProduct");
			BorderPane bpKeypad = (BorderPane) parent.lookup("#bpKeypad");
			
			tfProductId.setPromptText("0");
			tfProductId.focusedProperty().addListener(e -> linkKeypad(tfProductId.isFocused(), bpKeypad, tfProductId, false));
			
			// 상품 가격 스피너 설정.
			IntegerSpinnerValueFactory priceValueFactory = new IntegerSpinnerValueFactory(0, 100000000, 0, 100); // 최소. 최대. 기본. 스텝.
		    spnProductPrice.setValueFactory(priceValueFactory);
		    spnProductPrice.setEditable(true);
			// 상품 가격 스피너 키패드 연결.
		    spnProductPrice.getEditor().focusedProperty().addListener(e -> linkKeypad(spnProductPrice.getEditor().isFocused(), bpKeypad, spnProductPrice.getEditor(), true));
		    
		    // 상품 재고 스피너 설정.
			IntegerSpinnerValueFactory stockValueFactory = new IntegerSpinnerValueFactory(0, 9999, 1, 1); // 최소. 최대. 기본. 스텝.
			spnProductStock.setValueFactory(stockValueFactory);
		    spnProductStock.setEditable(true);
			// 상품 재고 스피너 키패드 연결.
		    spnProductStock.getEditor().focusedProperty().addListener(e -> linkKeypad(spnProductStock.getEditor().isFocused(), bpKeypad, spnProductStock.getEditor(), true));
		    
		    // 카테고리 콤보박스 설정.
			ObservableList<String> productCategoryList = cbProductCategory.getItems();
			// 카테고리 이름 리스트 가져옴.
			LinkedList<String> categoryNameList = AdminMainPageController.getMainPageController().getProductManager().getCategoryNameList();
			// 콤보박스 요소를 카테고리 리스트로 설정하기.
			productCategoryList.setAll(categoryNameList);
			// 콤보박스 기본값 선택한 카테고리 값으로 설정하기.
			// 선택한 카테고리 이름.
			if (selectedItemName.equals("All")) {
				// 만약 "All" 선택했으면, 처음 값(커피).
				cbProductCategory.setValue(categoryNameList.getFirst());
			} else {
				cbProductCategory.setValue(selectedItemName);
			}
			
			
			// 버튼 액션 설정.
			// 닫기 버튼.
			btnExit.setOnAction(e -> dialog.close());
			// 이미지 등록 버튼.
			btnUploadImage.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// 파일 선택창 열기.
					FileChooser fileChooser = new FileChooser();
					// 이미지로 확장자(jpg, png, gif)로 제한.
					fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
						);
					File selectedFile = fileChooser.showOpenDialog(dialog);
					if (selectedFile != null) {
						System.out.println(selectedFile.getPath());
						try {
							// 가져온 이미지 파일을 읽고,
							FileInputStream fis = new FileInputStream(selectedFile);
							BufferedInputStream bis = new BufferedInputStream(fis);
							Image previewImg = new Image(bis);
							// 이미지뷰에 띄움.
							ivProductImage.setImage(previewImg);							
						} catch (FileNotFoundException e) {
							System.out.println("!btnUploadImage : " + e.getMessage());
							e.printStackTrace();
							System.out.println("~!btnUploadImage : " + e.getMessage());
						}
					}
				}
			});
			// 상품 등록 버튼.
			btnAddProduct.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent arg0) {
					// 필수 입력/형식 에러 처리. 
					StringBuilder sb = new StringBuilder();
					boolean isError = false;
					
					try {
						// 상품 id 가 숫자가 아니면 예외처리 됨.
						Integer.valueOf(tfProductId.getText());
						
						if (tfProductId.getText() == null || tfProductId.getText().equals("") || tfProductId.getText().isBlank()) {
							isError = true;
							sb.append("상품 id가 입력되지 않았습니다.\n");
						}
						if (AdminMainPageController.getMainPageController().getProductManager().findProductById( Integer.valueOf(tfProductId.getText()) ) != null ) {
							isError = true;
							sb.append("이미 존재하는 상품 id 입니다.\n");
						}
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("상품 id 는 숫자로 입력해주세요.\n");
					}
					if (tfProductName.getText() == null || tfProductName.getText().equals("") || tfProductName.getText().isBlank()) {
						isError = true;
						sb.append("상품명이 입력되지 않았습니다.\n");
					}
					try {
						// 상품 가격이 숫자가 아니면 예외처리 됨.
						Integer.valueOf(spnProductPrice.getEditor().getText());

						if (spnProductPrice.getValue() == null || spnProductPrice.getValue().toString().isBlank()) {
							isError = true;
							sb.append("상품의 가격이 입력되지 않았습니다.\n");
						}
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("상품 가격은 숫자로 입력해주세요.\n");
					}
					try {
						// 상품 재고가 숫자가 아니면 예외처리 됨.
						Integer.valueOf(spnProductStock.getEditor().getText());
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("상품 재고 수량은 숫자로 입력해주세요.\n");
					}
					if (cbProductCategory.getValue() == null || cbProductCategory.getValue().toString().isBlank()) {
						isError = true;
						sb.append("상품 카테고리가 선택되지 않았습니다.\n");
					}
					
					// 입력이 부족하거나, 형식이 맞지 않으면,
					if (isError) {
						// alarmDialog 띄우고,
						AlarmDialog alarmDialog = new AlarmDialog();
						alarmDialog.setPrimaryStage(dialog);
						alarmDialog.makeAlarmDialog(null, sb.toString());
						// 리턴.
						return;
					}
					else {
						// 새로운 상품을 만들고,
						Product newProduct = new Product();
						// id.
						newProduct.setId(Integer.valueOf(tfProductId.getText()));
						// name.
						newProduct.setName(tfProductName.getText());
						// price.
						newProduct.setPrice(spnProductPrice.getValue().intValue());
						// stockCount.
						newProduct.setStockCount(spnProductStock.getValue().intValue());
						// isStockOut.
						newProduct.setIsStockOut(false);
						// category.
						newProduct.setCategoryId(AdminMainPageController.getMainPageController().getProductManager().findCategoryIdByName(cbProductCategory.getValue().toString()));
						// image.
						newProduct.setImage(ivProductImage.getImage());
						
						// 상품 추가 처리.
						AdminMainPageController.getMainPageController().getProductManager().addProduct(newProduct);
						
						// 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProductManagementPageController().getProductProductManagementPage();
						
						// 창 닫기.
						btnExit.fire();
					}
					
				}
			});
			
		} catch (Exception e) {
			System.out.println("!ProductAddDialogController : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!ProductAddDialogController : " + e.getMessage());
		}
		
	}
	
	// 텍스트필드 선택되었을 때 키패드 연결.
	public void linkKeypad(boolean isFocused , BorderPane bpKeypad, TextField textField, boolean isSpinner) {
		if (isFocused) {
			bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(textField, isSpinner));
		} else {
			bpKeypad.setCenter(null);
		}
	}
}
