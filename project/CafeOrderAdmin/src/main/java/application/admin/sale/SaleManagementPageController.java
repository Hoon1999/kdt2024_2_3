package application.admin.sale;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.etc.Dialog;
import application.admin.etc.Item;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
//

public class SaleManagementPageController implements Initializable {
	LinkedList<Item> itemList;
	
	@FXML TilePane tab2Tilepane;
	

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
	
	
	public Parent getSaleManagementPage() {
		if(saleManagementPage == null) {
			try {
				System.out.println("init saleManagementPage");
				saleManagementPage = FXMLLoader.load(getClass().getResource("SaleManagementPage.fxml"));
				
				// 요소 가져오기. // TODO
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
		
		System.out.println("return saleManagementPage");
		return saleManagementPage;
	}
	
	public void setSaleViewProfitListPage() {
		bpViewSaleList.setCenter(AdminMainPageController.getMainPageController().getSaleManagementPageController().getSaleViewProfitListPageController().getSaleViewProfitListPage());
	}
	
	public void setSaleAnalysisProfitPage() {
		bpAnalysisProfit.setCenter(AdminMainPageController.getMainPageController().getSaleManagementPageController().getSaleViewProfitListPageController().getSaleViewProfitListPage());
	}
	
	// TODO delete.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image("file:images/a.jpg");
		// TODO Auto-generated method stub
		itemList = new LinkedList<Item>();
		for (int i = 0; i < 100; i++) {
			itemList.add(new Item(i + 1, "아이템" + (i + 1), 1000 * (i + 1)));
//		 	tab2Tilepane.getChildren().add(
//		 			
//		 		new BorderPane(
//		 				null,
//		 				null,
//		 				new ImageView().setImage(new Image("/images/a.jpg")),
//		 				null,
//		 				
//		 		)
//		 	);
			
			ImageView imageView = new ImageView();

			imageView.setImage(image);
			imageView.setId(String.valueOf(i));
			imageView.setFitHeight(50.0);
			imageView.setPreserveRatio(true);
			
			BorderPane bp = new BorderPane();
			bp.setCenter(imageView);
			bp.setBottom(new Label(String.format("%s", itemList.get(i).getName())));
			
			imageView.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					Dialog dialog = new Dialog();
					dialog.makeCustomDialog(imageView.getId(), itemList.get(Integer.parseInt(imageView.getId())).getName());
				};
			});
			
			tab2Tilepane.getChildren().add(bp);
			
		}
		
		
		//items.add(itemList);
		
		
	}
}
