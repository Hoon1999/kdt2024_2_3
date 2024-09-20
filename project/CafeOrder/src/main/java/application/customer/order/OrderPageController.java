package application.customer.order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import application.customer.menu.Page;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrderPageController implements Initializable {

	@FXML
	private BorderPane bp;
	@FXML
	private VBox vbox;
	@FXML
	private VBox cart;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//TODO
		drawButtons();
		loadPage(-1);
	}
	@FXML
	private void recommendClick() {
		loadPage(-1);
	}
	@FXML
	private void previousOrderClick() {
		loadPage(-2);
	}
	private void drawButtons() {
		// 카테고리 목록을 요청합니다.
		// 가져온 카테고리를 출력합니다.
		OrderClient orderClient = OrderClient.getInstance();
		JSONObject data = orderClient.getCategories();
		JSONArray categories = data.getJSONArray("data");
		for(int i = 0; i < categories.length(); i++) {
			JSONObject categoryObj =(JSONObject) categories.get(i);
			Button button = new Button();
			button.setMaxWidth(Double.MAX_VALUE);
			button.setPrefHeight(70.0);
			VBox.setVgrow(button, Priority.ALWAYS);
			button.setMnemonicParsing(false);
			button.setTextAlignment(TextAlignment.CENTER);
			button.setWrapText(true);
			button.setText(categoryObj.getString("name"));
			button.setOnMouseClicked(event -> {loadPage(categoryObj.getInt("id"));});

			vbox.getChildren().addAll(button);
		}
	}
	private void loadPage(int id) {
		int count = 0;
		try {
//			Parent root = FXMLLoader.load(getClass().getResource( "/application/customer/order/" + page + ".fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/customer/order/productList.fxml"));
			Parent root = loader.load();
			OrderPageOptionPopUpController controller = (OrderPageOptionPopUpController) loader.getController();
			controller.setSelectedCategoryId(id);
			bp.setCenter(root);
			cartReDraw();

		} catch (IOException e) {
			Logger.getLogger(OrderPageController.class.getName()).log(Level.SEVERE, null, e);

		}
	}
	public void cartReDraw() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/customer/order/cart.fxml"));
		Parent root = loader.load();
		((CartController)loader.getController()).drawCart(Page.ORDER_PAGE);
		bp.setRight(root);
	}
	public void goPaymentPage() {
		if(CartController.isCartEmpty()) {
			//ToDo
			// 카트가 비어있습니다. 출력
			return ;
		}
		AdminMainPageController.resetMainPageController();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/customer/payment/paymentPage.fxml"));

			Screen screen = Screen.getPrimary(); // 현재 화면 정보를 가져온다
			StartPage.getPrimaryStage().setX(0); // 좌측 상단에서부터 프로그램을 출력한다. 이거 설정 안하면 부모 창 기준으로 위치가 결정된다.
			StartPage.getPrimaryStage().setY(0);

			StartPage.getPrimaryStage().setScene(new Scene(root, 1080, 1920)); // FHD(1920, 1080) 세로로 돌리면 1080, 1920 pixel 이다.
			StartPage.getPrimaryStage().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

















