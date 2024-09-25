package application.admin.setting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.etc.AlarmDialog;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPage;
import application.admin.main.AdminMainPageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

public class ChangePasswordPageController {
	
	// FXML 페이지 및 요소.
	private Parent changePasswordPage = null;
	
	private PasswordField pwfCurrentPassword;
	private PasswordField pwfChangePassword;
	private PasswordField pwfCheckPassword;
	private Button btnChange;
	private Button btnCancel;
	private BorderPane bpKeypad;
	
	public Parent getChangePasswordPage() {
		if (changePasswordPage == null) {
			try {
				System.out.println("init changePasswordPage");
				// 페이지 가져오기.
				changePasswordPage = FXMLLoader.load(getClass().getResource("ChangePasswordPage.fxml"));
				
				// 요소 가져오기.
				pwfCurrentPassword = (PasswordField) changePasswordPage.lookup("#pwfCurrentPassword");
				pwfChangePassword = (PasswordField) changePasswordPage.lookup("#pwfChangePassword");
				pwfCheckPassword = (PasswordField) changePasswordPage.lookup("#pwfCheckPassword");
				btnChange = (Button) changePasswordPage.lookup("#btnChange");
				btnCancel = (Button) changePasswordPage.lookup("#btnCancel");
				bpKeypad = (BorderPane) changePasswordPage.lookup("#bpKeypad");
			} catch (IOException e) {
				System.out.println("!(ChangePasswordPageCont-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(ChangePasswordPageCont-IOException) : " + e.getMessage());
			}
		}
		
		// 액션 설정.KeypadController
		pwfCurrentPassword.setOnTouchPressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfCurrentPassword, false)));
		pwfCurrentPassword.setOnMousePressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfCurrentPassword, false)));
		
		pwfChangePassword.setOnTouchPressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfChangePassword, false)));
		pwfChangePassword.setOnMousePressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfChangePassword, false)));
		
		pwfCheckPassword.setOnTouchPressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfCheckPassword, false)));
		pwfCheckPassword.setOnMousePressed(e -> bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfCheckPassword, false)));
		
		btnChange.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
//				System.out.println("---------------------------------------");
//				System.out.println("현재 : " + pwfCurrentPassword.getText());
//				System.out.println("바꿀 : " + pwfChangePassword.getText());
//				System.out.println("확인 : " + pwfCheckPassword.getText());
				
				if (pwfCurrentPassword.getText().equals(LoginPageController.getPassword())) {
					if (!pwfChangePassword.getText().isEmpty() && pwfChangePassword.getText().equals(pwfCheckPassword.getText())) {
						LoginPageController.setPassword(pwfChangePassword.getText());
						System.out.println("비밀번호 변경 : " + pwfChangePassword.getText());

						AlarmDialog dialog = new AlarmDialog();
						dialog.setPrimaryStage(AdminMainPage.getPrimaryStage());
						dialog.makeAlarmDialog("비밀번호 변경 성공", "비밀번호 변경 : " + pwfChangePassword.getText());
					}
					else {
						// 비밀번호 확인 불일치.
						System.out.println("비밀번호 확인 불일치. (또는 입력 없음)");

						AlarmDialog dialog = new AlarmDialog();
						dialog.setPrimaryStage(AdminMainPage.getPrimaryStage());
						dialog.makeAlarmDialog("비밀번호 변경 실패", "비밀번호 확인 불일치(또는 입력 없음).");
					}
				}
				else {
					// 현재 비밀번호 불일치.
					System.out.println("현재 비밀번호 불일치.");
					AlarmDialog dialog = new AlarmDialog();
					dialog.setPrimaryStage(AdminMainPage.getPrimaryStage());
					dialog.makeAlarmDialog("비밀번호 변경 실패", "현재 비밀번호 불일치.");
				}
				
				goSettingDefaultPage();
			}
		});
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("취소");
				goSettingDefaultPage();
			}
		});

		
		System.out.println("return changePasswordPage");
		return changePasswordPage;
		
	}
	
	private void goSettingDefaultPage() {
		// 모든 필드의 텍스트를 지우고,
		pwfCurrentPassword.setText("");
		pwfChangePassword.setText("");
		pwfCheckPassword.setText("");
		
		// 키패드도 지움.
		bpKeypad.setCenter(null);
		
		// 다시 기본 로그인 관리.
		AdminMainPageController.getMainPageController().getSettingManagementPageController().setSettingPageDefault();
	}
}
