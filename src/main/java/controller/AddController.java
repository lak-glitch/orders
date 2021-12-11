package controller;

import alert.SetDateConverter;
import com.jfoenix.controls.JFXComboBox;
import databaseconnection.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public DatePicker datePicker;
    public TextField nameTextField;
    public TextField phoneTextField;
    public JFXComboBox<String> productNameList;
    public TextField quantityTextField;
    public TextField dateTextField;
    public TextField productCostTextField;
    public TextField priceEachTextField;
    public Button addButton;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date orderDate = new Date();
    MySQLConnection mySQLConnection;
    ObservableList<String> observableList = FXCollections.observableArrayList("Noodles", "Steamed Rice", "Lobster", "Fried Rice", "Meat", "Vegetables");
    ObservableList<Integer> priceList = FXCollections.observableArrayList(15000, 5000,100000,15000,30000,2000);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mySQLConnection = new MySQLConnection();
        productNameList.setItems(observableList);
        datePicker.setEditable(false);
        SetDateConverter.setFormat(datePicker);
        if (MySQLConnection.isUser) {
            setUserRole();
        }
    }

    public void setAdminRole() {

    }

    public void setUserRole() {
        nameTextField.setText(LoginController.username);
        nameTextField.setEditable(false);
        //        nameTextField.setDisable(true);
        phoneTextField.setText(MySQLConnection.customerPhoneNumber);
        phoneTextField.setEditable(false);
        //        phoneTextField.setDisable(true);
        dateTextField.setText(formatter.format(orderDate));
        //        if (productNameList.getValue().equals("Noodles")) {
        //            productCostTextField.setText("15");
        //        }
        productNameList.setOnAction(mouseEvent -> {
            priceEachTextField.setText(null);
            quantityTextField.setText(null);
            productCostTextField.setText(null);
//            priceEachTextField.setText(productNameList.getValue());
            int index = productNameList.getSelectionModel().getSelectedIndex();
            priceEachTextField.setText(String.valueOf(priceList.get(index)));
        });
        quantityTextField.setOnMouseExited(mouseEvent -> {
            if(quantityTextField.getText() != null) {
                int price = Integer.parseInt(priceEachTextField.getText()) * Integer.parseInt(quantityTextField.getText());
                productCostTextField.setText(String.valueOf(price));
            }
        });
    }
}
