package application.admin.order.acceptance;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.option.SubOption;
import application.admin.etc.order.Order;
import application.admin.etc.order.OrderItem;
import application.admin.etc.order.OrderOption;
import application.admin.etc.product.Product;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;

public class OrderDetailDialogController {

	class NameLabel extends Label {
		public NameLabel(String text) {
			this.setMaxWidth(130.0);
			this.setPrefWidth(130.0);
			this.setMinWidth(130.0);
			this.setAlignment(Pos.CENTER_LEFT);
			this.setText(text);
		}
	}
	class CountLabel extends Label {
		public CountLabel(String text) {
			this.setMaxWidth(20.0);
			this.setPrefWidth(20.0);
			this.setMinWidth(20.0);
			this.setAlignment(Pos.CENTER_RIGHT);
			this.setText(text);
		}
	}
	class PriceLabel extends Label {
		public PriceLabel(String text) {
			this.setMaxWidth(90.0);
			this.setPrefWidth(90.0);
			this.setMinWidth(90.0);
			this.setAlignment(Pos.CENTER_RIGHT);
			this.setText(text);
		}
	}
	class MySeParator extends Separator {
		Insets myInset = new Insets(5.0, 0, 5.0, 0);
		public MySeParator() {
			this.setPadding(myInset);
		}
	}
	
	// 다이얼로그 실행.
	public void makeOrderDetailDialog(int orderNum) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.setResizable(false);
		dialog.initOwner(AdminMainPage.getPrimaryStage());
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("OrderDetailDialog.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
			Label lbOrderNum = (Label) parent.lookup("#lbOrderNum");
			Label lbIsTakeout = (Label) parent.lookup("#lbIsTakeout");
			Button btnExit = (Button) parent.lookup("#btnExit");
			Button btnOrderCancel = (Button) parent.lookup("#btnOrderCancel");
			Button btnOrderComplete = (Button) parent.lookup("#btnOrderComplete");
			VBox vbOrderList = (VBox) parent.lookup("#vbOrderList");
			ObservableList orderDetailList = vbOrderList.getChildren();
			
			
			// 주문 상세 내역. (주문번호로 가져옴).
			Order detailOrder = AdminMainPageController.getMainPageController().getOrderManager().findOrderByOrderNum(orderNum);
			// 주문 번호 레이블.
			lbOrderNum.setText(String.valueOf(orderNum));
			// 포장/매장 레이블.
			String strIsTakeout = detailOrder.getIsTakeout() ? "(포장 주문)" : "(매장 주문)" ;
			lbIsTakeout.setText(strIsTakeout);
			
			// 주문 상세 내역. 주문 품목 리스트.
			LinkedList<OrderItem> detailOrderList = detailOrder.getOrderList();
			setOrderDetailList(orderDetailList, detailOrderList);
			
			
			// 버튼 액션.
			btnExit.setOnAction(e -> dialog.close());
			btnOrderCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// 주문 취소.
					System.out.println("주문 취소");

					// 주문 취소 처리.
					AdminMainPageController.getMainPageController().getOrderManager().deleteOrder(detailOrder);
					
					// 주문 접수 및 완료 페이지 다시 로딩.
					AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderAcceptanceManagementPageController().getOrderAcceptanceManagementPage();
					AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderCompleteManagementPageController().getOrderCompleteManagementPage();
					
					// 창 닫기.
					btnExit.fire();
				}
			});
			btnOrderComplete.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					// 주문 완료.
					
					System.out.println("주문 완료");
					
					// 주문 완료 처리.
					AdminMainPageController.getMainPageController().getOrderManager().completeOrder(detailOrder);
					
					// 주문 접수 및 완료 페이지 다시 로딩.
					AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderAcceptanceManagementPageController().getOrderAcceptanceManagementPage();
					AdminMainPageController.getMainPageController().getOrderManagementPageController().getOrderCompleteManagementPageController().getOrderCompleteManagementPage();
					
					// 창 닫기.
					btnExit.fire();
				}
			});
			
		} catch (Exception e) {
			System.out.println("!OrderDetailDialog : " + e.getMessage());
			e.printStackTrace();
			System.out.println("~!OrderDetailDialog : " + e.getMessage());
		}
		
	}
	
	
	private void setOrderDetailList(ObservableList orderDetailList, LinkedList<OrderItem> detailOrderList) {
		int total = 0; // 총액.
		
		for (int i = 0; i < detailOrderList.size(); i++) {
			// 상품 하나.
			OrderItem orderItem = detailOrderList.get(i);
			// 상품 하나를 띄울 pane.
			BorderPane bp = new BorderPane();
			// 상품명 레이블.
			Product curProduct = AdminMainPageController.getMainPageController().getProductManager().findProductById(orderItem.getProductId()); // 현재 상품.
			Label lbProductName = new NameLabel(curProduct.getName());
			// 상품 구매 개수 레이블.
			Label lbProductCount = new CountLabel(String.valueOf(orderItem.getCount()));
			// 상품 가격 레이블
			Label lbProductPrice = new PriceLabel(String.valueOf(orderItem.getCount() * curProduct.getPrice()));	// 주문 개수 * 현재 상품 가격.
			// 상품 옵션 VBox.
			VBox bpOptions = new VBox();
			bpOptions.setFillWidth(true);
			ObservableList optionsList = bpOptions.getChildren();
			
			// 옵션 달아주기.
			for (int j = 0; j < orderItem.getOptions().size(); j++) {
				// 옵션 하나.
				OrderOption option = orderItem.getOptions().get(j);

				// 옵션명.
				SubOption curSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(option.getSubOptionId());
				String curOptionName = AdminMainPageController.getMainPageController().getOptionManager().findOptionNameById(curSubOption.getOptionId());
				
				Label optionName = new NameLabel("└" + curOptionName);
				optionsList.add(optionName);
				
				// 하위 옵션명.
				Label lbSubOptionName = new NameLabel("　　" + curSubOption.getName());
				// 하위 옵션 구매 개수.
				Label lbSubOptionCount = new CountLabel(String.valueOf(option.getCount()));
				// 하위 옵션 구매 가격.
				Label lbSubOptionPrice = new PriceLabel(String.valueOf((orderItem.getCount() * option.getCount() * curSubOption.getPrice())));
				
				// 하위 옵션 하나 띄울 pane.
				BorderPane bpOption = new BorderPane();
				bpOption.setLeft(lbSubOptionName);
				bpOption.setCenter(lbSubOptionCount);
				bpOption.setRight(lbSubOptionPrice);
				
				total += Integer.parseInt(lbSubOptionPrice.getText()); // 하위 옵션 구매 가격 더해주기.
				optionsList.add(bpOption);
			}
			total += Integer.parseInt(lbProductPrice.getText()); // 상품 구매 가격(옵션 제외) 더해주기.

			
			bp.setLeft(lbProductName);
			bp.setCenter(lbProductCount);
			bp.setRight(lbProductPrice);
			bp.setBottom(bpOptions);
			
			orderDetailList.add(bp); // 상품 하나.
			orderDetailList.add(new MySeParator()); // 상품 하나 끝날 때마다 separator.
		}

		// 총액.
		BorderPane bpTotal = new BorderPane();
		bpTotal.setLeft(new NameLabel("총액 : "));
		
		String strTotal = String.valueOf(total);
		bpTotal.setRight(new PriceLabel(strTotal));
		orderDetailList.add(bpTotal);
	}
}
