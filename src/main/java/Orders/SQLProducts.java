import java.sql.*;

public class SQLProducts {
    Connection connection;
    PreparedStatement statement;
    public void Connection () throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usingSelect() throws SQLException {
        statement = connection.prepareStatement("select * from tbl_products");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String productName = rs.getString(1);
            String productCode  = rs.getString(2);
            int productCost = rs.getInt(3);
            ProductsInformation p = new ProductsInformation(productName, productCode, productCost);
            ListProject.productsList.add(p);
        }
    }
}
