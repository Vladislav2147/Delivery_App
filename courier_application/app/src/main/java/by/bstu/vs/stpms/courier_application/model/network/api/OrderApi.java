package by.bstu.vs.stpms.courier_application.model.network.api;


import java.util.List;

import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {
    @GET("available")
    Call<List<OrderDto>> getAvailableOrders();

    @GET("active")
    Call<List<OrderDto>> getActiveOrders();

    @GET("accept/{id}")
    Call<ResponseBody> acceptOrder(@Path("id") long id);

    @GET("decline/{id}")
    Call<ResponseBody> declineOrder(@Path("id") long id);

    @PUT("updateState")
    Call<ResponseBody> updateState(@Query("id") long id, @Query("state") String newState);
}
