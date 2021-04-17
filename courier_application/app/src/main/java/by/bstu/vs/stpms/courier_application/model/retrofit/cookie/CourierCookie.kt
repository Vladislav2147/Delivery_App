package by.bstu.vs.stpms.courier_application.model.retrofit.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class CourierCookie : CookieJar {
    private var cookies: List<Cookie>? = null
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies ?: ArrayList()
    }
}