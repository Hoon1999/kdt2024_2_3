package application.admin.sale;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
//

public class SaleManagementPageController {
//	LinkedList<Item> itemList;
	
	// FXML 페이지 및 요소.
	private Parent saleManagementPage = null;
	
	private BorderPane bpViewSaleList;
	private BorderPane bpAnalysisProfit;
	private BorderPane bpAnalysisProduct;
	
	private TabPane tpTapPane;
	private Tab tabViewProfitList;
	private Tab tabAnalysisProfit;
	private Tab tabAnalysisProduct;
	
	
	private SaleViewProfitListPageController saleViewProfitListPageController = null;
	public SaleViewProfitListPageController getSaleViewProfitListPageController() {
		if (saleViewProfitListPageController == null) {
			saleViewProfitListPageController = new SaleViewProfitListPageController();
		}
		return saleViewProfitListPageController;
	}
	private SaleAnalysisProfitPageController saleAnalysisProfitPageController = null;
	public SaleAnalysisProfitPageController getSaleAnalysisProfitPageController() {
		if (saleAnalysisProfitPageController == null) {
			saleAnalysisProfitPageController = new SaleAnalysisProfitPageController();
		}
		return saleAnalysisProfitPageController;
	}
	private SaleAnalysisProductPageController saleAnalysisProductPageController = null;
	public SaleAnalysisProductPageController getSaleAnalysisProductPageController() {
		if (saleAnalysisProductPageController == null) {
			saleAnalysisProductPageController = new SaleAnalysisProductPageController();
		}
		return saleAnalysisProductPageController;
	}
	
	
	public Parent getSaleManagementPage() {
		if(saleManagementPage == null) {
			try {
				System.out.println("init saleManagementPage");
				saleManagementPage = FXMLLoader.load(getClass().getResource("SaleManagementPage.fxml"));
				
				// 요소 가져오기.
				bpViewSaleList = (BorderPane) saleManagementPage.lookup("#bpViewSaleList");
				bpAnalysisProfit = (BorderPane) saleManagementPage.lookup("#bpAnalysisProfit");
				bpAnalysisProduct = (BorderPane) saleManagementPage.lookup("#bpAnalysisProduct");
				
			} catch (IOException e) {
				System.out.println("!(saleManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(saleManagementPage-IOException) : " + e.getMessage());
			}
		}
		
		setSaleViewProfitListPage();
		setSaleAnalysisProfitPage();
		setSaleAnalysisProductPage();
		
		System.out.println("return saleManagementPage");
		return saleManagementPage;
	}
	
	public void setSaleViewProfitListPage() {
		bpViewSaleList.setCenter(AdminMainPageController.getMainPageController().getSaleManagementPageController().getSaleViewProfitListPageController().getSaleViewProfitListPage());
	}
	
	public void setSaleAnalysisProfitPage() {
		bpAnalysisProfit.setCenter(AdminMainPageController.getMainPageController().getSaleManagementPageController().getSaleAnalysisProfitPageController().getsaleAnalysisProfitPage());
	}
	
	public void setSaleAnalysisProductPage() {
		bpAnalysisProduct.setCenter(AdminMainPageController.getMainPageController().getSaleManagementPageController().getSaleAnalysisProductPageController().getsaleAnalysisProductPage());
	}
}
