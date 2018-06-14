package com.example.abhishek.gitexplorer.data

import com.google.gson.annotations.SerializedName

class PRData(@SerializedName("url") val url: String,
             @SerializedName("html_url") val htmlUrl: String,
             @SerializedName("diff_url") val diffUrl: String,
             @SerializedName("number") val prNumber: Int,
             @SerializedName("title") val title: String,
             @SerializedName("state") val state: String,
             @SerializedName("user") val user: User,
             @SerializedName("head") val head: Head,
             @SerializedName("created_at") val createdAt: String,
             @SerializedName("updated_at") val updatedAt: String)

class Head(@SerializedName("user") val user: User,
           @SerializedName("repo") val repo: Repo?)

class User(@SerializedName("login") val login: String,
           @SerializedName("id") val id: Long,
           @SerializedName("avatar_url") val avatarUrl: String,
           @SerializedName("html_url") val profileUrl: String,
           @SerializedName("body") val body: String)

class Repo(@SerializedName("id") val id: Long,
           @SerializedName("name") val name: String,
           @SerializedName("html_url") val htmlUrl: String,
           @SerializedName("description") val description: String,
           @SerializedName("owner") val owner: Owner)

class Owner(@SerializedName("id") val id: Long,
            @SerializedName("login") val login: String,
            @SerializedName("avatar_url") val avatarUrl: String,
            @SerializedName("html_url") val htmlUrl: String)