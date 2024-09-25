package application.admin.etc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.login.LoginPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class KeypadController {
	private Parent keypad = null;
	
	private Button btn1, btn2, btn3, btn4, btn5;
	private Button btn6, btn7, btn8, btn9, btn0;
	private Button btnEraseOne, btnEraseAll;
	
	public Parent getKeypad(TextField textField, boolean isSpinner) {
		if(keypad == null) {
			try {
				// System.out.println("init keypad");
				keypad = FXMLLoader.load(getClass().getResource("/application/admin/etc/Keypad.fxml"));
				
				// 버튼 가져오기.
				btn1 = (Button) keypad.lookup("#btn1");
				btn2 = (Button) keypad.lookup("#btn2");
				btn3 = (Button) keypad.lookup("#btn3");
				btn4 = (Button) keypad.lookup("#btn4");
				btn5 = (Button) keypad.lookup("#btn5");
				btn6 = (Button) keypad.lookup("#btn6");
				btn7 = (Button) keypad.lookup("#btn7");
				btn8 = (Button) keypad.lookup("#btn8");
				btn9 = (Button) keypad.lookup("#btn9");
				btn0 = (Button) keypad.lookup("#btn0");
				btnEraseOne = (Button) keypad.lookup("#btnEraseOne");
				btnEraseAll = (Button) keypad.lookup("#btnEraseAll");
			} catch (IOException e) {
				System.out.println("!(KeypadCont-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(KeypadCont-IOException) : " + e.getMessage());
			}
		}
		
		btn1.setOnAction(e -> appentText(textField, "1"));
		btn2.setOnAction(e -> appentText(textField, "2"));
		btn3.setOnAction(e -> appentText(textField, "3"));
		btn4.setOnAction(e -> appentText(textField, "4"));
		btn5.setOnAction(e -> appentText(textField, "5"));
		btn6.setOnAction(e -> appentText(textField, "6"));
		btn7.setOnAction(e -> appentText(textField, "7"));
		btn8.setOnAction(e -> appentText(textField, "8"));
		btn9.setOnAction(e -> appentText(textField, "9"));
		btn0.setOnAction(e -> appentText(textField, "0"));
		btnEraseOne.setOnAction(e -> eraseOne(textField, isSpinner));
		btnEraseAll.setOnAction(e -> eraseAll(textField));
		
		// System.out.println("return keypad");
		return keypad;
	}
	
	private void appentText(TextField textField, String str) {
		textField.requestFocus();
		textField.appendText(str);
	}
	
	private void eraseOne(TextField textField, boolean isSpinner) {
		textField.requestFocus();
		String str = textField.getText();
		if (!str.isEmpty()) {
			str = str.substring(0, str.length()-1);
		}
		textField.clear();
		textField.appendText(str);
		
		if (isSpinner) {
			if (textField.getText().equals("")) {
				textField.appendText("0");
			}
		}
	}
	
	private void eraseAll(TextField textField) {
		textField.requestFocus();
		textField.clear();
	}
}
