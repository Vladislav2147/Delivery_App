package by.bstu.vs.stpms.courier_application.model.retrofit

import android.content.Context
import by.bstu.vs.stpms.courier_application.model.retrofit.cookie.AddCookiesInterceptor
import by.bstu.vs.stpms.courier_application.model.retrofit.cookie.CourierCookie
import by.bstu.vs.stpms.courier_application.model.retrofit.cookie.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson

class NetworkService(private val context: Context) {

    private val BASE_URL = "http://10.0.2.2:8080/"


    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor { chain ->
                chain.proceed(
                        chain.request()
                                .newBuilder()
                                .header("User-Agent", "mobile")
                                .build()
                )
            }
            .build()

    private val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    fun loginService(): UserService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(UserService::class.java)
    }
}