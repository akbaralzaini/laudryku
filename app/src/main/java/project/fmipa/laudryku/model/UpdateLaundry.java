package project.fmipa.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class UpdateLaundry {
    @SerializedName("nama_laundry")
    private  String nama_laundry;
    @SerializedName("nama_pemilik")
    private String nama_pemilik;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tlp")
    private String tlp;

    public UpdateLaundry(String nama_laundry, String nama_pemilik, String alamat, String tlp) {
        this.nama_laundry = nama_laundry;
        this.nama_pemilik = nama_pemilik;
        this.alamat = alamat;
        this.tlp = tlp;
    }

    public String getNama_laundry() {
        return nama_laundry;
    }

    public void setNama_laundry(String nama_laundry) {
        this.nama_laundry = nama_laundry;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTlp() {
        return tlp;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }
}
