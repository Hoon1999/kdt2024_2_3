package application.customer.payment;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import application.customer.menu.Cart;
import application.customer.menu.MemberInfo;
import application.customer.menu.Page;
import application.customer.order.CartController;
import application.customer.order.OrderClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONObject orderReceipt = new JSONObject(); // 주문서.
        JSONArray productList = new JSONArray();
        MemberInfo member = MemberInfo.get();
        int id = member.getId();
        int point = member.getPoint();
        int totalPaymentAmount = 0;
        
        if( takeInBtn.isSelected()) {
            // 매장 식사의 경우
            orderReceipt.put("order_type", 0);
        }else if(takeOutBtn.isSelected()) {
            // 포장의 경우
            orderReceipt.put("order_type", 1);

        } else {
            // 둘 다 선택안한 경우
            // 결제 진행 불가
            return ;
        }

        // 변수에 포인트 값 저장
        if(earnBtn.isSelected()) {
            // 포인트 적립.. 없어도 될 듯?
            // 어차피 포인트 사용 후에도 포인트 적립이 이루어 져야한다.

        }else if(useBtn.isSelected()) {
            // 포인트 사용
            // 최종 결제 금액 = 결제 금액 - 포인트 보유량
            totalPaymentAmount = CartController.getSumPrice() - point;
            point = 0;
        }else {
            // 둘 다 선택안한 경우
            // 결제 진행 불가
            return ;
        }

        // 주문서 작성
        orderReceipt.put("id", id);
        for(Cart prod : CartController.getCartList()) {
            JSONObject prodObj = new JSONObject();
            prodObj.put("id", prod.id);
            prodObj.put("count", prod.count);

            JSONArray optList = new JSONArray();
            for(Cart opt: prod.options) {
                JSONObject optObj = new JSONObject();
                optObj.put("id", opt.id);
                optObj.put("count", opt.count);
                optList.put(optObj);
            }
            prodObj.put("options", optList);
            productList.put(prodObj);
        }
        orderReceipt.put("products", productList);

        //ToDo
        // 최종 결제 금액 만큼 결제 진행

        // 비회원이 아닌 경우
        // 최종 결제 금액의 일부를 포인트로 전환
        if(id != 0) {
            point += totalPaymentAmount * 0.05;
        }
        orderReceipt.put("point", point);

        // 서버에 처리 커맨드와  orderReceipt 전송
        int orderNumber = 0;
        try{
            OrderClient orderClient = OrderClient.getInstance();
            JSONObject obj = orderClient.sendOrder(orderReceipt);
            orderNumber = obj.getInt("data");
        } catch (IOException e) {
            System.out.println("서버 연결 오류 : " + e);
            e.printStackTrace();
        }
        showCountdownPopup((Stage) bp.getScene().getWindow(), orderNumber);
        // 메인 화면으로 복귀
        loadPage(Page.MAIN_PAGE);
    }
    public void loadPage(Page page) {
        String str ="";
        if(page == Page.MAIN_PAGE) {
            str = "main/mainPage";
        }
        AdminMainPageController.resetMainPageController();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/customer/" + str + ".fxml"));

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
    public void showCountdownPopup(Stage owner, int number) {
        // 팝업 생성
        Popup popup = new Popup();


        Label messageLabel = new Label("주문번호 : " + number);
        Label messageLabel2 = new Label("5초 후에 이 메세지가 사라집니다.");  // 첫 시작은 5초 후 메시지

        // VBox에 레이블 추가
        VBox popupLayout = new VBox(10, messageLabel, messageLabel2);
        popupLayout.setStyle("-fx-background-color: lightblue; -fx-padding: 20px; -fx-alignment: center;");
        popup.getContent().add(popupLayout);

        // 팝업 위치 설정 및 보이기
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        popup.setWidth(200);  // 팝업 폭
        popup.setHeight(100); // 팝업 높이
        popup.setX((screenWidth - popup.getWidth()) / 2); // 중앙 위치 X
        popup.setY((screenHeight - popup.getHeight()) / 2); // 중앙 위치 Y

        popup.setAutoHide(true);
        popup.show(owner);

        // 타임라인을 사용한 카운트다운
        Timeline countdown = new Timeline();
        countdown.setCycleCount(5);  // 5초 동안 카운트다운

        countdown.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    // 현재 메시지를 업데이트
                    String currentText = messageLabel2.getText();
                    int currentCount = Integer.parseInt(currentText.substring(0, 1));  // 현재 초를 추출
                    currentCount--;
                    messageLabel2.setText(currentCount + "초 후에 이 메세지가 사라집니다.");
                })
        );

        // 타임라인이 끝난 후 팝업 닫기
        countdown.setOnFinished(event -> popup.hide());
        countdown.play();
    }
}
