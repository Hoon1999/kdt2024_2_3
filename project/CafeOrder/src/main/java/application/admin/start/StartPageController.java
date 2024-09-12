package application.admin.start;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.admin.main.AdminMainPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StartPageController implements Initializable {
	
	@FXML private BorderPane bpStart;
	@FXML private VBox vbStart;
	@FXML private Button btnStart;
	@FXML private Button btnAdmin;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btnStart.setOnAction(event -> goMainPage());
		btnAdmin.setOnAction(event -> goAdminPage());
		
		AdminMainPageController.resetMainPageController();
		
		bpStart.setCenter(vbStart);
	}
	
	public void goMainPage() {
		// 메인으로.
		// 키오스크로.?

		AdminMainPageController.resetMainPageController();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/customer/main.fxml"));
			StartPage.getPrimaryStage().setScene(new Scene(root));
			StartPage.getPrimaryStage().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void goAdminPage() {
		AdminMainPageController.resetMainPageController();
		StartPage.getPrimaryStage().setTitle("CafeOrder - admin(POS)");

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/admin/main/AdminMainLayout.fxml"));
			StartPage.getPrimaryStage().setScene(new Scene(root));
			StartPage.getPrimaryStage().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void goStartPage() {
		bpStart.setCenter(vbStart);
	}
}
