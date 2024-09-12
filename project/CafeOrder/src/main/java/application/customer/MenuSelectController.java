package application.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuSelectController implements Initializable  {
	
	@Override
	public void initialize(URL url, ResourceBundle rb) { 
		if(gridPane != null)
			initProductList();
	}
	
	@FXML private Button btn;
	@FXML private Button closeBtn;
	@FXML private Text optionTxt;
	@FXML private GridPane gridPane;

	private Stage mainStage;
	Stage pop;

	private void initProductList() {
		//ToDo
		// 상품 목록을 조회합니다. (작성필요)
		// 조회한 상품 목록을 화면에 출력합니다. (완)
		// JSONObject products = getAllProducts();

		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				Group grp = new Group();
				GridPane.setRowIndex(grp,i);
				GridPane.setColumnIndex(grp,j);

				Button button = new Button();
				button.setPrefHeight(140.0);
				button.setPrefWidth(140.0);
				button.setTextAlignment(TextAlignment.CENTER);
				button.setOnMouseClicked(event -> {menuClick(event);});

				ImageView img = new ImageView();

				button.setGraphic(img);

				Label name = new Label("상품명");
				name.setLayoutX(10);
				name.setLayoutY(90);
				name.setPrefHeight(20.0);
				name.setPrefWidth(100.0);
				Label price = new Label("가격");
				price.setLayoutX(10);
				price.setLayoutY(110);
				price.setPrefHeight(20.0);
				price.setPrefWidth(100.0);

				grp.getChildren().addAll(button, name, price);
				gridPane.getChildren().addAll(grp);
			}
		}

	}
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
