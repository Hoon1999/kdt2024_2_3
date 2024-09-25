package application.admin.sale;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.table.SaleAnalysisProductDisplayTableData;
import application.admin.etc.table.SaleAnalysisProductTableData;
import application.admin.login.LoginPageController;

public class SaleAnalysisProductPageController {
	
	// FXML 페이지 및 요소.
	private Parent saleAnalysisProductPage = null;

	DatePicker dpStartDate;
	DatePicker dpEndDate;
	Button btnToday;
	
	PieChart pcCategoryChart;
	PieChart pcProductChart;
	TableView<?> tvSaleProductList;
	
	List saleProducDatatist;
	
	public Parent getsaleAnalysisProductPage() {
		if (saleAnalysisProductPage == null) {
			try {
				System.out.println("init saleAnalysisProductPage");
				// 페이지 가져오기. 
				saleAnalysisProductPage = FXMLLoader.load(getClass().getResource("SaleAnalysisProductPage.fxml"));

				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(saleAnalysisProductPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				dpStartDate = (DatePicker) saleAnalysisProductPage.lookup("#dpStartDate");
				dpEndDate = (DatePicker) saleAnalysisProductPage.lookup("#dpEndDate");
				btnToday = (Button) saleAnalysisProductPage.lookup("#btnToday");
				pcCategoryChart = (PieChart) saleAnalysisProductPage.lookup("#pcCategoryChart");
				pcProductChart = (PieChart) saleAnalysisProductPage.lookup("#pcProductChart");
				tvSaleProductList = (TableView<?>) saleAnalysisProductPage.lookup("#tvSaleProductList");
				
				// 테이블 뷰 컬럼 초기화.
				tvSaleProductList.getColumns().setAll(new SaleAnalysisProductDisplayTableData().getColumns());
				
				
				// 시작일자 datepicker changeListener 연결.
				dpStartDate.valueProperty().addListener((ov, oldValue, newValue) -> {
					// 시작일자.
					// DB.
					setTable();
					setChart(saleProducDatatist);
		        });
				// 종료일자 datepicker changeListener 연결.
				dpEndDate.valueProperty().addListener((ov, oldValue, newValue) -> {
					// 종료일자.
					// DB.
					setTable();
					setChart(saleProducDatatist);
		        });
				
				// 시작, 종료 일자를 모두 오늘 날짜로.
				setToday();
		        
				stage.close();
				
				System.out.println("init success saleAnalysisProductPage");
			} catch (Exception e) {
				System.out.println("!(saleAnalysisProductPage) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(saleAnalysisProductPage) : " + e.getMessage());
			}
		} // ! if null.
		
		//////////////////////////////////
		setToday();
		
		tvSaleProductList.getSelectionModel().clearSelection();
		tvSaleProductList.getSelectionModel().selectFirst();
		
		// 오늘 버튼.
		btnToday.setOnAction(event -> setToday());
		//////////////////////////////////
		
		System.out.println("return saleAnalysisProductPage");
		return saleAnalysisProductPage;
	}
	
	// 오늘로 세팅하는 메소드.
	public void setToday() {
		dpStartDate.setValue(LocalDate.now().minusDays(1));
		
		dpStartDate.setValue(LocalDate.now());
		dpEndDate.setValue(LocalDate.now());
	}
	
	// 테이블 세팅 메소드.
	public void setTable() {
		saleProducDatatist = new LinkedList<SaleAnalysisProductTableData>();
		
		// DB 에서 가져와야함.
		for (int i = 0; i < 5; i++) {
			int ranknum = i + 1;
			String productName = "임시상품" + i;
			String categoryName = "임시카테고리" + i;
			int totalCount = (i+1) * 10;
			int totalPrice = (i+1) * 1100;
			saleProducDatatist.add(new SaleAnalysisProductTableData(ranknum, productName, categoryName, totalCount, totalPrice));
		}
		
		tvSaleProductList.getItems().setAll(saleProducDatatist);
	}
	
	// 아레 테이블 토대로 차트 그리는 메소드.
	public void setChart(List<SaleAnalysisProductTableData> saleProducDatatist) {

		Map<String, Integer> categoryMap = new HashMap<String, Integer>();
		
		// 상품 차트.
		ObservableList<Data> productChartList = FXCollections.observableArrayList();
		productChartList.clear();
		for (SaleAnalysisProductTableData o : saleProducDatatist) {
			// 카테고리로.
			String categoryName = o.categoryNameProperty().get();
			if (categoryMap.containsKey(categoryName)) {
				// 이미 있으면, 현재 값 + 지금 값.
				categoryMap.put(categoryName, categoryMap.get(categoryName).intValue() + o.totalPaymentCountProperty().intValue());
			}
			else {
				// 없으면, 지금 값.
				categoryMap.put(categoryName, o.totalPaymentCountProperty().intValue());
			}
			
			// 상품으로.
			String productName = o.productNameProperty().get();
			int productCount = o.totalPaymentCountProperty().get();
			productChartList.add(new PieChart.Data(productName, productCount));
			
		}
		// 상품 차트 세팅.
		pcProductChart.setTitle("상품 별"); // 타이틀
		pcProductChart.setData(productChartList);
		
		// 카테고리 차트.
		ObservableList<Data> categoryChartList = FXCollections.observableArrayList();
		categoryChartList.clear();
		Iterator<String> categorykeys = categoryMap.keySet().iterator();
		while (categorykeys.hasNext()) {
		    String key = categorykeys.next();
		    categoryChartList.add(new PieChart.Data(key, categoryMap.get(key)));
		}
		// 카테고리 차트 세팅.
		pcCategoryChart.setTitle("카테고리 별"); // 타이틀
		pcCategoryChart.setData(categoryChartList);
	}
}
