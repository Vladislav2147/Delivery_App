package by.bstu.vs.stpms.courier_application.model.network

import android.content.Context
import by.bstu.vs.stpms.courier_application.model.network.util.cookie.AddCookiesInterceptor
import by.bstu.vs.stpms.courier_application.model.network.util.cookie.ReceivedCookiesInterceptor
import by.bstu.vs.stpms.courier_application.model.network.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson

object NetworkService {

    private val BASE_URL = "http://10.0.2.2:8080/"
    lateinit var context: Context

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client = lazy {
        OkHttpClient
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
    }

    private val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    fun loginService(): UserService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.value)
                .build()
                .create(UserService::class.java)
    }
}