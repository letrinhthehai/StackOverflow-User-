package com.haile.stackoverflowuser.di.application

import com.haile.stackoverflowuser.services.Config
import com.haile.stackoverflowuser.services.StackExchangeServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    fun providerService(retrofit: Retrofit): StackExchangeServices =
        retrofit.create(StackExchangeServices::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Config.READ_TIMEOUT, TimeUnit.MILLISECONDS)

        val logging = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            logging.level = HttpLoggingInterceptor.Level.BASIC
//        }
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor{chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Accept","application/json;charset=utf-t")
                .addHeader("Accept-Language","en")
                .build()
            chain.proceed(newRequest)
        }
        return httpClient.build()
    }
}