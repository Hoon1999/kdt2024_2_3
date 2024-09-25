package application.admin.product.option;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.AlarmDialog;
import application.admin.etc.option.Option;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class OptionUpdateDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeOptionUpdateDialog(String selectedOptionName) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		Option selectedOption;
		if (selectedOptionName.equals("All")) {
			// "All" 은 변경/수정 할 수 없음.
			return;
		} else {
			selectedOption = AdminMainPageController.getMainPageController().getOptionManager().findOptionByName(selectedOptionName);
		}
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("OptionUpdateDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			TextField tfOptionId = (TextField) parent.lookup("#tfOptionId");
			TextField tfOptionName = (TextField) parent.lookup("#tfOptionName");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnUpdateOption = (Button) parent.lookup("#btnUpdateOption");
			BorderPane bpKeypad = (BorderPane) parent.lookup("#bpKeypad");
			
			// 옵션 id 는 변경 불가.
			tfOptionId.setEditable(false);
			tfOptionId.setDisable(true);
			// 옵션 id 기본값 선택한 옵션 id 로 설정하기.
			tfOptionId.setText(String.valueOf(selectedOption.getId()));
			
			// 옵션명 기본값 선택한 옵션명으로 설정하기.
			tfOptionName.setText(selectedOption.getName());
			
////////////////////////////////////////////////////////////////////////////			
//			((ProductTableData)tvProductList.getSelectionModel().getSelectedItem()).idProperty().toString()
			
			// 버튼 액션 설정.
			// 닫기 버튼.
			btnExit.setOnAction(e -> dialog.close());
			// 옵션 등록 버튼.
			btnUpdateOption.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent arg0) {
					// 필수 입력 처리. 
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
					
					if (isError) {
						AlarmDialog alarmDialog = new AlarmDialog();
						alarmDialog.setPrimaryStage(dialog);
						alarmDialog.makeAlarmDialog(null, sb.toString());
						return;
					}
					else {
						int id = Integer.valueOf(tfOptionId.getText());
						String name = tfOptionName.getText();
						
						Option newOption = new Option(id, name);
						
						// 옵션 변경 처리.
						AdminMainPageController.getMainPageController().getOptionManager().updateOption(newOption);
						
						// 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
						
						// 창 닫기.
						btnExit.fire();
					}
					
				}
			});
			
		} catch (Exception e) {
			System.out.println("!OptionUpdateDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!OptionUpdateDialog : " + e.getMessage());
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
