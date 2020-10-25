package project.akbaralzaini.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class AddLaundry {
    @SerializedName("id_laundry") private int id_laundry;
    @SerializedName("nama_pemesan") private String nama_pemesan;
    @SerializedName("no_telpon") private String no_telpon;
    @SerializedName("alamat") private String alamat;
    @SerializedName("total_bayar") private float total_bayar;
    @SerializedName("status") private String status;
    @SerializedName("tanggal_masuk") private String tanggal_masuk;
    @SerializedName("tanggal_selesai") private String tanggal_selesai;

    public AddLaundry(int id_laundry, String nama_pemesan, String no_telpon, String alamat, float total_bayar, String status) {
        this.id_laundry = id_laundry;
        this.nama_pemesan = nama_pemesan;
        this.no_telpon = no_telpon;
        this.alamat = alamat;
        this.total_bayar = total_bayar;
        this.status = status;
    }

    public AddLaundry() {
        this.id_laundry = 0;
        this.nama_pemesan = "";
        this.no_telpon = "";
        this.alamat = "";
        this.total_bayar = 0;
        this.status = "";
    }

    public int getId_laundry() {
        return id_laundry;
    }

    public void setId_laundry(int id_laundry) {
        this.id_laundry = id_laundry;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama_pemesan(String nama_pemesan) {
        this.nama_pemesan = nama_pemesan;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public void setNo_telpon(String no_telpon) {
        this.no_telpon = no_telpon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public float getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(float total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }
}
