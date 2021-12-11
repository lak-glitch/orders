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
    public static String username;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button toSignupButton, loginButton;
    ChangeScene changeScene;
    MySQLConnection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScene = new ChangeScene();
        connection = new MySQLConnection();
    }

    //if log in successfully
    public void login(ActionEvent event) throws SQLException, IOException {
        connection.loginUser(usernameTextField.getText(), passwordField.getText());
        if (MySQLConnection.isLogin) {
            username = usernameTextField.getText();

            changeScene.changeScene(event, "/gui/CenterController.fxml");
        }
    }

    //direct user to sign up page
    public void changeToSignup(ActionEvent event) {
        try {
            changeScene.changeScene(event, "/gui/RegistrationForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
