package databaseconnection;

import controller.LoginController;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import registration.RegistrationForm;

import java.sql.*;
import java.time.LocalDate;

public class SQL {

    public static boolean isSignup = false;

    PreparedStatement statement = null;
    Connection connection = null;
    ResultSet rs = null;
    ResultSet rm = null;
    ResultSet rt = null;
    PreparedStatement psCheckUserExist = null;
    PreparedStatement psCheckEmailExist = null;

    public SQL() {
    }

    public void signupUser(RegistrationForm registrationForm) throws SQLException {
        try {
            connection = GetConnection.getConnection();
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


    // update information for user
    public void updateInformation(String name, String email, String phone, String birthday) throws ClassNotFoundException, SQLException {
        connection = GetConnection.getConnection();
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
        connection = GetConnection.getConnection();
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


}

