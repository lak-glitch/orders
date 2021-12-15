package databaseconnection;

import customers.Customer;
import javafx.collections.ObservableList;
import orders.OrderList;
import orders.OrdersInformation;
import product.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GetQueryOrderInformation {
    public static void getInformationList(PreparedStatement statement, ResultSet rs) throws SQLException {
        rs = statement.executeQuery();
        while (rs.next()) {
            String orderNumber = rs.getString(1);
            String customerName = rs.getString(2);
            String customerPhone = rs.getString(3);
            String productName = rs.getString(4);
            String quantity = rs.getString(5);
            String orderDate = rs.getString(6);
            String orderShippingDate = rs.getString(7);
            int total = Integer.parseInt(rs.getString(8));
            String status = rs.getString(9);
            Customer customer = new Customer(customerName, customerPhone);
            Product product = new Product(productName, total, Integer.parseInt(quantity));
            OrdersInformation ordersInformation = new OrdersInformation(orderNumber, customer, product, orderDate, orderShippingDate, status);
            OrderList.ordersInformationList.add(ordersInformation);
            switch (status) {
                // add in-progress order to the list
                case "In-progress" -> // add shipped order to the list
                        OrderList.ordersInformationInProgressList.add(ordersInformation);

                case "Shipped" -> OrderList.ordersInformationShippedList.add(ordersInformation);


                // add canceled order to the list
                case "Canceled" -> OrderList.ordersInformationCanceledList.add(ordersInformation);
            }
        }
        sort(OrderList.ordersInformationList);
        sort(OrderList.ordersInformationInProgressList);
        sort(OrderList.ordersInformationShippedList);
        sort(OrderList.ordersInformationCanceledList);

    }

    public static void sort(ObservableList<OrdersInformation> ob) {
        ob.sort(Comparator.comparing(o -> o.getCustomer().getLastName()));
        Collections.sort(ob, Comparator.comparing(OrdersInformation::getOrderDate));
    }
}
