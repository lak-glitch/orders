import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OrdersManagement {
    Scanner sc = new Scanner(System.in);
    public void showItems() {
        System.out.println("Siêu thị bao gồm các sản phẩm sau:\n" +
                "Đồ ăn:\n" + "Trà sữa - 45k\n" + "Cơm gà - 35k\n" +
                "Mì tôm - 30k\n" + "----------------\n" +
                "Đồ uống:\n" + "Trà sữa: 45k\n" +
                "----------------\n" + "Đồ gia dụng:\n" +
                "Nồi cơm - 300k\n" +
                "----------------\n" + "Quần áo:\n" +
                "Áo khoác - 410k\n");
    }
    public void addOrders() throws ClassNotFoundException, SQLException {
        String new_maDH = sc.nextLine();
        String new_tenKH = sc.nextLine();
        String new_tenHang = sc.nextLine();
        String new_ngayDat = sc.nextLine();
        String new_ngayGiao = sc.nextLine();
        int new_giaTri = sc.nextInt();
        sc.nextLine();
        String new_soDT = sc.nextLine();
        Orders new_order = new Orders(new_maDH, new_tenKH, new_tenHang, new_ngayDat, new_ngayGiao, new_giaTri, new_soDT);
        ListOrders.listOrders.add(new_order);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordersmanagement", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into orderdetails" +
                    "(Mã Đơn Hàng, Tên Khách Hàng, Loại Hàng, Ngày Đặt Hàng, Ngày Giao Hàng, Giá Trị Đơn Hàng, Số Điện Thoại Liên Lạc)"
                    + "values(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, new_maDH);
            preparedStatement.setString(2, new_tenKH);
            preparedStatement.setString(3, new_tenHang);
            preparedStatement.setString(4, new_ngayDat);
            preparedStatement.setString(5, new_ngayGiao);
            preparedStatement.setInt(6, new_giaTri);
            preparedStatement.setString(7, new_soDT);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void removeOrders() throws SQLException, ClassNotFoundException {
        String id = sc.nextLine();
        ListOrders.listOrders.removeIf(o->o.getMaDH().equalsIgnoreCase(id));

    }

    public void editOrders() {
        String nameToEdit = sc.nextLine();
        boolean check = false;
        for (Orders o : ListOrders.listOrders) {
            if (o.getTenKH().contains(nameToEdit)) {
                check = true;
                System.out.println("What do you want to change?\n" +
                        "1.Mã Khách Hàng\n" +
                        "2.Tên Khách Hàng\n" + "3.Tên Hàng\n" + "4.Ngày Đặt\n" + "5.Ngày Giao\n" +
                        "6.Giá Trị Đơn Hàng\n" + "7.Số Điện Thoại Liên Lạc\n");
                int n = sc.nextInt();
                sc.nextLine();
                if (n == 1) {
                    String new_maDH = sc.nextLine();
                    o.setMaDH(new_maDH);
                }
                if (n == 2) {
                    String new_tenKH = sc.nextLine();
                    o.setTenKH(new_tenKH);
                }
                if (n == 3) {
                    String new_tenHang = sc.nextLine();
                    o.setTenHang(new_tenHang);
                }
                if (n == 4) {
                    String new_ngayDat = sc.nextLine();
                    o.setNgayDat(new_ngayDat);
                }
                if (n == 5) {
                    String new_ngayGiao = sc.nextLine();
                    o.setNgayGiao(new_ngayGiao);
                }
                if (n == 6) {
                    int new_giaTri = sc.nextInt();
                    o.setGiaTriHang(new_giaTri);
                }
                if (n == 7) {
                    String new_soDT = sc.nextLine();
                    o.setSoDienThoai(new_soDT);
                }
                System.out.println("Successful!");
            }
        }
        if (!check) {
            System.out.println("Sorry! Please check again!");
        }
    }

    public void searchOrders() {
        String idToSearch = sc.nextLine();
        boolean check = false;
        for (Orders o : ListOrders.listOrders) {
            if (o.getMaDH().equalsIgnoreCase(idToSearch)) {
                check = true;
                System.out.println("Thông tin khách hàng:\n" + "Mã Đơn hàng: " + o.getMaDH() + "\n" +
                        "Tên khách: " + o.getTenKH() + "\n" +
                        "Tên hàng: " + o.getTenHang() + "\n" +
                        "Ngày đặt hàng: " + o.getNgayDat() + "\n" +
                        "Ngày giao hàng: " + o.getNgayGiao() + "\n" +
                        "Giá trị đơn hàng: " + o.getGiaTriHang() + "\n" +
                        "Số điện thoại: " + o.getSoDienThoai() + "\n");
            }
        }
        if (!check) {
            System.out.println("Sorry. Please check again");
        }
    }
    public void statisticsType() {
        for (Orders o:ListOrders.listOrders) {
            if (o.getTenHang().contains("Đồ ăn")) {
                ListOrders.listFoodOrders.add(o);
            }
            if (o.getTenHang().contains("Đồ uống")) {
                ListOrders.listDrinkingOrders.add(o);
            }
            if (o.getTenHang().contains("Quần áo")) {
                ListOrders.listClothesOrders.add(o);
            }
            if (o.getTenHang().contains("Gia dụng")) {
                ListOrders.listAppliancesOrders.add(o);
            }
        }
    }

    public void numberOfType() {
        int n = ListOrders.listOrders.size();
        int n1 = ListOrders.listClothesOrders.size();
        int n2 = ListOrders.listDrinkingOrders.size();
        int n3 = ListOrders.listFoodOrders.size();
        int n4 = ListOrders.listAppliancesOrders.size();
        System.out.println("There are " + n + " orders in the market, with\n" +
                 + n1 + " clothes orders,\n" +
                 + n2 + " drinking orders,\n" +
                 + n3 + " food orders,\n" +
                 + n4 + " appliances orders.\n");
    }

    public void statisticsPrices() {
        for (Orders o:ListOrders.listOrders) {
            if (o.getGiaTriHang() > 150000) {
                ListOrders.listExpensiveOrders.add(o);
            }
            else {
                ListOrders.listCheapOrders.add(o);
            }
        }
    }

    public void numberOfPrices() {
        int n = ListOrders.listOrders.size();
        int n1 = ListOrders.listExpensiveOrders.size();
        int n2 = ListOrders.listCheapOrders.size();
        System.out.println("There are " + n + " orders in the market, with\n" +
                n1 + " expensive orders,\n" +
                n2 + " cheap orders.");
    }

    public void sortedByPrices() {
        for (int i = 0; i < ListOrders.listOrders.size()-1; i++) {
            for (int j = i+1; j < ListOrders.listOrders.size(); j++) {
                Orders orders1 = ListOrders.listOrders.get(i);
                Orders orders2 = ListOrders.listOrders.get(j);
                if (orders1.getGiaTriHang() > orders2.getGiaTriHang()) {
                    Orders orders_temp = orders1;
                    ListOrders.listOrders.set(i, orders2);
                    ListOrders.listOrders.set(j, orders_temp);
                }
            }
        }
    }

}
