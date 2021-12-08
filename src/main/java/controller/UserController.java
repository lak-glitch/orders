package controller;

import alert.SetDateConverter;
import databaseconnection.MySQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Button updateButton;
    public TextField nameTextField, phoneTextField, emailTextField;
    public DatePicker birthdayPicker;
    MySQLConnection mySQLConnection = new MySQLConnection();
    @FXML
    AnchorPane userAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            mySQLConnection.setInformation(nameTextField, emailTextField, phoneTextField, birthdayPicker);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        SetDateConverter.setFormat(birthdayPicker);
    }

    public void setUpdateButton() throws SQLException, ClassNotFoundException {
        LocalDate date = birthdayPicker.getValue();
        mySQLConnection.updateInformation(nameTextField.getText(), emailTextField.getText(), phoneTextField.getText(), String.valueOf(date));
    }
}
