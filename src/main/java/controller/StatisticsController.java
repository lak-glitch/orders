package controller;

import alert.FormatCurrency;
import databaseconnection.GetConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    public Label todayLabel;
    public Label totalLabel;
    public Label dateLabel;
    public Label todayTotalLabel;
    Connection con = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countTodayOrders();
            sumTodayOrders();
            sumAllOrders();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // count all orders that are ordered today
    public void countTodayOrders() throws SQLException, ClassNotFoundException {
        con = GetConnection.getConnection();
        if (LoginController.isAdmin) {
            sql = "SELECT COUNT(orderNumber), SUM(total) FROM ordersmanagement.orderdetails WHERE orderDate = ?";
            statement = con.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
        } else {
            sql = "SELECT COUNT(orderNumber), SUM(total) FROM ordersmanagement.orderdetails WHERE username = ? AND orderDate = ? ";
            statement = con.prepareStatement(sql);
            statement.setString(1, LoginController.username);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
        }
        dateLabel.setText("Today order(s): " + LocalDate.now());
        rs = statement.executeQuery();
        while (rs.next()) {
            String countOrders = rs.getString(1);
            todayLabel.setText(countOrders);
        }
        //        todayLabel.setText();
    }

    //sum total amount of all orders;
    public void sumTodayOrders() throws SQLException, ClassNotFoundException {
        con = GetConnection.getConnection();
        if (LoginController.isAdmin) {
            sql = "SELECT SUM(total) FROM ordersmanagement.orderdetails WHERE orderDate = ?";
            statement = con.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
        } else {
            sql = "SELECT SUM(total) FROM ordersmanagement.orderdetails WHERE username = ? AND orderDate = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, LoginController.username);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
        }
        rs = statement.executeQuery();
        while (rs.next()) {
            int total = rs.getInt(1);
            todayTotalLabel.setText(FormatCurrency.formatCurrency(total));
        }
    }

    public void sumAllOrders() throws SQLException, ClassNotFoundException {
        con = GetConnection.getConnection();

        if (LoginController.isAdmin) {
            sql = "SELECT SUM(total) FROM ordersmanagement.orderdetails";
            statement = con.prepareStatement(sql);
        } else {
            sql = "SELECT SUM(total) FROM ordersmanagement.orderdetails WHERE username = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, LoginController.username);
        }
        rs = statement.executeQuery();
        while (rs.next()) {
            int total = rs.getInt(1);
            totalLabel.setText(FormatCurrency.formatCurrency(total));
        }
    }
}
