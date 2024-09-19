package application.customer.login;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.customer.menu.MemberInfo;
import application.customer.order.OrderClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

public class LoginController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	Stage stage;
	Parent root;
	
	@FXML Button btn1;
	@FXML Button btn2;
	@FXML Button btn3;
	@FXML Button btn4;
	@FXML Button btn5;
	@FXML Button btn6;
	@FXML Button btn7;
	@FXML Button btn8;
	@FXML Button btn9;
	@FXML Button btn0;
	@FXML TextField textField;
	@FXML Button goMainBtn;
	
	public void gotoPage() {
		try{
			stage = (Stage) goMainBtn.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource(  "/application/customer/order/orderPage.fxml"));

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e) {

		}

	}
	
	public void numBtnClick(ActionEvent event) throws IOException {
		String nval = ((Button)event.getSource()).getText();
		textField.appendText(nval);
	}
	@FXML
	public void cancle() {
		//close 버튼 눌렀을 때, 팝업창 아예 닫기
		Stage pop = (Stage) goMainBtn.getScene().getWindow(); //스테이지 가져오기
		pop.close(); //팝업창 닫기
	}
	@FXML
	public void auth() {
		String phoneNumber = textField.getText();
		OrderClient orderClient = OrderClient.getInstance();
		JSONObject obj = orderClient.findMember(phoneNumber) ; // phone number 로 회원조회. return 값 { id: 회원번호, point: 포인트값 }
		int id = obj.getInt("id");
		if(id == 0) {
			// 회원이 아님
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("확인");
			alert.setHeaderText(null);
			alert.initStyle(StageStyle.UTILITY); // 타이틀바 간결화. 삭제는 없는것 같다.
			alert.setGraphic(null); // 내부 아이콘 삭제
			alert.setContentText("해당 전화번호로 회원가입 하시겠습니까?");

			// 예/아니요 버튼을 기본 제공하는 ButtonType 으로 설정
			ButtonType yesButton = new ButtonType("예");
			ButtonType noButton = new ButtonType("아니요");

			alert.getButtonTypes().setAll(yesButton, noButton);

			// 사용자 입력 대기
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == yesButton) {
				// 사용자가 회원 가입에 동의
				orderClient.joinMember(phoneNumber); // 회원가입 요청
				obj = orderClient.findMember(phoneNumber); // phone number 로 회원조회
			}
			else if (result.isPresent() && result.get() == noButton) {
				// 사용자가 회원 가입에 거부
				// 아무일도 안일어남.
			}
		}
		MemberInfo member = new MemberInfo(obj.getInt("id"), obj.getInt("point"));
		gotoPage();
	}
	
}
