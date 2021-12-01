public class OrdersInformation {
    private String customerName;
    private String customerphoneNum;
    private String productName;
    private float productNumber;
    private float priceEachProduct;
    private String orderDate;

    public OrdersInformation() {
    }

    public OrdersInformation(String customerName, String customerphoneNum, String productName, float productNumber,
                             float priceEachProduct, String orderDate) {
        this.customerName = customerName;
        this.customerphoneNum = customerphoneNum;
        this.productName = productName;
        this.productNumber = productNumber;
        this.priceEachProduct = priceEachProduct;
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerphoneNum() {
        return customerphoneNum;
    }

    public void setCustomerphoneNum(String customerphoneNum) {
        this.customerphoneNum = customerphoneNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(float productNumber) {
        this.productNumber = productNumber;
    }

    public float getPriceEachProduct() {
        return priceEachProduct;
    }

    public void setPriceEachProduct(float priceEachProduct) {
        this.priceEachProduct = priceEachProduct;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
