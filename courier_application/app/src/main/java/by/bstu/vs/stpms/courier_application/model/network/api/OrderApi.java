package by.bstu.vs.stpms.courier_application.model.network.api;


import java.util.List;

import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("available")
    Call<List<OrderDto>> getAvailableOrders();

    @GET("accept/{id}")
    Call<ResponseBody> acceptOrder(@Path("id") long id);

    @GET("decline/{id}")
    Call<ResponseBody> declineOrder(@Path("id") long id);
}
