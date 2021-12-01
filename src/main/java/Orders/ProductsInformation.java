import java.sql.SQLException;

public class ProductsInformation {
    private String productName;
    private String productCode;
    private long productCost;

    public ProductsInformation() {
    }

    public ProductsInformation(String productName, String productCode, long productCost) {
        this.productName = productName;
        this.productCode = productCode;
        this.productCost = productCost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getProductCost() {
        return productCost;
    }

    public void setProductCost(long productCost) {
        this.productCost = productCost;
    }
}
