package com.example.adminapp

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiCalling {

    @POST("fcm/send")
    fun send(@HeaderMap header: Map<String,String>, @Body notificationData : NotificationData) : Observable<NotificationModel>

    companion object {
        fun Create(): ApiCalling {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                //cloud messaging api
                .baseUrl("https://fcm.googleapis.com/")
                .build()
            return retrofit.create(ApiCalling::class.java)

        }
    }
}