package application.admin.product.link;

import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.option.Option;
import application.admin.etc.product.Product;
import application.admin.etc.table.LinkOptionDisplayTableData;
import application.admin.etc.table.LinkOptionTableData;
import application.admin.etc.table.OptionTableData;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;


public class UnlinkOptionDialogController {
	
	// 다이얼로그 실행.
	public void makeUnlinkOptionDialog(int productId) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		
		Product selectedProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(productId);
		if (selectedProduct == null) {
			return;
		}
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("UnlinkOptionDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			// 요소 가져오기.
			Label lbProductName = (Label) parent.lookup("#lbProductName");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnUnlinkOption = (Button) parent.lookup("#btnUnlinkOption");
			TableView<?> tvLinkedOptionList = (TableView<?>) parent.lookup("#tvLinkedOptionList");
			
			// 테이블 뷰 컬럼 초기화.
			tvLinkedOptionList.getColumns().setAll(new LinkOptionDisplayTableData().getColumns());
			
			lbProductName.setText(selectedProduct.getName());
			
			// 현재 선택된 상품에 연결된 모든 옵션 리스트.
			List tableDataList = new LinkedList<OptionTableData>();
			for (int optionId : selectedProduct.getLinkedOptionList()) {
				Option option = AdminMainPageController.getMainPageController().getOptionManager().findOptionById(optionId);
				if (option != null) {
					tableDataList.add(new LinkOptionTableData(option.getId(), option.getName()));
				}
			}
			
			// 테이블 뷰 내용을 tableDataList 로 초기화.
			tvLinkedOptionList.getItems().setAll(tableDataList);
			
			
			// 버튼 액션 설정.
			// 닫기 버튼.
			btnExit.setOnAction(e -> dialog.close());
			// 옵션 연결 버튼.
			btnUnlinkOption.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// test/
					// 선택된 행의 옵션 id 만 가져오기 위한 리스트.
					LinkedList<Integer> selectedIdList = new LinkedList<Integer>();
					
					// 테이블의 모든 행을 돌면서,
					for (Object object : tvLinkedOptionList.getItems()) {
						// 체크박스 선택된 행만 모아서,
						if (((LinkOptionTableData) object).isSelectedProperty().get() == true) {
							// 해당 옵션을 찾아
							Option option = AdminMainPageController.getMainPageController().getOptionManager().findOptionById(((LinkOptionTableData) object).idProperty().intValue());
							if (option != null) {
								// 옵션이 존재하면, 리스트에 해당 행을 추가.
								selectedIdList.add(option.getId());
							}
						}
					}
					
					// 상품에 연결된 옵션 연결 해제 처리.
					selectedProduct.deleteLinkedOptions(selectedIdList);
					
					// 로딩.
					AdminMainPageController.getMainPageController().getProductManagementPageController().getProductProdOptLinkManagementPageController().getProductProdOptLinkManagementPage();
					
					// 창 닫기.
					btnExit.fire();
				}
			});
			
		} catch (Exception e) {
			System.out.println("!UnlinkOptionDialogController : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!UnlinkOptionDialogController : " + e.getMessage());
		}
		
	}
	
}
