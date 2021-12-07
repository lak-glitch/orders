package orders;

import customers.Customer;
import product.Product;

public class OrdersInformation {
    Customer customer;
    Product product;
    private String orderDate;

    public OrdersInformation() {
    }

    public OrdersInformation(Customer customer, Product product, String orderDate) {
        this.customer = customer;
        this.product = product;
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
