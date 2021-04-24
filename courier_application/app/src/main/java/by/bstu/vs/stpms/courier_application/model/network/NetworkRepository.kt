package by.bstu.vs.stpms.courier_application.model.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import by.bstu.vs.stpms.courier_application.model.network.api.UserApi
import by.bstu.vs.stpms.courier_application.model.util.cookie.AddCookiesInterceptor
import by.bstu.vs.stpms.courier_application.model.util.cookie.ReceivedCookiesInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkRepository {

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
                .connectTimeout(1500, TimeUnit.MILLISECONDS)
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

    fun userApi(): UserApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.value)
                .build()
                .create(UserApi::class.java)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }
}