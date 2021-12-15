package controller;

import alert.SetDateConverter;
import com.jfoenix.controls.JFXToggleButton;
import databaseconnection.GetConnection;
import databaseconnection.SQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Button updateButton;
    public TextField nameTextField, phoneTextField, emailTextField;
    public DatePicker birthdayPicker;
    SQL SQL = new SQL();
    Connection connection;
    PreparedStatement statement;
    @FXML
    AnchorPane userAnchorPane;
    @FXML
    JFXToggleButton toggleEdit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SQL.setInformation(nameTextField, emailTextField, phoneTextField, birthdayPicker);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        SetDateConverter.setFormat(birthdayPicker);

    }

    public void editMode() {
        if (toggleEdit.isSelected()) {
            updateButton.setVisible(true);
            nameTextField.setEditable(true);
            phoneTextField.setEditable(true);
            emailTextField.setEditable(true);
        } else {
            updateButton.setVisible(false);
            nameTextField.setEditable(false);
            phoneTextField.setEditable(false);
            emailTextField.setEditable(false);
        }
    }
    public void setUpdateButton() throws SQLException, ClassNotFoundException {
        getUpdateOrderDetailTable();
        LocalDate date = birthdayPicker.getValue();
        SQL.updateInformation(nameTextField.getText(), emailTextField.getText(), phoneTextField.getText(), String.valueOf(date));
    }

    public void getUpdateOrderDetailTable() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "UPDATE ordersmanagement.orderdetails SET phoneNumber = ?, customerName = ? WHERE username = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, phoneTextField.getText());
        statement.setString(2, nameTextField.getText());
        statement.setString(3, LoginController.username);
        statement.executeUpdate();
    }
}
