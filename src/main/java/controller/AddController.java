package controller;

import alert.FormatCurrency;
import alert.Notice;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import customers.Admin;
import databaseconnection.GetConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;
import snackbar.SetSnackbar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
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
    //    ObservableList<String> productList = FXCollections.observableArrayList();
    //    ObservableList<Integer> priceList = FXCollections.observableArrayList();
    ResultSet rs = null;
    Long day = null;
    SetSnackbar sn = new SetSnackbar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneTextField.setText("");
        nameTextField.setText("");
        productNameList.setItems(Admin.productList);
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
                priceEachTextField.setText(String.valueOf(Admin.priceList.get(index)));
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


    @FXML
    private void addOrder() throws SQLException, ClassNotFoundException {
        Alert alert = Notice.alertConfirmation("Notice!", "/icons/checked_32px.png");
        alert.setContentText("Do you want to add this order?");
        Optional<ButtonType> ot = alert.showAndWait();
        if (ot.get() == ButtonType.OK) {
            day = ChronoUnit.DAYS.between(orderDate.getValue(), datePicker.getValue());
            if (productNameList.getValue().equals("") || quantityTextField.getText().equals("") || nameTextField.getText().equals("") || phoneTextField.getText().equals("")) {
                snackbar = sn.setSnackbarPane(addPane, "/css/failed-snackbar.css", "Fill all the blank!");
            } else if (day < 0) {
                snackbar = sn.setSnackbarPane(addPane, "/css/failed-snackbar.css", "Your shipping date is invalid!");
            } else {
                snackbar = sn.setSnackbarPane(addPane, "/css/successful-snackbar.css", "Successfully");
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
