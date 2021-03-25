package project.fmipa.laudryku.rest;

import java.util.List;

import project.fmipa.laudryku.model.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotifikasiInterface {

    @GET("Order/getNotifikasi/{id}")
    Call<List<Order>> getNotifikasi(@Path("id") int id_laundry,@Query("date_1") String date);
}
