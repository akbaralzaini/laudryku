package project.fmipa.laudryku.rest;

import java.util.List;

import project.fmipa.laudryku.model.DetailLoundry;
import project.fmipa.laudryku.model.DetailOrder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DetailOrderApiInterface {

    @POST("DetailOrder/Create")
    Call<DetailLoundry> createDetail(@Body DetailLoundry listDetail);

    @GET("DetailOrder/index/{id}")
    Call<List<DetailOrder>> getDetail(@Path("id") int id);
}
