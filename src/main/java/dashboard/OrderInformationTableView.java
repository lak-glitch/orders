//package dashboard;
//
//import customers.Customer;
//import javafx.collections.FXCollections;
//import javafx.scene.control.cell.PropertyValueFactory;
//import orders.OrdersInformation;
//
//public class OrderInformationTableView {
//    public void test() {
//        Customer customer = new Customer("Nguyen Chi Hien", "0971792638");
//        String productName = "Chicken";
//        float productNumber = 12343289;
//        double priceEachProduct = 128.5;
//        String orderDate = "15/11/2018";
//
//        DashboardController.ordersInformation = FXCollections.observableArrayList(new OrdersInformation(customer,productName,productNumber,priceEachProduct,orderDate));
//        DashboardController.customerNameInfo.setCellValueFactory(new PropertyValueFactory<>("customerName"));
//        DashboardController.customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNum"));
//        DashboardController.productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        DashboardController.productNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
//        DashboardController.priceEachProduct.setCellValueFactory(new PropertyValueFactory<>("priceEachProduct"));
//        DashboardController.orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
//
//        DashboardController.orderInformation.setItems(DashboardController.ordersInformation);
//    }
//}
