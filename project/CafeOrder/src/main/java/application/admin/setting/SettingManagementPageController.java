package application.admin.setting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SettingManagementPageController {

	// FXML 페이지 및 요소.
	private Parent settingManagementPage = null;
	
	private VBox vbDefault;
	private BorderPane bp;
	private Button btnChangePassword;
	private Button btnLogout;
	private Button btnClose;
	
	private ChangePasswordPageController changePasswordPageController = null;
	public ChangePasswordPageController getChangePasswordPageController() {
		if (changePasswordPageController == null) {
			changePasswordPageController = new ChangePasswordPageController();
		}
		return changePasswordPageController;
	}
	
	
	public Parent getSettingManagementPage() {
		if(settingManagementPage == null) {
			try {
				System.out.println("init settingManagementPage");
				settingManagementPage = FXMLLoader.load(getClass().getResource("SettingManagementPage.fxml"));
				
				// 요소 가져오기.
				vbDefault = (VBox) settingManagementPage.lookup("#vbDefault");
				bp = (BorderPane) settingManagementPage.lookup("#bp");
				btnChangePassword = (Button) settingManagementPage.lookup("#btnChangePassword");
				btnLogout = (Button) settingManagementPage.lookup("#btnLogout");
				btnClose = (Button) settingManagementPage.lookup("#btnClose");
			} catch (IOException e) {
				System.out.println("!(settingManagementPage-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(settingManagementPage-IOException) : " + e.getMessage());
			} catch (Exception e) {
				System.out.println("!(settingManagementPage-Exception) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(settingManagementPage-Exception) : " + e.getMessage());
			}
		}

		
		// 액션 설정.
		btnChangePassword.setOnAction(event -> setChangePasswordPage());
		
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("로그아웃");
				
				AdminMainPageController.resetMainPageController();
				StartPage.initStartPage();
//				StartPage.getPrimaryStage().setScene(StartPage.getScene());
				
			}
		});
		
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("닫기");

				// TODO 나중에 끄지 전에 연결 같은거 해제하고, 저장할거 저장하는거 필요.
				// 지금은 그냥 프로그램 종료시켜버림.
				AdminMainPage.getPrimaryStage().close();
			}
		});
		
		// 기본페이지로.
		setSettingPageDefault();
		
		System.out.println("return settingManagementPage");
		return settingManagementPage;
	}
	
	public void setSettingPageDefault() {
		AdminMainPageController.getMainPageController().getSettingManagementPageController().bp.setCenter(
				AdminMainPageController.getMainPageController().getSettingManagementPageController().vbDefault);
		
//		bp.setCenter(vbDefault);
	}
	
	public void setChangePasswordPage() {
		bp.setCenter(AdminMainPageController.getMainPageController().getSettingManagementPageController().getChangePasswordPageController().getChangePasswordPage());
	}
}
