package application.customer.main;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML Button memberLoginBtn;
    @FXML Button nonMemberOrderBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ToDo
    }
    /**
     * 상품 선택 페이지로 이동합니다.*/
    @FXML
    private void gotoPage() {
        AdminMainPageController.resetMainPageController();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/customer/order/orderPage.fxml"));

            Screen screen = Screen.getPrimary(); // 현재 화면 정보를 가져온다
            //double width = screen.getVisualBounds().getWidth(); // 화면의 너비를 가져온다
            //double height = screen.getVisualBounds().getHeight(); // 화면의 높이를 가져온다
            StartPage.getPrimaryStage().setX(0); // 좌측 상단에서부터 프로그램을 출력한다. 이거 설정 안하면 부모 창 기준으로 위치가 결정된다.
            StartPage.getPrimaryStage().setY(0);

            // 크기 변경 감지용 ChangeListener
            ChangeListener<Number> sizeChangeListener = (observable, oldValue, newValue) -> {
                double width2 = StartPage.getPrimaryStage().getWidth();
                double height2 = StartPage.getPrimaryStage().getHeight();
                // 여기서 크기 변경 시 수행할 작업 수행
                System.out.println("Width: " + width2 + ", Height: " + height2);
            };

            // 너비와 높이 프로퍼티에 크기변경감지 리스너 추가
            StartPage.getPrimaryStage().widthProperty().addListener(sizeChangeListener);
            StartPage.getPrimaryStage().heightProperty().addListener(sizeChangeListener);

            StartPage.getPrimaryStage().setScene(new Scene(root, 1080, 1920)); // FHD(1920, 1080) 세로로 돌리면 1080, 1920 pixel 이다.
            StartPage.getPrimaryStage().show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
