open module application.admin {
    requires java.se;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    requires org.json;

    opens application.admin.start to javafx.fxml;
    opens application.admin.main to javafx.fxml;
    exports application.admin;
}