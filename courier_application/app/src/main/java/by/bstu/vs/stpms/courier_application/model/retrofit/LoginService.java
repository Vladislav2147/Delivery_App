package by.bstu.vs.stpms.courier_application.model.retrofit;

import by.bstu.vs.stpms.courier_application.model.entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("username") String email, @Field("password") String password);
}