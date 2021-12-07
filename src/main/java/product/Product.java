package product;

public class Product {
    private String productName;
    private double productCost;
    private int quantity;

    public Product() {
    }

    public Product(String productName, double productCost, int quantity) {
        this.productName = productName;
        this.productCost = productCost;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
