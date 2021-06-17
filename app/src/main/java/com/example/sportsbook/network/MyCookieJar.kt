package com.example.sportsbook.network

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class MyCookieJar : CookieJar {
    private val cookieStore = mutableMapOf<HttpUrl, List<Cookie>>()

    private val loginUrl = "https://www.sportsbook.ag/cca/customerauthn/pl/login"
    private val allCookies = mutableSetOf<Cookie>()

    /**
     * Store only cookies from the login call
     */
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        allCookies.addAll(cookies)
        Log.e("JIA", "Received ${cookies.size} cookies, total unique: ${allCookies.size}" )
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        Log.e("JIA", "getting cookies for $url,  :: ${allCookies.joinToString(";")}")
        return allCookies.toList()
    }
}