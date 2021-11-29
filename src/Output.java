public class Output {
    public void showAllOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showClothesOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listClothesOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showFoodOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listFoodOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showDrinkingOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listDrinkingOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showAppliancesOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listAppliancesOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showExpensiveOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listExpensiveOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
    public void showCheapOrders() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "No",
                "Mã Đơn Hàng",
                "Tên Khách Hàng", "Tên Hàng", "Ngày Đặt", "Ngày Giao", "Giá Trị Đơn Hàng");
        for (Orders s : ListOrders.listCheapOrders) {
            System.out.format("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    n, s.getMaDH(), s.getTenKH(), s.getTenHang(), s.getNgayDat(),s.getNgayGiao(),s.getGiaTriHang());
            n++;
        }
    }
}
