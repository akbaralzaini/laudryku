package project.fmipa.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class DetailLoundry {
//    @SerializedName("id_detail")
//    private int id_detail;
    @SerializedName("id_order")
    private int id_order;
    @SerializedName("berat")
    private float berat;
    @SerializedName("total_harga")
    private float total_harga;
    @SerializedName("id_jenis")
    private int id_jenis;

    public DetailLoundry(float berat, float total_harga, int id_jenis) {
        this.berat = berat;
        this.total_harga = total_harga;
        this.id_jenis = id_jenis;
    }

    public DetailLoundry() {
        this.id_order = 0;
        this.berat = 0;
        this.total_harga = 0;
        this.id_jenis = 0;
    }

//    public int getId_detail() {
//        return id_detail;
//    }
//
//    public void setId_detail(int id_detail) {
//        this.id_detail = id_detail;
//    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
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

    public int getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(int id_jenis) {
        this.id_jenis = id_jenis;
    }
}
