package application.admin.product.option;

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
import application.admin.etc.option.SubOption;
import application.admin.etc.product.Product;
import application.admin.etc.product.ProductCategory;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class SubOptionAddDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeSubOptionAddDialog(String selectedItemName) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SubOptionAddDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			TextField tfSubOptionId = (TextField) parent.lookup("#tfSubOptionId");
			TextField tfSubOptionName = (TextField) parent.lookup("#tfSubOptionName");
			Spinner<Integer> spnSubOptionPrice = (Spinner<Integer>) parent.lookup("#spnSubOptionPrice");
			Spinner<Integer> spnSubOptionStock = (Spinner<Integer>) parent.lookup("#spnSubOptionStock");
			ComboBox<String> cbOptionCategory = (ComboBox<String>) parent.lookup("#cbOptionCategory");
			ImageView ivSubOptionImage = (ImageView) parent.lookup("#ivSubOptionImage");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnUploadImage = (Button) parent.lookup("#btnUploadImage");
			Button btnAddSubOption = (Button) parent.lookup("#btnAddSubOption");
			BorderPane bpKeypad = (BorderPane) parent.lookup("#bpKeypad");
			
			tfSubOptionId.setPromptText("0");
			tfSubOptionId.focusedProperty().addListener(e -> linkKeypad(tfSubOptionId.isFocused(), bpKeypad, tfSubOptionId, false));
			
			// 옵션 가격 스피너 설정.
			IntegerSpinnerValueFactory priceValueFactory = new IntegerSpinnerValueFactory(0, 100000000, 0, 100); // 최소. 최대. 기본. 스텝.
		    spnSubOptionPrice.setValueFactory(priceValueFactory);
		    spnSubOptionPrice.setEditable(true);
			// 옵션 가격 스피너 키패드 연결.
		    spnSubOptionPrice.getEditor().focusedProperty().addListener(e -> linkKeypad(spnSubOptionPrice.getEditor().isFocused(), bpKeypad, spnSubOptionPrice.getEditor(), true));
		    
		    // 옵션 재고 스피너 설정.
			IntegerSpinnerValueFactory stockValueFactory = new IntegerSpinnerValueFactory(0, 9999, 1, 1); // 최소. 최대. 기본. 스텝.
			spnSubOptionStock.setValueFactory(stockValueFactory);
		    spnSubOptionStock.setEditable(true);
			// 옵션 재고 스피너 키패드 연결.
		    spnSubOptionStock.getEditor().focusedProperty().addListener(e -> linkKeypad(spnSubOptionStock.getEditor().isFocused(), bpKeypad, spnSubOptionStock.getEditor(), true));
		    
		    // 카테고리 콤보박스 설정.
			ObservableList<String> optionCategoryList = cbOptionCategory.getItems();
			// 카테고리 이름 리스트 가져옴.
			LinkedList<String> optionNameList = AdminMainPageController.getMainPageController().getOptionManager().getOptionNameList();
			// 콤보박스 요소를 카테고리 리스트로 설정하기.
			optionCategoryList.setAll(optionNameList);
			// 콤보박스 기본값 선택한 카테고리 값으로 설정하기.
			// 선택한 카테고리 이름.
			if (selectedItemName.equals("All")) {
				// 만약 "All" 선택했으면, 처음 값(Size).
				cbOptionCategory.setValue(optionNameList.getFirst());
			} else {
				cbOptionCategory.setValue(selectedItemName);
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
							ivSubOptionImage.setImage(previewImg);							
						} catch (FileNotFoundException e) {
							System.out.println("!btnUploadImage : " + e.getMessage());
							e.printStackTrace();
							System.out.println("~!btnUploadImage : " + e.getMessage());
						}
					}
				}
			});
			// 옵션 등록 버튼.
			btnAddSubOption.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent arg0) {
					// 필수 입력/형식 에러 처리. 
					StringBuilder sb = new StringBuilder();
					boolean isError = false;
					
					try {
						// 옵션 id 가 숫자가 아니면 예외처리 됨.
						Integer.valueOf(tfSubOptionId.getText());
						
						if (tfSubOptionId.getText().equals("") || tfSubOptionId.getText().isBlank()) {
							isError = true;
							sb.append("하위옵션 id가 입력되지 않았습니다.\n");
						}
						if (AdminMainPageController.getMainPageController().getProductManager().findProductById( Integer.valueOf(tfSubOptionId.getText()) ) != null ) {
							isError = true;
							sb.append("이미 존재하는 하위옵션 id 입니다.\n");
						}
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("하위옵션 id 는 숫자로 입력해주세요.\n");
					}
					if (tfSubOptionName.getText().equals("") || tfSubOptionName.getText().isBlank()) {
						isError = true;
						sb.append("하위옵션명이 입력되지 않았습니다.\n");
					}
					try {
						// 옵션 가격이 숫자가 아니면 예외처리 됨.
						Integer.valueOf(spnSubOptionPrice.getEditor().getText());

						if (spnSubOptionPrice.getValue() == null || spnSubOptionPrice.getValue().toString().isBlank()) {
							isError = true;
							sb.append("하위옵션의 가격이 입력되지 않았습니다.\n");
						}
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("하위옵션 가격은 숫자로 입력해주세요.\n");
					}
					try {
						// 옵션 재고가 숫자가 아니면 예외처리 됨.
						Integer.valueOf(spnSubOptionStock.getEditor().getText());
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("하위옵션 재고 수량은 숫자로 입력해주세요.\n");
					}
					if (spnSubOptionPrice.getValue() == null || spnSubOptionPrice.getValue().toString().isBlank()) {
						isError = true;
						sb.append("옵션 카테고리가 선택되지 않았습니다.\n");
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
						int id = Integer.valueOf(tfSubOptionId.getText());
						String name = tfSubOptionName.getText();
						int price = spnSubOptionPrice.getValue().intValue();
						int stockCount = spnSubOptionStock.getValue().intValue();
						int optionId = AdminMainPageController.getMainPageController().getProductManager().findCategoryIdByName(cbOptionCategory.getValue().toString());
						
						// 새로운 옵션을 만들고,
						SubOption newOption = new SubOption(id, name, price, stockCount, optionId);
						newOption.setIsStockOut(false);
						
						// 옵션 추가 처리.
						AdminMainPageController.getMainPageController().getOptionManager().addSubOption(newOption);
						
						// 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
						
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
