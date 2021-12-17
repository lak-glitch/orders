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
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CenterController implements Initializable {

    public JFXButton addProductButton;
    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    JFXButton dashboardButton, newOrdersButton, statisticsButton, userButton;
    @FXML
    Label roleLabel;
    boolean isClickedDashboard = false;
    boolean isClickedAddPage = false;
    boolean isClickedUser = false;
    boolean isClickedStatistics = false;
    boolean isClickedAddProduct = false;
    Admin admin = new Admin();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductButton.setVisible(false);
        try {
            admin.getProductList();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                isClickedStatistics = false;
                isClickedAddProduct = false;
            }
        });
        newOrdersButton.setOnAction(event -> {
            if (!isClickedAddPage) {
                initSelectedScene("/gui/AddOrderController.fxml");
                // set not reload to the page after re-click
                isClickedDashboard = false;
                isClickedAddPage = true;
                isClickedUser = false;
                isClickedStatistics = false;
                isClickedAddProduct = false;
            }
        });
        userButton.setOnAction(event -> {
            if (!isClickedUser) {

                // set not reload to the page after re-click
                isClickedDashboard = false;
                isClickedAddPage = false;
                isClickedUser = true;
                isClickedStatistics = false;
                isClickedAddProduct = false;
                if (LoginController.isAdmin) {
                    initSelectedScene("/gui/AdminController.fxml");
                } else if (LoginController.isUser) {
                    initSelectedScene("/gui/UserController.fxml");
                }
            }
        });
        statisticsButton.setOnAction(event -> {
            if (!isClickedStatistics) {
                initSelectedScene("/gui/StatisticsController.fxml");
                isClickedDashboard = false;
                isClickedAddPage = false;
                isClickedUser = false;
                isClickedStatistics = true;
                isClickedAddProduct = false;
            }
        });
        if (LoginController.isAdmin) {
            addProductButton.setVisible(true);
            addProductButton.setOnAction(event -> {
                if (!isClickedAddProduct) {
                    initSelectedScene("/gui/AddFoodForm.fxml");
                    isClickedDashboard = false;
                    isClickedAddPage = false;
                    isClickedUser = false;
                    isClickedStatistics = false;
                    isClickedAddProduct = true;
                }
            });
        }
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
