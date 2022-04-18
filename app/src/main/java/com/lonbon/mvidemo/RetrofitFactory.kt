/*
 * Copyright (C),2007-2020, LonBon Technologies Co. Ltd. All Rights Reserved.
 */

package com.lonbon.mvidemo

import android.util.Log
import com.lonbon.mvidemo.ui.DefineApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author liwu
 * @create 20-8-6
 * 描述：
 */
object RetrofitFactory {


    /**
     *
     */
    fun <T> createRetrofitService(
        clazz: Class<T>,
        logLeave: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
        connectTimeout: Long = 20,
        readTimeOut: Long = 20,
        writeTimeOut: Long = 20,
        specialBaseUrl: String = ""
    ): T? {


        //判断网络
        if (!NetWorkUtil.isConnected(DefineApp.appContext!!)) {
            return null
        }


        val builder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)


        builder.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Connection", "close")
                .build()
            chain.proceed(request)
        }

        //是否需要打印log
        val interceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("logMes", message)
                }
            })
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        interceptor.level = logLeave
        builder.addInterceptor(interceptor)


        val client = builder.build()

        val baseUrl =
            if (specialBaseUrl.isEmpty()) "https://www.wanandroid.com/" else specialBaseUrl
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val create = retrofit.create(clazz)

        return create
    }


}
