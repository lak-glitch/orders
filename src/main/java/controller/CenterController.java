package controller;

import com.jfoenix.controls.JFXButton;
import customers.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CenterController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    JFXButton dashboardButton, newOrdersButton, statisticsButton, userButton;
    @FXML
    Label roleLabel;
    boolean isClickedDashboard = false;
    boolean isClickedAddPage = false;
    boolean isClickedUser = false;
    Admin admin = new Admin();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LoginController.isAdmin) {
            try {
                admin.getAllCustomerName();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            roleLabel.setText("Admin");
        } else {
            roleLabel.setText("Customer");
        }
        dashboardButton.setOnAction(event -> {
            if (!isClickedDashboard) {
                initSelectedScene("/gui/DashboardController.fxml");
                // set not reload to the page after re-click
                isClickedDashboard = true;
                isClickedAddPage = false;
                isClickedUser = false;
            }
        });
        newOrdersButton.setOnAction(event -> {
            if (!isClickedAddPage) {
                initSelectedScene("/gui/AddOrderController.fxml");
                // set not reload to the page after re-click
                isClickedDashboard = false;
                isClickedAddPage = true;
                isClickedUser = false;
            }
        });
        userButton.setOnAction(event -> {
            if (!isClickedUser) {
                initSelectedScene("/gui/UserController.fxml");
                // set not reload to the page after re-click
                isClickedDashboard = false;
                isClickedAddPage = false;
                isClickedUser = true;
            }
        });
    }


    public void initSelectedScene(String path) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
