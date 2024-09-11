package application.admin.main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.admin.etc.KeypadController;
import application.admin.login.LoginPageController;
import application.admin.menu.MenuManagementPageController;
import application.admin.order.OrderManagementPageController;
import application.admin.product.ProductManagementPageController;
import application.admin.sale.SaleManagementPageController;
import application.admin.setting.SettingManagementPageController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class AdminMainPageController implements Initializable {

	// FXML 페이지 및 요소.
	@FXML private BorderPane bp;
	@FXML private ToggleGroup group;
	private ObservableList<Toggle> toggleList;

	@FXML private RadioButton btnOrderManagement;
	@FXML private RadioButton btnSaleManagement;
	@FXML private RadioButton btnProductManagement;
	@FXML private RadioButton btnMenuManagement;
	@FXML private RadioButton btnSettingManagement;
	
	private static AdminMainPageController mainPageController = null;
	
	public static AdminMainPageController getMainPageController() {
		if (mainPageController == null) {
			mainPageController = new AdminMainPageController();
		}
		return mainPageController;
	}

	public static void resetMainPageController() {
		mainPageController = null;
	}
	
	
	private KeypadController keypadController;
	
	public KeypadController getKeypadController() {
		if (keypadController == null) {
			keypadController = new KeypadController();
		}
		return keypadController;
	}

	private OrderManagementPageController orderManagementPageController = null;

	public OrderManagementPageController getOrderManagementPageController() {
		if (orderManagementPageController == null) {
			orderManagementPageController = new OrderManagementPageController();
		}
		return orderManagementPageController;
	}
	
	private SaleManagementPageController saleManagementPageController = null;
	
	public SaleManagementPageController getSaleManagementPageController() {
		if (saleManagementPageController == null) {
			saleManagementPageController = new SaleManagementPageController();
		}
		return saleManagementPageController;
	}
	
	private ProductManagementPageController productManagementPageController = null;
	
	public ProductManagementPageController getProductManagementPageController() {
		if (productManagementPageController == null) {
			productManagementPageController = new ProductManagementPageController();
		}
		return productManagementPageController;
	}
	
	private MenuManagementPageController menuManagementPageController = null;
	
	public MenuManagementPageController getMenuManagementPageController() {
		if (menuManagementPageController == null) {
			menuManagementPageController = new MenuManagementPageController();
		}
		return menuManagementPageController;
	}
	
	private SettingManagementPageController settingManagementPageController = null;
	
	public SettingManagementPageController getSettingManagementPageController() {
		if (settingManagementPageController == null) {
			settingManagementPageController = new SettingManagementPageController();
		}
		return settingManagementPageController;
	}
	
	
	// initialize.
	public void initialize(URL url, ResourceBundle rb) {
		
		toggleList = group.getToggles();
		System.out.println("asd : " + toggleList);
		group.getToggles().addListener(new ListChangeListener<Toggle> () {
			@Override
			public void onChanged(Change<? extends Toggle> arg0) {
				System.out.println(arg0);
			}
		});
		
		ObservableList<Toggle> toggleList2 = group.getToggles();
		group.selectToggle(toggleList2.get(0));
		
		for (int i = 0; i < group.getToggles().size(); i++) {
			((RadioButton) (group.getToggles().get(i))).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("::::::+" + arg0);
				}
			});
		}
		
		btnOrderManagement.setOnAction(event -> callOrderManagementPage(null));
		btnSaleManagement.setOnAction(event -> callSaleManagementPage(null));
		btnProductManagement.setOnAction(event -> callProductManagementPage(null));
		btnMenuManagement.setOnAction(event -> callMenuManagementPage(null));
		btnSettingManagement.setOnAction(event -> callSettingManagementPage(null));
		
