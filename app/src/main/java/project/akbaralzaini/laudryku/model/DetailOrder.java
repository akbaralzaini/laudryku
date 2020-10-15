package project.akbaralzaini.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class DetailOrder {
    @SerializedName("nama_jenis")
    private String nama_jenis;
    @SerializedName("lama_waktu")
    private int lama_waktu;
    @SerializedName("berat")
    private float berat;
    @SerializedName("total_harga")
    private float total_harga;

    public String getNama_jenis() {
        return nama_jenis;
    }

    public void setNama_jenis(String nama_jenis) {
        this.nama_jenis = nama_jenis;
    }

    public int getLama_waktu() {
        return lama_waktu;
    }

    public void setLama_waktu(int lama_waktu) {
        this.lama_waktu = lama_waktu;
    }

    public float getBerat() {
        return berat;
    }

    public void setBerat(float berat) {
        this.berat = berat;
    }

    public float getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(float total_harga) {
        this.total_harga = total_harga;
    }
}
