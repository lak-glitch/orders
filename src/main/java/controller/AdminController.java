package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import scene.ChangeScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public Button addProductButton;
    public Button deleteUserButton;
    public AnchorPane adminSettingPane;
    ChangeScene changeScene = new ChangeScene();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addProduct();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProduct() throws IOException {
        addProductButton.setOnAction(event -> {
            try {
                changeScene.changeScene(event,"/gui/AddFoodForm.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
