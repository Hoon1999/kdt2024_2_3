package application.customer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("root.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setTitle("OrderMain");
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
