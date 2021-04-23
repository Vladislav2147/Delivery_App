package by.bstu.vs.stpms.courier_application.model.util.cookie;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import by.bstu.vs.stpms.courier_application.R;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    public static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) context.getSharedPreferences(context.getString(R.string.shared_prefs_cookies), Context.MODE_PRIVATE).getStringSet(PREF_COOKIES, new HashSet<String>());

        Request original = chain.request();
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}