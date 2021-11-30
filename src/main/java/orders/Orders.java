package orders;

import customers.Customer;

public class Orders {
    private String maDH;
    private String tenHang;
    private String ngayDat;
    private String ngayGiao;
    private int giaTriHang;
    private Customer customer;

    public Orders() {
    }

    public Orders(Customer customer, String maDH, String tenHang, String ngayDat, String ngayGiao, int giaTriHang) {
        this.customer = customer;
        this.maDH = maDH;
        this.tenHang = tenHang;
        this.ngayDat = ngayDat;
        this.ngayGiao = ngayGiao;
        this.giaTriHang = giaTriHang;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public int getGiaTriHang() {
        return giaTriHang;
    }

    public void setGiaTriHang(int giaTriHang) {
        this.giaTriHang = giaTriHang;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
