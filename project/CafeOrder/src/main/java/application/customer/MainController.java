package application.customer;

import application.admin.main.AdminMainPageController;
import application.admin.start.StartPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
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
            Parent root = FXMLLoader.load(getClass().getResource("/application/customer/root.fxml"));
            StartPage.getPrimaryStage().setScene(new Scene(root));
            StartPage.getPrimaryStage().show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
