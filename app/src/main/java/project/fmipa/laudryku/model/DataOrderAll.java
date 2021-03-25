package project.fmipa.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class DataOrderAll {

    @SerializedName("total_order")
    private int total_order;
    @SerializedName("total_bayar")
    private int total_bayar;
    @SerializedName("total_order_today")
    private int total_order_today;
    @SerializedName("total_bayar_today")
    private int total_bayar_today;

    public DataOrderAll(int total_order, int total_bayar, int total_order_today, int total_bayar_today) {
        this.total_order = total_order;
        this.total_bayar = total_bayar;
        this.total_order_today = total_order_today;
        this.total_bayar_today = total_bayar_today;
    }

    public DataOrderAll() {
        this.total_order = 0;
        this.total_bayar = 0;
        this.total_order_today = 0;
        this.total_bayar_today = 0;
    }

    public int getTotal_order() {
        return total_order;
    }

    public void setTotal_order(int total_order) {
        this.total_order = total_order;
    }

    public int getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(int total_bayar) {
        this.total_bayar = total_bayar;
    }

    public int getTotal_order_today() {
        return total_order_today;
    }

    public void setTotal_order_today(int total_order_today) {
        this.total_order_today = total_order_today;
    }

    public int getTotal_bayar_today() {
        return total_bayar_today;
    }

    public void setTotal_bayar_today(int total_bayar_today) {
        this.total_bayar_today = total_bayar_today;
    }
}
