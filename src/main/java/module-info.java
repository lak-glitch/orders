module com.example.HappyChoice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires mysql.connector.java;

    exports dashboard;
    opens dashboard to javafx.fxml;
    exports customers;
    opens customers to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports registration;
    opens registration to javafx.fxml;
    exports orders;
    opens orders to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
    exports add.order;
    opens add.order to javafx.fxml;
    exports product;
    opens product to javafx.fxml;
}