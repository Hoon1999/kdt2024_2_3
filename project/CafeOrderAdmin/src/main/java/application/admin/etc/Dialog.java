package application.admin.etc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog {
	
	private Stage primaryStage;	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}	
	
	//커스텀 다이얼로그 실행
	public void makeCustomDialog(String title, String msg) {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle(title);
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("AlarmDialog.fxml"));
			TextArea txtTitle = (TextArea) parent.lookup("#txtMsg");
			txtTitle.setText(msg);
			Button btnOk = (Button) parent.lookup("#btnOk");
			btnOk.setOnAction(event->dialog.close());	
			Scene scene = new Scene(parent);
			
			dialog.setScene(scene); 
			dialog.setResizable(false);
			dialog.show();
			
		} catch (Exception e) {
			
		}
		
	}
}
