package project.fmipa.laudryku.rest;

import project.fmipa.laudryku.model.LoginUser;
import project.fmipa.laudryku.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiInterface {
    @POST("Authentication/login")
    Call<User>  LoginUser(@Body LoginUser loginUser);

    @POST("User/create")
    Call<User> registerUser(@Body User user);

}
