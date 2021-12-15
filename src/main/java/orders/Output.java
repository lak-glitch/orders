//package orders;
//
//import orders.OrderList;
//
//public class Output {
//    public void showDataProduct() {
//        int n = 1;
//        System.out.format("%-5s %-25s %-15s %-15s\n",
//                "No", "productName",
//                "productCode", "productCost");
//        for (Product p : OrderList.productsList) {
//            System.out.format("%-5s %-25s %-15s %-15s\n",
//                    n, p.getProductName(), p.getProductCode(), p.getProductCost());
//            n++;
//        }
//    }
//
//    public void showDataOrders() {
//        int n = 1;
//        System.out.format("%-5s %-25s %-25s %-25s %-20s %-20s %-20s\n",
//                "No", "customerName",
//                "customerPhoneNum", "productName", "productNumber", "productCost", "OrderDate");
//        for (OrdersInformation o : OrderList.ordersList) {
//            System.out.format("%-5s %-25s %-25s %-25s %-20s %-20s %-20s\n",
//                    n, o.getCustomer().getCustomerName(), o.getCustomer().getCustomerPhoneNum(), o.getProductName(),
//                    o.getProductNumber(), o.getProductNumber() * o.getPriceEachProduct(), o.getOrderDate());
//            n++;
//        }
//    }
//}
