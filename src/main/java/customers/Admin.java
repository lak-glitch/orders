package customers;

import databaseconnection.GetConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {
    public static ArrayList<String> name = new ArrayList<>();
    public static ArrayList<String> phoneNumber = new ArrayList<>();
    public static ObservableList<String> productList = FXCollections.observableArrayList();
    public static ObservableList<Integer> priceList = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    // refresh after add or delete food
    public static void refresh() throws SQLException, ClassNotFoundException {
        productList.clear();
        priceList.clear();
        Connection con = GetConnection.getConnection();
        String sql = "SELECT productName, productCost FROM ordersmanagement.products";
        PreparedStatement stm = con.prepareStatement(sql);
        ResultSet rf = stm.executeQuery();

        while (rf.next()) {
            productList.add(rf.getString(1));
            priceList.add(rf.getInt(2));
        }
    }

    public void getAllCustomerName() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "SELECT customerName, phoneNumber FROM ordersmanagement.orderdetails";
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();

        while (rs.next()) {
            if (!name.contains(rs.getString(1))) {
                name.add(rs.getString(1));
            }
            if (!phoneNumber.contains(rs.getString(2))) {
                phoneNumber.add(rs.getString(2));
            }
        }
        rs.close();
    }

    public void getProductList() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "SELECT productName, productCost FROM ordersmanagement.products";
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();

        while (rs.next()) {
            productList.add(rs.getString(1));
            priceList.add(rs.getInt(2));
        }
    }
}
