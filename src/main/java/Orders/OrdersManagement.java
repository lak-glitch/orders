import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OrdersManagement {
    SQLOrders sqlOrders = new SQLOrders();
    Scanner sc = new Scanner(System.in);

    /**
     * them don hang.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void addOrders() throws ClassNotFoundException, SQLException {
        String new_cusName = sc.nextLine();
        String new_phoneNum = sc.nextLine();
        String new_proName = sc.nextLine();
        int new_numberPro = sc.nextInt();
        sc.nextLine();
        int new_priceEachProduct = sc.nextInt();
        int new_proCost = new_numberPro * new_priceEachProduct;
        sc.nextLine();
        String new_date = sc.nextLine();
        OrdersInformation o = new OrdersInformation(new_cusName, new_phoneNum, new_proName, new_numberPro, new_proCost,
                new_date);
        ListProject.ordersList.add(o);
        sqlOrders.Connection1();
        sqlOrders.usingInsert(new_cusName, new_phoneNum, new_proName, new_numberPro, new_priceEachProduct, new_date);
    }

    /**
     * xoa thong tin don hang dua vao ten khach hang
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void removeOrders() throws SQLException, ClassNotFoundException {
        String name_toDelete = sc.nextLine();
        ListProject.ordersList.removeIf(o -> o.getCustomerName().equals(name_toDelete));
        sqlOrders.Connection1();
        sqlOrders.usingDelete(name_toDelete);
    }

    /**
     * chinh sua ten cua nguoi mua hang
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void editCustomerName() throws SQLException, ClassNotFoundException {
        String name_toEdit = sc.nextLine();
        boolean check = false;
        for (OrdersInformation o:ListProject.ordersList) {
            if (o.getCustomerName().contains(name_toEdit)) {
                check = true;
                String new_customer = sc.nextLine();
                o.setCustomerName(new_customer);
                sqlOrders.Connection1();
                sqlOrders.usingUpdate(name_toEdit, new_customer);
            }
        }
        if (!check) System.out.println("Invalid customerName");

    }

    /**
     * tim kiem thong tin don hang dua theo ngay mua hang.
     */
    public void searchOrders() {
        String date_toSearch = sc.nextLine();
        boolean check = false;
        for (OrdersInformation o:ListProject.ordersList) {
            if (o.getOrderDate().equals(date_toSearch)) {
                check = true;
                System.out.println(o.getCustomerName() + "\n" +
                        o.getCustomerphoneNum() + "\n" +
                        o.getProductName() + "\n" +
                        o.getProductNumber() + "\n" +
                        o.getPriceEachProduct() * o.getProductNumber() + "\n" +
                        o.getOrderDate());
            }
        }
        if (!check) System.out.println("Invalid Date");;
    }

    /**
     * su dung ma khuyen mai
     */
    public float usingPromotion() {

        float promotion = 0;
        for (OrdersInformation o:ListProject.ordersList) {
            if (o.getPriceEachProduct() * o.getProductNumber() > 60000) {
                promotion = o.getProductNumber() * o.getPriceEachProduct() * 20/100;
            }
            else if (o.getPriceEachProduct() * o.getProductNumber() < 150000 ) {
                promotion = o.getProductNumber() * o.getPriceEachProduct() * 25/100;
            }
            else {
                promotion = o.getProductNumber() * o.getPriceEachProduct() * 30/100;
            }
        }
        return promotion;
    }

    /**
     * sap xep tang dan theo so tien mua hang.
     */
    public void SortedByProductCost() {
        for (int i = 0; i < ListProject.ordersList.size()-1; i++) {
            for (int j = i+1; j < ListProject.ordersList.size(); j++) {
                OrdersInformation orders1 = ListProject.ordersList.get(i);
                OrdersInformation orders2 = ListProject.ordersList.get(j);
                if (orders1.getPriceEachProduct() * orders1.getProductNumber() > orders2.getPriceEachProduct() *
                orders2.getProductNumber()) {
                    OrdersInformation orders_temp = orders1;
                    ListProject.ordersList.set(i, orders2);
                    ListProject.ordersList.set(j, orders_temp);
                }
            }
        }
    }
}
