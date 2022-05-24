package com.althurdinok.fromandwith.di.module

import android.util.Log
import com.althurdinok.fromandwith.BuildConfig
import com.althurdinok.fromandwith.data.repository.auth.AuthRepository
import com.althurdinok.fromandwith.data.repository.auth.impl.AuthRepositoryImpl
import com.althurdinok.fromandwith.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideAuthRepository(retrofit: Retrofit): AuthRepository = AuthRepositoryImpl(retrofit)

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttp) // OkHttpclient
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava2
            .addConverterFactory(GsonConverterFactory.create()) // Gson
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(Interceptor {
                var newBuilder = it.request().newBuilder()
                newBuilder = newBuilder.addHeader("Content-Type", "application/json")
                it.proceed(newBuilder.build())
            })
            // OkHttp日志拦截器
            builder.addInterceptor(HttpLoggingInterceptor { message ->
                val strLength: Int = message.length
                var start = 0
                var end = 2000
                for (i in 0..99) {
                    // 剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        Log.d("okhttp", message.substring(start, end))
                        start = end
                        end += 2000
                    } else {
                        Log.d("okhttp", message.substring(start, strLength))
                        break
                    }
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }
}