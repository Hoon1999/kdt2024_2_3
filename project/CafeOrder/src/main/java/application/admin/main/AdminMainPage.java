package application.admin.main;

import application.admin.start.StartPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMainPage extends Application {
	private static Stage primaryStage = null;
	public static Stage getPrimaryStage() {
		if (primaryStage == null) {
			primaryStage = StartPage.getPrimaryStage();
		}
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = getPrimaryStage();
		
		Parent root = FXMLLoader.load(getClass().getResource("AdminMainLayout.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("CafeOrder - admin(POS)");
		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}