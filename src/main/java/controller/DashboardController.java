package controller;

import dashboard.SetOrderInformation;
import databaseconnection.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import orders.OrdersInformation;
import style.Style;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    final SetOrderInformation setOrderInformation = new SetOrderInformation();
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
    ObservableList<OrdersInformation> ordersInformationTable;
    @FXML
    Button allOrdersButton, inProgressButton, shippedButton, canceledButton, addButton;
    Style style = new Style();
    MySQLConnection connection = new MySQLConnection();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        try {
            ordersInformationTable = connection.getOrderInformation();
            Collections.sort(ordersInformationTable, Comparator.comparing(o -> o.getCustomer().getLastName()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        setDefault();
        style.setStyle(allOrdersButton, inProgressButton, shippedButton, canceledButton);
        style.setStyle(inProgressButton, allOrdersButton, shippedButton, canceledButton);
        style.setStyle(shippedButton, inProgressButton, allOrdersButton, canceledButton);
        style.setStyle(canceledButton, inProgressButton, shippedButton, allOrdersButton);
        setOrderInformation.display(orderNumberColumn,nameColumn,phoneColumn,productNameColumn,quantityColumn,orderDateColumn,shippingDateColumn,totalColumn,statusColumn, orderInformationView,ordersInformationTable);
//        setEdit();
    }


    //set default for all-order button
    private void setDefault() {
        allOrdersButton.getStyleClass().add("taskbar-style");
        inProgressButton.getStyleClass().remove("taskbar-style");
        shippedButton.getStyleClass().remove("taskbar-style");
        canceledButton.getStyleClass().remove("taskbar-style");
    }

    public void setEdit() {
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        OrdersInformation name = orderInformationView.getSelectionModel().getSelectedItem();
    }

}