//		touch/mouse press events.
//		btnOrderManagement.setOnTouchPressed(event -> callOrderManagementPage(null));
//		btnSaleManagement.setOnTouchPressed(event -> callSaleManagementPage(null));
//		btnProductManagement.setOnTouchPressed(event -> callProductManagementPage(null));
//		btnMenuManagement.setOnTouchPressed(event -> callMenuManagementPage(null));
//		btnSettingManagement.setOnTouchPressed(event -> callSettingManagementPage(null));
//		
//		btnOrderManagement.setOnMousePressed(event -> callOrderManagementPage(null));
//		btnSaleManagement.setOnMousePressed(event -> callSaleManagementPage(null));
//		btnProductManagement.setOnMousePressed(event -> callProductManagementPage(null));
//		btnMenuManagement.setOnMousePressed(event -> callMenuManagementPage(null));
//		btnSettingManagement.setOnMousePressed(event -> callSettingManagementPage(null));
		
		
		group.getToggles().get(0).setSelected(true);
		
		// TODO 버튼 자체로 동작시킬 수 있느닞....
//		btnOrderManagement.
		callOrderManagementPage(null);
	}
	
	// 각 페이지 호출 메소드들.
	public void callOrderManagementPage(Event event) {
		group.selectToggle(group.getToggles().get(0));
		setPage(btnOrderManagement.getUserData().toString());
	}
	
	public void callSaleManagementPage(Event event) {
		group.selectToggle(group.getToggles().get(1));
		setPageWithLogin(btnSaleManagement.getUserData().toString());
	}
	
	public void callProductManagementPage(Event event) {
		group.selectToggle(group.getToggles().get(2));
		setPageWithLogin(btnProductManagement.getUserData().toString());
	}
	
	public void callMenuManagementPage(Event event) {
		group.selectToggle(group.getToggles().get(3));
		setPageWithLogin(btnMenuManagement.getUserData().toString());
	}
	
	public void callSettingManagementPage(Event event) {
		group.selectToggle(group.getToggles().get(4));
		setPageWithLogin(btnSettingManagement.getUserData().toString());
	}
	

	// 페이지 호출 메소드.
	public void setPage(String pageName) {
		if (pageName.equals(btnOrderManagement.getUserData().toString())) {
			bp.setCenter(AdminMainPageController.getMainPageController()
					.getOrderManagementPageController().getOrderManagementPage());			
		}
		else if (pageName.equals(btnSaleManagement.getUserData().toString())) {
			bp.setCenter(AdminMainPageController.getMainPageController()
					.getSaleManagementPageController().getSaleManagementPage());
		}
		else if (pageName.equals(btnProductManagement.getUserData().toString())) {
			bp.setCenter(AdminMainPageController.getMainPageController()
					.getProductManagementPageController().getProductManagementPage());
		}
		else if (pageName.equals(btnMenuManagement.getUserData().toString())) {
			bp.setCenter(AdminMainPageController.getMainPageController()
					.getMenuManagementPageController().getMenuManagementPage());
		}
		else if (pageName.equals(btnSettingManagement.getUserData().toString())) {
			bp.setCenter(AdminMainPageController.getMainPageController()
					.getSettingManagementPageController().getSettingManagementPage());
		}
	}

	// 로그인 필요한 페이지 호출 메소드.
	public void setPageWithLogin(String pageName) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/admin/login/LoginPage.fxml")); // 로그인 fxml 열기.
			
			PasswordField pwfPassword = (PasswordField) root.lookup("#pwfPassword"); // 비밀번호 입력 필드.
			Button btnOk = (Button) root.lookup("#btnOK"); // 확인 버튼.
			Button btnCancel = (Button) root.lookup("#btnCancel"); // 취소 버튼.

			// 로그인 화면으로 띄움.
			bp.setCenter(root);

			// 취소 버튼 클릭 시, 주문 관리 탭으로 이동.
			btnCancel.setOnAction(event -> callOrderManagementPage(event));

			// 확인 버튼 클릭 시, 입력한 비밀번호 확인 후,
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (pwfPassword.getText().equals(LoginPageController.getPassword())) { // 비밀번호 일치하면,
						setPage(pageName); // 해당 페이지로 이동.
					} else { // 일치하지 않으면,
						callOrderManagementPage(arg0); // 주문 관리 페이지로 이동.
					}
				}
			});
		} catch (Exception e) {
			System.out.println("!!" + e.getMessage());
			Logger.getLogger(AdminMainPageController.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("~!" + e.getMessage());
		}
	}
	
}
