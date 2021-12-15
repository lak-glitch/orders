package controller;

import alert.FormatCurrency;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import customers.Admin;
import databaseconnection.GetConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import snackbar.SetSnackbar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public DatePicker datePicker;
    public TextField nameTextField;
    public TextField phoneTextField;
    public JFXComboBox<String> productNameList;
    public TextField quantityTextField;
    public DatePicker orderDate;
    public TextField productCostTextField;
    public TextField priceEachTextField;
    public Button addButton;
    public JFXSnackbar snackbar;
    public Pane addPane;
    public AnchorPane anchor;
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<String> productList = FXCollections.observableArrayList();
    ObservableList<Integer> priceList = FXCollections.observableArrayList();
    ResultSet rs = null;
    Long day = null;
    SetSnackbar sn = new SetSnackbar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameList.setItems(productList);
        datePicker.setEditable(false);
        quantityTextField.setText("0");
        orderDate.setValue(LocalDate.now());
        orderDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfYear() != LocalDate.now().getDayOfYear());
            }
        });
        datePicker.setValue(LocalDate.now());
        try {
            getProductListFromDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (LoginController.isUser) {
            setUserRole();
            try {
                getUserInformation();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setAdminRole();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAdminRole() throws SQLException, ClassNotFoundException {
        TextFields.bindAutoCompletion(nameTextField, Admin.name);
        phoneTextField.setText("");
        nameTextField.setOnKeyTyped(keyEvent -> {
            int index = Admin.name.indexOf(nameTextField.getText());
            System.out.println(index);
            if (index != -1) {
                phoneTextField.setText(Admin.phoneNumber.get(index));
            }
        });
        getStuff();
    }

    public void setUserRole() {
        nameTextField.setEditable(false);
        orderDate.setEditable(false);
        phoneTextField.setEditable(false);
        getStuff();
    }

    //get user's product option and set product price each
    public void getStuff() {
        productNameList.setValue("");
        quantityTextField.setText("");
        productCostTextField.setText("");
        productNameList.setOnAction(mouseEvent -> {
            priceEachTextField.setText("");
            quantityTextField.setText("");
            productCostTextField.setText("");
            if (!productNameList.getValue().equals("") || !quantityTextField.getText().equals("")) {
                int index = productNameList.getSelectionModel().getSelectedIndex();
                priceEachTextField.setText(String.valueOf(priceList.get(index)));
                System.out.println(productNameList.getValue());
                System.out.println(priceEachTextField.getText());
            }
        });

        quantityTextField.setOnMouseExited(mouseEvent -> {
            try {
                int price = Integer.parseInt(priceEachTextField.getText()) * Integer.parseInt(quantityTextField.getText());
                productCostTextField.setText("Total: " + FormatCurrency.formatCurrency(price));
            } catch (NumberFormatException e) {
                System.out.println();
            }
        });
    }

    // get product name and price from database
    public void getProductListFromDatabase() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "SELECT * FROM ordersmanagement.products";
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();

        while (rs.next()) {
            String productName = rs.getString(1);
            int price = (int) Double.parseDouble(rs.getString(2));
            productList.add(productName);
            priceList.add(price);
        }
    }

    @FXML
    private void addOrder() throws SQLException, ClassNotFoundException {
        day = ChronoUnit.DAYS.between(orderDate.getValue(), datePicker.getValue());
        if (productNameList.getValue().equals("") || quantityTextField.getText().equals("")) {
           snackbar =  sn.setSnackbarPane(addPane,"/css/failed-snackbar.css", "Fill all the blank!");
        } else if (day < 0) {
           snackbar =  sn.setSnackbarPane(addPane, "/css/failed-snackbar.css", "Your shipping date is invalid!");
        } else {
            snackbar = sn.setSnackbarPane(addPane,"/css/successful-snackbar.css", "Successfully");
            String orderNumber = getRandomOrderNumber();
            String customerName = nameTextField.getText();
            String customerPhoneNumber = phoneTextField.getText();
            String productName = productNameList.getValue();
            String quantity = quantityTextField.getText();
            String getOrderDate = String.valueOf(orderDate.getValue());
            String shippingDate = String.valueOf(datePicker.getValue());
            String total = String.valueOf(Integer.parseInt(priceEachTextField.getText()) * Integer.parseInt(quantityTextField.getText()));
            String sql = "INSERT INTO ordersmanagement.orderdetails(orderdetails.orderNumber, orderdetails.customerName, orderdetails.phoneNumber, orderdetails.productName, orderdetails.quantity,  orderdetails.orderShippingDate, orderdetails.orderDate, orderdetails.total, orderdetails.username) VALUES (?,?,?,?,?,?,?,?,?);";
            connection = GetConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, orderNumber);
            statement.setString(2, customerName);
            statement.setString(3, customerPhoneNumber);
            statement.setString(4, productName);
            statement.setString(5, quantity);
            statement.setString(6, getOrderDate);
            statement.setString(7, shippingDate);
            statement.setString(8, total);
            statement.setString(9, LoginController.username);
            statement.executeUpdate();
        }
    }

    private void getUserInformation() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "SELECT * FROM ordersmanagement.users where username = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, LoginController.username);
        rs = statement.executeQuery();

        while (rs.next()) {
            nameTextField.setText(rs.getString(5));
            phoneTextField.setText(rs.getString(6));
        }
    }

    private String getRandomOrderNumber() {
        int min = 10;
        int max = 99;
        int orderNumberRandom = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return String.valueOf(orderNumberRandom);
    }

}
