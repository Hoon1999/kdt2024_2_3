package application.admin.product.product;

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

import application.admin.etc.product.Product;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;

public class ProductDeleteDialogController {
	Stage dialog;
	
	// 다이얼로그 실행.
	public void makeProductDeleteDialog(int productId) {
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		Product selectedProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(productId);
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("ProductDeleteDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			Label lbProductId = (Label) parent.lookup("#lbProductId");
			Label lbProductName = (Label) parent.lookup("#lbProductName");
			Label lbProductCategory = (Label) parent.lookup("#lbProductCategory");
			Button btnOk = (Button) parent.lookup("#btnOk");
			Button btnCancel = (Button) parent.lookup("#btnCancel");
			
			// 텍스트 내용 설정.
			lbProductId.setText(String.valueOf(selectedProduct.getId()));
			lbProductName.setText(selectedProduct.getName());
			lbProductCategory.setText(AdminMainPageController.getMainPageController().getProductManager().findCategoryNameById(selectedProduct.getCategoryId()));
			
			// 버튼 액션 설정.
			// 취소 버튼.
			btnCancel.setOnAction(e -> dialog.close());
			// 확인 버튼.
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// 상품 리스트에서 선택한 상품 삭제.
					AdminMainPageController.getMainPageController().getProductManager().deleteProduct(selectedProduct);
					
					// 상품 관리창 로딩.
					AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProductManagementPageController().getProductProductManagementPage();
					
					// 창 닫기.
					btnCancel.fire();
				}
			});
		} catch (Exception e) {
			System.out.println("!ProductDeleteDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!ProductDeleteDialog : " + e.getMessage());
		}
		
		
	}
}
