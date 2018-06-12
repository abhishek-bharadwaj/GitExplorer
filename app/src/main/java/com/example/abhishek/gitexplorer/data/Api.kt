package com.example.abhishek.gitexplorer.data

import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Api {

    private val apiService by lazy {
        Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getPRs() = apiService.getPRs()

    interface ApiService {
        @GET("repos/instacart/Snacks/pulls?state=${State.ALL}")
        fun getPRs(): Single<List<PRData>>
    }
}