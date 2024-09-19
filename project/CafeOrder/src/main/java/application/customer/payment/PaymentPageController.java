package application.customer.payment;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import application.customer.menu.Cart;
import application.customer.menu.MemberInfo;
import application.customer.menu.Page;
import application.customer.order.CartController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
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
        //ToDo
        // 테스트 member 코드 삭제 필요
        member = new MemberInfo(0, 100);
        int id = member.getId();
        int point = member.getPoint();
        int totalPaymentAmount = 0;
        
        if( takeInBtn.isSelected()) {
            //ToDo
            // 매장 식사의 경우
            orderReceipt.put("order_type", 0);
        }else if(takeOutBtn.isSelected()) {
            //ToDo
            // 포장의 경우
            orderReceipt.put("order_type", 1);

        } else {
            //ToDo
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

        //ToDo
        // 서버에 처리 커맨드와  orderReceipt 전송
        System.out.println("주문서: "+ orderReceipt.toString());

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
}
