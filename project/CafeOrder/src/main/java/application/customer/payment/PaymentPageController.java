package application.customer.payment;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import application.customer.menu.Page;
import application.customer.menu.SharedLabelText;
import application.customer.order.CartController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PaymentPageController  implements Initializable {
    @FXML
    BorderPane bp;
    @FXML
    ToggleButton takeInBtn;
    @FXML
    ToggleButton takeOutBtn;
    @FXML
    ToggleButton earnBtn;
    @FXML
    ToggleButton useBtn;
    @FXML
    Button cancelBtn;
    @FXML
    Button paymentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
           if(bp != null) {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/customer/order/cart.fxml"));
               Parent root = loader.load();
               ((CartController)loader.getController()).drawCart(Page.PAYMENT_PAGE);
               bp.setCenter(root);


           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    @FXML
    public void cancel() {
        //Todo
        // 이전 화면으로 되돌아가는 코드
        AdminMainPageController.resetMainPageController();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/customer/order/orderPage.fxml"));

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
    @FXML
    public void payment() {
        //ToDo
        // 결제 진행하는 코드
//        for(ToggleGroup tg: ) {
//
//        }
        if( takeInBtn.isSelected()) {
            //ToDo
            // 매장 식사의 경우
            // 주문서에 포장 여부를 안적었네
        }else if(takeOutBtn.isSelected()) {
            //ToDo
            // 포장의 경우

        } else {
            //ToDo
            // 둘 다 선택안한 경우
            // 결제 진행 불가
        }

        if(earnBtn.isSelected()) {
            //ToDo
            // 포인트 적립

        }else if(useBtn.isSelected()) {
            //ToDo
            // 포인트 사용

        }else {
            //ToDo
            // 둘 다 선택안한 경우
            // 결제 진행 불가
        }
    }

}
