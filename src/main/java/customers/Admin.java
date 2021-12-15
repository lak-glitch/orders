package customers;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import databaseconnection.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {
    public static ArrayList<String> name = new ArrayList<>();
    public static ArrayList<String> phoneNumber = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    public void getAllCustomerName() throws SQLException, ClassNotFoundException {
        connection = GetConnection.getConnection();
        String sql = "SELECT customerName, phoneNumber FROM ordersmanagement.orderdetails";
        statement = connection.prepareStatement(sql);
        rs = statement.executeQuery();

        while (rs.next()) {
            if (!name.contains(rs.getString(1))){
                name.add(rs.getString(1));
            }
            if (!phoneNumber.contains(rs.getString(2))) {
                phoneNumber.add(rs.getString(2));
            }
        }
        rs.close();
    }
}
