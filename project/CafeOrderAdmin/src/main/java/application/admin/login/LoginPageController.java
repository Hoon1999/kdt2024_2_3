package application.admin.login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import org.json.JSONObject;

import application.admin.main.AdminMainPageController;
import javafx.scene.layout.BorderPane;

public class LoginPageController implements Initializable {
	@FXML private PasswordField pwfPassword;
	@FXML private Button btnOK;
	@FXML private Button btnCancel;
	
	@FXML private BorderPane bpKeypad;
	
	
	// TODO 이동 필요.
	//private static final String PASSWORD_FILE_NAME = "json\\password.json"; // on windows;
	private static final String PASSWORD_FILE_NAME = "json/password.json"; // on mac;
	
	public static String getPassword() {
		String pw;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(PASSWORD_FILE_NAME, Charset.forName("UTF-8")));
			
			String inJson = br.readLine();
			br.close();
			
			JSONObject root = new JSONObject(inJson);
			
			pw = root.getString("password");
		} catch (IOException e) {
			pw = "error";
		}
		
		return pw;
	}
	
	public static void setPassword(String password) {
		
		JSONObject root = new JSONObject();
		root.put("password", password);
		String jsonStr = root.toString();
		System.out.println(jsonStr);
		
		// write. file.
		Writer writer;
		try {
			writer = new FileWriter(PASSWORD_FILE_NAME, Charset.forName("UTF-8"));
			writer.write(jsonStr);
			writer.flush();
			writer.close();
			
			System.out.println("json pw 변경 성공.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("json pw 변경 실패.");
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btnOK.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("비밀번호 : " + pwfPassword.getText());
			}
		});
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				
			}
		});
		
		bpKeypad.setCenter(AdminMainPageController.getMainPageController().getKeypadController().getKeypad(pwfPassword));
	}


	
	
}
