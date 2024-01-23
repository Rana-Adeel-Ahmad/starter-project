package com.app.di


import com.app.constants.AppConstants
import com.app.network.ApiClientInterface
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val URL = AppConstants.WebURL.BASE_URL
    private const val CONTENT_TYPE = "Content-Type"
    private const val CONTENT_TYPE_ = "application/json"


    @Singleton
    @Provides
    fun provideRetrofitBuilder(): ApiClientInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())

        val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

        var httpClient: OkHttpClient.Builder =
            OkHttpClient.Builder().connectionSpecs(specs)
        httpClient.addInterceptor(interceptor())

        httpClient.connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)

        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            var request = original.newBuilder()
                .method(original.method, original.body)
                .header(CONTENT_TYPE, CONTENT_TYPE_)
                .build()
            chain.proceed(request)
        })


        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(
            ApiClientInterface::class.java
        )
    }


    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}

