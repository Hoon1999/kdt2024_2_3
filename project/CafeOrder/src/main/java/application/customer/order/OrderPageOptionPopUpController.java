package application.customer.order;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import application.customer.menu.Cart;
import application.customer.menu.Option;
import application.customer.menu.Page;
import application.customer.menu.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrderPageOptionPopUpController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            if (product.isEmpty()) {
                initProducts();
                initOptionType();
            }
            if (gridPane != null) {
                gridPane.setPadding(new Insets(0));
                drawProducts();
            }
            if (optionList != null)
                drawOptions();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    private static  Button btn;
    @FXML
    private Button closeBtn;
    @FXML
    private Text optionTxt;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox optionList;
    @FXML
    private Label name; // 상품명. 팝업 메뉴에서 사용되는 변수
    @FXML
    private Label price; // 상품가격. 팝업 메뉴에서 사용되는 변수


    private Stage mainStage;
    Stage pop;
    private static List<Product> product = new ArrayList<>();
    private static VBox preparedOptionList = null;
    private static Map<Integer, Integer> duplicate = new HashMap<>();
    private Map<Integer, ToggleGroup> toggleGroupMap = new HashMap<>();
    private Cart cart;
    private Map<Integer, Cart> selectedOption= new HashMap<>();

    private void initProducts() {
        OrderClient orderClient = OrderClient.getInstance();
        JSONObject data = orderClient.callmenu();
//        String sample = "[{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":2000,\"name\":\"아메리카노\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\",\"id\":1},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\",\"id\":2},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\",\"id\":5},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10}],\"id\":1},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4000,\"name\":\"카페라떼\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\",\"id\":1},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\",\"id\":2},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\",\"id\":5},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10}],\"id\":2},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4500,\"name\":\"카페모카\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\",\"id\":1},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\",\"id\":2},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\",\"id\":5},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10}],\"id\":3},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4500,\"name\":\"녹차라떼\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\",\"id\":1},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\",\"id\":2},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4}],\"id\":4},{\"out_of_stock\":0,\"category_id\":2,\"stock_count\":17,\"price\":2500,\"name\":\"복숭아 아이스티\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"펄 추가\",\"option_type_id\":\"3\",\"id\":6},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"코코아 젤리 추가\",\"option_type_id\":\"3\",\"id\":7}],\"id\":5},{\"out_of_stock\":0,\"category_id\":3,\"stock_count\":17,\"price\":3500,\"name\":\"청포도 에이드\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\",\"id\":3},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\",\"id\":4},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"펄 추가\",\"option_type_id\":\"3\",\"id\":6},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"코코아 젤리 추가\",\"option_type_id\":\"3\",\"id\":7},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\",\"id\":8},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\",\"id\":9},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\",\"id\":10}],\"id\":6},{\"out_of_stock\":0,\"category_id\":4,\"stock_count\":3,\"price\":5000,\"name\":\"초코 케이크\",\"options\":[],\"id\":7}]";
        JSONArray products = data.getJSONArray("data"); // data 에서 product list 를 꺼냅니다.
        for (int i = 0; i < products.length(); i++) {
            JSONObject pd = (JSONObject) products.get(i);
            JSONArray options = new JSONArray(pd.getJSONArray("options"));
            List<Option> opt = new ArrayList<>();
            for (int j = 0; j < options.length(); j++) {
                JSONObject optionData = (JSONObject) options.get(j);
                Option option = new Option(
                        optionData.getInt("id"),
                        optionData.getString("name"),
                        optionData.getInt("price"),
                        optionData.getInt("stock_count"),
                        optionData.getInt("out_of_stock"),
                        optionData.getInt("option_type_id"));
                opt.add(option);
            }
            Product p = new Product(
                    pd.getInt("id"),
                    pd.getInt("category_id"),
                    pd.getString("name"),
                    pd.getInt("price"),
                    pd.getInt("stock_count"),
                    pd.getInt("out_of_stock"),
                    opt);
            product.add(p);
        }
    }

    private void drawProducts() {
        int count = 0;
        for (int i = 0; (double) i < (double) product.size() / 3.0; i++) {
            for (int j = 0; j < 3; j++) {
                if (count >= product.size())
                    break;
                Product p = product.get(count);

                StackPane sp = new StackPane();
                GridPane.setRowIndex(sp, i);
                GridPane.setColumnIndex(sp, j);

                Button button = new Button();
                button.setTextAlignment(TextAlignment.CENTER);
                button.setOnMouseClicked(event -> {
                    menuClick(event);
                });
                button.setId("product" + count);

                ImageView img = new ImageView();
                img.setImage(new Image("https://placehold.co/150x150.png"));
                button.setGraphic(img);

                Label name = new Label(p.getName());
                name.setLayoutX(10);
                name.setLayoutY(90);
                name.setPrefHeight(20.0);
                name.setPrefWidth(100.0);
                name.setId("name");
                name.setMouseTransparent(true);
                Label price = new Label(Integer.toString(p.getPrice()));
                price.setLayoutX(10);
                price.setLayoutY(110);
                price.setPrefHeight(20.0);
                price.setPrefWidth(100.0);
                price.setId("price");
                price.setMouseTransparent(true);

                sp.getChildren().addAll(button, name, price);
                gridPane.getChildren().addAll(sp);

                count++;
            }
        }

    }
    private void initOptionType() {
        // category db 를 받음
//        String sample = "{\"data\": [{\"priority\":1, \"name\": \"Hot/Ice\" , \"id\": 1, \"duplicate\": 0}, {\"priority\":2, \"name\": \"사이즈 변경\", \"id\": 2, \"duplicate\": 0}, {\"priority\":3, \"name\": \"시럽추가\", \"id\": 3, \"duplicate\": 1}, {\"priority\":4, \"name\": \"얼음변경\", \"id\": 4, \"duplicate\": 0}]}";
//        JSONObject data = new JSONObject(sample);
        OrderClient orderClient = OrderClient.getInstance();
        JSONObject data = orderClient.getOptionTypes();
        JSONArray datas = data.getJSONArray("data");
        HBox[] category = new HBox[datas.length() + 4];
        preparedOptionList= new VBox();
        for(int i = 0 ; i < category.length; i++) {
            category[i] = new HBox();
        }
        for(int i = 0 ; i < datas.length(); i++) {
            data = (JSONObject) datas.get(i);
            category[data.getInt("priority")].setId("hbox" + data.getInt("id")); // fx:id 등록 hbox1, hbox2, ...

            Label label = new Label(data.getString("name"));
            preparedOptionList.getChildren().addAll(label); // 레이블 추가. ex) Hot/Ice , 얼음 변경 등
            preparedOptionList.getChildren().addAll(category[data.getInt("priority")]); // HBox 추가
            duplicate.put(data.getInt("id"), data.getInt("duplicate")); // id : 중복가능여부 저장
        }

    }
    private void drawOptions() {
        if(preparedOptionList.getChildren().isEmpty()) {
            initOptionType();
        }
        optionList.getChildren().addAll(preparedOptionList.getChildren()) ; // 미리 준비한 옵션 리스트를 팝업 창의 옵션 목록에 삽입한다.
        List<Option> opts = product.get(Integer.parseInt(btn.getId().substring(7))).getOptions();
        for (int i = 0; i < opts.size(); i++) {
            Option o = opts.get(i);
            StackPane sp = new StackPane();

            ToggleGroup toggleGroup = null;
            if(duplicate.get(o.getOption_type_id()) == 0) {
                // 중복 불가능이면 여기로 온다.
                // 중복 불가능이면 토글 그룹을 만든다.
                // option type id 에 해당하는 toggle group 이 존재하면 가져오고, 없으면 새로 만듬
                toggleGroup = toggleGroupMap.computeIfAbsent(o.getOption_type_id(), key -> new ToggleGroup());
            }

            ToggleButton button = new ToggleButton();
            if(toggleGroup != null) {
                // 토글 그룹이 존재하면 버튼을 토글 그룹에 포함한다.
                button.setToggleGroup(toggleGroup);
            }
            button.selectedProperty().addListener((new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        // 선택될 때 발생하는 이벤트
                        if(selectedOption.get(o.getId()) == null) {
                            Cart c = new Cart();
                            c.id = o.getId();
                            c.name = o.getName();
                            c.price = o.getPrice();
                            selectedOption.put(o.getId(), c);
                        }
                    } else {
                        // 선택 해제될 때 발생하는 이벤트
                        System.out.println("Button Deselected");
                        if(selectedOption.get(o.getId()) != null) {
                            selectedOption.remove(o.getId()); // remove. Key : true
                        }
                    }
                }
            }));
            button.setId("" + o.getId());
            button.setPrefHeight(40.0);

            ImageView img = new ImageView();
            button.setGraphic(img);

            Label name = new Label(o.getName());
            name.setMouseTransparent(true);
            Label price = new Label("+" + o.getPrice() + "원");
            price.setMouseTransparent(true);

            sp.getChildren().addAll(button, name, price);

            HBox hbox = (HBox) optionList.lookup("#hbox" +  o.getOption_type_id());
            hbox.getChildren().addAll(sp);
        }
    }

    @FXML
    private void menuClick(MouseEvent event) {
        btn = (Button) event.getTarget();
        String name = ((Label) btn.getParent().lookup("#name")).getText();
        String price = ((Label) btn.getParent().lookup("#price")).getText();
        mainStage = (Stage) btn.getScene().getWindow().getScene().getWindow();

        pop = new Stage(StageStyle.DECORATED);
        pop.initModality(Modality.WINDOW_MODAL);
        pop.initOwner(mainStage);
        pop.initStyle(StageStyle.UNDECORATED); // 팝업 창 타이틀바 제거
        pop.setWidth(mainStage.getWidth() * 0.6); // 팝업 창 width 설정
        pop.setHeight(mainStage.getHeight() * 0.8); // 팝업 창 height 설정

        try {
            //새로운 스테이지에 레이아웃 불러오기
            FXMLLoader loader = new FXMLLoader(getClass().getResource("optionPopup.fxml"));
            Parent root = loader.load();

            //씬에 추가
            Scene sc = new Scene(root);
            pop.setScene(sc);
            pop.setResizable(false); //팝업창 크기변경

            //보여주기
            OrderPageOptionPopUpController controller = loader.getController();
            //controller.setPop(pop);
            controller.setName(name);
            controller.setPrice(price);
            controller.setMainStage(mainStage);
            pop.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeBtnClick() { //close 버튼 눌렀을 때, 팝업창 아예 닫기
        pop = (Stage) closeBtn.getScene().getWindow(); //스테이지 가져오기
        pop.close(); //팝업창 닫기
    }

    public void cartBtnClick() throws IOException{
        //cart 버튼 눌렀을 때
        //TODO
        // 장바구니에 메뉴 저장
        Product selectedProduct = product.get(Integer.parseInt(btn.getId().substring(7)));
        int sum = 0;
        cart = new Cart();
        cart.id = selectedProduct.getId();
        cart.name = selectedProduct.getName();
        sum = selectedProduct.getPrice();
        cart.count = 1;
        cart.options = new ArrayList<>();
        for(int key: selectedOption.keySet()) {
            cart.options.add(selectedOption.get(key));
            sum += selectedOption.get(key).price;
        }
        cart.price = sum;

        pop = (Stage) closeBtn.getScene().getWindow();
        System.out.println("option - main stage: " + mainStage);
        BorderPane bp = (BorderPane) mainStage.getScene().getRoot();
        CartController.addCart(cart);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/customer/order/cart.fxml"));
        Parent root = loader.load();
        ((CartController)loader.getController()).drawCart(Page.ORDER_PAGE);
        bp.setRight(root);
        pop.close(); //팝업창 닫기
    }

    public void optionSubBtn() {
        int opNum = Integer.parseInt(optionTxt.getText());
        if (opNum <= 0) return;
        opNum--;
        optionTxt.setText("" + opNum + "");
    }

    private int opNumMax = 3;

    public void optionAddBtn() {
        int opNum = Integer.parseInt(optionTxt.getText());
        if (opNum >= opNumMax) return;
        opNum++;
        optionTxt.setText("" + opNum + "");
    }
    public void setPop(Stage pop) {
        this.pop = pop;
    }

    public void setName(String str) {
        name.setText(str);
    }

    public void setPrice(String str) {
        price.setText(str);
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
