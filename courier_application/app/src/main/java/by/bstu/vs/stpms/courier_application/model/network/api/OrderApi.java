package by.bstu.vs.stpms.courier_application.model.network.api;


import java.util.List;

import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderApi {
    @GET("available")
    Call<List<OrderDto>> getAvailableOrders();
}
