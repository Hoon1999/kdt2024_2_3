package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Pos; // Pos import 추가

public class CoffeeShopApp extends Application {
	
    private Stage primaryStage;
    private Scene mainScene, paymentScene;
    private Label orderNumberLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Coffee Order System");

        // 메인 화면 구현
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("");
        mainLayout.setAlignment(Pos.CENTER); // 가운데 정렬 추가

        // 배경 이미지 설정
        Image backgroundImage = new Image("file:C:/Users/user/Documents/로고.jpg");
        BackgroundImage myBI = new BackgroundImage(backgroundImage, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(myBI));

        // 사용자 안내 메세지
        Label welcomeLabel = new Label("환영합니다! 주문을 시작하세요.");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // 로그인 및 비회원 주문 버튼 추가
        Button loginButton = new Button("로그인");
        Button guestOrderButton = new Button("비회원 주문");

        loginButton.setOnAction(e -> showPaymentScreen());
        guestOrderButton.setOnAction(e -> showPaymentScreen());

        mainLayout.getChildren().addAll(welcomeLabel, loginButton, guestOrderButton);
        mainScene = new Scene(mainLayout, 420, 420);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showPaymentScreen() {
        // 결제 화면 설계
        VBox paymentLayout = new VBox(20);
        paymentLayout.setStyle("-fx-background-color: #ffffff;");
        paymentLayout.setAlignment(Pos.CENTER); // 가운데 정렬 추가

        // 결제 방법 선택 기능 구현
        Label paymentLabel = new Label("총 결제비용 : "+"  원  입니다.");
        Label paymentLabel2 = new Label("결제 방법을 선택하세요:");
        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton cardButton = new RadioButton("카드");
        RadioButton cashButton = new RadioButton("현금");
        cardButton.setToggleGroup(paymentGroup);
        cashButton.setToggleGroup(paymentGroup);

        // 최종 결제 버튼 구현
        Button finalizeButton = new Button("최종 결제");
        finalizeButton.setOnAction(e -> finalizeOrder());

        paymentLayout.getChildren().addAll(paymentLabel, paymentLabel2, cardButton, cashButton, finalizeButton);
        paymentLayout.setAlignment(Pos.CENTER); // 가운데 정렬 추가
        paymentScene = new Scene(paymentLayout, 400, 300);
        primaryStage.setScene(paymentScene);
    }

    private void finalizeOrder() {
        // 주문 번호 생성 로직 구현
        String orderNumber = "ORD" + System.currentTimeMillis();
        orderNumberLabel = new Label("주문 번호: " + orderNumber);
        orderNumberLabel.setStyle("-fx-font-size: 20px;");

        // 초기 배경화면으로 돌아가기 기능
        VBox orderLayout = new VBox(20);
        orderLayout.setAlignment(Pos.CENTER); // 가운데 정렬 추가
        orderLayout.getChildren().addAll(orderNumberLabel);
        Scene orderScene = new Scene(orderLayout, 400, 300);
        primaryStage.setScene(orderScene);

        // 타이머 설정 (5초 후)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> primaryStage.setScene(mainScene)));
        timeline.play();
    }
}
