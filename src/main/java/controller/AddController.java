package controller;

import alert.SetDateConverter;
import databaseconnection.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public DatePicker datePicker;
    public ChoiceBox<String> statusList;
    int count = 0;
    public TextField nameTextField;
    public TextField phoneTextField;
    public TextField productNameTextField;
    public TextField quantityTextField;
    public TextField dateTextField;
    public TextField productCostTextField;
    public Button addButton;
    MySQLConnection mySQLConnection;
    ObservableList<String> observableList = FXCollections.observableArrayList("In progress", "Shipping", "Shipped", "Canceled");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mySQLConnection = new MySQLConnection();
        statusList.setItems(observableList);
        datePicker.setEditable(false);
        SetDateConverter.setFormat(datePicker);
    }


    public void setAddButton() {
        count++;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String name = nameTextField.getText();
        String phoneNumber = phoneTextField.getText();
        String productName = productNameTextField.getText();
        double productCost = Double.parseDouble(productCostTextField.getText());
        int quantity = Integer.parseInt(quantityTextField.getText());
        String total = String.valueOf(productCost*quantity);

        String date = dtf.format(now);
        String order = String.valueOf(count);
        LocalDate shippingDate = datePicker.getValue();
        String status = statusList.getValue();
        mySQLConnection.addToOrder(order, String.valueOf(shippingDate), total, date, productName, name,status,phoneNumber, quantityTextField.getText());
    }
}
