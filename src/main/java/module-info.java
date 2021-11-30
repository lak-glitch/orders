module com.example.HappyChoice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires mysql.connector.java;


    exports customers;
    opens customers to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports registration;
    opens registration to javafx.fxml;
}