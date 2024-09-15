package application.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.customer.menu.Option;
import application.customer.menu.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

public class MenuSelectController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            if (product.isEmpty()) {
                initProduct();
                //initCategory();
            }
            if (gridPane != null)
                initProductList();
            if (optionList != null) {
                initCategory();
                printOptionList();

            }

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
    private Label name;
    @FXML
    private Label price;


    private Stage mainStage;
    Stage pop;
    private static List<Product> product = new ArrayList<>();
    private static HBox[] category;

    private void initProduct() {
        String sample = "[{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":2000,\"name\":\"아메리카노\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"}],\"id\":1},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4000,\"name\":\"카페라떼\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"}],\"id\":2},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4500,\"name\":\"카페모카\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"샷 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"}],\"id\":3},{\"out_of_stock\":0,\"category_id\":1,\"stock_count\":17,\"price\":4500,\"name\":\"녹차라떼\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"Hot\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"Ice\",\"option_type_id\":\"1\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"}],\"id\":4},{\"out_of_stock\":0,\"category_id\":2,\"stock_count\":17,\"price\":2500,\"name\":\"복숭아 아이스티\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"펄 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"코코아 젤리 추가\",\"option_type_id\":\"3\"}],\"id\":5},{\"out_of_stock\":0,\"category_id\":3,\"stock_count\":17,\"price\":3500,\"name\":\"청포도 에이드\",\"options\":[{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"작은 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"500\",\"name\":\"큰 컵\",\"option_type_id\":\"2\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"펄 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"300\",\"name\":\"코코아 젤리 추가\",\"option_type_id\":\"3\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 적게\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 중간\",\"option_type_id\":\"4\"},{\"out_of_stock\":\"0\",\"stock_count\":\"9999\",\"price\":\"0\",\"name\":\"얼음 많게\",\"option_type_id\":\"4\"}],\"id\":6},{\"out_of_stock\":0,\"category_id\":4,\"stock_count\":3,\"price\":5000,\"name\":\"초코 케이크\",\"options\":[],\"id\":7}]\n";
        JSONArray products = new JSONArray(sample);
        for (int i = 0; i < products.length(); i++) {
            JSONObject pd = (JSONObject) products.get(i);
            JSONArray options = new JSONArray(pd.getJSONArray("options"));
            List<Option> opt = new ArrayList<>();
            for (int j = 0; j < options.length(); j++) {
                JSONObject optionData = (JSONObject) options.get(j);
                Option option = new Option(
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

    private void initProductList() {
        int count = 0;
        for (int i = 0; (double) i < (double) product.size() / 3.0; i++) {
            for (int j = 0; j < 3; j++) {
                if (count >= product.size())
                    break;
                Product p = product.get(count);

                Group grp = new Group();
                GridPane.setRowIndex(grp, i);
                GridPane.setColumnIndex(grp, j);

                Button button = new Button();
                button.setPrefHeight(140.0);
                button.setPrefWidth(140.0);
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
                Label price = new Label(Integer.toString(p.getPrice()));
                price.setLayoutX(10);
                price.setLayoutY(110);
                price.setPrefHeight(20.0);
                price.setPrefWidth(100.0);
                price.setId("price");

                grp.getChildren().addAll(button, name, price);
                gridPane.getChildren().addAll(grp);

                count++;
            }
        }

    }
    private void initCategory() {
        // category db 를 받음
        String sample = "{\"data\": [{\"priority\":1, \"name\": \"Hot/Ice\" , \"id\": 1 }, {\"priority\":2, \"name\": \"사이즈 변경\", \"id\": 2}, {\"priority\":3, \"name\": \"시럽추가\", \"id\": 3}, {\"priority\":4, \"name\": \"얼음변경\", \"id\": 4}]}";
        JSONObject data = new JSONObject(sample);
        JSONArray datas = data.getJSONArray("data");
        category = new HBox[datas.length() + 4];
        for(int i = 0 ; i < category.length; i++) {
            category[i] = new HBox();
        }
        for(int i = 0 ; i < datas.length(); i++) {
            data = (JSONObject) datas.get(i);
            category[data.getInt("priority")].setId("hbox" + data.getInt("id"));

            Label label = new Label(data.getString("name"));
            //category[data.getInt("priority")].getChildren().addAll(label);
            optionList.getChildren().addAll(label);
            optionList.getChildren().addAll(category[data.getInt("priority")]);
        }

    }
    private void printOptionList() {
//        optionList.getChildren().addAll(category);
        List<Option> opts = product.get(Integer.parseInt(btn.getId().substring(7))).getOptions();
        for (int i = 0; i < opts.size(); i++) {
            Option o = opts.get(i);

            Group grp = new Group();

            Button button = new Button();
            button.setPrefHeight(40.0);

            ImageView img = new ImageView();
            button.setGraphic(img);

            Label name = new Label(o.getName());
            Label price = new Label("+" + o.getPrice() + "원");

            grp.getChildren().addAll(button, name, price);

            HBox hbox = (HBox) optionList.lookup("#hbox" +  o.getOption_type_id());
            hbox.getChildren().addAll(grp);
        }
    }

    @FXML
    private void menuClick(MouseEvent event) {
        btn = (Button) event.getTarget();
        String name = ((Label) btn.getParent().lookup("#name")).getText();
        String price = ((Label) btn.getParent().lookup("#price")).getText();
//        System.out.println(btn.getParent());
//
//        System.out.println(btn.getParent().lookup("#name"));
        mainStage = (Stage) btn.getScene().getWindow().getScene().getWindow();

        pop = new Stage(StageStyle.DECORATED);
        pop.initModality(Modality.WINDOW_MODAL);
        pop.initOwner(mainStage);
        pop.initStyle(StageStyle.UNDECORATED); // 팝업 창 타이틀바 제거
        pop.setWidth(mainStage.getWidth() * 0.6); // 팝업 창 width 설정
        pop.setHeight(mainStage.getHeight() * 0.8); // 팝업 창 height 설정

        try {
            //새로운 스테이지에 레이아웃 불러오기
            FXMLLoader loader = new FXMLLoader(getClass().getResource("optionSelect.fxml"));
            Parent root = loader.load();

            //씬에 추가
            Scene sc = new Scene(root);
            pop.setScene(sc);
            pop.setResizable(false); //팝업창 크기변경

            //보여주기
            MenuSelectController controller = loader.getController();
            //controller.setPop(pop);
            controller.setName(name);
            controller.setPrice(price);
            pop.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeBtnClick() { //close 버튼 눌렀을 때, 팝업창 아예 닫기
        pop = (Stage) closeBtn.getScene().getWindow(); //스테이지 가져오기
        pop.close(); //팝업창 닫기
    }

    public void cartBtnClick() { //cart 버튼 눌렀을 때
        //TODO
        // 장바구니에 메뉴 저장

        pop = (Stage) closeBtn.getScene().getWindow();
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

        if (optionList != null)
            printOptionList();
    }

    public void setName(String str) {
        name.setText(str);
    }

    public void setPrice(String str) {
        price.setText(str);
    }
}
