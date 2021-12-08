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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class MySQLConnection {
    public static boolean isLogin = false;
    public static boolean isSignup = false;
    public static boolean isAdmin = false;
    public static boolean isUser = false;
    PreparedStatement statement = null;
    Connection connection = null;
    ResultSet rs = null;
    ResultSet rm = null;
    ResultSet rt = null;
    PreparedStatement psCheckUserExist = null;
    PreparedStatement psCheckEmailExist = null;
    int userRole;

    public void Connection() throws ClassNotFoundException, SQLException {
        //        Class.forName("com.mysql.cj.jdbc.Driver");
        //        try {
        //            statement = connection.prepareStatement("select * from users");
        //            rs = statement.executeQuery();
        //                        while (rs.next()) {
        //                            String maDH = rs.getString(1);
        //                            String tenKH  = rs.getString(2);
        //                            String tenHang = rs.getString(3);
        //                            String ngayDat = rs.getString(4);
        //                            String ngayGiao = rs.getString(5);
        //                            int giaTriHang = rs.getInt(6);
        //                            String soDT = rs.getString(7);
        //                            Orders o = new Orders(maDH, tenKH, tenHang, ngayDat, ngayGiao, giaTriHang, soDT);
        //                            ListOrders.listOrders.add(o);
        //                        }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        } finally {
        //            assert rs != null;
        //            rs.close();
        //        }
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
                psCheckUserExist = connection.prepareStatement("SELECT username, password, userRole FROM users");
                rs = psCheckUserExist.executeQuery();
                while (rs.next()) {
                    if (username.equals(rs.getString("username"))) {
                        if (password.equals(rs.getString("password"))) {
                            System.out.println("Log in!");
                            isLogin = true;
                            if (rs.getString("userRole").equals("1")) {
                                userRole = 1;
                                isAdmin = true;
                            } else {
                                userRole = 2;
                                isUser = true;
                            }
                            System.out.println(isAdmin);
                            System.out.println(isUser);
                            System.out.println(userRole);
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
    public ObservableList<OrdersInformation> getOrderInformation() throws ClassNotFoundException, SQLException {
        ObservableList<OrdersInformation> ordersInformationList = FXCollections.observableArrayList();
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
        String sql;
        if (isAdmin) {
            sql = "SELECT * FROM orderdetails";
            statement = connection.prepareStatement(sql);
        } else if (isUser) {
            sql = "SELECT * FROM orderdetails WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,LoginController.username);
        }
//
//        sql = "SELECT * FROM orderdetails";
//        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        while (rs.next()) {
            String orderNumber = rs.getString(1);
            String customerName = rs.getString(2);
            String customerPhoneNumber = rs.getString(3);
            String productName = rs.getString(4);
            String quantity = rs.getString(5);
            String orderShippingDate = rs.getString(6);
            String orderDate = rs.getString(7);
            String total = rs.getString(8);
            String status = rs.getString(9);
            Customer customer = new Customer(customerName,customerPhoneNumber);
            Product product = new Product(productName,Double.parseDouble(total),Integer.parseInt(quantity));
            OrdersInformation ordersInformation = new OrdersInformation(orderNumber,customer,product,orderDate,orderShippingDate,status);
            ordersInformationList.add(ordersInformation);
        }
        return ordersInformationList;
    }

//    private String reverseString(String date) {
//        StringBuilder sb = new StringBuilder(date).reverse();
//        return String.valueOf(sb);
//    }
}
