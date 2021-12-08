package controller;

import databaseconnection.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import registration.RegistrationForm;
import scene.ChangeScene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController implements Initializable {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    static RegistrationForm registrationForm;
    @FXML
    TextField usernameTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    PasswordField passwordField, repeatPasswordField;
    @FXML
    CheckBox checkBox;
    @FXML
    Button registerButton, toSigninButton;
    String username;
    String password;
    String email = null;
    MySQLConnection connection;
    ChangeScene changeScene;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScene = new ChangeScene();
        connection = new MySQLConnection();
    }

    //     get username, password and email from new user
    public void signup(ActionEvent event) {
        username = usernameTextField.getText();
        password = passwordField.getText();
        email = emailTextField.getText();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if(!matcher.find() || username == null || password == null) {
            System.out.println("Invalid Email");
        } else {
            try {
                registrationForm = new RegistrationForm(username, password, email);
                connection.signupUser(registrationForm);
                if (MySQLConnection.isSignup) {
                    changeScene.changeScene(event, "/gui/LoginController.fxml");
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String getUsername() {
        return usernameTextField.getText();
    }
    public void changeToSignin(ActionEvent event) {
        try {
            changeScene.changeScene(event, "/gui/LoginController.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
