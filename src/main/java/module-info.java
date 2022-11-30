module com.example.aatankbattle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.aatankbattle to javafx.fxml;
    exports com.example.aatankbattle;
}