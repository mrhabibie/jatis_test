package com.habibie.jatistest.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "292e222a050d03147d0b6a2776155f66"
const val BASE_URL = "https://api.themoviedb.org/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

object MovieDbClient {
    fun getClient(): MovieDbInterface {
        val requestInterceptor = Interceptor { chain ->
            val url =
                chain.request().url().newBuilder().addQueryParameter("api_key", API_KEY).build()

            Log.e("==> URL:", url.toString());

            val request = chain.request().newBuilder().url(url).build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS).build()

        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieDbInterface::class.java)
    }
}