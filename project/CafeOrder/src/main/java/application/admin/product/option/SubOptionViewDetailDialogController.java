package application.admin.product.option;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.option.SubOption;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class SubOptionViewDetailDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeSubOptionViewDetailDialog(int subOptionId) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		SubOption selectedSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(subOptionId);
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SubOptionViewDetailDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			TextField tfSubOptionId = (TextField) parent.lookup("#tfSubOptionId");
			TextField tfSubOptionName = (TextField) parent.lookup("#tfSubOptionName");
			TextField tfSubOptionPrice = (TextField) parent.lookup("#tfSubOptionPrice");
			TextField tfSubOptionStock = (TextField) parent.lookup("#tfSubOptionStock");
			TextField tfOptionId = (TextField) parent.lookup("#tfOptionId");
			TextField tfOptionName = (TextField) parent.lookup("#tfOptionName");
			ImageView ivSubOptionImage = (ImageView) parent.lookup("#ivSubOptionImage");
			Button btnExit = (Button) parent.lookup("#btnExit");
			
			// 모든 텍스트필드는 변경 불가.
			// 하위옵션 id.
			tfSubOptionId.setEditable(false);
			tfSubOptionId.setDisable(true);
			// 하위옵션명.
			tfSubOptionName.setEditable(false);
			tfSubOptionName.setDisable(true);
			// 하위옵션 가격.
			tfSubOptionPrice.setEditable(false);
			tfSubOptionPrice.setDisable(true);
			// 하위옵션 재고.
			tfSubOptionStock.setEditable(false);
			tfSubOptionStock.setDisable(true);
			// 옵션 카테고리 id.
			tfOptionId.setEditable(false);
			tfOptionId.setDisable(true);
			// 옵션 카테고리명.
			tfOptionName.setEditable(false);
			tfOptionName.setDisable(true);
			
			// 텍스트필드 텍스트 설정.
			// 하위옵션 id 텍스트 설정.
			tfSubOptionId.setText(String.valueOf(selectedSubOption.getId()));
			// 하위옵션명 텍스트 설정.
			tfSubOptionName.setText(selectedSubOption.getName());
			// 하위옵션 가격 텍스트 설정.
			tfSubOptionPrice.setText(String.valueOf(selectedSubOption.getPrice()));
			// 하위옵션 재고 텍스트 설정.
			tfSubOptionStock.setText(String.valueOf(selectedSubOption.getStockCount()));
			// 옵션 카테고리 id 텍스트 설정.
			tfOptionId.setText(String.valueOf(selectedSubOption.getOptionId()));
			// 옵션 카테고리명 텍스트 설정.
			tfOptionName.setText(AdminMainPageController.getMainPageController().getOptionManager().findOptionNameById(selectedSubOption.getOptionId()));
			// 하위옵션 이미지 설정.
			ivSubOptionImage.setImage(selectedSubOption.getImage());			
			
////////////////////////////////////////////////////////////////////////////			
//			((ProductTableData)tvProductList.getSelectionModel().getSelectedItem()).idProperty().toString()
			
			// 버튼 액션 설정.
			// 닫기 버튼.
			btnExit.setOnAction(e -> dialog.close());
			
		} catch (Exception e) {
			System.out.println("!SubOptionViewDetailDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!SubOptionViewDetailDialog : " + e.getMessage());
		}
		
	}
	
}
