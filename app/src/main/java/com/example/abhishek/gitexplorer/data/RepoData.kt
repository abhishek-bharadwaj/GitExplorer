package com.example.abhishek.gitexplorer.data

import com.google.gson.annotations.SerializedName

class RepoData(@SerializedName("full_name") val fullName: String,
               @SerializedName("name") val name: String,
               @SerializedName("language") val language: String)