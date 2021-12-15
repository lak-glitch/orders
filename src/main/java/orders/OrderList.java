package orders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.Product;

import java.util.ArrayList;

public class OrderList {
    public static ObservableList<OrdersInformation> ordersInformationList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationInProgressList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationShippedList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationCanceledList = FXCollections.observableArrayList();
}
