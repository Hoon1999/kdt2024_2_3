package application.admin.product;

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

public class ProductOptionManagementPageController {
	
	// FXML 페이지 및 요소.
	private Parent productOptionManagementPage = null;

	ListView lvCategoryList;
	Button btnInsertOption;
	Button btnUpdateOption;
	Button btnDeleteOption;
	
	TableView tvProductList;
	ImageView ivProductImage;
	
	Button btnInsertSubOption;
	Button btnDetailSubOption;
	Button btnUpdateSubOption;
	Button btnDeleteSubOption;
	
	
	public Parent getProductOptionManagementPage() {
		if (productOptionManagementPage == null) {
			try {
				System.out.println("init productOptionManagementPage");
				// 페이지 가져오기. 
				productOptionManagementPage = FXMLLoader.load(getClass().getResource("ProductOptionManagementPage.fxml"));
				
				// 요소 가져오기.
				lvCategoryList = (ListView) productOptionManagementPage.lookup("#lvCategoryList");
				btnInsertOption = (Button) productOptionManagementPage.lookup("#btnInsertOption");
				btnUpdateOption = (Button) productOptionManagementPage.lookup("#btnUpdateOption");
				btnDeleteOption = (Button) productOptionManagementPage.lookup("#btnDeleteOption");
				tvProductList = (TableView) productOptionManagementPage.lookup("#tvProductList");
				ivProductImage = (ImageView) productOptionManagementPage.lookup("#ivProductImage");
				btnInsertSubOption = (Button) productOptionManagementPage.lookup("#btnInsertSubOption");
				btnDetailSubOption = (Button) productOptionManagementPage.lookup("#btnDetailSubOption");
				btnUpdateSubOption = (Button) productOptionManagementPage.lookup("#btnUpdateSubOption");
				btnDeleteSubOption = (Button) productOptionManagementPage.lookup("#btnDeleteSubOption");
				
				System.out.println("init success productOptionManagementPage");
			} catch (IOException e) {
				System.out.println("!(KeypadCont-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(KeypadCont-IOException) : " + e.getMessage());
			} catch (Exception e) {
				System.out.println("!(KeypadCont-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(KeypadCont-Exception) : " + e.getMessage());
			}
		}
		
		btnInsertOption.setOnAction(event -> System.out.println("옵션 추가 버튼 클릭!"));
		btnUpdateOption.setOnAction(event -> System.out.println("옵션 수정 버튼 클릭!"));
		btnDeleteOption.setOnAction(event -> System.out.println("옵션 삭제 버튼 클릭!"));
		
		btnInsertSubOption.setOnAction(event -> System.out.println("하위 옵션 추가 버튼 클릭!"));
		btnUpdateSubOption.setOnAction(event -> System.out.println("하위 옵션 수정 버튼 클릭!"));
		btnDeleteSubOption.setOnAction(event -> System.out.println("하위 옵션 삭제 버튼 클릭!"));
		btnDetailSubOption.setOnAction(event -> System.out.println("하위 옵션 상세 버튼 클릭! " + btnDetailSubOption.getText() ));
		
		System.out.println("return productOptionManagementPage");
		return productOptionManagementPage;
		
	}
	
}
