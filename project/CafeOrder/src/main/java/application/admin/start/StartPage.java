package application.admin.start;

import java.io.IOException;

import application.customer.order.OrderClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class StartPage extends Application {
	private static Stage primaryStage = null;
	public static Stage getPrimaryStage() {
		if (primaryStage == null) {
			primaryStage = new Stage();
		}
		return primaryStage;
	}

	public static void main(String[] args) {
		try {
			OrderClient orderClient = OrderClient.getInstance();
			orderClient.connect();
			orderClient.makenewid(); // 키오스크 아이디 할당
		} catch (IOException e) {
			System.out.println("서버 접속 실패: " +e.getMessage());
			e.printStackTrace();
		}

		launch(args);
	}
	
	private static Scene scene;
	public static Scene getScene() { return scene; }
	
	public static void initStartPage() {
		getPrimaryStage().setTitle("CafeOrder");
		getPrimaryStage().setScene(scene);

//		getPrimaryStage().initStyle(StageStyle.UNDECORATED);
//		getPrimaryStage().setResizable(false);
		
		getPrimaryStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				System.out.println("시스템 종료");
				System.exit(0);
			}
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = getPrimaryStage();
				
				
		Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
		scene = new Scene(root);
		
		initStartPage();
		getPrimaryStage().show();
		
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	
}