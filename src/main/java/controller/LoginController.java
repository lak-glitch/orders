package controller;

import databaseconnection.GetConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import scene.ChangeScene;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static String username;
    public static boolean isLogin = false;
    public static boolean isAdmin = false;
    public static boolean isUser = false;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button toSignupButton, loginButton;
    ChangeScene changeScene;
    //    SQL connection;
    Connection connection = null;
    PreparedStatement psCheckUserExist = null;
    ResultSet rs = null;
    int userRole;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScene = new ChangeScene();
    }

    //if log in successfully
    public void login(ActionEvent event) throws SQLException, IOException {
        loginUser(usernameTextField.getText(), passwordField.getText());
        if (LoginController.isLogin) {
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

    public void loginUser(String username, String password) throws SQLException {
        try {
            connection = GetConnection.getConnection();
            psCheckUserExist = connection.prepareStatement("SELECT username FROM ordersmanagement.users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            rs = psCheckUserExist.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User is not exist!");
            } else {
                psCheckUserExist = connection.prepareStatement("SELECT username, password, userRole,phoneNumber FROM ordersmanagement.users");
                rs = psCheckUserExist.executeQuery();
                while (rs.next()) {
                    if (username.equals(rs.getString("username"))) {
                        if (password.equals(rs.getString("password"))) {
                            isLogin = true;
                            if (rs.getString("userRole").equals("1")) {
                                userRole = 1;
                                isAdmin = true;
                            } else {
                                userRole = 2;
                                isUser = true;
                            }
                        } else {
                            System.out.println("Wrong password!");
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }
    }

}
