package com.example.abhishek.gitexplorer.data

import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object Api {

    private val apiService by lazy {
        Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getAllRepos(ownerName: String) = apiService.getAllRepos(ownerName)

    fun getPRs(repoFullName: String) = apiService.getPRs(repoFullName)

    interface ApiService {
        @GET("users/{owner}/repos")
        fun getAllRepos(@Path("owner") ownerName: String): Single<List<RepoData>>

        @GET("repos/{repoFullName}/pulls?state=${State.ALL}")
        fun getPRs(@Path("repoFullName") repoFullName: String): Single<List<PRData>>
    }
}