package orders;

import customers.Customer;
import product.Product;

public class OrdersInformation {
    private String orderNumber;
    private Customer customer;
    private Product product;
    private String orderDate;
    private String orderShippingDate;
    private String status;

    public OrdersInformation() {
    }

    public OrdersInformation(String orderNumber, Customer customer, Product product, String orderDate, String orderShippingDate, String status) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.product = product;
        this.orderDate = orderDate;
        this.orderShippingDate = orderShippingDate;
        this.status = status;
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

    public String getOrderShippingDate() {
        return orderShippingDate;
    }

    public void setOrderShippingDate(String orderShippingDate) {
        this.orderShippingDate = orderShippingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
