package application.admin.sale;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class SaleAnalysisProductPageController {
	
	// FXML 페이지 및 요소.
	private Parent saleAnalysisProductPage = null;

//	TableView tvSaleList;
	
//	Button btnDeleteSubOption;
	
	public Parent getsaleAnalysisProductPage() {
		if (saleAnalysisProductPage == null) {
			try {
				System.out.println("init saleAnalysisProductPage");
				// 페이지 가져오기. 
				saleAnalysisProductPage = FXMLLoader.load(getClass().getResource("SaleAnalysisProductPage.fxml"));
				
				// 요소 가져오기.
//				tvSaleList = (TableView) saleAnalysisProfitPage.lookup("#tvSaleList");
				
				System.out.println("init success saleAnalysisProductPage");
			} catch (IOException e) {
				System.out.println("!(saleAnalysisProductPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(saleAnalysisProductPage-IOException) : " + e.getMessage());
			}
		}
		
		System.out.println("return saleAnalysisProductPage");
		return saleAnalysisProductPage;
	}
	
}
