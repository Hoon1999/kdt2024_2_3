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
import application.admin.etc.table.ProductTableData;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class SubOptionUpdateDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeSubOptionUpdateDialog(int subOptionId) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		SubOption selectedSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(subOptionId);
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SubOptionUpdateDialog.fxml"));
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
			Button btnUpdateSubOption = (Button) parent.lookup("#btnUpdateSubOption");
			BorderPane bpKeypad = (BorderPane) parent.lookup("#bpKeypad");
			
			// 하위옵션 id 는 변경 불가.
			tfSubOptionId.setEditable(false);
			tfSubOptionId.setDisable(true);
			// 하위옵션 id 기본값 선택한 하위옵션 id 로 설정하기.
			tfSubOptionId.setText(String.valueOf(selectedSubOption.getId()));
			
			// 하위옵션명 기본값 선택한 하위옵션명으로 설정하기.
			tfSubOptionName.setText(selectedSubOption.getName());
			
			// 하위옵션 가격 스피너 설정.
			IntegerSpinnerValueFactory priceValueFactory = new IntegerSpinnerValueFactory(0, 100000000, 0, 100); // 최소. 최대. 기본. 스텝.
		    spnSubOptionPrice.setValueFactory(priceValueFactory);
		    spnSubOptionPrice.setEditable(true);
			// 하위옵션 가격 스피너 키패드 연결.
		    spnSubOptionPrice.getEditor().focusedProperty().addListener(e -> linkKeypad(spnSubOptionPrice.getEditor().isFocused(), bpKeypad, spnSubOptionPrice.getEditor(), true));
		    // 하위옵션 가격 기본값 선택한 하위옵션 가격으로 설정하기.
		    spnSubOptionPrice.getValueFactory().setValue(selectedSubOption.getPrice());
		    
		    // 하위옵션 재고 스피너 설정.
			IntegerSpinnerValueFactory stockValueFactory = new IntegerSpinnerValueFactory(0, 9999, 1, 1); // 최소. 최대. 기본. 스텝.
			spnSubOptionStock.setValueFactory(stockValueFactory);
		    spnSubOptionStock.setEditable(true);
			// 하위옵션 재고 스피너 키패드 연결.
		    spnSubOptionStock.getEditor().focusedProperty().addListener(e -> linkKeypad(spnSubOptionStock.getEditor().isFocused(), bpKeypad, spnSubOptionStock.getEditor(), true));
		    // 하위옵션 재고 기본값 선택한 하위옵션 재고로 설정하기.
		    spnSubOptionStock.getValueFactory().setValue(selectedSubOption.getStockCount());
		    
		    // 카테고리 콤보박스 설정.
			ObservableList<String> optionCategoryList = cbOptionCategory.getItems();
			// 카테고리 이름 리스트 가져옴.
			LinkedList<String> optionNameList = AdminMainPageController.getMainPageController().getOptionManager().getOptionNameList();
			// 콤보박스 요소를 카테고리 리스트로 설정하기.
			optionCategoryList.setAll(optionNameList);
			// 콤보박스 기본값 선택한 옵션 카테고리로 설정하기.
			cbOptionCategory.setValue(AdminMainPageController.getMainPageController().getOptionManager().findOptionNameById(selectedSubOption.getOptionId()));
			
			
			// 하위옵션 이미지 기본값 선택한 하위옵션 이미지로 설정하기.
			ivSubOptionImage.setImage(selectedSubOption.getImage());
			
////////////////////////////////////////////////////////////////////////////			
//			((ProductTableData)tvProductList.getSelectionModel().getSelectedItem()).idProperty().toString()
			
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
			// 하위옵션 수정/변경 버튼.
			btnUpdateSubOption.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent arg0) {
					// 필수 입력 처리. 
					StringBuilder sb = new StringBuilder();
					boolean isError = false;
					
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
					
					if (isError) {
						AlarmDialog alarmDialog = new AlarmDialog();
						alarmDialog.setPrimaryStage(dialog);
						alarmDialog.makeAlarmDialog(null, sb.toString());
						return;
					}
					else {
						int id = Integer.valueOf(tfSubOptionId.getText());
						String name = tfSubOptionName.getText();
						int price = spnSubOptionPrice.getValue().intValue();
						int stockCount = spnSubOptionStock.getValue().intValue();
						int optionId = AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName(cbOptionCategory.getValue());
						Image image = ivSubOptionImage.getImage();
						
						// 변경할 옵션을 만들고,
						SubOption newSubOption = new SubOption(id, name, price, stockCount, optionId, image);
						
						// 하위옵션 수정 처리.
						AdminMainPageController.getMainPageController().getOptionManager().updateSubOption(newSubOption);
						
						// 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
						
						// 창 닫기.
						btnExit.fire();
					}
					
				}
			});
			
		} catch (Exception e) {
			System.out.println("!SubOptionUpdateDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!SubOptionUpdateDialog : " + e.getMessage());
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
