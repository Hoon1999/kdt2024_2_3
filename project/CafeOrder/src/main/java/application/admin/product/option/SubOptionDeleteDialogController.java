package application.admin.product.option;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.admin.etc.option.SubOption;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;

public class SubOptionDeleteDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeSubOptionDeleteDialog(int subOptionId) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		SubOption selectedSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(subOptionId);
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SubOptionDeleteDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			Label lbSubOptionId = (Label) parent.lookup("#lbSubOptionId");
			Label lbSubOptionName = (Label) parent.lookup("#lbSubOptionName");
			Label lbOptionCategory = (Label) parent.lookup("#lbOptionCategory");
			Button btnOk = (Button) parent.lookup("#btnOk");
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			
			// 텍스트 내용 설정.
			lbSubOptionId.setText(String.valueOf(selectedSubOption.getId()));
			lbSubOptionName.setText(selectedSubOption.getName());
			lbOptionCategory.setText(AdminMainPageController.getMainPageController().getOptionManager().findOptionNameById(selectedSubOption.getOptionId()));
			
			// 버튼 액션 설정.
			// 취소 버튼.
			btnCancel.setOnAction(e -> dialog.close());
			// 확인 버튼.
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// 하위 옵션 리스트에서 선택한 하위 옵션 삭제.
					AdminMainPageController.getMainPageController().getOptionManager().deleteSubOption(selectedSubOption);
					
					// 옵션 관리창 로딩.
					AdminMainPageController.getMainPageController().getProductManagementPageController().getProductOptionManagementPageController().getProductOptionManagementPage();
					
					// 창 닫기.
					btnCancel.fire();
				}
			});
		} catch (Exception e) {
			System.out.println("!SubOptionDeleteDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!SubOptionDeleteDialog : " + e.getMessage());
		}
		
		
	}
}
