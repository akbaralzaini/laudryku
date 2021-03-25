package project.fmipa.laudryku.rest;

import java.util.List;

import project.fmipa.laudryku.model.AddLaundry;
import project.fmipa.laudryku.model.DataOrderAll;
import project.fmipa.laudryku.model.Order;
import project.fmipa.laudryku.model.UpdateOrder;
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

    @PUT("Order/Update/{id_order}")
    Call<Order> updateStatus(@Path("id_order") int id_order, @Body UpdateOrder updateOrder);

    @GET("Order/getAllOrder/{id}")
    Call<DataOrderAll> getDataOrderAll(@Path("id") int id);


}
