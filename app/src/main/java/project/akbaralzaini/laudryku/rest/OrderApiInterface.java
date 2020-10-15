package project.akbaralzaini.laudryku.rest;

import java.util.List;

import project.akbaralzaini.laudryku.model.AddLaundry;
import project.akbaralzaini.laudryku.model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiInterface {

    @GET("Order/Index")
    Call<List<Order>> getOrder();

    @GET("Order/getOrder/{id}")
    Call<List<Order>> getOrderby(@Path("id") int id);

    @POST("Order/Create")
    Call<Order> createOrder(@Body AddLaundry order);


}
