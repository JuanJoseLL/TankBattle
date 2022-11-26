module com.example.aatankbattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aatankbattle to javafx.fxml;
    exports com.example.aatankbattle;
}