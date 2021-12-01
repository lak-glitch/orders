import java.sql.*;
public class SQLOrders {
    Connection conn;
    PreparedStatement statement;
    public void Connection1 () throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordersmanagement", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xu li cau lenh select
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void usingSelect() throws SQLException, ClassNotFoundException {
        try {
            statement = conn.prepareStatement("select * from orderdetails");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String cus_name = rs.getString(1);
                String phone_number = rs.getString(2);
                String pro_name = rs.getString(3);
                int pro_number = rs.getInt(4);
                int pro_cost = rs.getInt(5);
                String date = rs.getString(6);
                OrdersInformation o = new OrdersInformation(cus_name, phone_number, pro_name, pro_number, pro_cost, date);
                ListProject.ordersList.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xu li cau lenh insert
     * @param name
     * @param phone
     * @param proName
     * @param proNum
     * @param proCost
     * @param date
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void usingInsert(String name, String phone, String proName, int proNum, int proCost, String date) throws SQLException, ClassNotFoundException {
        try {
            statement = conn.prepareStatement("insert into orderdetails" +
                    "(`customerName`, `customerPhoneNum`, `productName`, `productNumber`, `productCost`, `OrderDate`)"
                    + "values(?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, proName);
            statement.setInt(4, proNum);
            statement.setLong(5, proCost);
            statement.setString(6, date);
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * xu li cau lenh delete
     * @param name .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    public void usingDelete(String name) throws SQLException, ClassNotFoundException{
        try {
            String sql =  String.format("delete from orderdetails where customerName = \"%s\"", name);
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xu li cau lenh update
     * @param old_name
     * @param new_name
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void usingUpdate(String old_name, String new_name ) throws SQLException, ClassNotFoundException {
        try {
            String sql1 =  String.format("update orderdetails set customerName = \"%s\"", new_name);
            String sql2 =  String.format(" where customerName = \"%s\"", old_name);
            String sql = sql1.concat(sql2);
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
