package application.customer.payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

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
               Parent root = FXMLLoader.load(getClass().getResource( "/application/customer/order/cart.fxml"));
               bp.setCenter(root);
           }
           ToggleGroup toggleGroup1 = new ToggleGroup();
           takeInBtn.setToggleGroup(toggleGroup1);
           takeOutBtn.setToggleGroup(toggleGroup1);

           ToggleGroup toggleGroup2 = new ToggleGroup();
           earnBtn.setToggleGroup(toggleGroup2);
           useBtn.setToggleGroup(toggleGroup2);

       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    @FXML
    public void cancel() {
        //Todo
        // 이전 화면으로 되돌아가는 코드
    }
    @FXML
    public void payment() {
        //ToDo
        // 결제 진행하는 코드
    }

}
