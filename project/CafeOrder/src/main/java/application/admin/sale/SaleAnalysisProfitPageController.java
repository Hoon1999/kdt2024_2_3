package application.admin.sale;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.customer.order.OrderClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.admin.login.LoginPageController;
import org.json.JSONArray;
import org.json.JSONObject;

public class SaleAnalysisProfitPageController {
	
	// FXML 페이지 및 요소.
	private Parent saleAnalysisProfitPage = null;
	private Button leftBtn;
	private Button rightBtn;
	private Label yearLbl;
	private Label monthLbl;
	private Label monthlySalesLbl;
	private TilePane tpTilePane;
	private Map<String, Integer> dailySalesMap = new HashMap<>();
	private Map<String, Integer> monthlySalesMap = new HashMap<>();
	private int year;
	private int month;

	public Parent getsaleAnalysisProfitPage() {
		if (saleAnalysisProfitPage == null) {
			try {
				System.out.println("init saleAnalysisProfitPage");
				// 페이지 가져오기. 
				saleAnalysisProfitPage = FXMLLoader.load(getClass().getResource("SaleAnalysisProfitPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(saleAnalysisProfitPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				
				// 요소 가져오기.
				leftBtn= (Button) saleAnalysisProfitPage.lookup("#leftBtn");
				rightBtn = (Button) saleAnalysisProfitPage.lookup("#rightBtn");
				yearLbl  = (Label) saleAnalysisProfitPage.lookup("#yearLbl");
				monthLbl = (Label) saleAnalysisProfitPage.lookup("#monthLbl");
				monthlySalesLbl = (Label) saleAnalysisProfitPage.lookup("#monthlySalesLbl");
				tpTilePane = (TilePane) saleAnalysisProfitPage.lookup("#tpTilePane");
				tpTilePane.setPrefColumns(7); // 열 개수를 7로 설정
				tpTilePane.setPrefTileWidth(60); // 각 타일의 너비를 60으로 설정
				tpTilePane.setPrefTileHeight(60);

				OrderClient orderClient = OrderClient.getInstance();
				JSONObject data = orderClient.getDailySales();
				JSONArray dailySales = data.getJSONArray("data");
				for(int i = 0; i < dailySales.length(); i++) {
					JSONObject obj = (JSONObject) dailySales.get(i);
					dailySalesMap.put(obj.getString("date"), obj.getInt("sales"));
					System.out.println("tt1 : " + obj.getString("date"));
				}

				data = orderClient.getMonthlySales();
				JSONArray monthlySales = data.getJSONArray("data");
				for(int i = 0; i < monthlySales.length(); i++) {
					JSONObject obj = (JSONObject) monthlySales.get(i);
					monthlySalesMap.put(obj.getString("date"), obj.getInt("sales"));
				}

				year = 2024;
				month = 9;

				yearLbl.setText(String.valueOf(year));
				monthLbl.setText(String.valueOf(month));
				tilePaneReDraw();

				leftBtn.setOnAction(e -> {
					month--;
					if(month < 1) {
						month = 12;
						year--;
					}
					monthLbl.setText(String.valueOf(month));
					yearLbl.setText(String.valueOf(year));

					int temp = 0;
					if(month < 10) {
						if( monthlySalesMap.get(year + "-0" + month) != null)
							temp = monthlySalesMap.get(year + "-0" + month);
					}
					else {
						if( monthlySalesMap.get(year + "-0" + month) != null)
							temp = monthlySalesMap.get(year + "-" + month);
					}
					monthlySalesLbl.setText(String.valueOf(temp));
					tilePaneReDraw();
				});

				rightBtn.setOnAction(e -> {
					month++;
					if(month > 12) {
						month = 1;
						year++;
					}
					monthLbl.setText(String.valueOf(month));
					yearLbl.setText(String.valueOf(year));

					int temp = 0;
					if(month < 10) {
						if( monthlySalesMap.get(year + "-0" + month) != null)
							temp = monthlySalesMap.get(year + "-0" + month);
					}
					else {
						if( monthlySalesMap.get(year + "-0" + month) != null)
							temp = monthlySalesMap.get(year + "-" + month);
					}
					monthlySalesLbl.setText(String.valueOf(temp));
					tilePaneReDraw();
				});



				stage.close();
				
				System.out.println("init success saleAnalysisProfitPage");
			} catch (IOException e) {
				System.out.println("!(saleAnalysisProfitPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(saleAnalysisProfitPage-IOException) : " + e.getMessage());
			}
		}
		
		System.out.println("return saleAnalysisProfitPage");
		return saleAnalysisProfitPage;
	}

	public void tilePaneReDraw() {
		tpTilePane.getChildren().clear();
		int lastDay = 31;
		if(month == 2) {
			lastDay = 28;
		} else if(month == 4 | month == 6 | month == 9 | month == 11 ) {
			lastDay = 30;
		}
		for(int day = 1; day <= lastDay; day++) {
			int temp = 0;
			String  str = year + "-";
			if(month < 10)
				str += "0" + month + "-";
			else
				str += month + "-";

			if(day < 10)
				str += "0" + day;
			else
				str += day;
			if(dailySalesMap.get(str) != null)
				temp = dailySalesMap.get(str);

			Button btn = new Button(temp + " 원");
//			btn.setPrefWidth(60.0);
//			btn.setMaxWidth(60.0);
//			HBox.setHgrow(btn, Priority.ALWAYS);
			tpTilePane.getChildren().addAll(btn);
		}
	}
}
