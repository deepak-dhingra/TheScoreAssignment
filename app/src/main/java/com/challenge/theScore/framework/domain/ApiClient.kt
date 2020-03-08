package com.challenge.theScore.framework.domain

import android.app.Application
import com.challenge.theScore.BuildConfig
import com.challenge.theScore.utils.hasNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor(application: Application) {


    companion object {
        @Volatile
        private var INSTANCE: ApiClient? = null

        /**
         * Initializing INSTANCE of this class
         */
        fun getInstance(application: Application): ApiClient {
            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: ApiClient(application).also {
                    INSTANCE = it
                }
            }
        }
    }

    /**
     * Size of the Cache
     */
    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val appCache = Cache(application.cacheDir, cacheSize)

    /**
     * Initializing OkHttpClient
     */
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(appCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(application))
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            chain.proceed(request)
        }
        .build()

    /**
     * Function for initializing Retrofit client
     */
    fun getClient(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}