import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        OrdersManagement o = new OrdersManagement();
        SQLOrders sqlOrders = new SQLOrders();
        sqlOrders.Connection1();
        sqlOrders.usingSelect();
        o.usingPromotion();
        o.SortedByProductCost();
        Output o1 = new Output();
        o1.showDataOrders();
        /*SQLProducts sqlProducts = new SQLProducts();
        sqlProducts.Connection();
        sqlProducts.usingSelect();
        Output o = new Output();
        o.showDataProduct();*/
    }
}
