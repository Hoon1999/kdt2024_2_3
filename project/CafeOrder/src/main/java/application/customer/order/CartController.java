package application.customer.order;

import application.customer.menu.Cart;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private VBox vbox;
    private static List<Cart> cart = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!cart.isEmpty()) {
            drawCart();
        }
    }
    private void drawCart() {
        for(Cart prod : cart) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(300.0);

            VBox vbox = new VBox();
            Label label = new Label(prod.name);
            vbox.getChildren().addAll(label);
            for(Cart opt : prod.options) {
                Label optLabel = new Label("    +" + opt.name);
                vbox.getChildren().addAll(optLabel);
            }

            HBox hbox2 = new HBox();
            HBox.setHgrow(hbox2, Priority.ALWAYS);
            hbox2.setAlignment(Pos.BOTTOM_RIGHT);

            Button btnMinus = new Button("-");
            Button btnPlus = new Button("+");
            Label count = new Label(" " + prod.count + " ");
            Label price = new Label("가격: " + prod.price * prod.count);
            btnMinus.setOnMouseClicked(evnet -> {
                if (prod.count > 1) { // count가 0보다 클 때만 감소
                    prod.count--;  // count 감소
                    count.setText(" " + prod.count + " ");  // Label 업데이트
                    price.setText("가격: " + prod.price * prod.count);
                }
            });
            btnPlus.setOnAction(e -> {
                prod.count++;  // count 증가
                count.setText(" " + prod.count + " ");  // Label 업데이트
                price.setText("가격: " + prod.price * prod.count);
            });
            hbox2.getChildren().addAll(btnMinus, count, btnPlus);


            vbox.getChildren().addAll(price);
            this.vbox.getChildren().addAll(hbox);
            hbox.getChildren().addAll(vbox, hbox2);
        }
        vbox.getChildren().addAll();
    }
    public static void addCart(Cart product) {
        cart.add(product);
    }
}
