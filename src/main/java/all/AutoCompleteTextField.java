package all;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import orders.OrdersInformation;

import java.util.Locale;

public class AutoCompleteTextField {
    public void searchOrderInformation(TextField searchTextField, ObservableList<OrdersInformation> ol, TableView<OrdersInformation> orderInformationView) {
        final FilteredList<OrdersInformation> filteredData = new FilteredList<>(ol, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> filteredData.setPredicate(ordersInformation -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFiltered = newValue.toLowerCase(Locale.ROOT);

            if (ordersInformation.getCustomer().getCustomerName().toLowerCase(Locale.ROOT).contains(lowerCaseFiltered)) {
                return true;
            } else if (ordersInformation.getOrderDate().toLowerCase(Locale.ROOT).contains(lowerCaseFiltered)) {
                return true;
            } else {
                return false;
            }
        }));
        SortedList<OrdersInformation> orderData = new SortedList<>(filteredData);
        orderData.comparatorProperty().bind(orderInformationView.comparatorProperty());
        orderInformationView.setItems(orderData);
    }
}
