package application.admin.product.option;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.admin.etc.AlarmDialog;
import application.admin.etc.option.Option;
import application.admin.etc.product.Product;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;

public class OptionDeleteDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeProductDeleteDialog(String selectedOptionName) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		Option selectedOption;
		if (selectedOptionName.equals("All")) {
			// "All" 은 삭제할 수 없음.
			return;
		}
		else {
			selectedOption = AdminMainPageController.getMainPageController().getOptionManager().findOptionByName(selectedOptionName);
		}
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("OptionDeleteDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			Label lbOptionId = (Label) parent.lookup("#lbOptionId");
			Label lbOptionName = (Label) parent.lookup("#lbOptionName");
			Button btnOk = (Button) parent.lookup("#btnOk");
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			
			// 텍스트 내용 설정.
			lbOptionId.setText(String.valueOf(selectedOption.getId()));
			lbOptionName.setText(selectedOption.getName());
			
			// 버튼 액션 설정.
			// 취소 버튼.
			btnCancel.setOnAction(e -> dialog.close());
			// 확인 버튼.
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// 선택한 옵션의 하위 옵션이 없을 때에만 해당 선택한 옵션을 삭제.
					if (AdminMainPageController.getMainPageController().getOptionManager().findSubOptionsByOption(selectedOption).isEmpty()) {
						// 옵션 리스트에서 선택한 옵션 삭제.
						AdminMainPageController.getMainPageController().getOptionManager().deleteOption(selectedOption);
						
						// 옵션 관리창 로딩.
						AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
					}
					else {
						AlarmDialog alarmDialog = new AlarmDialog();
						alarmDialog.makeAlarmDialog("경고", "해당 옵션을 사용 중인 하위 옵션이 있습니다!");
					}
					
					// 창 닫기.
					btnCancel.fire();
				}
			});
		} catch (Exception e) {
			System.out.println("!OptionDeleteDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!OptionDeleteDialog : " + e.getMessage());
		}
		
		
	}
}
