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
import application.admin.etc.option.Option;
import application.admin.etc.product.Product;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class OptionAddDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeOptionAddDialog() {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("OptionAddDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			TextField tfOptionId = (TextField) parent.lookup("#tfOptionId");
			TextField tfOptionName = (TextField) parent.lookup("#tfOptionName");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnAddOption = (Button) parent.lookup("#btnAddOption");
			BorderPane bpKeypad = (BorderPane) parent.lookup("#bpKeypad");
			
			tfOptionId.setPromptText("0");
			tfOptionId.focusedProperty().addListener(e -> linkKeypad(tfOptionId.isFocused(), bpKeypad, tfOptionId, false));
			
			tfOptionName.setPromptText("옵션명을 입력하세요.");
			
			
			// 버튼 액션 설정.
			// 닫기 버튼.
			btnExit.setOnAction(e -> dialog.close());
			// 옵션 등록 버튼.
			btnAddOption.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent arg0) {
					// 필수 입력/형식 에러 처리. 
					StringBuilder sb = new StringBuilder();
					boolean isError = false;
					
					try {
						// 옵션 id 가 숫자가 아니면, 예외처리 됨.
						Integer.valueOf(tfOptionId.getText());
						
						if (tfOptionId.getText() == null || tfOptionId.getText().equals("") || tfOptionId.getText().isBlank()) {
							isError = true;
							sb.append("옵션 id가 입력되지 않았습니다.\n");
						}
						if (AdminMainPageController.getMainPageController().getOptionManager().findOptionById( Integer.valueOf(tfOptionId.getText()) ) != null ) {
							isError = true;
							sb.append("이미 존재하는 옵션 id 입니다.\n");
						}
					} catch (NumberFormatException e) {
						isError = true;
						sb.append("옵션 id 는 숫자로 입력해주세요.\n");
					}
					if (tfOptionName.getText() == null || tfOptionName.getText().equals("") || tfOptionName.getText().isBlank()) {
						isError = true;
						sb.append("옵션명이 입력되지 않았습니다.\n");
					}
					if (AdminMainPageController.getMainPageController().getOptionManager().findOptionByName( tfOptionName.getText() ) != null ) {
						isError = true;
						sb.append("이미 존재하는 옵션명입니다.\n");
					} //////////////////////////////////////////////////
					
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
						// 새로운 옵션을 만들고,
						Option newOption = new Option(Integer.valueOf(tfOptionId.getText()), tfOptionName.getText());
						
						// 옵션 추가 처리.
						AdminMainPageController.getMainPageController().getOptionManager().addOption(newOption);
						
						// 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
						
						// 창 닫기.
						btnExit.fire();
					}
					
				}
			});
			
		} catch (Exception e) {
			System.out.println("!OptionAddDialogController : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!OptionAddDialogController : " + e.getMessage());
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
