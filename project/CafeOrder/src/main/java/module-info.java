module org.example.cafeorder {
    requires java.se;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;

    opens org.example.cafeorder to javafx.fxml;
    exports org.example.cafeorder;
}