package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    Button dashboardButton, newOrderButton, statisticsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardButton.setOnAction(event -> initSelectedScene("/gui/DashboardController.fxml"));
        newOrderButton.setOnAction(event -> initSelectedScene("/gui/AddOrderController.fxml"));
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
