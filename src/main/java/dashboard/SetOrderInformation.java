package dashboard;

import alert.FormatCurrency;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import orders.OrdersInformation;


// display order information in table view at dashboard scene
public class SetOrderInformation {
    public void display(TableColumn<OrdersInformation, String> orderNumber,TableColumn<OrdersInformation, String> customerNameInfo, TableColumn<OrdersInformation, String> customerPhone,
                        TableColumn<OrdersInformation, String>productNameView, TableColumn<OrdersInformation, String> quantityColumn,
                        TableColumn<OrdersInformation, String> orderDateView, TableColumn<OrdersInformation, String> shippingDateColumn,
                        TableColumn<OrdersInformation, String> totalColumn, TableColumn<OrdersInformation, String> statusColumn,
                        TableView<OrdersInformation> orderInformationView, ObservableList<OrdersInformation> ordersInformationTable) {
        orderNumber.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getOrderNumber())));
        customerNameInfo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer().getCustomerName()));
        customerPhone.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCustomer().getCustomerPhoneNum()));
        productNameView.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getProductName()));
        quantityColumn.setCellValueFactory(data->new ReadOnlyStringWrapper(String.valueOf(data.getValue().getProduct().getQuantity())));
        orderDateView.setCellValueFactory(data->new ReadOnlyStringWrapper(data.getValue().getOrderDate()));
        shippingDateColumn.setCellValueFactory(data->new ReadOnlyStringWrapper(data.getValue().getOrderShippingDate()));
        totalColumn.setCellValueFactory(data->new ReadOnlyStringWrapper(FormatCurrency.formatCurrency((int) data.getValue().getProduct().getProductCost())));
        statusColumn.setCellValueFactory(data->new ReadOnlyStringWrapper(data.getValue().getStatus()));
        orderInformationView.setItems(ordersInformationTable);
    }
}
