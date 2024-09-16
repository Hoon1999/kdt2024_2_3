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
import javafx.stage.Screen;

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
			Parent root = FXMLLoader.load(getClass().getResource("/application/customer/main/mainPage.fxml"));

			Screen screen = Screen.getPrimary(); // 현재 화면 정보를 가져온다. 왜 가져오나요? - 전체화면으로 출력하기 위해서
			double width = screen.getVisualBounds().getWidth(); // 화면의 너비를 가져온다
			double height = screen.getVisualBounds().getHeight(); // 화면의 높이를 가져온다
			StartPage.getPrimaryStage().setX(0); // 좌측 상단에서부터 프로그램을 출력한다. 이거 설정 안하면 부모 창 기준으로 위치가 결정된다.
			StartPage.getPrimaryStage().setY(0);

			StartPage.getPrimaryStage().setScene(new Scene(root, 1080, 1920));
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
