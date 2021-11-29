import java.sql.*;

public class MySQLConnection {
    public void Connection () throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordersmanagement", "root", "");
            PreparedStatement statement = conn.prepareStatement("select * from orderdetails");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String maDH = rs.getString(1);
                String tenKH  = rs.getString(2);
                String tenHang = rs.getString(3);
                String ngayDat = rs.getString(4);
                String ngayGiao = rs.getString(5);
                int giaTriHang = rs.getInt(6);
                String soDT = rs.getString(7);
                Orders o = new Orders(maDH, tenKH, tenHang, ngayDat, ngayGiao, giaTriHang, soDT);
                ListOrders.listOrders.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
