package customers;


public class Customer {
    private String customerName;
    private String customerPhoneNum;

    public Customer() {
    }

    public Customer(String customerName, String customerPhoneNum) {
        this.customerName = customerName;
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getLastName() {
        String[] splitName = this.customerName.split("\\s");
        return splitName[splitName.length-1];
    }
}
