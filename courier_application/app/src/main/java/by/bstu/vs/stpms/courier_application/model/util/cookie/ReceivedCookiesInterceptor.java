package by.bstu.vs.stpms.courier_application.model.util.cookie;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import by.bstu.vs.stpms.courier_application.R;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;
    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = (HashSet<String>) context.getSharedPreferences(context.getString(R.string.shared_prefs_cookies), Context.MODE_PRIVATE).getStringSet("PREF_COOKIES", new HashSet<String>());

            for (String header : originalResponse.headers("Set-Cookie")) {
                String headerName = header.substring(0, header.indexOf('='));
                Optional<String> storedHeader = cookies.stream().filter(str -> str.startsWith(headerName)).findFirst();
                storedHeader.ifPresent(cookies::remove);
                cookies.add(header);
            }

            SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.shared_prefs_cookies), Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.putStringSet("PREF_COOKIES", cookies).apply();
            editor.commit();
        }

        return originalResponse;
    }
}