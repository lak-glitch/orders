package dashboard;

import customers.Customer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import orders.OrdersInformation;


// display order information in table view at dashboard scene
public class SetOrderInformation {
    public void display(TableColumn<OrdersInformation, String> customerNameInfo, TableColumn<OrdersInformation, String> customerPhone,
                        TableColumn<OrdersInformation, String>productNameView, TableColumn<OrdersInformation, String> productNumberView,
                        TableColumn<OrdersInformation, String> priceEachProductView, TableColumn<OrdersInformation, String> orderDateView,
                        TableView<OrdersInformation> orderInformationView, ObservableList<OrdersInformation> ordersInformationTable) {

        customerNameInfo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer().getCustomerName()));
        customerPhone.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer().getCustomerPhoneNum()));
        productNameView.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getProductName()));
        productNumberView.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getProduct().getProductCost())));
        priceEachProductView.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getProduct().getQuantity())));
        orderDateView.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        orderInformationView.setItems(ordersInformationTable);
    }
}
