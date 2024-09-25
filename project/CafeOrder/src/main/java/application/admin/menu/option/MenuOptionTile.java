package application.admin.menu.option;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import application.admin.etc.option.SubOption;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class MenuOptionTile {

	Parent menuOptionTile;
	
	Label lbOptionId;
	Label lbOptionName;
	Button btnToSale;
	Button btnToStockOut;
	
	public Parent getMenuOptionTile(int subOptionId) {
		try {
			menuOptionTile = FXMLLoader.load(getClass().getResource("MenuOptionTile.fxml"));
			
			// 요소 가져오기.
			lbOptionId = (Label) menuOptionTile.lookup("#lbOptionId");
			lbOptionName = (Label) menuOptionTile.lookup("#lbOptionName");
			btnToSale = (Button) menuOptionTile.lookup("#btnToSale");
			btnToStockOut = (Button) menuOptionTile .lookup("#btnToStockOut");
			
		} catch (Exception e) {
			System.out.println("!(menuOptionTile-Exception) :" + e.getMessage());
			Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("~!(menuOptionTile-Exception) : " + e.getMessage());
		}
		
		SubOption option = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(subOptionId);
		
		if (option != null) {
			lbOptionId.setText(String.valueOf(option.getId()));
			lbOptionName.setText(option.getName());

			if (option.getIsStockOut()) {
				// 품절이면, 품절로 변경 버튼은 비활성화. / 판매로 변경 버튼은 활성화.
				btnToStockOut.setDisable(true);
				btnToSale.setDisable(false);
			}
			else
			{
				// 아니면, 품절로 변경 버튼은 활성화. / 판매로 변경 버튼은 비활성화.
				btnToStockOut.setDisable(false);
				btnToSale.setDisable(true);
			}
			
			btnToSale.setOnAction(event -> toSale(subOptionId));
			btnToStockOut.setOnAction(event -> toStockOut(subOptionId));
		}
		
		return menuOptionTile;
	}
	
	public void toStockOut(int selectedSubOptionId) {
		SubOption selectedSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(selectedSubOptionId);
		selectedSubOption.toStockOut();
		
		AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuOptionPageController().getMenuOptionManagementPage();
	}
	
	public void toSale(int selectedSubOptionId) {
		SubOption selectedSubOption = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionById(selectedSubOptionId);
		selectedSubOption.toInStock();
		
		AdminMainPageController.getMainPageController().getMenuManagementPageController().getMenuOptionPageController().getMenuOptionManagementPage();
	}
}
