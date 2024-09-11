package application.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuSelectController implements Initializable  {
	
	@Override
	public void initialize(URL url, ResourceBundle rb) { 
		//TO DO
	}
	
	@FXML private Button btn;
	@FXML private Button closeBtn;
	@FXML private Text optionTxt;
	
	private Stage mainStage;
	Stage pop;
	
	@FXML
	private void menuClick(MouseEvent event) {
		btn = (Button) event.getTarget();
		mainStage = (Stage) btn.getScene().getWindow().getScene().getWindow();
		
		pop = new Stage(StageStyle.DECORATED);
		pop.initModality(Modality.WINDOW_MODAL);
		pop.initOwner(mainStage);
		
		try {
			   //새로운 스테이지에 레이아웃 불러오기
			   Parent root = FXMLLoader.load(getClass().getResource("optionSelect.fxml"));
			   
			   //씬에 추가
			   Scene sc = new Scene(root);
			   pop.setScene(sc);
			   pop.setResizable(false); //팝업창 크기변경
			   
			   //보여주기
			   pop.show();
		  
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	} 
	
	public void closeBtnClick() { //close 버튼 눌렀을 때, 팝업창 아예 닫기
		  pop=(Stage) closeBtn.getScene().getWindow(); //스테이지 가져오기
		  pop.close(); //팝업창 닫기
	}
	
	public void cartBtnClick() { //cart 버튼 눌렀을 때
		
		// 장바구니에 메뉴 저장
		
		
		pop=(Stage) closeBtn.getScene().getWindow();
		pop.close(); //팝업창 닫기
	 }

	public void optionSubBtn() {
		int opNum = Integer.parseInt(optionTxt.getText());
		if (opNum <= 0) return;
		opNum--;
		optionTxt.setText(""+ opNum +"");
	}
	private int opNumMax = 3;
	public void optionAddBtn() {
		int opNum = Integer.parseInt(optionTxt.getText());
		if (opNum >= opNumMax) return;
		opNum++;
		optionTxt.setText(""+ opNum +"");
	}
}
