package databaseconnection;

import controller.LoginController;
import customers.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import orders.OrdersInformation;
import product.Product;
import registration.RegistrationForm;

import java.sql.*;
import java.time.LocalDate;

public class MySQLConnection {
    public static boolean isLogin = false;
    public static boolean isSignup = false;
    public static boolean isAdmin = false;
    public static boolean isUser = false;
    public static boolean IS_LOAD = false;
    public static ObservableList<OrdersInformation> ordersInformationList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationInProgressList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationShippedList = FXCollections.observableArrayList();
    public static ObservableList<OrdersInformation> ordersInformationCanceledList = FXCollections.observableArrayList();
    public static String customerPhoneNumber;
    PreparedStatement statement = null;
    Connection connection = null;
    ResultSet rs = null;
    ResultSet rm = null;
    ResultSet rt = null;
    PreparedStatement psCheckUserExist = null;
    PreparedStatement psCheckEmailExist = null;
    int userRole;

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private static String formatDateString(String s) {
        String[] splitString = s.split("/");
        return splitString[2] + "/" + splitString[1] + "/" + splitString[0];
    }

    public void signupUser(RegistrationForm registrationForm) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckEmailExist = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            psCheckUserExist.setString(1, registrationForm.getUsername());
            psCheckEmailExist.setString(1, registrationForm.getEmail());
            rs = psCheckUserExist.executeQuery();
            rm = psCheckEmailExist.executeQuery();
            if (rs.isBeforeFirst() || rm.isBeforeFirst()) {
                System.out.println("INVALID!");
            } else {
                String userRole = "2";
                isSignup = true;
                if (registrationForm.getUsername().equals("hiennguyen10x")) {
                    userRole = "1";
                }
                statement = connection.prepareStatement("INSERT INTO users(username, email,password, userRole) VALUES (?,?,?,?)");
                statement.setString(1, registrationForm.getUsername());
                statement.setString(2, registrationForm.getEmail());
                statement.setString(3, registrationForm.getPassWord());
                statement.setString(4, userRole);
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }
    }

    // input log in information of user
    public void loginUser(String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            psCheckUserExist = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            rs = psCheckUserExist.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User is not exist!");
            } else {
                psCheckUserExist = connection.prepareStatement("SELECT username, password, userRole,phoneNumber FROM users");
                rs = psCheckUserExist.executeQuery();
                while (rs.next()) {
                    if (username.equals(rs.getString("username"))) {
                        if (password.equals(rs.getString("password"))) {
                            isLogin = true;
                            if (rs.getString("userRole").equals("1")) {
                                userRole = 1;
                                isAdmin = true;
                            } else {
                                userRole = 2;
                                isUser = true;
                                customerPhoneNumber = rs.getString("phoneNumber");
                            }
                        } else {
                            System.out.println("Wrong password!");
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }
    }

    public void addToProduct(String productName, double productCost, int quantity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            statement = connection.prepareStatement("INSERT INTO products(productName, productCost, quantity) VALUES (?,?,?)");
            statement.setString(1, productName);
            statement.setString(2, String.valueOf(productCost));
            statement.setString(3, String.valueOf(quantity));
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addToOrder(String order, String shippingDate, String orderValue, String orderDate, String productName, String customerName, String status, String phoneNumber, String quantity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            statement = connection.prepareStatement("INSERT INTO orderdetails(orderNumber, customerName, phoneNumber, productName, quantity,  orderShippingDate, orderDate, total, status ) VALUES (?,?,?,?,?,?,?,?,?);");
            statement.setString(1, order);
            statement.setString(2, customerName);
            statement.setString(3, phoneNumber);
            statement.setString(4, productName);
            statement.setString(5, quantity);
            statement.setString(6, orderDate);
            statement.setString(7, shippingDate);
            statement.setString(8, orderValue);
            statement.setString(9, status);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // update information for user
    public void updateInformation(String name, String email, String phone, String birthday) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
        psCheckUserExist = connection.prepareStatement("SELECT username FROM users");
        rt = psCheckUserExist.executeQuery();

        while (rt.next()) {
            String state = "UPDATE users " + " SET name = ? , email=?, phoneNumber = ?, birthday = ?" + "WHERE username = ?";
            PreparedStatement statement1 = connection.prepareStatement(state);
            statement1.setString(1, name);
            statement1.setString(2, email);
            statement1.setString(3, phone);
            statement1.setString(4, birthday);
            statement1.setString(5, LoginController.username);
            statement1.executeUpdate();
            break;

        }
        rt.close();
    }

    // display user information
    public void setInformation(TextField t1, TextField t2, TextField t3, DatePicker date) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM users WHERE username=? ";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
        statement = connection.prepareStatement(sql);
        statement.setString(1, LoginController.username);
        rs = statement.executeQuery();
        while (rs.next()) {
            t2.setText(rs.getString(2));
            t1.setText(rs.getString(5));
            t3.setText(rs.getString(6));
            String setDate = rs.getString(7);
            if (setDate == null) {
                date.setValue(null);
            } else {
                date.setValue(LocalDate.parse(setDate));
            }
        }
        rs.close();
    }

    // get order information from database
    public void getOrderInformation() throws ClassNotFoundException, SQLException {
        if (!IS_LOAD) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            String sql;
            if (isAdmin) {
                sql = "SELECT * FROM orderdetails";
                statement = connection.prepareStatement(sql);
            } else if (isUser) {
                sql = "SELECT * FROM orderdetails WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, LoginController.username);
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                String orderNumber = rs.getString(1);
                String customerName = rs.getString(2);
                String customerPhone = rs.getString(3);
                String productName = rs.getString(4);
                String quantity = rs.getString(5);
                String orderShippingDate = formatDateString(rs.getString(6));
                String orderDate = formatDateString(rs.getString(7));
                int total = Integer.parseInt(rs.getString(8));
                String status = rs.getString(9);
                Customer customer = new Customer(customerName, customerPhone);
                Product product = new Product(productName, total, Integer.parseInt(quantity));
                OrdersInformation ordersInformation = new OrdersInformation(orderNumber, customer, product, orderDate, orderShippingDate, status);
                ordersInformationList.add(ordersInformation);

                switch (status) {
                    // add in-progress order to the list
                    case "In-progress" -> ordersInformationInProgressList.add(ordersInformation);

                    // add shipped order to the list

                    case "Shipped" -> ordersInformationShippedList.add(ordersInformation);


                    // add canceled order to the list
                    case "Canceled" -> ordersInformationCanceledList.add(ordersInformation);
                }
                IS_LOAD = true;
            }
        }
    }
}
