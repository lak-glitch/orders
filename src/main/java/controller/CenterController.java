package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CenterController implements Initializable {
    @FXML
    public Tooltip dashboardTooltip;
    @FXML
    AnchorPane mainAchorpane;
    @FXML
    Button dashboardButton, newOrderButton, statisticsButton, userButton;
    boolean isClickedDashboard = false;
    boolean isClickedAddPage = false;
    boolean isClickedUser = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardButton.setOnAction(event -> {
            if (!isClickedDashboard) {
                initSelectedScene("/gui/DashboardController.fxml");
                // set not reload to the page after re-click
                isClickedDashboard = true;
                isClickedAddPage = false;
                isClickedUser = false;
            }
        });
        newOrderButton.setOnAction(event -> {
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
            mainAchorpane.getChildren().clear();
            mainAchorpane.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
