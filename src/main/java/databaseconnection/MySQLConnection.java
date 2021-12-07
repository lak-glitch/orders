package databaseconnection;

import registration.RegistrationForm;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MySQLConnection {
    public static boolean isLogin = false;
    public static boolean isSignup = false;
    PreparedStatement statement = null;
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement psCheckUserExist = null;

    public void Connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            statement = connection.prepareStatement("select * from users");
            rs = statement.executeQuery();
            //            while (rs.next()) {
            //                String maDH = rs.getString(1);
            //                String tenKH  = rs.getString(2);
            //                String tenHang = rs.getString(3);
            //                String ngayDat = rs.getString(4);
            //                String ngayGiao = rs.getString(5);
            //                int giaTriHang = rs.getInt(6);
            //                String soDT = rs.getString(7);
            //                Orders o = new Orders(maDH, tenKH, tenHang, ngayDat, ngayGiao, giaTriHang, soDT);
            //                ListOrders.listOrders.add(o);
            //            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert rs != null;
            rs.close();
        }
    }

    public void signupUser(RegistrationForm registrationForm) throws SQLException {
        //        Class.forName()
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1, registrationForm.getUsername());
            rs = psCheckUserExist.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("INVALID!");
            } else {
                isSignup = true;
                statement = connection.prepareStatement("INSERT INTO users(username, password, email,create_time) VALUES (?,?,?,?)");
                statement.setString(1, registrationForm.getUsername());
                statement.setString(2, registrationForm.getPassWord());
                statement.setString(3, registrationForm.getEmail());
                statement.setString(4, dtf.format(now));
                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }
    }

    // input log in information of user
    public void loginUser(String username, String pasword) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            psCheckUserExist = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            rs = psCheckUserExist.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User is not exist!");
            } else {
                psCheckUserExist = connection.prepareStatement("SELECT username, password FROM users");
                rs = psCheckUserExist.executeQuery();
                while (rs.next()) {
                    if (username.equals(rs.getString("username"))) {
                        if (pasword.equals(rs.getString("password"))) {
                            System.out.println("Log in!");
                            isLogin = true;

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

    public void addToOrder(String order, String shippingDate, String orderValue, String orderDate, String productName, String customerName, String status) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ordersmanagement", "root", "Hien11092002");
            statement = connection.prepareStatement("INSERT INTO orderdetails(orderNumber, orderShippingDate, orderValue, orderDate, productName, customerName, status) VALUES (?,?,?,?,?,?,?);");
            statement.setString(1,order);
            statement.setString(2,shippingDate);
            statement.setString(3,orderValue);
            statement.setString(4,orderDate);
            statement.setString(5,productName);
            statement.setString(6,customerName);
            statement.setString(7,status);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
