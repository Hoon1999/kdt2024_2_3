package application.admin.menu.option;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.admin.etc.option.SubOption;
import application.admin.login.LoginPageController;
import application.admin.main.AdminMainPageController;

public class MenuOptionPageController {
	
	// FXML 페이지 및 요소.
	private Parent menuOptionManagementPage = null;

	ListView<String> lvOptionList;
	ObservableList<String> optionList;
	
	VBox vbSubOptionList;
	ObservableList subOptionList;
	
	public Parent getMenuOptionManagementPage() {
		if (menuOptionManagementPage == null) {
			try {
				System.out.println("init menuOptionManagementPage");
				// 페이지 가져오기. 
				menuOptionManagementPage = FXMLLoader.load(getClass().getResource("MenuOptionManagementPage.fxml"));
				
				Stage stage = new Stage(); // 요소 로딩을 위한 임시 스테이지 생성.
				stage.setScene(new Scene(menuOptionManagementPage));
				stage.setWidth(0);
				stage.setHeight(0);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				
				// 요소 가져오기.
				lvOptionList = (ListView<String>) menuOptionManagementPage.lookup("#lvOptionList");
				vbSubOptionList = (VBox) menuOptionManagementPage.lookup("#vbSubOptionList");
				
				// optionList 와 lvOptionList 연결.
				optionList = lvOptionList.getItems();
				
				// subOptionList 와 vbSubOptionList 연결.
				subOptionList = vbSubOptionList.getChildren();
				
				// 리스트뷰 changeListener 추가.
				lvOptionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
						System.out.println("menu option list changed :: " + oldValue + " -> " + newValue);
						
						subOptionList.clear();
						
						// 옵션 선택(변경) 시 해당 옵션에 맞게 vbox 설정.
						if (newValue != null) {
							LinkedList selectedSubOptionList = AdminMainPageController.getMainPageController().getOptionManager().findSubOptionsByOption(newValue);
							
							if (selectedSubOptionList == null || selectedSubOptionList.isEmpty()) {
								subOptionList.add(new Label("현재 선택된 옵션의 하위옵션이 없습니다."));
							}
							else {
								Iterator<SubOption> iter = selectedSubOptionList.iterator();
								while (iter.hasNext()) {
									SubOption subOption = iter.next();
									MenuOptionTile menuOptionTile = new MenuOptionTile();
									Parent tile = menuOptionTile.getMenuOptionTile(subOption.getId());
									
									subOptionList.add(tile);
								}
							}
						}
					}
				});
				
				stage.close(); // 임시 스테이지 close.
				
				System.out.println("init success menuOptionManagementPage");
			} catch (IOException e) {
				System.out.println("!(MenuOptionPageController-IOException) :" + e.getMessage());
				Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, e);
				System.out.println("~!(MenuOptionPageController-IOException) : " + e.getMessage());
			}
		} // ~ if (menuOptionManagementPage == null).
		
		/////////////////////////////////////////
		
		// 옵션 리스트 초기화.
		optionList = lvOptionList.getItems();
		optionList.setAll(AdminMainPageController.getMainPageController().getOptionManager().getOptionNameList());
		lvOptionList.setItems(optionList);
		
		// 리스트에서 제일 앞의 요소 선택.
		lvOptionList.getSelectionModel().clearSelection();
		lvOptionList.getSelectionModel().selectFirst();
		
		
		System.out.println("return menuOptionManagementPage");
		return menuOptionManagementPage;
		
	}
	
}
