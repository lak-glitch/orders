package controller;

import alert.Notice;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToggleButton;
import dashboard.SetOrderInformation;
import databaseconnection.GetConnection;
import databaseconnection.GetQueryOrderInformation;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import orders.OrderList;
import orders.OrdersInformation;
import snackbar.SetSnackbar;
import style.Style;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public static Alert CONFIRMATION;
    public static boolean IS_LOAD = false;
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
    TableColumn<OrdersInformation, String> totalColumn = new TableColumn<>();
    @FXML
    TableColumn<OrdersInformation, String> statusColumn = new TableColumn<>();
    @FXML
    Button allOrdersButton, inProgressButton, shippedButton, canceledButton;
    @FXML
    JFXButton refreshButton, markShippedButton, markCanceledButton;
    @FXML
    TextField searchTextField;
    @FXML
    JFXToggleButton editToggle;
    Style style = new Style();
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    @FXML
    JFXSnackbar snackbar;
    SetSnackbar sn = new SetSnackbar();
    @FXML
    AnchorPane dashboardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getOrderInformation();
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
        setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, OrderList.ordersInformationList);
        searchOrderInformation(OrderList.ordersInformationList);
        allOrdersButton.getStyleClass().add("taskbar-style");
        inProgressButton.getStyleClass().remove("taskbar-style");
        shippedButton.getStyleClass().remove("taskbar-style");
        canceledButton.getStyleClass().remove("taskbar-style");
        refresh();
    }


    // show  order with all status
    public void setAllOrders() {
        allOrdersButton.setOnMouseClicked(mouseEvent -> {
            style.setStyle(allOrdersButton, inProgressButton, shippedButton, canceledButton);
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, OrderList.ordersInformationList);
            searchOrderInformation(OrderList.ordersInformationList);

        });
    }

    // set event for in-progress order button
    public void setInProgressOrders() {
        inProgressButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, OrderList.ordersInformationInProgressList);
            searchOrderInformation(OrderList.ordersInformationInProgressList);
            style.setStyle(inProgressButton, allOrdersButton, shippedButton, canceledButton);
        });
    }

    // set event for shipped order button
    public void setShippedOrders() {
        shippedButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, OrderList.ordersInformationShippedList);
            style.setStyle(shippedButton, allOrdersButton, inProgressButton, canceledButton);
            searchOrderInformation(OrderList.ordersInformationShippedList);

        });
    }

    // set event for canceled order button
    public void setCanceledOrders() {
        canceledButton.setOnMouseClicked(mouseEvent -> {
            searchTextField.setText(null);
            style.setStyle(canceledButton, allOrdersButton, shippedButton, inProgressButton);
            setOrderInformation.display(orderNumberColumn, nameColumn, phoneColumn, productNameColumn, quantityColumn, orderDateColumn, shippingDateColumn, totalColumn, statusColumn, orderInformationView, OrderList.ordersInformationCanceledList);
            searchOrderInformation(OrderList.ordersInformationCanceledList);

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
        allOrders.setText(OrderList.ordersInformationList.size() + "\n" + "Total orders");
    }

    public void setInProgressOrderText() {
        inProgressOrders.setText(OrderList.ordersInformationInProgressList.size() + "\n" + "In-progress orders");
    }

    public void setShippingOrderText() {
        shippingOrders.setText(OrderList.ordersInformationShippedList.size() + "\n" + "Shipped orders");
    }

    public void setCanceledOrderText() {
        canceledOrders.setText(OrderList.ordersInformationCanceledList.size() + "\n" + "Canceled orders");
    }

    // set edit mode
    public void setEditMode() {
        CONFIRMATION.setContentText("Do you want to save changed?");
        Optional<ButtonType> buttonType = CONFIRMATION.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            //            String orderNumber = nameColumn.getCellObservableValue
        }
    }

    // refresh button
    public void refresh() {

        OrderList.ordersInformationInProgressList.clear();
        OrderList.ordersInformationList.clear();
        OrderList.ordersInformationShippedList.clear();
        OrderList.ordersInformationCanceledList.clear();
        try {
            con = GetConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql;
            if (LoginController.isUser) {
                sql = "SELECT orderNumber, customerName, phoneNumber, productName, quantity, orderShippingDate, orderDate, total, status  FROM ordersmanagement.orderdetails WHERE username = ?";
                statement = con.prepareStatement(sql);
                statement.setString(1, LoginController.username);
            } else {
                sql = "SELECT orderNumber, customerName, phoneNumber, productName, quantity, orderShippingDate, orderDate, total, status  FROM ordersmanagement.orderdetails";
                statement = con.prepareStatement(sql);
            }
            GetQueryOrderInformation.getInformationList(statement, rs);
            setAllOrderText();
            setInProgressOrderText();
            setShippingOrderText();
            setCanceledOrderText();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get order information from database
    public void getOrderInformation() throws ClassNotFoundException, SQLException {
        if (!IS_LOAD) {
            con = GetConnection.getConnection();
            String sql;
            if (LoginController.isAdmin) {
                sql = "SELECT * FROM ordersmanagement.orderdetails";
                statement = con.prepareStatement(sql);
            } else if (LoginController.isUser) {
                sql = "SELECT orderNumber, customerName, phoneNumber, productName, quantity, orderShippingDate, orderDate, total, status  FROM ordersmanagement.orderdetails WHERE username = ?";
                statement = con.prepareStatement(sql);
                statement.setString(1, LoginController.username);
            }
            GetQueryOrderInformation.getInformationList(statement, rs);
        }
        IS_LOAD = true;
    }

    public void setEditTable() {
        if (editToggle.isSelected()) {
            orderInformationView.refresh();
            markShippedButton.setVisible(true);
            markCanceledButton.setVisible(true);
        } else {
            orderInformationView.setEditable(false);
            markShippedButton.setVisible(false);
            markCanceledButton.setVisible(false);
        }
    }

    public void setShippedOrder() throws SQLException, ClassNotFoundException {
        showAlert();
        Optional<ButtonType> setEdit;
        setEdit = CONFIRMATION.showAndWait();
        if (setEdit.get() == ButtonType.OK) {
            OrdersInformation od = orderInformationView.getSelectionModel().getSelectedItem();
            if (od == null) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "Choose order to edit!");
                return;
            }
            if (od.getStatus().equals("Canceled")) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "This order is already canceled!");
            } else if (od.getStatus().equals("Shipped")) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "This order is already shipped!");
            } else {
                od.setStatus("Shipped");
                changeToShippedStatus(od);
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/successful-snackbar.css", "Successfully!");
                refresh();
            }
        }
    }

    public void setCanceledOrder() throws SQLException, ClassNotFoundException {
        showAlert();
        Optional<ButtonType> setEdit;
        setEdit = CONFIRMATION.showAndWait();
        if (setEdit.get() == ButtonType.OK) {
            OrdersInformation od = orderInformationView.getSelectionModel().getSelectedItem();
            if (od == null) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "Choose order to edit!");
                return;
            }
            if (od.getStatus().equals("Shipped")) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "Cannot cancel shipped order!");
            } else if (od.getStatus().equals("Canceled")) {
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/failed-snackbar.css", "This order is already canceled!");
            } else {
                od.setStatus("Canceled");
                changeToCancelStatus(od);
                snackbar = sn.setSnackbarAnchorPane(dashboardPane, "/css/successful-snackbar.css", "Successfully!");
                refresh();
            }
        }
    }

    public void showAlert() throws SQLException, ClassNotFoundException {
        CONFIRMATION = Notice.alertConfirmation("Edit", "/icons/edit_property_24px.png");
        CONFIRMATION.setContentText("Do you want to save change?");
    }

    public void changeToShippedStatus(OrdersInformation od) throws SQLException, ClassNotFoundException {
        getSelectedOrder(od);
    }

    public void changeToCancelStatus(OrdersInformation od) throws SQLException, ClassNotFoundException {
        getSelectedOrder(od);
    }

    // choose selected order in database
    private void getSelectedOrder(OrdersInformation od) throws SQLException, ClassNotFoundException {
        con = GetConnection.getConnection();
        String sql = "UPDATE ordersmanagement.orderdetails SET status = ? WHERE customerName = ? AND status = ? AND orderDate = ? AND orderShippingDate = ? AND orderNumber = ? AND productName = ?";
        statement = con.prepareStatement(sql);
        statement.setString(1, od.getStatus());
        statement.setString(2, od.getCustomer().getCustomerName());
        statement.setString(3, "In-progress");
        statement.setString(4, od.getOrderDate());
        statement.setString(5, od.getOrderShippingDate());
        statement.setString(6, od.getOrderNumber());
        statement.setString(7, od.getProduct().getProductName());
        statement.executeUpdate();
    }

}
