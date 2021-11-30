package controller;

import databaseconnection.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import scene.ChangeScene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button toSignupButton;
    ChangeScene changeScene;
    MySQLConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScene = new ChangeScene();
        connection = new MySQLConnection();
    }

    public void login() throws SQLException {
        connection.loginUser(usernameTextField.getText(), passwordField.getText());
    }

    public void changeToSignup(ActionEvent event) {
        try {
            changeScene.changeScene(event, "/gui/RegistrationForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
