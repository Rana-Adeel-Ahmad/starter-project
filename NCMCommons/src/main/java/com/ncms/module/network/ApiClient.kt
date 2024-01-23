package com.ncms.module.network

import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object ApiClient {
    private const val TEMP_BASE_URL = "https://apps.ncm.ae/"
    private const val CONTENT_TYPE = "Content-Type"
    private const val CONTENT_TYPE_ = "application/json"
    private const val HEADER_KEY = "forecast-api-key"
    private const val HEADER_KEY_VALUE = "7B5zIqmRGXmrJTFmKa99vcit"
    private const val TIME_OUT: Long = 60


    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface {

        val gson = GsonBuilder()
            .setLenient()
            .create()


        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(TEMP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())


        val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()


        var httpClient: OkHttpClient.Builder =
            OkHttpClient.Builder().connectionSpecs(Collections.singletonList(spec))
        httpClient.addInterceptor(interceptor())

        httpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            var request = original.newBuilder()
                .method(original.method, original.body)
                .header(CONTENT_TYPE, CONTENT_TYPE_)
                .header(HEADER_KEY, HEADER_KEY_VALUE)
                .build()
            chain.proceed(request)
        })
        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )
        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}