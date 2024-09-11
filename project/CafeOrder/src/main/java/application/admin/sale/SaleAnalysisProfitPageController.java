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

public class SaleAnalysisProfitPageController {
	
	// FXML 페이지 및 요소.
	private Parent saleAnalysisProfitPage = null;

//	TableView tvSaleList;
	
//	Button btnDeleteSubOption;
	
	public Parent getsaleAnalysisProfitPage() {
		if (saleAnalysisProfitPage == null) {
			try {
				System.out.println("init saleAnalysisProfitPage");
				// 페이지 가져오기. 
				saleAnalysisProfitPage = FXMLLoader.load(getClass().getResource("SaleAnalysisProfitPage.fxml"));
				
				// 요소 가져오기.
//				tvSaleList = (TableView) saleAnalysisProfitPage.lookup("#tvSaleList");
				
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
	
}
