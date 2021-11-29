public class Orders {
    private String maDH;
    private String tenKH;
    private String tenHang;
    private String ngayDat;
    private String ngayGiao;
    private int giaTriHang;
    private String soDienThoai;

    public Orders() {
    }

    public Orders(String maDH, String tenKH, String tenHang, String ngayDat, String ngayGiao, int giaTriHang,
                  String soDienThoai) {
        this.maDH = maDH;
        this.tenKH = tenKH;
        this.tenHang = tenHang;
        this.ngayDat = ngayDat;
        this.ngayGiao = ngayGiao;
        this.giaTriHang = giaTriHang;
        this.soDienThoai = soDienThoai;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
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
}
