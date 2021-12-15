package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import databaseconnection.SQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    TextField usernameTextField,emailTextField;
    @FXML
    PasswordField passwordField, repeatPasswordField;
    @FXML
    JFXRadioButton checkBox;
    @FXML
    Button  toSigninButton;
    @FXML
    JFXButton registerButton;
    String username;
    String password;
    String email = null;
    SQL connection;
    ChangeScene changeScene;
    private final String setMouseEntered = "-fx-background-color: #ff0055;" +
            "-fx-background-radius: 40px;";
    private final String setMouseExited = "-fx-background-color: #2f2f2f;" +
            "-fx-text-fill: #fff;" ;
//            + "-fx-background-radius: 40px";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScene = new ChangeScene();
        connection = new SQL();
        getUsername();
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
                if (SQL.isSignup) {
                    changeScene.changeScene(event, "/gui/LoginController.fxml");
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void getUsername() {
        registerButton.setOnMouseEntered(mouseEvent -> {
            registerButton.setStyle(setMouseEntered);
            registerButton.setText("Sign-up now!");
        });
        registerButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                registerButton.setStyle(setMouseExited);
                registerButton.setText("Register!");
            }
        });
    }
    public void changeToSignin(ActionEvent event) {
        try {
            changeScene.changeScene(event, "/gui/LoginController.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setButtonChecked() {
        registerButton.setDisable(!checkBox.isSelected());
    }

}
