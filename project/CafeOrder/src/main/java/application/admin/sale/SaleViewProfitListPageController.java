package application.admin.sale;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.customer.order.OrderClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.admin.etc.order.Order;
import application.admin.etc.table.SaleDisplayTableData;
import application.admin.etc.table.SaleTableData;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import org.json.JSONArray;
import org.json.JSONObject;

public class SaleViewProfitListPageController {
	
	// FXML 페이지 및 요소.
	private Parent saleViewProfitListPage = null;

	TableView<?> tvSaleList;
	List saleTableDataList;
	
	Button btnViewDetail;
	
	public Parent getSaleViewProfitListPage() {
		if (saleViewProfitListPage == null) {
			try {
				System.out.println("init saleViewProfitListPage");
				// 페이지 가져오기. 
				saleViewProfitListPage = FXMLLoader.load(getClass().getResource("SaleViewProfitListPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(saleViewProfitListPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				tvSaleList = (TableView<?>) saleViewProfitListPage.lookup("#tvSaleList");
				btnViewDetail = (Button) saleViewProfitListPage.lookup("#btnViewDetail");
				
				// 판매 테이블 뷰 컬럼 초기화.
				tvSaleList.getColumns().setAll(new SaleDisplayTableData().getColumns());
				
				// saleTableDataList 와 tvSaleList 연결.
				saleTableDataList = tvSaleList.getItems();
				
				
				stage.close();
				
				System.out.println("init success saleViewProfitListPage");
			} catch (Exception e) {
				System.out.println("!(saleViewProfitListPage) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(saleViewProfitListPage) : " + e.getMessage());
			}
		} // ! if null.
		
		//////////////////////////////////
		
		saleTableDataList = new LinkedList<SaleTableData>();
		
		// TODO
		// 지금 주문 내역으로 되어있음.
		OrderClient orderClient = OrderClient.getInstance();
		JSONObject data = orderClient.getSales();
		JSONArray sales = data.getJSONArray("data");
		for(int i = 0; i < sales.length(); i++) {
			JSONObject orderJSON = (JSONObject) sales.get(i); // {"phoneNumber":"000-0000-0000","id":8,"paymentTime":"2024-09-20 15:11:41","paymentAmount":4500}

//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			ZonedDateTime paymentTime = ZonedDateTime.of(LocalDateTime.parse(orderJSON.getString("paymentTime"), formatter));
//			LocalDateTime.parse(orderJSON.getString("paymentTime"), formatter);

			saleTableDataList.add(new SaleTableData(orderJSON.getInt("id"), orderJSON.getString("phoneNumber"), orderJSON.getInt("paymentAmount"), ZonedDateTime.now()));
		}

		for (Order order : AdminMainPageController.getMainPageController().getOrderManager().getOrderList()) {
			saleTableDataList.add(new SaleTableData(order.getOrderId(), order.getMemberId(), order.getTotalPaymentAmount(), order.getPaymentTime()));
		}
		for (Order order : AdminMainPageController.getMainPageController().getOrderManager().getOrdersCompleteList()) {
			saleTableDataList.add(new SaleTableData(order.getOrderId(), order.getMemberId(), order.getTotalPaymentAmount(), order.getPaymentTime()));
		}
		
		tvSaleList.getItems().setAll(saleTableDataList);
		
		tvSaleList.getSelectionModel().clearSelection();
		tvSaleList.getSelectionModel().selectFirst();
		
		
		btnViewDetail.setOnAction(event -> viewDetail(tvSaleList.getSelectionModel().getSelectedItem()));
		
		System.out.println("return saleViewProfitListPage");
		return saleViewProfitListPage;
	}
	
	public void viewDetail(Object selectedItem) {
		if (selectedItem instanceof SaleTableData) {
			int orderId = ((SaleTableData) selectedItem).idProperty().intValue();
			
			Order order = AdminMainPageController.getMainPageController().getOrderManager().findOrderByOrderId(orderId);
			if (order != null) {
				SaleViewDetailDialogController viewDetailDialogController = new SaleViewDetailDialogController();
				viewDetailDialogController.makeSaleDetailDialog(orderId);
			}
			
		}
	}
	
}
