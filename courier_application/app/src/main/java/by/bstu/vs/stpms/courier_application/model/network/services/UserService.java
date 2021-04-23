package by.bstu.vs.stpms.courier_application.model.network.services;

import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("login")
    Call<UserDto> login(@Field("username") String email, @Field("password") String password, @Field("remember-me") Boolean rememberMe);

    @GET("logout")
    Call<ResponseBody> logout();

    @GET("profile")
    Call<UserDto> currentUser();
}