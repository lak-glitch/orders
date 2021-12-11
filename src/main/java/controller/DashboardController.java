package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dashboard.SetOrderInformation;
import databaseconnection.MySQLConnection;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import orders.OrdersInformation;
import style.Style;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    final SetOrderInformation setOrderInformation = new SetOrderInformation();
    public Label allOrders, inProgressOrders, shippingOrders, canceledOrders;
    @FXML
    TableView<OrdersInformation> orderInformationView;
    @FXML
    TableColumn<OrdersInformation, String> orderNumberColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> nameColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> phoneColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> productNameColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> quantityColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> shippingDateColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> orderDateColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, Number> totalColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> statusColumn = new TableColumn<>();
    @FXML
    Button allOrdersButton, inProgressButton, shippedButton, canceledButton;
    @FXML
    TextField searchTextField;
    Style style = new Style();
    MySQLConnection connection = new MySQLConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection.getOrderInformation();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        setAllOrderText();
        setInProgressOrderText();
        setShippingOrderText();
        setCanceledOrderText();
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        setDefault();
        setAllOrders();
        setInProgressOrders();
        setShippedOrders();
        setCanceledOrders();
    }


    //set default
    private void setDefault() {
        setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, MySQLConnection.ordersInformationList);
        searchOrderInformation(MySQLConnection.ordersInformationList);
        allOrdersButton.getStyleClass().add("taskbar-style");
        inProgressButton.getStyleClass().remove("taskbar-style");
        shippedButton.getStyleClass().remove("taskbar-style");
        canceledButton.getStyleClass().remove("taskbar-style");
    }


    // show  order with all status
    public void setAllOrders() {
        allOrdersButton.setOnMouseClicked(mouseEvent -> {
            style.setStyle(allOrdersButton, inProgressButton, shippedButton, canceledButton);
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, MySQLConnection.ordersInformationList);
            searchOrderInformation(MySQLConnection.ordersInformationList);

        });
    }

    // set event for in-progress order button
    public void setInProgressOrders() {
        inProgressButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, MySQLConnection.ordersInformationInProgressList);
            searchOrderInformation(MySQLConnection.ordersInformationInProgressList);
            style.setStyle(inProgressButton, allOrdersButton, shippedButton, canceledButton);
        });
    }

    // set event for shipped order button
    public void setShippedOrders() {
        shippedButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, MySQLConnection.ordersInformationShippedList);
            style.setStyle(shippedButton, allOrdersButton, inProgressButton, canceledButton);
            searchOrderInformation(MySQLConnection.ordersInformationShippedList);

        });
    }

    // set event for canceled order button
    public void setCanceledOrders() {
        canceledButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            style.setStyle(canceledButton, allOrdersButton, shippedButton, inProgressButton);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, MySQLConnection.ordersInformationCanceledList);
            searchOrderInformation(MySQLConnection.ordersInformationCanceledList);

        });
    }

    public void searchOrderInformation(ObservableList<OrdersInformation> ol) {
        final FilteredList<OrdersInformation> filteredData = new FilteredList<>(ol, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> filteredData.setPredicate(ordersInformation -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFiltered = newValue.toLowerCase(Locale.ROOT);

            if (ordersInformation.getCustomer().getCustomerName().toLowerCase(Locale.ROOT).contains(lowerCaseFiltered)) {
                return true;
            } else if (ordersInformation.getOrderDate().toLowerCase(Locale.ROOT).contains(lowerCaseFiltered)) {
                return true;
            } else if (ordersInformation.getOrderNumber().toLowerCase(Locale.ROOT).equals(lowerCaseFiltered)) {
                return true;
            } else {
                return false;
            }
        }));
        SortedList<OrdersInformation> orderData = new SortedList<>(filteredData);
        orderData.comparatorProperty().bind(orderInformationView.comparatorProperty());
        orderInformationView.setItems(orderData);
    }

    // display total orders in database
    public void setAllOrderText() {
        allOrders.setText(MySQLConnection.ordersInformationList.size() + "\n" + "Total orders");
    }
    public void setInProgressOrderText() {
        inProgressOrders.setText(MySQLConnection.ordersInformationInProgressList.size() + "\n" + "In-progress orders");
    }
    public void setShippingOrderText() {
        shippingOrders.setText(MySQLConnection.ordersInformationShippedList.size() + "\n" + "Shipped orders");
    }
    public void setCanceledOrderText() {
        canceledOrders.setText(MySQLConnection.ordersInformationCanceledList.size() + "\n" + "Canceled orders");
    }
}
